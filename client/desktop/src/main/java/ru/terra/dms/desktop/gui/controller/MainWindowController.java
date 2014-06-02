package ru.terra.dms.desktop.gui.controller;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import ru.terra.dms.desktop.configuration.Configuration;
import ru.terra.dms.desktop.configuration.bean.ViewPart;
import ru.terra.dms.desktop.configuration.parser.JSONConfigurationReader;
import ru.terra.dms.desktop.core.viewpart.ViewPartHelper;
import ru.terra.dms.desktop.gui.parts.ProgressDialog;
import ru.terra.dms.desktop.gui.parts.StageHelper;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Date: 02.06.14
 * Time: 14:30
 */
public class MainWindowController implements Initializable {
    @FXML
    public MenuItem miViewparts;
    private Configuration configuration;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Service<Configuration> loadConfigurationService = new Service<Configuration>() {
            @Override
            protected Task<Configuration> createTask() {
                return new Task<Configuration>() {
                    @Override
                    protected Configuration call() throws Exception {
                        return JSONConfigurationReader.load("configuration.json");
                    }
                };
            }
        };
        ProgressDialog.create(loadConfigurationService, StageHelper.currStage, true).show();
        loadConfigurationService.reset();
        loadConfigurationService.start();
        loadConfigurationService.setOnSucceeded(event -> {
            configuration = loadConfigurationService.getValue();
            StageHelper.currStage.setTitle(configuration.getName());
            configuration.getMenus().forEach(menuPart -> {
                MenuItem viewPartMenuItem = new MenuItem();
                viewPartMenuItem.setText(menuPart.getText());
                viewPartMenuItem.setOnAction(action -> {
                    ViewPart viewPart = configuration.getViewPart(menuPart.getViewPart());
                    StageHelper.openWindow(ViewPartHelper.getInstance().getFXML(viewPart.getControllerType()), menuPart.getViewPart(), false).getValue();
                });
            });
        });
    }
}
