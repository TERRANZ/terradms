package ru.terra.dms.desktop.core.viewpart;

import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 10.07.14
 * Time: 10:19
 */
public abstract class AbstractWindow implements Initializable {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected Stage currStage;

    public Stage getCurrStage() {
        return currStage;
    }

    public void setCurrStage(Stage currStage) {
        this.currStage = currStage;
    }
}
