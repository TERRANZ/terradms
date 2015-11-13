package ru.terra.dms.server.processing.impl;

import org.apache.log4j.Logger;
import ru.terra.dms.server.processing.Processing;
import ru.terra.dms.server.processing.ProcessingTrigger;

/**
 * Date: 14.07.14
 * Time: 14:01
 */
@Processing("products")
public class ProductsProcessingTrigger extends ProcessingTrigger {
    @Override
    public void onCreate(Integer newObjectId) {
        logger.info("Process new product " + newObjectId);
    }

    @Override
    public void onUpdate(Integer objectId) {

    }

    @Override
    public void onDelete(Integer objectId) {
        logger.info("Process delete product " + objectId);
    }
}
