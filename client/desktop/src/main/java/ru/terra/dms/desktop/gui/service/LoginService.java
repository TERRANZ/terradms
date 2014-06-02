package ru.terra.dms.desktop.gui.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import ru.terra.dms.client.rest.Localhost_Dms;
import ru.terra.dms.client.rest.LoginDTO;

/**
 * Date: 16.05.14
 * Time: 17:53
 */
public class LoginService extends Service<LoginDTO> {

    private final String user;
    private final String pass;

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
                    return Localhost_Dms.users().doLoginJson().getAsLoginDTO(user, pass);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }
}
