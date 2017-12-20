package ru.terra.dms.server.processing.impl;

import ru.terra.dms.server.im.jabber.JabberManager;
import ru.terra.dms.server.processing.Processing;
import ru.terra.dms.server.processing.ProcessingTrigger;
import ru.terraobjects.entity.TObject;
import ru.terraobjects.manager.ObjectsManager;

import java.util.Map;

/**
 * Created by terranz on 13.11.15.
 */
@Processing("TwitterMessage")
public class TwitterMessage extends ProcessingTrigger {
    @Override
    public void onCreate(Integer objectId) {
        if (JabberManager.getInstance().isOk()) {
            ObjectsManager<TObject> objectsManager = new ObjectsManager<>();
            Map<String, Object> fields = objectsManager.getObjectFieldValues(objectId);
            StringBuilder sb = new StringBuilder();
            sb.append(fields.get("username"));
            sb.append(" : ");
            sb.append(fields.get("text"));
            JabberManager.getInstance().sendMessage(sb.toString());
        }
    }

    @Override
    public void onUpdate(Integer objectId) {

    }

    @Override
    public void onDelete(Integer objectId) {

    }
}
