package ru.terra.dms.server.processing.impl;

import org.apache.log4j.Logger;
import ru.terra.dms.server.processing.Processing;
import ru.terra.dms.server.processing.ProcessingTrigger;

/**
 * Date: 03.06.14
 * Time: 21:02
 */
@Processing("waybill")
public class WayBillsProcessingTrigger implements ProcessingTrigger {
    private Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void onCreate(Integer newObjectId) {
        logger.info("Process new waybill: " + newObjectId);
    }

    @Override
    public void onUpdate(Integer objectId) {

    }

    @Override
    public void onDelete(Integer objectId) {

    }
}
