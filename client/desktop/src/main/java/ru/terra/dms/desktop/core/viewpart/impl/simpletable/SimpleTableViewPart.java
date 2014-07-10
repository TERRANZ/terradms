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
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import ru.terra.dms.client.rest.ListDTO;
import ru.terra.dms.client.rest.Localhost_Dms;
import ru.terra.dms.client.rest.ObjectDTO;
import ru.terra.dms.client.rest.Pojo;
import ru.terra.dms.desktop.core.annotations.ViewPart;
import ru.terra.dms.desktop.core.configuration.ConfigurationManager;
import ru.terra.dms.desktop.core.util.WorkIsDoneListener;
import ru.terra.dms.desktop.core.viewpart.AbstractViewPart;
import ru.terra.dms.desktop.gui.parts.ProgressDialog;
import ru.terra.dms.desktop.gui.parts.StageHelper;
import ru.terra.dms.desktop.gui.service.SendObjectsService;
import ru.terra.dms.desktop.gui.util.Pair;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Date: 26.05.14
 * Time: 15:41
 */
@ViewPart(name = "simpletable", fxml = "p_simple_table.fxml")
public class SimpleTableViewPart extends AbstractViewPart {
    public static class SimpleTableItem implements Serializable {
        Integer id = 0;
        public String values = "";

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
    private ru.terra.dms.client.rest.ViewPart viewPart;
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
                        String json = Localhost_Dms.objects().doListBynameJson().getAsJson(viewPart.getPojo().getType(), String.class);
                        logger.info("json: " + json);
//                        ListDTO listDTO = Localhost_Dms.objects().doListBynameJson().getAsListDTO(viewPart.getPojo().getType());
                        ObjectMapper objectMapper = new ObjectMapper();
                        JavaType type = objectMapper.getTypeFactory().constructParametricType(ListDTO.class, ru.terra.dms.shared.dto.ObjectDTO.class);
                        ListDTO<ru.terra.dms.shared.dto.ObjectDTO> listDTO = objectMapper.readValue(json, type);
                        if (listDTO.getData() != null)
                            for (ru.terra.dms.shared.dto.ObjectDTO dto : listDTO.getData()) {
                                ObjectDTO o = new ObjectDTO();
                                o.setId(dto.id);
                                o.setType(dto.type);
                                o.setFields(new ObjectDTO.Fields());
                                o.getFields().setEntry(new ArrayList<>());
                                for (String key : dto.fields.keySet())
                                    o.getFields().getEntry().add(new ObjectDTO.Fields.Entry(key, dto.fields.get(key)));
                                ret.add(processObjectDTO(o));
                            }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return FXCollections.observableArrayList(ret);
                }
            };
        }
    }

    private SimpleTableItem processObjectDTO(ObjectDTO objectDTO) {
        SimpleTableItem tableItem = new SimpleTableItem();
        tableItem.id = objectDTO.getId();
        StringBuilder sb = new StringBuilder();
        for (ObjectDTO.Fields.Entry entry : objectDTO.getFields().getEntry()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append(";");
        }
        tableItem.values = sb.toString();

        return tableItem;
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
        objectDTO.setType(viewPart.getPojo().getType());
        objectDTO.setId(0);
        objectDTO.setFields(new ObjectDTO.Fields());
        objectDTO.getFields().setEntry(new ArrayList<>());
        for (Pojo.Fields.Entry entry : viewPart.getPojo().getFields().getEntry())
            objectDTO.getFields().getEntry().add(new ObjectDTO.Fields.Entry(entry.getKey(), viewPart.getPojo().getFields().getEntry(entry.getKey())));
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
        ProgressDialog.create(sendObjectsService, currStage, true).show();
        sendObjectsService.reset();
        sendObjectsService.start();
        sendObjectsService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                newObjects.clear();
            }
        });


    }
}
