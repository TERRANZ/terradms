package ru.terra.dms.server.processing.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.terra.dms.server.processing.Processing;
import ru.terra.dms.server.processing.ProcessingTrigger;

/**
 * Date: 03.06.14
 * Time: 21:02
 */
@Processing("waybill")
public class WayBillsProcessingTrigger implements ProcessingTrigger {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onCreate(Integer newObjectId) {
        logger.info("Process new waybill: " + newObjectId);
    }
}
