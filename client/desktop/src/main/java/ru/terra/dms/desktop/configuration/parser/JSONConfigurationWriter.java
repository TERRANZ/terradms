package ru.terra.dms.desktop.configuration.parser;

import flexjson.JSONSerializer;
import ru.terra.dms.desktop.configuration.Configuration;

import java.io.*;

/**
 * Date: 02.06.14
 * Time: 13:48
 */
public class JSONConfigurationWriter {
    public static void write(String fileName, Configuration configuration) throws IOException {
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
            new JSONSerializer().deepSerialize(configuration, out);
        } finally {
            out.close();
        }
    }
}
