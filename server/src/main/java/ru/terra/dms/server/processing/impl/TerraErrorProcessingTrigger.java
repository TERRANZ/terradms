package ru.terra.dms.server.processing.impl;

import ru.terra.dms.server.processing.Processing;
import ru.terra.dms.server.processing.ProcessingTrigger;

/**
 * Created by terranz on 13.10.15.
 */
@Processing("TERRAERRORS")
public class TerraErrorProcessingTrigger extends ProcessingTrigger {
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
