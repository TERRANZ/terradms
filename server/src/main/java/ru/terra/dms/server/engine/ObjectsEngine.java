package ru.terra.dms.server.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.terra.dms.shared.dto.ObjectDTO;
import ru.terra.dms.server.processing.ProcessingManager;
import ru.terra.dms.server.processing.ProcessingTrigger;
import ru.terraobjects.entity.TObject;
import ru.terraobjects.manager.ObjectsManager;

import java.util.ArrayList;
import java.util.Date;

/**
 * Date: 03.06.14
 * Time: 20:59
 */
public class ObjectsEngine {
    private ObjectsManager<TObject> objectsManager = new ObjectsManager<>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ObjectsEngine() {
    }

    public void createObject(ObjectDTO objectDTO) {
        TObject newObject = new TObject();
        newObject.setId(0);
        newObject.setName(objectDTO.type);
        newObject.setUpdated(new Date());
        newObject.setCreated(new Date());
        newObject.setParent(0);
        newObject.setVersion(0);
        newObject.setObjectFieldsList(new ArrayList<>());

        try {
            objectsManager.saveObject(newObject);
            objectsManager.updateObjectFields(newObject.getId(), objectDTO.fields);
            ProcessingTrigger trigger = ProcessingManager.getInstance().getTrigger(newObject.getName());
            if (trigger != null)
                trigger.onCreate(newObject.getId());
        } catch (Exception e) {
            logger.error("Error while persisting new object", e);
        }
    }
}
