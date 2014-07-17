package ru.terra.dms.server.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.terra.dms.server.processing.ProcessingManager;
import ru.terra.dms.server.processing.ProcessingTrigger;
import ru.terra.dms.shared.dto.ObjectDTO;
import ru.terraobjects.entity.ObjectFields;
import ru.terraobjects.entity.TObject;
import ru.terraobjects.entity.controller.exceptions.NonexistentEntityException;
import ru.terraobjects.manager.ObjectsManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        newObject.setParent(objectDTO.parent == null ? 0 : objectDTO.parent);
        newObject.setVersion(0);
        newObject.setObjectFieldsList(new ArrayList<ObjectFields>());

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

    public ObjectDTO getObject(Integer id) {
        TObject tObject = objectsManager.findById(id);
        if (tObject == null)
            return null;
        return convert(tObject);
    }

    public Boolean isExists(Integer id) {
        return objectsManager.getCount(id, "id").intValue() > 0;
    }

    public Boolean deleteObject(Integer id) {
        TObject tObject = objectsManager.findById(id);
        if (tObject == null)
            return false;
        logger.info("Found " + tObject + " for deleting");
        try {
            objectsManager.remove(tObject);
        } catch (NonexistentEntityException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ObjectDTO convert(TObject tObject) {
        ObjectDTO objectDTO = new ObjectDTO();
        objectDTO.id = tObject.getId();
        objectDTO.type = tObject.getName();
        objectDTO.fields = objectsManager.getObjectFieldValues(tObject.getId());
        return objectDTO;
    }

    public List<ObjectDTO> getByName(String name) {
        List<ObjectDTO> data = new ArrayList<>();
        for (TObject tObject : objectsManager.load(name, -1, -1, true))
            data.add(convert(tObject));
        return data;
    }

    public Boolean update(ObjectDTO dto, Integer id) {
        ObjectDTO currentObject = getObject(id);
        if (currentObject == null)
            return false;
        objectsManager.updateObjectFields(id, dto.fields);
        return true;
    }
}
