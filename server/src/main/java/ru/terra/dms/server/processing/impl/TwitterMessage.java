package ru.terra.dms.server.processing.impl;

import ru.terra.dms.server.processing.Processing;
import ru.terra.dms.server.processing.ProcessingTrigger;

/**
 * Created by terranz on 13.11.15.
 */
@Processing("TwitterMessage")
public class TwitterMessage extends ProcessingTrigger {
    @Override
    public void onCreate(Integer objectId) {

    }

    @Override
    public void onUpdate(Integer objectId) {

    }

    @Override
    public void onDelete(Integer objectId) {

    }
}
