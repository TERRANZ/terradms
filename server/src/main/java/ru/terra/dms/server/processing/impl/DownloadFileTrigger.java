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
import java.util.List;

@Processing("TerraFile")
public class DownloadFileTrigger extends ProcessingTrigger {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ObjectsEngine objectsEngine = ObjectsEngine.getInstance();

    @Override
    public void onCreate(Integer objectId) {
        ObjectsManager<TObject> objectsManager = new ObjectsManager<>();
        TObject object = objectsManager.findById(objectId);

        String url ="";
        String folder = "";
        Boolean needCheck = false;
        ObjectFields md5field = null;
        for (ObjectFields of : object.getObjectFieldsList()) {
            switch (of.getName()) {
                case "url": {
                    url = of.getStrval();
                }
                break;
                case "folder": {
                    folder = of.getStrval();
                }
                break;
                case "needcheck": {
                    needCheck = Boolean.parseBoolean(of.getStrval());
                }
                break;
                case "md5": {
                    md5field = of;
                }
                break;
            }
        }
        Path targetFile = downloadFile(folder, url);

        if (JabberManager.getInstance().isOk()) {
            JabberManager.getInstance().sendMessage(url);
        }

        String md5 = null;
        if (targetFile != null) {
            md5 = doMd5(targetFile);
            md5field.setStrval(md5);
        }

        if (needCheck && md5 != null) {
            List<ObjectDTO> filesWithSameHash = objectsEngine.getByNameAndFieldValue("TerraFile", "md5", md5);
            if (!filesWithSameHash.isEmpty()) {
                logger.info("Found " + filesWithSameHash.size() + " files with same hash: " + md5);
                for (ObjectDTO o : filesWithSameHash)
                    objectsEngine.deleteObject(o.id);
            }
        }

        try {
            objectsManager.saveTObject(object);
        } catch (Exception e) {
            logger.error("Unable to update object", e);
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