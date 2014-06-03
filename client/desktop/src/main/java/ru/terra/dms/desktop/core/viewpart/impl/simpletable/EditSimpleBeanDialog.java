package ru.terra.dms.desktop.core.viewpart.impl.simpletable;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import ru.terra.dms.client.rest.ObjectDTO;
import ru.terra.dms.desktop.core.viewpart.AbstractEditDialog;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Date: 03.06.14
 * Time: 16:35
 */
public class EditSimpleBeanDialog extends AbstractEditDialog<ObjectDTO> {
    @FXML
    public TableView<ObjectDTO.Fields.Entry> tblFields;
    @FXML
    public TableColumn<ObjectDTO.Fields.Entry, String> colKey;
    @FXML
    public TableColumn<ObjectDTO.Fields.Entry, String> colVal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblFields.setEditable(true);
        colKey.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObjectDTO.Fields.Entry, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObjectDTO.Fields.Entry, String> entryStringCellDataFeatures) {
                return new ReadOnlyStringWrapper(entryStringCellDataFeatures.getValue().getKey());
            }
        });
        colKey.setCellFactory(TextFieldTableCell.<ObjectDTO.Fields.Entry>forTableColumn());
        colKey.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ObjectDTO.Fields.Entry, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ObjectDTO.Fields.Entry, String> t) {
                ObjectDTO.Fields.Entry entry = t.getTableView().getItems().get(t.getTablePosition().getRow());
                entry.setKey(t.getNewValue());
            }
        });
        colVal.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObjectDTO.Fields.Entry, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObjectDTO.Fields.Entry, String> entryStringCellDataFeatures) {
                return new ReadOnlyStringWrapper(String.valueOf(entryStringCellDataFeatures.getValue().getValue()));
            }
        });
        colVal.setCellFactory(TextFieldTableCell.<ObjectDTO.Fields.Entry>forTableColumn());
        colVal.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ObjectDTO.Fields.Entry, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ObjectDTO.Fields.Entry, String> t) {
                ObjectDTO.Fields.Entry entry = t.getTableView().getItems().get(t.getTablePosition().getRow());
                entry.setValue(t.getNewValue());
            }
        });
        ContextMenu tableContextMenu = new ContextMenu();
        MenuItem miAdd = new MenuItem();
        miAdd.setText("Добавить");
        miAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tblFields.getItems().addAll(new ObjectDTO.Fields.Entry());
            }
        });
        MenuItem miDel = new MenuItem();
        miDel.setText("Добавить");
        tableContextMenu.getItems().addAll(miAdd, miDel);
        tblFields.setContextMenu(tableContextMenu);
    }

    public void ok(ActionEvent actionEvent) {
        if (returnValue == null)
            returnValue = new ObjectDTO();
        returnValue.setFields(new ObjectDTO.Fields());
        returnValue.getFields().setEntry(new ArrayList<>(tblFields.getItems()));
        if (workIsDoneListener != null)
            workIsDoneListener.workIsDone(0);
    }

    public void cancel(ActionEvent actionEvent) {
        thisStage.close();
    }

    @Override
    public void loadExisting(ObjectDTO value) {
        thisStage.setTitle(value.getType());

    }
}
