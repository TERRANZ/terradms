package ru.terra.dms.desktop.core.viewpart.impl;

import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import ru.terra.dms.desktop.core.annotations.ViewPart;
import ru.terra.dms.desktop.core.viewpart.AbstractViewPart;

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
        Long id = 0L;
        public List<String> values = new ArrayList<>();
    }

    @FXML
    public Button btnRefresh;
    @FXML
    public TableView<SimpleTableItem> table;

    private class LoadService extends Service<ObservableList<List<SimpleTableItem>>> {
        @Override
        protected Task<ObservableList<List<SimpleTableItem>>> createTask() {
            return new Task<ObservableList<List<SimpleTableItem>>>() {
                @Override
                protected ObservableList<List<SimpleTableItem>> call() throws Exception {

                    return null;
                }
            };
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnRefresh.setOnAction(actionEvent -> load());
    }

    public static void load() {
    }
}
