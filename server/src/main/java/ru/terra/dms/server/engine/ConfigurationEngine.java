package ru.terra.dms.server.engine;

import ru.terra.dms.configuration.Configuration;

/**
 * Created by terranz on 16.11.15.
 */
public class ConfigurationEngine {
    private static ConfigurationEngine instance = new ConfigurationEngine();
    private Configuration configuration;

    private ConfigurationEngine() {
    }

    public static ConfigurationEngine getInstance() {
        return instance;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
