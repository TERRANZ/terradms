package ru.terra.dms.desktop.core.viewpart.impl.simpletable;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.dialog.Dialogs;
import ru.terra.dms.client.rest.RestService;
import ru.terra.dms.desktop.core.annotations.ViewPartWindow;
import ru.terra.dms.desktop.core.configuration.ConfigurationManager;
import ru.terra.dms.desktop.core.util.WorkIsDoneListener;
import ru.terra.dms.desktop.core.viewpart.AbstractViewPart;
import ru.terra.dms.desktop.core.viewpart.PojoEditDialog;
import ru.terra.dms.desktop.core.viewpart.PojoTableItem;
import ru.terra.dms.desktop.gui.parts.StageHelper;
import ru.terra.dms.desktop.gui.service.DeleteObjectService;
import ru.terra.dms.desktop.gui.service.SendObjectsService;
import ru.terra.dms.desktop.gui.util.Pair;
import ru.terra.dms.shared.dto.ObjectDTO;
import ru.terra.server.dto.ListDTO;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Date: 26.05.14
 * Time: 15:41
 */
@ViewPartWindow(name = "simpletable", fxml = "p_simple_table.fxml")
public class SimpleTableViewPart extends AbstractViewPart {
    @FXML
    public Button btnRefresh;
    @FXML
    public TableView<PojoTableItem> table;
    @FXML
    public TableColumn<PojoTableItem, String> colValue;

    private List<ObjectDTO> newObjects = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnRefresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                load();
            }
        });
        colValue.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PojoTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PojoTableItem, String> simpleTableItemStringCellDataFeatures) {
                return new ReadOnlyStringWrapper(simpleTableItemStringCellDataFeatures.getValue().toString());
            }
        });
        addEditingToTable(table);
    }

    @Override
    protected void loadInternal() {
        final LoadService loadService = new LoadService();
        loadService.start();
        loadService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                table.getItems().clear();
                table.setItems(loadService.getValue());
            }
        });
    }

    public void add(ActionEvent actionEvent) {
        final Pair<Stage, PojoEditDialog> dialogPair = StageHelper.<PojoEditDialog>openWindow("d_create_simple_bean.fxml", "редактирование");
        ObjectDTO objectDTO = new ObjectDTO();
        objectDTO.type = viewPart.getPojo();
        objectDTO.id = 0;
        objectDTO.fields = new HashMap<>(ConfigurationManager.getConfiguration().getPojo(viewPart.getPojo()).getFields());
        dialogPair.getValue().setReturnValue(objectDTO);
        dialogPair.getValue().setWorkIsDoneListener(new WorkIsDoneListener() {
            @Override
            public void workIsDone(int code, String... msg) {
                dialogPair.getKey().close();
                ObjectDTO dto = dialogPair.getValue().getReturnValue();
                newObjects.add(dto);
                table.getItems().addAll(processObjectDTO(dto));

            }
        });
    }

    public void save(ActionEvent actionEvent) {
        SendObjectsService sendObjectsService = new SendObjectsService(newObjects);
        Dialogs.create().owner(currStage).showWorkerProgress(sendObjectsService);
        sendObjectsService.reset();
        sendObjectsService.start();
        sendObjectsService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                newObjects.clear();
            }
        });
    }

    public void delete(ActionEvent actionEvent) {
        final PojoTableItem item = table.getSelectionModel().getSelectedItem();
        if (item != null) {
            DeleteObjectService deleteObjectService = new DeleteObjectService(item.id);
            Dialogs.create().owner(currStage).showWorkerProgress(deleteObjectService);
            deleteObjectService.reset();
            deleteObjectService.start();
            deleteObjectService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent workerStateEvent) {
                    table.getItems().remove(item);
                }
            });
        }
    }
}
