package ru.terra.dms.desktop.core.viewpart;

import javafx.event.ActionEvent;
import ru.terra.dms.desktop.core.util.WorkIsDoneListener;

/**
 * Date: 03.06.14
 * Time: 16:39
 */
public abstract class AbstractEditDialog<RetVal> extends AbstractWindow {
    protected WorkIsDoneListener workIsDoneListener;
    protected RetVal returnValue;

    public WorkIsDoneListener getWorkIsDoneListener() {
        return workIsDoneListener;
    }

    public void setWorkIsDoneListener(WorkIsDoneListener workIsDoneListener) {
        this.workIsDoneListener = workIsDoneListener;
    }

    public RetVal getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(RetVal returnValue) {
        this.returnValue = returnValue;
    }

    public abstract void ok(ActionEvent actionEvent);

    public void cancel(ActionEvent actionEvent) {
        currStage.close();
    }
}
