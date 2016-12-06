package ru.terra.dms.server.processing.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.terra.dms.server.engine.ObjectsEngine;
import ru.terra.dms.server.jabber.JabberManager;
import ru.terra.dms.server.processing.Processing;
import ru.terra.dms.server.processing.ProcessingTrigger;
import ru.terra.dms.shared.dto.ObjectDTO;
import ru.terraobjects.entity.ObjectFields;
import ru.terraobjects.entity.TObject;
import ru.terraobjects.manager.ObjectsManager;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Processing("TerraFile")
public class DownloadFileTrigger extends ProcessingTrigger {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ObjectsEngine objectsEngine = ObjectsEngine.getInstance();

    @Override
    public void onCreate(Integer objectId) {
        ObjectsManager<TObject> objectsManager = new ObjectsManager<>();
        Map<String, String> fields = objectsManager.getObjectFieldValues(objectId);
        String url = fields.get("url");
        String folder = fields.get("folder");
        Boolean needCheck = Boolean.parseBoolean(fields.get("needcheck"));
        Path targetFile = downloadFile(folder, url);

        if (JabberManager.getInstance().isOk()) {
            JabberManager.getInstance().sendMessage(url);
        }

        if (targetFile != null) {
            TObject object = objectsManager.findById(objectId);
            for (ObjectFields of : object.getObjectFieldsList()) {
                switch (of.getName()) {
                    case "md5": {
                        of.setStrval(doMd5(targetFile));
                    }
                    break;
                    case "filename": {
                        of.setStrval(targetFile.toFile().getAbsolutePath());
                    }
                    break;
                }
            }
            try {
                objectsManager.saveOrUpdate(object);
            } catch (Exception e) {
                logger.error("Unable to update object", e);
            }
        }
        if (needCheck) {
            Map<String, List<ObjectDTO>> ret = new HashMap<>();
            List<ObjectDTO> downloadedFiles = objectsEngine.getByName("TerraFile", -1, -1);
            List<ObjectDTO> toDelete = new ArrayList<>();
            for (ObjectDTO df : downloadedFiles) {
                List<ObjectDTO> hashes = ret.get(df.fields.get("md5"));
                if (hashes == null) {
                    hashes = new ArrayList<>();
                    if (df.fields.get("md5") != null && !df.fields.get("md5").isEmpty())
                        ret.put(df.fields.get("md5"), hashes);
                    else
                        toDelete.add(df);
                }
                hashes.add(df);
            }
            Map<String, List<ObjectDTO>> result = new HashMap<>();
            for (String hash : ret.keySet()) {
                if (ret.get(hash).size() > 1) {
                    result.put(hash, ret.get(hash));
                }
            }

            for (String hash : result.keySet())
                for (int i = 1; i < result.get(hash).size(); i++) {
                    String fn = result.get(hash).get(i).fields.get("filename");
                    if (!fn.isEmpty()) {
                        logger.info("Removing duplicate: " + fn + " : " + new File(fn).delete());
                        logger.info("Deleting object: " + objectsEngine.deleteObject(result.get(hash).get(i).id));
                    }
                }
            for (ObjectDTO o : toDelete)
                objectsEngine.deleteObject(o.id);
        }
    }

    @Override
    public void onUpdate(Integer objectId) {

    }

    @Override
    public void onDelete(Integer objectId) {

    }

    public Path downloadFile(String folder, String url) {
        File f = new File(folder);
        if (!f.exists()) {
            f.mkdirs();
        }
        for (int i = 0; i <= 2; ++i) {
            try {
                URL imageUrl = new URL(url);
                Path path = Paths.get(folder + url.substring(url.lastIndexOf("/")), new String[0]);
                if (!path.toFile().exists())
                    Files.copy(imageUrl.openStream(), path, StandardCopyOption.REPLACE_EXISTING);
                logger.info("Downloaded: " + url);
                return path;
            } catch (Exception e) {
                logger.error("Unable to download: " + url, e);
                continue;
            }
        }
        return null;
    }

    public static String doMd5(Path p) {
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
            e.printStackTrace();
        }
        return "";
    }

    private static class MD5Hash {
        public String name, hash;

        public MD5Hash(ObjectDTO dto) {
            name = dto.fields.get("filename");
            hash = dto.fields.get("md5");
        }
    }
}