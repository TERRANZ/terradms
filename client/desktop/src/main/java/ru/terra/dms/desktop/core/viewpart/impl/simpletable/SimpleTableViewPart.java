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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.dialog.Dialogs;
import ru.terra.dms.client.rest.RestService;
import ru.terra.dms.configuration.bean.ViewPart;
import ru.terra.dms.desktop.core.annotations.ViewPartWindow;
import ru.terra.dms.desktop.core.configuration.ConfigurationManager;
import ru.terra.dms.desktop.core.util.WorkIsDoneListener;
import ru.terra.dms.desktop.core.viewpart.AbstractViewPart;
import ru.terra.dms.desktop.gui.parts.StageHelper;
import ru.terra.dms.desktop.gui.service.DeleteObjectService;
import ru.terra.dms.desktop.gui.service.SendObjectsService;
import ru.terra.dms.desktop.gui.util.Pair;
import ru.terra.dms.shared.dto.ObjectDTO;
import ru.terra.server.dto.ListDTO;

import java.io.Serializable;
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
    public static class SimpleTableItem implements Serializable {
        public Integer id = 0;
        public String values = "";
        public ObjectDTO dto;

        @Override
        public String toString() {
            return "id=" + id +
                    ", values='" + values + '\'' +
                    '}';
        }
    }

    @FXML
    public Button btnRefresh;
    @FXML
    public TableView<SimpleTableItem> table;
    @FXML
    public TableColumn<SimpleTableItem, String> colValue;
    private ViewPart viewPart;
    private List<ObjectDTO> newObjects = new ArrayList<>();

    private class LoadService extends Service<ObservableList<SimpleTableItem>> {
        @Override
        protected Task<ObservableList<SimpleTableItem>> createTask() {
            return new Task<ObservableList<SimpleTableItem>>() {
                @Override
                protected ObservableList<SimpleTableItem> call() throws Exception {
                    if (viewPart == null)
                        return null;
                    List<SimpleTableItem> ret = new ArrayList<>();
                    try {
                        ListDTO<ObjectDTO> listDTO = new RestService().getObjectsByName(viewPart.getPojo().getType());
                        if (listDTO.data != null)
                            for (ObjectDTO dto : listDTO.data)
                                ret.add(processObjectDTO(dto));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return FXCollections.observableArrayList(ret);
                }
            };
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnRefresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                load();
            }
        });
        colValue.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SimpleTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SimpleTableItem, String> simpleTableItemStringCellDataFeatures) {
                return new ReadOnlyStringWrapper(simpleTableItemStringCellDataFeatures.getValue().toString());
            }
        });
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() > 1) {
                    SimpleTableItem item = table.getSelectionModel().getSelectedItem();
                    if (item != null) {
                        Pair<Stage, EditSimpleBeanDialog> dialogPair = StageHelper.<EditSimpleBeanDialog>openWindow("d_create_simple_bean.fxml", "редактирование");
                        dialogPair.getValue().setReturnValue(item.dto);
                        dialogPair.getValue().setWorkIsDoneListener(new WorkIsDoneListener() {
                            @Override
                            public void workIsDone(int code, String... msg) {
                                int index = table.getItems().indexOf(item);
                                table.getItems().remove(item);
                                table.getItems().add(index, processObjectDTO(dialogPair.getValue().getReturnValue()));
                            }
                        });
                    }
                }
            }
        });
    }

    private SimpleTableItem processObjectDTO(ObjectDTO objectDTO) {
        SimpleTableItem tableItem = new SimpleTableItem();
        tableItem.id = objectDTO.id;
        StringBuilder sb = new StringBuilder();
        for (String key : objectDTO.fields.keySet()) {
            sb.append(key);
            sb.append("=");
            sb.append(objectDTO.fields.get(key));
            sb.append(";");
        }
        tableItem.values = sb.toString();
        tableItem.dto = objectDTO;
        return tableItem;
    }

    @Override
    public void load() {
        viewPart = ConfigurationManager.getConfiguration().getViewPart(viewPartName);
        LoadService loadService = new LoadService();
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
        Pair<Stage, EditSimpleBeanDialog> dialogPair = StageHelper.<EditSimpleBeanDialog>openWindow("d_create_simple_bean.fxml", "редактирование");
        ObjectDTO objectDTO = new ObjectDTO();
        objectDTO.type = viewPart.getPojo().getType();
        objectDTO.id = 0;
        objectDTO.fields = new HashMap<>(viewPart.getPojo().getFields());
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
        SimpleTableItem item = table.getSelectionModel().getSelectedItem();
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
