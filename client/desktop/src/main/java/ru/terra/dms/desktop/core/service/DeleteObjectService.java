package ru.terra.dms.desktop.core.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import ru.terra.dms.rest.RestService;

/**
 * Date: 14.07.14
 * Time: 23:27
 */
public class DeleteObjectService extends Service<Boolean> {
    private Integer id;

    public DeleteObjectService(Integer id) {
        this.id = id;
    }

    @Override
    protected Task<Boolean> createTask() {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                return Boolean.valueOf(RestService.getInstance().deleteObject(id).status);
            }
        };
    }
}
