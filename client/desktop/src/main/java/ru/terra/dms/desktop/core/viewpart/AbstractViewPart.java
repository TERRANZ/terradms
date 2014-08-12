package ru.terra.dms.desktop.core.viewpart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ru.terra.dms.client.rest.RestService;
import ru.terra.dms.configuration.bean.ViewPart;
import ru.terra.dms.desktop.core.configuration.ConfigurationManager;
import ru.terra.dms.desktop.core.util.WorkIsDoneListener;
import ru.terra.dms.desktop.gui.parts.StageHelper;
import ru.terra.dms.desktop.gui.util.Pair;
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
                            listDTO = new RestService().getObjectsByName(viewPart.getPojo());
                        else
                            listDTO = new RestService().getObjectsByParent(parentid);
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
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() > 1) {
                    final PojoTableItem item = table.getSelectionModel().getSelectedItem();
                    if (item != null) {
                        final Pair<Stage, PojoEditDialog> dialogPair = StageHelper.<PojoEditDialog>openWindow("d_create_simple_bean.fxml", "редактирование");
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

    protected PojoTableItem processObjectDTO(ObjectDTO objectDTO) {
        PojoTableItem tableItem = new PojoTableItem();
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
}
