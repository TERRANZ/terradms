package ru.terra.dms.desktop.core.viewpart;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ru.terra.dms.shared.dto.ObjectDTO;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Date: 03.06.14
 * Time: 16:35
 */
public class PojoEditDialog extends AbstractEditDialog<ObjectDTO> {
    @FXML
    public TableView<ObjectDTO> tblFields;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblFields.setEditable(true);
        ContextMenu tableContextMenu = new ContextMenu();
        MenuItem miAdd = new MenuItem();
        miAdd.setText("Добавить");
        miAdd.setOnAction(actionEvent -> tblFields.getItems().add(new ObjectDTO()));
        MenuItem miDel = new MenuItem();
        miDel.setText("Добавить");
        tableContextMenu.getItems().addAll(miAdd, miDel);
        tblFields.setContextMenu(tableContextMenu);
    }

    public void ok(ActionEvent actionEvent) {
        if (returnValue == null)
            returnValue = new ObjectDTO();
        if (workIsDoneListener != null)
            workIsDoneListener.workIsDone(0);
    }

    @Override
    public void setReturnValue(ObjectDTO returnValue) {
        super.setReturnValue(returnValue);
        currStage.setTitle(returnValue.type);
        if (returnValue.fields != null && returnValue.fields.size() > 0) {
            TableColumn<ObjectDTO, String> colId = new TableColumn<>("Ид");
            colId.setCellValueFactory(t -> new ReadOnlyStringWrapper(t.getValue().id.toString()));
            tblFields.getColumns().add(colId);
            for (String fieldId : returnValue.fields.keySet()) {
                TableColumn<ObjectDTO, String> colDescription = new TableColumn<>(fieldId);
                colDescription.setCellValueFactory(t -> new ReadOnlyObjectWrapper<>(t.getValue().fields.get(fieldId)));
                colDescription.setCellFactory(EditableCell.<ObjectDTO>getFactory());
                colDescription.setOnEditCommit(t -> returnValue.fields.put(fieldId, t.getNewValue()));
                tblFields.getColumns().add(colDescription);
            }
            tblFields.getItems().add(returnValue);
        } else
            tblFields.getItems().add(new ObjectDTO());
    }
}
