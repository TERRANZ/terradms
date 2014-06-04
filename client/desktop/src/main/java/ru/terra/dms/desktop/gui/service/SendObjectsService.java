package ru.terra.dms.desktop.gui.service;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import flexjson.JSONSerializer;
import flexjson.transformer.MapTransformer;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.terra.dms.client.rest.Localhost_Dms;
import ru.terra.dms.client.rest.ObjectDTO;
import ru.terra.dms.desktop.dto.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 04.06.14
 * Time: 13:07
 */
public class SendObjectsService extends Service<Boolean> {
    private List<ObjectDTO> objects;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public SendObjectsService(List<ObjectDTO> objects) {
        this.objects = objects;
    }


    @Override
    protected Task<Boolean> createTask() {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                for (ObjectDTO objectDTO : objects)
                    try {
                        ru.terra.dms.server.network.dto.ObjectDTO objectWithFieldsDTO = new ru.terra.dms.server.network.dto.ObjectDTO();
                        objectWithFieldsDTO.id = objectDTO.getId();
                        objectWithFieldsDTO.type = objectDTO.getType();
                        objectWithFieldsDTO.fields = new ArrayList<>();

                        for (ObjectDTO.Fields.Entry entry : objectDTO.getFields().getEntry())
                            objectWithFieldsDTO.fields.add(new Pair<>(entry.getKey(), entry.getValue()));

                        String json = new JSONSerializer().deepSerialize(objectWithFieldsDTO);
                        MultivaluedMapImpl values = new MultivaluedMapImpl();
                        values.add("object", json);
                        Localhost_Dms.objects().doCreateJson().postXWwwFormUrlencodedAsJson(values, Boolean.class);
                        //logger.info("uploaded: " + objectDTO.getId() + " -> " + Localhost_Dms.objects().doCreateJson().<Boolean>putXWwwFormUrlencodedAsJson(objectDTO, Boolean.class));
                    } catch (Exception e) {
                        logger.error("unable to send object ", e);
                    }
                return true;
            }
        };
    }
}
