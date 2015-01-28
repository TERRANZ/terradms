package ru.terra.dms.desktop.core.service;

import flexjson.JSONSerializer;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.terra.dms.rest.RestService;
import ru.terra.dms.shared.dto.ObjectDTO;
import ru.terra.server.dto.CommonDTO;

import java.util.List;

/**
 * Date: 04.06.14
 * Time: 13:07
 */
public class SendObjectsService extends Service<Boolean> {
    private List<ObjectDTO> objects;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public SendObjectsService(List<ObjectDTO> objects) {
        this.objects = objects;
    }


    @Override
    protected Task<Boolean> createTask() {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                for (ObjectDTO objectDTO : objects)
                    try {
                        String json = new JSONSerializer().exclude("class").deepSerialize(objectDTO);
                        CommonDTO ret = RestService.getInstance().createObjects(json);
                        logger.info("Result: " + ret.status);
                    } catch (Exception e) {
                        logger.error("unable to send object ", e);
                    }
                return true;
            }
        };
    }
}
