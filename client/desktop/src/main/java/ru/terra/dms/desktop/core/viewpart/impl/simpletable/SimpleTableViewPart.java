package ru.terra.dms.desktop.core.viewpart.impl.simpletable;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import ru.terra.dms.client.rest.ListDTO;
import ru.terra.dms.client.rest.Localhost_Dms;
import ru.terra.dms.client.rest.ObjectDTO;
import ru.terra.dms.desktop.configuration.ConfigurationManager;
import ru.terra.dms.desktop.core.annotations.ViewPart;
import ru.terra.dms.desktop.core.util.WorkIsDoneListener;
import ru.terra.dms.desktop.core.viewpart.AbstractViewPart;
import ru.terra.dms.desktop.gui.parts.StageHelper;
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
            return "SimpleTableItem{" +
                    "id=" + id +
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
    private ru.terra.dms.desktop.configuration.bean.ViewPart viewPart;

    private class LoadService extends Service<ObservableList<SimpleTableItem>> {
        @Override
        protected Task<ObservableList<SimpleTableItem>> createTask() {
            return new Task<ObservableList<SimpleTableItem>>() {
                @Override
                protected ObservableList<SimpleTableItem> call() throws Exception {
                    if (viewPart == null)
                        return null;
                    List<SimpleTableItem> ret = new ArrayList<>();
                    ListDTO listDTO = Localhost_Dms.objects().doListBynameJson().getAsListDTO(viewPart.getPojo().getName());
                    if (listDTO.getData() != null)
                        for (Object o : listDTO.getData()) {
                            ObjectDTO objectDTO = (ObjectDTO) o;
                            ret.add(processObjectDTO(objectDTO));
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
        viewPart = ConfigurationManager.getInstance().getViewPart(viewPartName);
        LoadService loadService = new LoadService();
        loadService.start();
        loadService.onSucceededProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                table.setItems(loadService.getValue());
            }
        });
    }

    public void add(ActionEvent actionEvent) {
        Pair<Stage, EditSimpleBeanDialog> dialogPair = StageHelper.<EditSimpleBeanDialog>openWindow("d_create_simple_bean.fxml", "редактирование", false);
        dialogPair.getValue().setWorkIsDoneListener(new WorkIsDoneListener() {
            @Override
            public void workIsDone(int code, String... msg) {
                dialogPair.getKey().close();
                ObjectDTO dto = dialogPair.getValue().getReturnValue();
                table.getItems().addAll(processObjectDTO(dto));

            }
        });
    }
}
