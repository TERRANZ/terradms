package ru.terra.dms.desktop.core.viewpart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ru.terra.dms.configuration.bean.ViewPart;
import ru.terra.dms.desktop.core.configuration.ConfigurationManager;
import ru.terra.dms.desktop.core.util.Pair;
import ru.terra.dms.desktop.gui.parts.StageHelper;
import ru.terra.dms.rest.RestService;
import ru.terra.dms.shared.dto.ObjectDTO;
import ru.terra.server.dto.ListDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 26.05.14
 * Time: 15:42
 */
public abstract class AbstractViewPart extends AbstractWindow {
    protected String viewPartName;
    protected ViewPart viewPart;

    public class LoadService extends Service<ObservableList<PojoTableItem>> {
        private Integer parentid;

        public LoadService(Integer parentid) {
            this.parentid = parentid;
        }

        public LoadService() {
        }

        @Override
        protected Task<ObservableList<PojoTableItem>> createTask() {
            return new Task<ObservableList<PojoTableItem>>() {
                @Override
                protected ObservableList<PojoTableItem> call() throws Exception {
                    if (viewPart == null)
                        return null;
                    List<PojoTableItem> ret = new ArrayList<>();
                    try {
                        ListDTO<ObjectDTO> listDTO;
                        if (parentid == null)
                            listDTO = RestService.getInstance().getObjectsByName(viewPart.getPojo(), 1, 100);
                        else
                            listDTO = RestService.getInstance().getObjectsByParent(parentid);
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

    public String getViewPartName() {
        return viewPartName;
    }

    public void setViewPartName(String viewPartName) {
        this.viewPartName = viewPartName;
    }

    public void load() {
        viewPart = ConfigurationManager.getConfiguration().getViewPart(viewPartName);
        loadInternal();
    }

    protected abstract void loadInternal();

    protected void addEditingToTable(final TableView<PojoTableItem> table) {
        table.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() > 1) {
                final PojoTableItem item = table.getSelectionModel().getSelectedItem();
                if (item != null) {
                    final Pair<Stage, PojoEditDialog> dialogPair = StageHelper.<PojoEditDialog>openWindow("d_create_simple_bean.fxml", "редактирование");
                    dialogPair.getValue().setReturnValue(item.dto);
                    dialogPair.getValue().setWorkIsDoneListener((code, msg) -> {
                        int index = table.getItems().indexOf(item);
                        table.getItems().remove(item);
                        table.getItems().add(index, processObjectDTO(dialogPair.getValue().getReturnValue()));
                    });
                }
            }
        });
    }

    protected PojoTableItem processObjectDTO(ObjectDTO objectDTO) {
        PojoTableItem tableItem = new PojoTableItem();
        tableItem.id = objectDTO.id;
        tableItem.dto = objectDTO;
        return tableItem;
    }
}
