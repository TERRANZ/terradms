package ru.terra.dms.desktop.core.viewpart;

import javafx.fxml.Initializable;

/**
 * Date: 26.05.14
 * Time: 15:42
 */
public abstract class AbstractViewPart implements Initializable {
    protected String viewPartName;

    public String getViewPartName() {
        return viewPartName;
    }

    public void setViewPartName(String viewPartName) {
        this.viewPartName = viewPartName;
    }

    public abstract void load();
}
