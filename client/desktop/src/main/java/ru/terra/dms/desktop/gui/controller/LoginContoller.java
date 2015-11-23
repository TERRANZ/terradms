package ru.terra.dms.desktop.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ru.terra.dms.desktop.core.service.LoginService;
import ru.terra.dms.desktop.core.viewpart.AbstractWindow;
import ru.terra.dms.desktop.gui.parts.StageHelper;
import ru.terra.server.dto.LoginDTO;

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
        final LoginService loginService = new LoginService(tfUser.getText(), tfPass.getText());
//        Dialogs.create().owner(currStage).showWorkerProgress(loginService);
        loginService.reset();
        loginService.start();
        loginService.setOnSucceeded(workerStateEvent -> {
            LoginDTO loginDTO = loginService.getValue();
            currStage.close();
            if (loginDTO.logged)
                StageHelper.openWindow("w_main.fxml", "Main");
            else
                StageHelper.openWindow("w_reg.fxml", "Reg");
        });
    }

    public void cancel(ActionEvent actionEvent) {
        currStage.close();
    }
}
