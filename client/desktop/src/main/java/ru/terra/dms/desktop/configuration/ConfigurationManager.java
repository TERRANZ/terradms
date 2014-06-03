package ru.terra.dms.desktop.configuration;

import ru.terra.dms.desktop.configuration.parser.JSONConfigurationReader;

import java.io.FileNotFoundException;

/**
 * Date: 03.06.14
 * Time: 15:48
 */
public class ConfigurationManager {
    private static ConfigurationManager instance = new ConfigurationManager();
    private Configuration configuration;

    private ConfigurationManager() {
        try {
            configuration = JSONConfigurationReader.load("configuration.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Configuration getInstance() {
        return instance.configuration;
    }
}
