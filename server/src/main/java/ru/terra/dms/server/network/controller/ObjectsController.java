package ru.terra.dms.server.network.controller;

import com.sun.jersey.api.core.HttpContext;
import flexjson.JSONDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.terra.dms.desktop.dto.Pair;
import ru.terra.dms.server.constants.URLConstants;
import ru.terra.dms.server.network.dto.ObjectDTO;
import ru.terra.dms.server.network.engine.ObjectsEngine;
import ru.terra.server.controller.AbstractResource;
import ru.terra.server.dto.ListDTO;
import ru.terraobjects.entity.TObject;
import ru.terraobjects.manager.ObjectsManager;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public Boolean create(@Context HttpContext hc, @FormParam("object") String json) {
        logger.info(json);
        ObjectDTO objectDTO = new JSONDeserializer<ObjectDTO>().use("fields", ArrayList.class).use("field.values", Pair.class).deserialize(json, ObjectDTO.class);
        objectsEngine.createObject(objectDTO);
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
    public ListDTO<ObjectDTO> listByName(@Context HttpContext hc, @QueryParam("name") String name) {
        ListDTO<ObjectDTO> ret = new ListDTO<>();
        List<ObjectDTO> data = new ArrayList<>();
        for (TObject tObject : objectsManager.load(name, -1, -1, true)) {
            ObjectDTO objectDTO = new ObjectDTO();
            objectDTO.id = tObject.getId();
            objectDTO.type = tObject.getName();
            objectDTO.fields = new ArrayList<>();
            Map<String, Object> map = objectsManager.getObjectFieldValues(tObject.getId());
            for (String key : map.keySet()) {
                Pair<String, Object> pair = new Pair<String, Object>();
                pair.key = key;
                pair.value = map.get(key);
                objectDTO.fields.add(pair);
            }
            data.add(objectDTO);
        }
        ret.setData(data);
        return ret;
    }
}
