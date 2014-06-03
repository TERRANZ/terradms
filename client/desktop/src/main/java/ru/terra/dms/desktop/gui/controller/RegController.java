package ru.terra.dms.desktop.gui.controller;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import ru.terra.dms.client.rest.LoginDTO;
import ru.terra.dms.desktop.gui.parts.ProgressDialog;
import ru.terra.dms.desktop.gui.parts.StageHelper;
import ru.terra.dms.desktop.gui.service.RegUserService;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Date: 03.06.14
 * Time: 14:45
 */
public class RegController implements Initializable {
    @FXML
    public TextField tfUser;
    @FXML
    public TextField tfPass;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void login(ActionEvent actionEvent) {
        RegUserService regUserService = new RegUserService(tfUser.getText(), tfPass.getText());
        ProgressDialog.create(regUserService, StageHelper.currStage, true);
        regUserService.reset();
        regUserService.start();
        regUserService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                LoginDTO loginDTO = regUserService.getValue();
                System.out.println(loginDTO.isLogged());
                if (loginDTO.isLogged())
                    StageHelper.openWindow("w_main.fxml", "Main", true);
            }
        });
    }

    public void cancel(ActionEvent actionEvent) {
        StageHelper.currStage.close();
    }
}
