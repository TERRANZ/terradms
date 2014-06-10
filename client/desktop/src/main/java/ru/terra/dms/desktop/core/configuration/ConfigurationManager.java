package ru.terra.dms.desktop.core.configuration;

import ru.terra.dms.client.rest.Configuration;
import ru.terra.dms.client.rest.Localhost_Dms;

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
            configuration = Localhost_Dms.configuration().doGetJson().getAsConfiguration();
        return configuration;
    }
}
