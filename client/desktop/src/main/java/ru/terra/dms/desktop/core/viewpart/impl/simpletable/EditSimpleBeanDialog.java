package ru.terra.dms.desktop.core.viewpart.impl.simpletable;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import ru.terra.dms.desktop.core.viewpart.AbstractEditDialog;
import ru.terra.dms.shared.dto.ObjectDTO;
import ru.terra.dms.shared.dto.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Date: 03.06.14
 * Time: 16:35
 */
public class EditSimpleBeanDialog extends AbstractEditDialog<ObjectDTO> {
    @FXML
    public TableView<Pair<String, String>> tblFields;
    @FXML
    public TableColumn<Pair<String, String>, String> colKey;
    @FXML
    public TableColumn<Pair<String, String>, String> colVal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblFields.setEditable(true);
        colKey.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pair<String, String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pair<String, String>, String> pairStringCellDataFeatures) {
                return new ReadOnlyStringWrapper(pairStringCellDataFeatures.getValue().key);
            }
        });
        colKey.setCellFactory(TextFieldTableCell.<Pair<String, String>>forTableColumn());
        colKey.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Pair<String, String>, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Pair<String, String>, String> t) {
                Pair<String, String> entry = t.getTableView().getItems().get(t.getTablePosition().getRow());
                entry.key = t.getNewValue();
            }
        });
        colVal.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pair<String, String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pair<String, String>, String> entryStringCellDataFeatures) {
                return new ReadOnlyStringWrapper(String.valueOf(entryStringCellDataFeatures.getValue().value));
            }
        });
        colVal.setCellFactory(TextFieldTableCell.<Pair<String, String>>forTableColumn());
        colVal.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Pair<String, String>, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Pair<String, String>, String> t) {
                Pair<String, String> entry = t.getTableView().getItems().get(t.getTablePosition().getRow());
                entry.value = t.getNewValue();
            }
        });
        ContextMenu tableContextMenu = new ContextMenu();
        MenuItem miAdd = new MenuItem();
        miAdd.setText("Добавить");
        miAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tblFields.getItems().addAll(new Pair<String, String>());
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
        returnValue.fields = new HashMap<>();
        for (Pair<String, String> pair : tblFields.getItems())
            returnValue.fields.put(pair.key, pair.value);
        if (workIsDoneListener != null)
            workIsDoneListener.workIsDone(0);
    }

    @Override
    public void setReturnValue(ObjectDTO returnValue) {
        super.setReturnValue(returnValue);
        currStage.setTitle(returnValue.type);
        if (returnValue.fields != null && returnValue.fields.size() > 0) {
            List<Pair<String, String>> list = new ArrayList<>();
            for (String key : returnValue.fields.keySet()) {
                Pair<String, String> pair = new Pair<>();
                pair.key = key;
                pair.value = returnValue.fields.get(key);
                list.add(pair);
            }
            tblFields.setItems(FXCollections.observableArrayList(list));
        }
    }
}
