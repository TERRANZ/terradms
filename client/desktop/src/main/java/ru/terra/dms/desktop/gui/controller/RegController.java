package ru.terra.dms.desktop.gui.controller;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.controlsfx.dialog.Dialogs;
import ru.terra.dms.desktop.core.viewpart.AbstractWindow;
import ru.terra.dms.desktop.gui.parts.StageHelper;
import ru.terra.dms.desktop.gui.service.RegUserService;
import ru.terra.server.dto.LoginDTO;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Date: 03.06.14
 * Time: 14:45
 */
public class RegController extends AbstractWindow {
    @FXML
    public TextField tfUser;
    @FXML
    public TextField tfPass;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void login(ActionEvent actionEvent) {
        RegUserService regUserService = new RegUserService(tfUser.getText(), tfPass.getText());
        Dialogs.create().owner(currStage).showWorkerProgress(regUserService);
        regUserService.reset();
        regUserService.start();
        regUserService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                LoginDTO loginDTO = regUserService.getValue();
                System.out.println(loginDTO.logged);
                currStage.close();
                if (loginDTO.logged)
                    StageHelper.openWindow("w_main.fxml", "Main");
            }
        });
    }

    public void cancel(ActionEvent actionEvent) {
        currStage.close();
    }
}
