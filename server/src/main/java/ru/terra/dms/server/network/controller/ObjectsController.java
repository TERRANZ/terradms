package ru.terra.dms.server.network.controller;

import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.core.util.ReaderWriter;
import com.sun.jersey.multipart.FormDataParam;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import ru.terra.dms.server.constants.URLConstants;
import ru.terra.dms.server.engine.ConfigurationEngine;
import ru.terra.dms.server.engine.ObjectsEngine;
import ru.terra.dms.shared.dto.ObjectDTO;
import ru.terra.server.constants.ErrorConstants;
import ru.terra.server.controller.AbstractResource;
import ru.terra.server.dto.CommonDTO;
import ru.terra.server.dto.ListDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.nio.charset.Charset;

/**
 * Date: 02.06.14
 * Time: 11:54
 */
@Path(URLConstants.Objects.OBJECTS)
public class ObjectsController extends AbstractResource {
    private Logger logger = Logger.getLogger(this.getClass());
    private ObjectsEngine objectsEngine = ObjectsEngine.getInstance();

    @POST
    @Path(URLConstants.DoJson.DO_CREATE)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public CommonDTO create(@Context HttpContext hc, @FormDataParam("jsonfile") InputStream fileInputStream,
                            @FormDataParam("jsonfile") FormDataContentDisposition contentDispositionHeader) {
        if (!isAuthorized(hc)) {
            CommonDTO ret = new CommonDTO();
            ret.errorCode = ErrorConstants.ERR_NOT_AUTHORIZED_ID;
            ret.errorMessage = ErrorConstants.ERR_NOT_AUTHORIZED_MSG;
            return ret;
        }
        String json = null;
        try {
            json = ReaderWriter.readFromAsString(new InputStreamReader(fileInputStream, Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Received json " + json);
        ObjectDTO objectDTO = null;
        try {
            objectDTO = new ObjectMapper().readValue(json, ObjectDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        objectsEngine.createObject(objectDTO, ConfigurationEngine.getInstance().getConfiguration().getPojo(objectDTO.type));
        return new CommonDTO();
    }

    @POST
    @Path(URLConstants.DoJson.DO_UPDATE + "/{id}/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public CommonDTO update(@Context HttpContext hc, @FormDataParam("jsonfile") InputStream fileInputStream,
                            @FormDataParam("jsonfile") FormDataContentDisposition contentDispositionHeader, @PathParam("id") Integer id) {
        if (!isAuthorized(hc)) {
            CommonDTO ret = new CommonDTO();
            ret.errorCode = ErrorConstants.ERR_NOT_AUTHORIZED_ID;
            ret.errorMessage = ErrorConstants.ERR_NOT_AUTHORIZED_MSG;
            return ret;
        }
        String json = null;
        try {
            json = ReaderWriter.readFromAsString(new InputStreamReader(fileInputStream, Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Received json " + json);
        ObjectDTO objectDTO = null;
        try {
            objectDTO = new ObjectMapper().readValue(json, ObjectDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonDTO ret = new CommonDTO();
        ret.status = objectsEngine.update(objectDTO, id, ConfigurationEngine.getInstance().getConfiguration().getPojo(objectDTO.type)).toString();
        return ret;
    }

    @DELETE
    @Path(URLConstants.DoJson.DO_DEL + "/{id}/")
    public CommonDTO delete(@Context HttpContext hc, @PathParam("id") Integer id) {
        if (!isAuthorized(hc)) {
            CommonDTO ret = new CommonDTO();
            ret.errorCode = ErrorConstants.ERR_NOT_AUTHORIZED_ID;
            ret.errorMessage = ErrorConstants.ERR_NOT_AUTHORIZED_MSG;
            return ret;
        }
        CommonDTO result = new CommonDTO();
        result.status = objectsEngine.deleteObject(id).toString();
        return result;
    }

    @GET
    @Path(URLConstants.DoJson.DO_GET + "/{id}/")
    public ObjectDTO get(@Context HttpContext hc, @PathParam("id") Integer id) {
        if (!isAuthorized(hc)) {
            ObjectDTO ret = new ObjectDTO();
            return ret;
        }
        return objectsEngine.getObject(id);
    }

    @GET
    @Path(URLConstants.Objects.LIST_BY_NAME)
    public String listByName(@Context HttpContext hc, @QueryParam("name") String name) throws IOException {
        if (!isAuthorized(hc)) {
            CommonDTO ret = new CommonDTO();
            ret.errorCode = ErrorConstants.ERR_NOT_AUTHORIZED_ID;
            ret.errorMessage = ErrorConstants.ERR_NOT_AUTHORIZED_MSG;
            return new ObjectMapper().writeValueAsString(ret);
        }
        ListDTO<ObjectDTO> ret = new ListDTO<>();
        ret.setData(objectsEngine.getByName(name));
        String json = new ObjectMapper().writeValueAsString(ret);
        return json;
    }

    @GET
    @Path(URLConstants.Objects.LIST_BY_PARENT)
    public String listByParent(@Context HttpContext hc, @QueryParam("parent") Integer parentId) throws IOException {
        if (!isAuthorized(hc)) {
            CommonDTO ret = new CommonDTO();
            ret.errorCode = ErrorConstants.ERR_NOT_AUTHORIZED_ID;
            ret.errorMessage = ErrorConstants.ERR_NOT_AUTHORIZED_MSG;
            return new ObjectMapper().writeValueAsString(ret);
        }
        ListDTO<ObjectDTO> ret = new ListDTO<>();
        ret.setData(objectsEngine.getByParent(parentId));
        String json = new ObjectMapper().writeValueAsString(ret);
        return json;
    }
}
