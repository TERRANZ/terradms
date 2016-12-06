package ru.terra.dms.desktop.gui.controller;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import org.controlsfx.dialog.Dialogs;
import ru.terra.dms.desktop.core.service.SendObjectsService;
import ru.terra.dms.desktop.core.viewpart.AbstractWindow;
import ru.terra.dms.rest.RestService;
import ru.terra.dms.shared.dto.ObjectDTO;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Date: 09.02.15
 * Time: 18:31
 */
public class MD5Controller extends AbstractWindow {

    @FXML
    public ListView<String> lvFiles;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void scan(ActionEvent actionEvent) {
        try {
            File targetDir = new DirectoryChooser().showDialog(currStage);
            if (targetDir != null) {
                List<ObjectDTO> newObjects = new ArrayList<>();
                Files.walk(Paths.get(targetDir.toURI())).forEach(path -> {
                    if (path.toFile().isFile()) {
                        String hash = doMd5(path);
                        lvFiles.getItems().add(path.toFile().getName() + " = " + hash);
                        ObjectDTO dto = new ObjectDTO();
                        dto.id = 0;
                        dto.type = "MD5Hash";
                        dto.fields = new HashMap<>();
                        dto.fields.put("name", path.toFile().getAbsolutePath());
                        dto.fields.put("hash", hash);
                        newObjects.add(dto);
                    }
                });
                if (newObjects.size() > 0) {
                    SendObjectsService sendObjectsService = new SendObjectsService(newObjects);
                    Dialogs.create().owner(currStage).showWorkerProgress(sendObjectsService);
                    sendObjectsService.reset();
                    sendObjectsService.start();
                    sendObjectsService.setOnSucceeded(workerStateEvent -> newObjects.clear());
                }
            }
        } catch (Exception e) {
            logger.error("Unable to list files", e);
        }
    }

    public String doMd5(Path p) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(p.toFile());

            byte[] dataBytes = new byte[1024];

            int nread = 0;
            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }
            ;
            byte[] mdbytes = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (Exception e) {
            logger.error("Unable to make md5 hash from " + p.toFile().getAbsolutePath(), e);
        }
        return "";
    }

    public void find_doubles(ActionEvent actionEvent) {
        Service<Map<String, List<MD5Hash>>> findDoublesService = new Service<Map<String, List<MD5Hash>>>() {
            @Override
            protected Task<Map<String, List<MD5Hash>>> createTask() {
                return new Task<Map<String, List<MD5Hash>>>() {
                    @Override
                    protected Map<String, List<MD5Hash>> call() throws Exception {
                        Map<String, List<MD5Hash>> ret = new HashMap<>();
                        RestService.getInstance().getObjectsByName("MD5Hash", -1, -1).data.parallelStream().map(MD5Hash::new).forEach(md5 -> {
                            List<MD5Hash> hashes = ret.get(md5.hash);
                            if (hashes == null) {
                                hashes = new ArrayList<>();
                                ret.put(md5.hash, hashes);
                            }
                            hashes.add(md5);
                        });
                        return ret;
                    }
                };
            }
        };
        findDoublesService.reset();
        findDoublesService.start();
        Dialogs.create().owner(currStage).showWorkerProgress(findDoublesService);
        findDoublesService.setOnSucceeded(w -> {
            Map<String, List<MD5Hash>> ret = findDoublesService.getValue();
            Map<String, List<MD5Hash>> result = new HashMap<>();
            ret.keySet().forEach(hash -> {
                if (ret.get(hash).size() > 1) {
                    result.put(hash, ret.get(hash));
                }
            });
            result.keySet().forEach(hash -> {
                lvFiles.getItems().add(hash + " => " + collectHashes(result.get(hash)));
                for (int i = 1; i < result.get(hash).size(); i++)
                    System.out.println(result.get(hash).get(i).name);
            });
        });
    }

    private String collectHashes(List<MD5Hash> hashes) {
        return hashes.stream().map(h -> h.name).collect(Collectors.joining(", "));
    }

    private static class MD5Hash {
        public String name, hash;

        public MD5Hash(ObjectDTO dto) {
            name = dto.fields.get("name");
            hash = dto.fields.get("hash");
        }
    }
}
