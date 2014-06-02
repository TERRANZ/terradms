package ru.terra.dms.server.network.controller;

import com.sun.jersey.api.core.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.terra.dms.server.constants.URLConstants;
import ru.terra.dms.server.network.dto.ObjectDTO;
import ru.terra.server.controller.AbstractResource;
import ru.terraobjects.entity.TObject;
import ru.terraobjects.manager.ObjectsManager;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
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

    @PUT
    @Path(URLConstants.DoJson.DO_CREATE)
    public Boolean create(@Context HttpContext hc, @FormParam("object") ObjectDTO objectDTO) {
        return true;
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
    public List<ObjectDTO> listByName(@Context HttpContext hc, @QueryParam("name") String name) {
        List<ObjectDTO> ret = new ArrayList<>();
        for (TObject tObject : objectsManager.load(name, -1, -1, true)) {
            ObjectDTO objectDTO = new ObjectDTO();
            objectDTO.id = tObject.getId();
            objectDTO.type = tObject.getName();
            objectDTO.fields = objectsManager.getObjectFieldValues(tObject.getId());
            ret.add(objectDTO);
        }
        return ret;
    }
}
