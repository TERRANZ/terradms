package ru.terra.dms.server.processing.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.terra.dms.server.processing.ProcessingTrigger;
import ru.terraobjects.entity.TObject;
import ru.terraobjects.manager.ObjectsManager;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

public class DownloadFileTrigger extends ProcessingTrigger {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onCreate(Integer objectId) {
        ObjectsManager<TObject> objectsManager = new ObjectsManager<>();
        Map<String, String> fields = objectsManager.getObjectFieldValues(objectId);
        String url = fields.get("url");
        String folder = fields.get("folder");
        downloadFile(folder, url);
    }

    @Override
    public void onUpdate(Integer objectId) {

    }

    @Override
    public void onDelete(Integer objectId) {

    }

    public void downloadFile(String folder, String url) {
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
                break;
            } catch (Exception e) {
                logger.error("Unable to download: " + url, e);
                continue;
            }
        }
    }
}