package ru.terra.dms.rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import ru.terra.dms.configuration.Configuration;
import ru.terra.dms.constants.URLConstants;
import ru.terra.dms.shared.dto.ObjectDTO;
import ru.terra.server.config.Config;
import ru.terra.server.dto.CommonDTO;
import ru.terra.server.dto.ListDTO;
import ru.terra.server.dto.LoginDTO;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Date;

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
    private final String URL = Config.getConfig().getValue("server_url", "http://xn--80aafhfrpg0adapheyc1nya.xn--p1ai:5555/dms");

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
        return client.resource(URL + URLConstants.Configuration.CONFIGURATION + URLConstants.DO.GET)
                .get(Configuration.class);
    }

    public LoginDTO login(String user, String pass) {
        return client.resource(URL + URLConstants.Login.USERS + URLConstants.Login.DO_LOGIN)
                .queryParam(URLConstants.Login.PARAM_USER, user)
                .queryParam(URLConstants.Login.PARAM_PASS, pass)
                .get(LoginDTO.class);
    }

    public LoginDTO reg(String user, String pass) {
        return client.resource(URL + URLConstants.Login.USERS + URLConstants.Login.DO_REG)
                .queryParam(URLConstants.Login.PARAM_USER, user)
                .queryParam(URLConstants.Login.PARAM_PASS, pass)
                .get(LoginDTO.class);
    }

    public ListDTO<ObjectDTO> getObjectsByName(String name) {
        String json = client.resource(URL + URLConstants.Objects.OBJECTS + URLConstants.Objects.LIST_BY_NAME).queryParam("name", name)
                .header(URLConstants.COOKIE, URLConstants.COOKIE_PARAM + "=" + session)
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
        String json = client.resource(URL + URLConstants.Objects.OBJECTS + URLConstants.Objects.LIST_BY_PARENT).queryParam("parent", parent.toString())
                .header(URLConstants.COOKIE, URLConstants.COOKIE_PARAM + "=" + session)
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
        File tempFile = new File(new Date().getTime() + ".temp");
        try {
            PrintWriter printWriter = new PrintWriter(tempFile, Charset.forName("UTF-8").name());
            printWriter.println(json);
            printWriter.close();
            FileDataBodyPart filePart = new FileDataBodyPart("jsonfile", tempFile);
            FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
            final FormDataMultiPart multipart = (FormDataMultiPart) formDataMultiPart.bodyPart(filePart);
            return client
                    .resource(URL + URLConstants.Objects.OBJECTS + URLConstants.DO.CREATE)
                    .header(URLConstants.COOKIE, URLConstants.COOKIE_PARAM + "=" + session)
                    .type(MediaType.MULTIPART_FORM_DATA)
                    .post(CommonDTO.class, multipart);
//                .post(CommonDTO.class, new MultiPart().bodyPart(new BodyPart(json, MediaType.APPLICATION_JSON_TYPE)));
        } catch (Exception e) {
            logger.error("Unable to create object", e);
        } finally {
            tempFile.delete();
        }
        return null;
    }

    public CommonDTO deleteObject(Integer id) {
        WebResource resource = client.resource(URL + URLConstants.Objects.OBJECTS + URLConstants.DO.DELETE + "/" + id.toString() + "/");
        resource.header(URLConstants.COOKIE, URLConstants.COOKIE_PARAM + "=" + session);
        return resource.delete(CommonDTO.class);
    }

    public ObjectDTO getObject(Integer id) {
        WebResource resource = client.resource(URL + URLConstants.Objects.OBJECTS + URLConstants.DO.GET + "/" + id.toString() + "/");
        resource.header(URLConstants.COOKIE, URLConstants.COOKIE_PARAM + "=" + session);
        return resource.get(ObjectDTO.class);
    }
}
