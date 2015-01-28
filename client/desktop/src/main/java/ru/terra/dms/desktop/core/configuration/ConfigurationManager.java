package ru.terra.dms.desktop.core.configuration;

import ru.terra.dms.rest.RestService;
import ru.terra.dms.configuration.Configuration;

/**
 * Date: 10.06.14
 * Time: 11:16
 */
public class ConfigurationManager {
    private static Configuration configuration;

    private ConfigurationManager() {
    }

    public static Configuration getConfiguration() {
        if (configuration == null)
            configuration = RestService.getInstance().loadConfiguration();
        return configuration;
    }
}
