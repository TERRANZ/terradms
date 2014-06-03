package ru.terra.dms.desktop.core.viewpart;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import ru.terra.dms.desktop.core.util.WorkIsDoneListener;

/**
 * Date: 03.06.14
 * Time: 16:39
 */
public abstract class AbstractEditDialog<RetVal> implements Initializable {
    protected WorkIsDoneListener workIsDoneListener;
    protected Stage thisStage;
    protected RetVal returnValue;

    public WorkIsDoneListener getWorkIsDoneListener() {
        return workIsDoneListener;
    }

    public void setWorkIsDoneListener(WorkIsDoneListener workIsDoneListener) {
        this.workIsDoneListener = workIsDoneListener;
    }

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public RetVal getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(RetVal returnValue) {
        this.returnValue = returnValue;
    }

    public abstract void ok(ActionEvent actionEvent);

    public void cancel(ActionEvent actionEvent) {
        thisStage.close();
    }

    public abstract void loadExisting(RetVal value);

}
