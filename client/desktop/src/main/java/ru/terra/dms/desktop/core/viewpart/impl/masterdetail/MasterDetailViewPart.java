package ru.terra.dms.desktop.core.viewpart.impl.masterdetail;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import ru.terra.dms.desktop.core.annotations.ViewPartWindow;
import ru.terra.dms.desktop.core.viewpart.AbstractViewPart;
import ru.terra.dms.desktop.core.viewpart.PojoTableItem;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Date: 18.07.14
 * Time: 9:52
 */
@ViewPartWindow(name = "masterdetail", fxml = "p_master_detail.fxml")
public class MasterDetailViewPart extends AbstractViewPart {
    @FXML
    public TableView<PojoTableItem> tblMaster;
    @FXML
    public TableView<PojoTableItem> tblDetail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addEditingToTable(tblMaster);
        addEditingToTable(tblDetail);
        tblMaster.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() > 1) {
                PojoTableItem tableItem = tblMaster.getSelectionModel().getSelectedItem();
                loadDetailPojo(tableItem.dto.id);
            }
        });
    }

    @Override
    protected void loadInternal() {
        final LoadService loadService = new LoadService();
        loadService.start();
        loadService.setOnSucceeded(workerStateEvent -> {
            tblMaster.getItems().clear();
            tblMaster.setItems(loadService.getValue());
        });
    }

    private void loadDetailPojo(Integer parentId) {
        final LoadService loadService = new LoadService(parentId);
        loadService.start();
        loadService.setOnSucceeded(workerStateEvent -> {
            tblDetail.getItems().clear();
            tblDetail.setItems(loadService.getValue());
        });
    }
}
