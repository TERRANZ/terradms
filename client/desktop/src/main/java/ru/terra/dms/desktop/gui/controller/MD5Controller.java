package ru.terra.dms.desktop.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import org.controlsfx.dialog.Dialogs;
import ru.terra.dms.desktop.core.service.SendObjectsService;
import ru.terra.dms.desktop.core.viewpart.AbstractWindow;
import ru.terra.dms.shared.dto.ObjectDTO;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

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
                        lvFiles.getItems().add(path.toFile().getName() + "=" + hash);
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
}
