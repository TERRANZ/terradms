package ru.terra.dms.desktop.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import ru.terra.dms.configuration.Configuration;
import ru.terra.dms.configuration.bean.MenuPart;
import ru.terra.dms.configuration.bean.ViewPart;
import ru.terra.dms.desktop.core.configuration.ConfigurationManager;
import ru.terra.dms.desktop.core.viewpart.AbstractViewPart;
import ru.terra.dms.desktop.core.viewpart.AbstractWindow;
import ru.terra.dms.desktop.core.viewpart.ViewPartHelper;
import ru.terra.dms.desktop.gui.parts.StageHelper;
import ru.terra.dms.desktop.core.util.Pair;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Date: 02.06.14
 * Time: 14:30
 */
public class MainWindowController extends AbstractWindow {
    @FXML
    public Menu miViewparts;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final Configuration configuration = ConfigurationManager.getConfiguration();
//        currStage.setTitle(configuration.getName());
        for (final MenuPart menuPart : configuration.getMenus()) {
            MenuItem viewPartMenuItem = new MenuItem();
            viewPartMenuItem.setText(menuPart.getText());
            viewPartMenuItem.setOnAction(actionEvent -> {
                ViewPart viewPart = configuration.getViewPart(menuPart.getViewPart());
                Pair<Stage, AbstractViewPart> windowPair = StageHelper.<AbstractViewPart>openWindow(ViewPartHelper.getInstance().getFXML(viewPart.getControllerType()), menuPart.getViewPart());
                windowPair.getValue().setViewPartName(viewPart.getName());
                windowPair.getValue().load();
            });
            miViewparts.getItems().add(viewPartMenuItem);
        }
    }
}
