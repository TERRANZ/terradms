package ru.terra.dms.server.processing;

import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;

/**
 * Date: 05.06.14
 * Time: 17:07
 */
public class ProcessingManager {
    private static ProcessingManager instance = new ProcessingManager();
    private Map<String, Class<ProcessingTrigger>> triggers = new HashMap<>();

    private ProcessingManager() {
        for (Class c : new Reflections("ru.terra.dms.server.processing.impl").getTypesAnnotatedWith(Processing.class)) {
            Processing processing = (Processing) c.getAnnotation(Processing.class);
            triggers.put(processing.value(), (Class<ProcessingTrigger>) c);
        }
        ;
    }

    public static ProcessingManager getInstance() {
        return instance;
    }

    public ProcessingTrigger getTrigger(String documentName) {
        try {
            Class<ProcessingTrigger> processingTriggerClass = triggers.get(documentName);
            return processingTriggerClass != null ? triggers.get(documentName).newInstance() : null;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
