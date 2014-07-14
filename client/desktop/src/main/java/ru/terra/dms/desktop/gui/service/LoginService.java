package ru.terra.dms.desktop.gui.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import ru.terra.dms.client.rest.RestService;
import ru.terra.server.dto.LoginDTO;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Date: 16.05.14
 * Time: 17:53
 */
public class LoginService extends Service<LoginDTO> {

    protected final String user;
    protected final String pass;
    protected Logger logger = Logger.getLogger(this.getClass().getName());

    public LoginService(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    @Override
    protected Task<LoginDTO> createTask() {
        return new Task<LoginDTO>() {
            @Override
            protected LoginDTO call() throws Exception {
                try {
                    return new RestService().login(user, pass);
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Unable to login", e);
                }
                return null;
            }
        };
    }
}
