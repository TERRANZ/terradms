package ru.terra.dms.server.network.controller;

import com.sun.jersey.api.core.HttpContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.terra.dms.server.constants.URLConstants;
import ru.terra.dms.shared.dto.ObjectDTO;
import ru.terra.dms.server.engine.ObjectsEngine;
import ru.terra.server.controller.AbstractResource;
import ru.terra.server.dto.CommonDTO;
import ru.terra.server.dto.ListDTO;
import ru.terraobjects.entity.TObject;
import ru.terraobjects.manager.ObjectsManager;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 02.06.14
 * Time: 11:54
 */
@Path(URLConstants.Objects.OBJECTS)
public class ObjectsController extends AbstractResource {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ObjectsManager<TObject> objectsManager = new ObjectsManager<>();
    private ObjectsEngine objectsEngine = new ObjectsEngine();

    @POST
    @Path(URLConstants.DoJson.DO_CREATE)
    public CommonDTO create(@Context HttpContext hc, @FormParam("object") String json) throws IOException {
        ObjectDTO objectDTO = new ObjectMapper().readValue(json, ObjectDTO.class);
        objectsEngine.createObject(objectDTO);
        return new CommonDTO();
    }

    @POST
    @Path(URLConstants.DoJson.DO_UPDATE)
    public Boolean update(@Context HttpContext hc, @FormParam("object") ObjectDTO objectDTO) {
        return true;
    }

    @DELETE
    @Path(URLConstants.DoJson.DO_DEL + "/{id}/")
    public Boolean delete(@Context HttpContext hc, @PathParam("id") Integer id) {
        return true;
    }

    @GET
    @Path(URLConstants.DoJson.DO_GET + "/{id}/")
    public ObjectDTO get(@Context HttpContext hc, @PathParam("id") Integer id) {
        return new ObjectDTO();
    }

    @GET
    @Path(URLConstants.Objects.LIST_BY_NAME)
    public String listByName(@Context HttpContext hc, @QueryParam("name") String name) throws IOException {
        ListDTO<ObjectDTO> ret = new ListDTO<>();
        List<ObjectDTO> data = new ArrayList<>();
        for (TObject tObject : objectsManager.load(name, -1, -1, true)) {
            ObjectDTO objectDTO = new ObjectDTO();
            objectDTO.id = tObject.getId();
            objectDTO.type = tObject.getName();
            objectDTO.fields = objectsManager.getObjectFieldValues(tObject.getId());
            data.add(objectDTO);
        }
        ret.setData(data);
        String json = new ObjectMapper().writeValueAsString(ret);
        return json;
    }
}
