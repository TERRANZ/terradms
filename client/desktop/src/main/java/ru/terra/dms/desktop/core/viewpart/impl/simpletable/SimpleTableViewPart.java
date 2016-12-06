package ru.terra.dms.desktop.core.viewpart.impl.simpletable;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;
import ru.terra.dms.configuration.bean.Pojo;
import ru.terra.dms.desktop.core.annotations.ViewPartWindow;
import ru.terra.dms.desktop.core.configuration.ConfigurationManager;
import ru.terra.dms.desktop.core.service.DeleteObjectService;
import ru.terra.dms.desktop.core.service.SendObjectsService;
import ru.terra.dms.desktop.core.util.Pair;
import ru.terra.dms.desktop.core.viewpart.AbstractViewPart;
import ru.terra.dms.desktop.core.viewpart.PojoEditDialog;
import ru.terra.dms.desktop.core.viewpart.PojoTableItem;
import ru.terra.dms.desktop.gui.parts.StageHelper;
import ru.terra.dms.shared.dto.ObjectDTO;

import java.net.URL;
import java.util.*;

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
    public Label lblPage;
    @FXML
    public Label lblCount;
    private Long count;
    private Integer page;
    private LoadService loadService;

    private List<ObjectDTO> newObjects = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnRefresh.setOnAction((e) -> load());
        addEditingToTable(table);
    }

    @Override
    protected void loadInternal() {
        loadService = new LoadService();
        table.getColumns().clear();
        TableColumn<PojoTableItem, String> colId = new TableColumn<>("Ид");
        colId.setCellValueFactory(t -> new ReadOnlyStringWrapper(t.getValue().id.toString()));
        table.getColumns().add(colId);
        TableColumn<PojoTableItem, String> colCreated = new TableColumn<>("Создано");
        colCreated.setCellValueFactory(t -> new ReadOnlyStringWrapper(new Date(t.getValue().dto.created).toString()));
        table.getColumns().add(colCreated);
        Pojo pojo = ConfigurationManager.getConfiguration().getPojo(viewPart.getPojo());
        if (pojo == null) {
            Dialogs.create().message("Pojo " + viewPart.getPojo() + " не найден").showError();
        } else {
            for (String fieldId : pojo.getFields().keySet()) {
                TableColumn<PojoTableItem, String> colDescription = new TableColumn<>(fieldId);
                colDescription.setCellValueFactory(t -> new ReadOnlyObjectWrapper<>(t.getValue().dto.fields.get(fieldId)));
                table.getColumns().add(colDescription);
            }
            loadService.start();
            table.getItems().clear();
            loadService.setOnSucceeded(workerStateEvent -> {
                onLoaded(true);
            });
        }
    }

    private void onLoaded(boolean updateScroll) {
        ObservableList<PojoTableItem> items = table.getItems();
        items.addAll(loadService.getValue());
        if (updateScroll) {
            ScrollBar bar = getVerticalScrollbar(table);
            bar.valueProperty().addListener(this::scrolled);
        }
        count = loadService.getCount();
        lblCount.setText(count.toString());
        page = loadService.getPage();
        lblPage.setText(page.toString());
    }

    public void add(ActionEvent actionEvent) {
        final Pair<Stage, PojoEditDialog> dialogPair = StageHelper.<PojoEditDialog>openWindow("d_create_simple_bean.fxml", "редактирование");
        ObjectDTO objectDTO = new ObjectDTO();
        objectDTO.type = viewPart.getPojo();
        objectDTO.id = 0;
        Pojo pojo = ConfigurationManager.getConfiguration().getPojo(viewPart.getPojo());
        if (pojo == null) {
            Dialogs.create().title("Ошибка").message("Ошибка конфигурации, не найден pojo: " + viewPart.getPojo()).showError();
        } else {
            objectDTO.fields = new HashMap<>(pojo.getFields());
            dialogPair.getValue().setReturnValue(objectDTO);
            dialogPair.getValue().setWorkIsDoneListener((code, msg) -> {
                dialogPair.getKey().close();
                ObjectDTO dto = dialogPair.getValue().getReturnValue();
                newObjects.add(dto);
                table.getItems().addAll(processObjectDTO(dto));
            });
        }
    }

    public void save(ActionEvent actionEvent) {
        SendObjectsService sendObjectsService = new SendObjectsService(newObjects);
        Dialogs.create().owner(currStage).showWorkerProgress(sendObjectsService);
        sendObjectsService.reset();
        sendObjectsService.start();
        sendObjectsService.setOnSucceeded(workerStateEvent -> newObjects.clear());
    }

    public void delete(ActionEvent actionEvent) {
        final PojoTableItem item = table.getSelectionModel().getSelectedItem();
        if (item != null) {
            DeleteObjectService deleteObjectService = new DeleteObjectService(item.id);
            Dialogs.create().owner(currStage).showWorkerProgress(deleteObjectService);
            deleteObjectService.reset();
            deleteObjectService.start();
            deleteObjectService.setOnSucceeded(workerStateEvent -> table.getItems().remove(item));
        }
    }

    private void fillTableColumns(Map<String, String> fields) {

    }

    protected void scrolled(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        double value = newValue.doubleValue();
        ScrollBar bar = getVerticalScrollbar(table);
        if (value == bar.getMax()) {
            double targetValue = value * table.getItems().size();
            if (!loadService.isRunning() && count >= page * loadService.getPerpage()) {
                loadService = new LoadService();
                loadService.setPage(page + 1);
                loadService.start();
                loadService.setOnSucceeded(workerStateEvent -> {
                    onLoaded(false);
                    bar.setValue(targetValue / table.getItems().size());
                });
            }

        }
    }

    private void loadMore() {

    }
}
