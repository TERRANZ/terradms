package ru.terra.dms.desktop.gui.controller;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import ru.terra.dms.client.rest.LoginDTO;
import ru.terra.dms.desktop.core.viewpart.AbstractWindow;
import ru.terra.dms.desktop.gui.parts.ProgressDialog;
import ru.terra.dms.desktop.gui.parts.StageHelper;
import ru.terra.dms.desktop.gui.service.LoginService;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Date: 16.05.14
 * Time: 17:43
 */
public class LoginContoller extends AbstractWindow {
    @FXML
    public TextField tfUser;
    @FXML
    public TextField tfPass;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void login(ActionEvent actionEvent) {
        LoginService loginService = new LoginService(tfUser.getText(), tfPass.getText());
        ProgressDialog.create(loginService, currStage, true);
        loginService.reset();
        loginService.start();
        loginService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                LoginDTO loginDTO = loginService.getValue();
                System.out.println(loginDTO.isLogged());
                if (loginDTO.isLogged())
                    StageHelper.openWindow("w_main.fxml", "Main");
                else
                    StageHelper.openWindow("w_reg.fxml", "Reg");
            }
        });
    }

    public void cancel(ActionEvent actionEvent) {
        currStage.close();
    }
}
