package ru.terra.dms.server.processing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 03.06.14
 * Time: 20:57
 */
public abstract class ProcessingTrigger {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public abstract void onCreate(Integer objectId);

    public abstract void onUpdate(Integer objectId);

    public abstract void onDelete(Integer objectId);
}
