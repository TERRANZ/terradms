package ru.terra.dms.server.network.engine;

import ru.terra.dms.server.network.dto.ObjectDTO;
import ru.terra.dms.desktop.dto.Pair;
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
            objectsManager.saveOrUpdate(newObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> map = new HashMap<>();
        for (Pair<String, Object> pair : objectDTO.fields)
            map.put(pair.key, pair.value);
        objectsManager.updateObjectFields(newObject.getId(), map);

        for (ProcessingTrigger trigger : triggers)
            trigger.onCreate(newObject.getId());
    }
}
