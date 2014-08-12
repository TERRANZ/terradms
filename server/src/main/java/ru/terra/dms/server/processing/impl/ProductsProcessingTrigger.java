package ru.terra.dms.server.processing.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.terra.dms.server.processing.Processing;
import ru.terra.dms.server.processing.ProcessingTrigger;

/**
 * Date: 14.07.14
 * Time: 14:01
 */
@Processing("products")
public class ProductsProcessingTrigger implements ProcessingTrigger {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
