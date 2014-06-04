package ru.terra.dms.server.network.engine;

import ru.terra.dms.server.network.dto.ObjectDTO;
import ru.terra.dms.server.processing.ProcessingTrigger;
import ru.terra.dms.server.processing.impl.WayBillsProcessingTrigger;
import ru.terraobjects.entity.TObject;
import ru.terraobjects.manager.ObjectsManager;

import java.util.ArrayList;
import java.util.List;

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
        for (ProcessingTrigger trigger : triggers)
            trigger.onCreate(0);
    }
}
