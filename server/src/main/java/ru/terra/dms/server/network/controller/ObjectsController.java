package ru.terra.dms.server.network.controller;

import com.sun.jersey.api.core.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.terra.dms.server.constants.URLConstants;
import ru.terra.dms.server.network.dto.ObjectDTO;
import ru.terra.server.controller.AbstractResource;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;

/**
 * Date: 02.06.14
 * Time: 11:54
 */
@Path(URLConstants.Objects.OBJECTS)
public class ObjectsController extends AbstractResource {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
    public Boolean delete(@Context HttpContext hc, @PathParam("id") Long id) {
        return true;
    }

    @GET
    @Path(URLConstants.DoJson.DO_GET + "/{id}/")
    public ObjectDTO get(@Context HttpContext hc, @PathParam("id") Long id) {
        return new ObjectDTO();
    }
}
