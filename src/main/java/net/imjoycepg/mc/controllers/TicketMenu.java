package net.imjoycepg.mc.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.OrderEntity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TicketMenu implements Initializable {
    @FXML
    private TableView<OrderEntity> table_model;
    @FXML
    private TableColumn<OrderEntity, String> table_column_idOrder, table_column_dateOrder, table_column_dniEmployee, table_column_rucProved;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableModel();
    }

    private void tableModel() {
        table_column_idOrder.setCellValueFactory(new PropertyValueFactory<>("idOrder"));
        table_column_dateOrder.setCellValueFactory(new PropertyValueFactory<>("dateOrder"));
        table_column_dniEmployee.setCellValueFactory(new PropertyValueFactory<>("dniEmployee"));
        table_column_rucProved.setCellValueFactory(new PropertyValueFactory<>("rucProved"));

        ObservableList<OrderEntity> loginObservableList = DorisRooms.getInstance().getOrderTable().showOrder();
        table_model.setItems(loginObservableList);
        table_model.refresh();
    }

    @FXML
    public void backMenu() {
        try {
            DorisRooms.getInstance().sceneManageProduct();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void reportPDF(){
        if(table_model.getSelectionModel().getSelectedItem() != null){
            DorisRooms.getInstance().getOrderProductTemp().setIdOrder(table_model.getSelectionModel().getSelectedItem().getIdOrder());
            DorisRooms.getInstance().getReportUtil().FactureReportPDF();
        }
    }
}
