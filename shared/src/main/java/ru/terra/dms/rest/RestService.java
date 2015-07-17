package ru.terra.dms.rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.MultiPart;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
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
    private static RestService instance = new RestService();
    private ClientConfig config = new DefaultClientConfig();
    private Client client;
    private Logger logger = Logger.getLogger(this.getClass());
    private String session;

    private RestService() {
        client = Client.create(config);
    }

    public static RestService getInstance() {
        return instance;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
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

        String json = client.resource(URLConstants.URL + URLConstants.Objects.OBJECTS + URLConstants.Objects.LIST_BY_NAME).queryParam("name", name)
                .header("Cookie", "JSESSIONID=" + session)
                .get(String.class);
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
        String json = client.resource(URLConstants.URL + URLConstants.Objects.OBJECTS + URLConstants.Objects.LIST_BY_PARENT).queryParam("parent", parent.toString())
                .header("Cookie", "JSESSIONID=" + session)
                .get(String.class);
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
        return client
                .resource(URLConstants.URL + URLConstants.Objects.OBJECTS + "/do.create.json")
                .header("Cookie", "JSESSIONID=" + session)
                .type("multipart/mixed")
                .post(CommonDTO.class, new MultiPart().bodyPart(new BodyPart(json, MediaType.APPLICATION_JSON_TYPE)));
    }

    public CommonDTO deleteObject(Integer id) {
        WebResource resource = client.resource(URLConstants.URL + URLConstants.Objects.OBJECTS + "/do.delete.json" + "/" + id.toString() + "/");
        resource.header("Cookie", "JSESSIONID=" + session);
        return resource.delete(CommonDTO.class);
    }

    public ObjectDTO getObject(Integer id) {
        WebResource resource = client.resource(URLConstants.URL + URLConstants.Objects.OBJECTS + "/do.get.json" + "/" + id.toString() + "/");
        resource.header("Cookie", "JSESSIONID=" + session);
        return resource.get(ObjectDTO.class);
    }
}
