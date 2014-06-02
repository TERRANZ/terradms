package ru.terra.dms.desktop.configuration.parser;

import flexjson.JSONDeserializer;
import ru.terra.dms.desktop.configuration.Configuration;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Date: 02.06.14
 * Time: 13:38
 */
public class JSONConfigurationReader {
    public static Configuration load(String fileName) throws FileNotFoundException {
        return new JSONDeserializer<Configuration>().deserialize(new FileReader(new File(fileName)), Configuration.class);
    }
}
