package ru.terra.dms.server.engine;

import org.apache.log4j.Logger;
import ru.terra.dms.configuration.bean.Pojo;
import ru.terra.dms.server.processing.ProcessingManager;
import ru.terra.dms.server.processing.ProcessingTrigger;
import ru.terra.dms.shared.dto.ObjectDTO;
import ru.terraobjects.entity.ObjectFields;
import ru.terraobjects.entity.TObject;
import ru.terraobjects.entity.controller.exceptions.NonexistentEntityException;
import ru.terraobjects.manager.ObjectsManager;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Date: 03.06.14
 * Time: 20:59
 */
public class ObjectsEngine {
    private ObjectsManager<TObject> objectsManager = new ObjectsManager<>();
    private Logger logger = Logger.getLogger(this.getClass());
    private ExecutorService threadPool = Executors.newFixedThreadPool(20);

    public ObjectsEngine() {
    }

    public void createObject(final ObjectDTO objectDTO, final Pojo pojo) {
        final TObject newObject = new TObject();
        newObject.setId(0);
        newObject.setName(objectDTO.type);
        newObject.setUpdated(new Date());
        newObject.setCreated(new Date());
        newObject.setParent(objectDTO.parent == null ? 0 : objectDTO.parent);
        newObject.setVersion(0);
        newObject.setObjectFieldsList(new ArrayList<ObjectFields>());

        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    objectsManager.createObject(newObject);
                    objectsManager.updateObjectFields(newObject.getId(), convertDtoFields(objectDTO.fields, pojo));
                    for (final ProcessingTrigger trigger : ProcessingManager.getInstance().getTrigger(newObject.getName()))
                        threadPool.submit(new Runnable() {
                            @Override
                            public void run() {
                                trigger.onCreate(newObject.getId());
                            }
                        });
                } catch (Exception e) {
                    logger.error("Error while persisting new object", e);
                }
            }
        });
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

    public Boolean deleteObject(final Integer id) {
        TObject tObject = objectsManager.findById(id);
        if (tObject == null)
            return false;
        logger.info("Found " + tObject + " for deleting");
        for (final ProcessingTrigger trigger : ProcessingManager.getInstance().getTrigger(tObject.getName()))
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    trigger.onDelete(id);
                }
            });
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
        objectDTO.created = tObject.getCreated().getTime();
        objectDTO.updated = tObject.getUpdated().getTime();
        objectDTO.parent = tObject.getParent();
        objectDTO.version = tObject.getVersion();
        return objectDTO;
    }

    public List<ObjectDTO> getByName(String name) {
        List<ObjectDTO> data = new ArrayList<>();
        for (TObject tObject : objectsManager.load(name, -1, -1, true))
            data.add(convert(tObject));
        return data;
    }


    public List<ObjectDTO> getByParent(Integer parent) {
        List<ObjectDTO> data = new ArrayList<>();
        for (TObject tObject : objectsManager.load(parent, -1, -1, true))
            data.add(convert(tObject));
        return data;
    }


    public Boolean update(ObjectDTO dto, final Integer id, Pojo pojo) {
        ObjectDTO currentObject = getObject(id);
        if (currentObject == null)
            return false;
        objectsManager.updateObjectFields(id, convertDtoFields(dto.fields, pojo));
        for (final ProcessingTrigger trigger : ProcessingManager.getInstance().getTrigger(dto.type))
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    trigger.onUpdate(id);
                }
            });
        return true;
    }

    public Map<String, Object> convertDtoFields(Map<String, String> fields, Pojo pojo) {
        Map<String, Object> fieldsMap = new HashMap<>();
        for (String fieldName : fields.keySet()) {
            String fieldType = pojo.getFields().get(fieldName).toLowerCase();
            switch (fieldType) {
                case "integer":
                    fieldsMap.put(fieldName, Integer.valueOf(fields.get(fieldName)));
                    break;
                case "long":
                    fieldsMap.put(fieldName, Long.valueOf(fields.get(fieldName)));
                    break;
                case "double":
                    fieldsMap.put(fieldName, Double.valueOf(fields.get(fieldName)));
                    break;
                case "date":
                    fieldsMap.put(fieldName, new Date(Long.valueOf(fields.get(fieldName))));
                    break;
                case "string":
                    fieldsMap.put(fieldName, fields.get(fieldName));
                    break;
            }
        }
        return fieldsMap;
    }
}
