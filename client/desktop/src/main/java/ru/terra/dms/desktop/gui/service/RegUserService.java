package ru.terra.dms.desktop.gui.service;

import javafx.concurrent.Task;
import ru.terra.dms.client.rest.Localhost_Dms;
import ru.terra.dms.client.rest.LoginDTO;

import java.util.logging.Level;

/**
 * Date: 03.06.14
 * Time: 14:42
 */
public class RegUserService extends LoginService {
    public RegUserService(String user, String pass) {
        super(user, pass);
    }

    @Override
    protected Task<LoginDTO> createTask() {
        return new Task<LoginDTO>() {
            @Override
            protected LoginDTO call() throws Exception {
                try {
                    return Localhost_Dms.users().doRegJson().getAsLoginDTO(user, pass);
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Unable to reg", e);
                }
                return null;
            }
        };
    }
}
