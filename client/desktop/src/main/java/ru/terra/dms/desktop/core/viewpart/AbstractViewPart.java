package ru.terra.dms.desktop.core.viewpart;

import javafx.fxml.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 26.05.14
 * Time: 15:42
 */
public abstract class AbstractViewPart implements Initializable {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String viewPartName;

    public String getViewPartName() {
        return viewPartName;
    }

    public void setViewPartName(String viewPartName) {
        this.viewPartName = viewPartName;
    }

    public abstract void load();
}
