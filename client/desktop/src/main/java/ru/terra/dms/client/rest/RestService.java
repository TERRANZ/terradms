package ru.terra.dms.client.rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.MultiPart;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.terra.dms.configuration.Configuration;
import ru.terra.dms.constants.URLConstants;
import ru.terra.dms.shared.dto.ObjectDTO;
import ru.terra.server.dto.CommonDTO;
import ru.terra.server.dto.ListDTO;
import ru.terra.server.dto.LoginDTO;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Date: 14.07.14
 * Time: 12:24
 */
public class RestService {
    private ClientConfig config = new DefaultClientConfig();
    private Client client;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public RestService() {
        client = Client.create(config);
    }


    public Configuration loadConfiguration() {
        String json = client.resource(URLConstants.URL + URLConstants.Configuration.CONFIGURATION + "/do.get.json").get(String.class);
        try {
            return new ObjectMapper().readValue(json, Configuration.class);
        } catch (IOException e) {
            logger.error("unable to read json", e);
            return null;
        }
    }

    public LoginDTO login(String user, String pass) {
        return client.resource(URLConstants.URL + URLConstants.Login.USERS + URLConstants.Login.DO_LOGIN)
                .queryParam(URLConstants.Login.PARAM_USER, user)
                .queryParam(URLConstants.Login.PARAM_PASS, pass)
                .get(LoginDTO.class);
    }

    public LoginDTO reg(String user, String pass) {
        return client.resource(URLConstants.URL + URLConstants.Login.USERS + URLConstants.Login.DO_REG)
                .queryParam(URLConstants.Login.PARAM_USER, user)
                .queryParam(URLConstants.Login.PARAM_PASS, pass)
                .get(LoginDTO.class);
    }

    public ListDTO<ObjectDTO> getObjectsByName(String name) {
        String json = client.resource(URLConstants.URL + URLConstants.Objects.OBJECTS + URLConstants.Objects.LIST_BY_NAME).queryParam("name", name).get(String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType type = objectMapper.getTypeFactory().constructParametricType(ListDTO.class, ru.terra.dms.shared.dto.ObjectDTO.class);
        try {
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            logger.error("Unable to read objects", e);
            return null;
        }
    }

    public ListDTO<ObjectDTO> getObjectsByParent(Integer parent) {
        String json = client.resource(URLConstants.URL + URLConstants.Objects.OBJECTS + URLConstants.Objects.LIST_BY_PARENT).queryParam("parent", parent.toString()).get(String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType type = objectMapper.getTypeFactory().constructParametricType(ListDTO.class, ru.terra.dms.shared.dto.ObjectDTO.class);
        try {
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            logger.error("Unable to read objects", e);
            return null;
        }
    }


    public CommonDTO createObjects(String json) {
        WebResource resource = client.resource(URLConstants.URL + URLConstants.Objects.OBJECTS + "/do.create.json");
        MultiPart multiPart = new MultiPart().
                bodyPart(new BodyPart(json, MediaType.APPLICATION_JSON_TYPE));
        return resource.type("multipart/mixed").post(CommonDTO.class, multiPart);
    }

    public CommonDTO deleteObject(Integer id) {
        WebResource resource = client.resource(URLConstants.URL + URLConstants.Objects.OBJECTS + "/do.delete.json" + "/" + id.toString() + "/");
        return resource.delete(CommonDTO.class);
    }

    public ObjectDTO getObject(Integer id) {
        WebResource resource = client.resource(URLConstants.URL + URLConstants.Objects.OBJECTS + "/do.get.json" + "/" + id.toString() + "/");
        return resource.get(ObjectDTO.class);
    }
}
