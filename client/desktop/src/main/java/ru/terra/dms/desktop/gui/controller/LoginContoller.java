package ru.terra.dms.desktop.gui.controller;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.controlsfx.dialog.Dialogs;
import ru.terra.dms.desktop.core.viewpart.AbstractWindow;
import ru.terra.dms.desktop.gui.parts.StageHelper;
import ru.terra.dms.desktop.gui.service.LoginService;
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
        Dialogs.create().owner(currStage).showWorkerProgress(loginService);
        loginService.reset();
        loginService.start();
        loginService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                LoginDTO loginDTO = loginService.getValue();
                System.out.println(loginDTO.logged);
                currStage.close();
                if (loginDTO.logged)
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
