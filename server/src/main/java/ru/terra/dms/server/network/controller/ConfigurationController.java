package ru.terra.dms.server.network.controller;

import org.codehaus.jackson.map.ObjectMapper;
import ru.terra.dms.configuration.Configuration;
import ru.terra.dms.server.constants.URLConstants;
import ru.terra.dms.server.engine.ConfigurationEngine;
import ru.terra.server.controller.AbstractResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.File;
import java.io.IOException;

/**
 * Date: 10.06.14
 * Time: 10:49
 */
@Path(URLConstants.Configuration.CONFIGURATION)
public class ConfigurationController extends AbstractResource {
    @GET
    @Path(URLConstants.DoJson.DO_GET)
    public Configuration get() throws IOException {
        ConfigurationEngine.getInstance().setConfiguration(new ObjectMapper().readValue(new File("configuration.json"), Configuration.class));
        return ConfigurationEngine.getInstance().getConfiguration();
    }
}
