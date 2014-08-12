package ru.terra.dms.server.processing;

/**
 * Date: 03.06.14
 * Time: 20:57
 */
public interface ProcessingTrigger {
    public void onCreate(Integer objectId);

    public void onUpdate(Integer objectId);

    public void onDelete(Integer objectId);
}
