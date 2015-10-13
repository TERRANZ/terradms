package ru.terra.dms.server.processing;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 05.06.14
 * Time: 17:07
 */
public class ProcessingManager {
    private static ProcessingManager instance = new ProcessingManager();
    private Map<String, List<Class<? extends ProcessingTrigger>>> triggers = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ProcessingManager() {
        for (Class c : new Reflections("ru.terra.dms.server.processing.impl").getTypesAnnotatedWith(Processing.class)) {
            Processing processing = (Processing) c.getAnnotation(Processing.class);
            if (triggers.get(processing.value()) == null)
                triggers.put(processing.value(), new ArrayList<Class<? extends ProcessingTrigger>>());
            triggers.get(processing.value()).add(c);
        }
    }

    public static ProcessingManager getInstance() {
        return instance;
    }

    public List<ProcessingTrigger> getTrigger(String documentName) {
        try {
            List<Class<? extends ProcessingTrigger>> ts = triggers.get(documentName);
            List<ProcessingTrigger> ret = new ArrayList<>();
            if (ts != null && ts.size() > 0)
                for (Class<? extends ProcessingTrigger> tsc : ts)
                    ret.add(tsc.newInstance());
            return ret;
        } catch (InstantiationException e) {
            logger.error("Unable to instantiate trigger class", e);
        } catch (IllegalAccessException e) {
            logger.error("Unable to access trigger class", e);
        }
        return null;
    }
}
