package ru.terra.dms.desktop;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.terra.dms.desktop.gui.parts.StageHelper;

/**
 * Date: 27.05.14
 * Time: 12:09
 */
public class Main extends Application {
    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        StageHelper.openWindow("w_login.fxml", "Start", false);
    }
}
