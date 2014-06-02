package ru.terra.dms.desktop.core.viewpart;

import org.apache.log4j.Logger;
import org.reflections.Reflections;
import ru.terra.dms.desktop.core.annotations.ViewPart;

import java.util.HashMap;
import java.util.Map;

/**
 * Date: 02.06.14
 * Time: 14:41
 */
public class ViewPartHelper {
    private static ViewPartHelper instance = new ViewPartHelper();
    private Logger logger = Logger.getLogger(this.getClass());
    private Map<String, String> viewParts = new HashMap<>();

    public static ViewPartHelper getInstance() {
        return instance;
    }

    private ViewPartHelper() {
        new Reflections("ru.terra.dms.desktop.core.viewpart.impl").getTypesAnnotatedWith(ViewPart.class).forEach(c -> {
            ViewPart viewPart = c.getAnnotation(ViewPart.class);
            viewParts.put(viewPart.name(), viewPart.fxml());
        });
    }

    public String getFXML(String partName) {
        return viewParts.get(partName);
    }
}
