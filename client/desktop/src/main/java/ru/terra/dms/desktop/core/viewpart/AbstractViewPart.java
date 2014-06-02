package ru.terra.dms.desktop.core.viewpart;

import javafx.fxml.Initializable;
import ru.terra.dms.desktop.configuration.bean.Pojo;

/**
 * Date: 26.05.14
 * Time: 15:42
 */
public abstract class AbstractViewPart implements Initializable {
    protected Pojo pojo;

    public Pojo getPojo() {
        return pojo;
    }

    public void setPojo(Pojo pojo) {
        this.pojo = pojo;
    }
}
