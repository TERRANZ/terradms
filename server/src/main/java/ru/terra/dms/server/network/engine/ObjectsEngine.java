package ru.terra.dms.server.network.engine;

import ru.terra.dms.server.network.dto.ObjectDTO;
import ru.terra.dms.server.processing.ProcessingTrigger;
import ru.terra.dms.server.processing.impl.WayBillsProcessingTrigger;
import ru.terraobjects.entity.TObject;
import ru.terraobjects.manager.ObjectsManager;

import java.util.*;

/**
 * Date: 03.06.14
 * Time: 20:59
 */
public class ObjectsEngine {
    private ObjectsManager<TObject> objectsManager = new ObjectsManager<>();
    private List<ProcessingTrigger> triggers = new ArrayList<>();

    public ObjectsEngine() {
        triggers.add(new WayBillsProcessingTrigger());
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        objectsManager.updateObjectFields(newObject.getId(), objectDTO.fields);
        for (ProcessingTrigger trigger : triggers)
            trigger.onCreate(newObject.getId());
    }
}
