package net.imjoycepg.mc.controllers;

import com.dustinredmond.fxalert.FXAlert;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.OrderEntity;
import net.imjoycepg.mc.utils.entity.OrderProductEntity;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class OrderMenu implements Initializable {

    @FXML
    private DatePicker rpta_date;
    @FXML
    private TextField rpta_stock, rpta_price;
    @FXML
    private ListView<String> listProducts, listSelected;
    @FXML
    private Button buttonRegister, buttonJoin, buttonLeave, buttonDelete;
    @FXML
    private ComboBox<String> rpta_dni, rpta_ruc;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rpta_date.getEditor().setText(formatter.format(LocalDate.now()));
        rpta_dni.setItems(DorisRooms.getInstance().getOrderTable().listEmployee());
        rpta_ruc.setItems(DorisRooms.getInstance().getOrderTable().listProved());
        listProducts.setItems(DorisRooms.getInstance().getOrderTable().listProducts());
        offProducts();
    }

    private void onProducts(){
        rpta_stock.setEditable(true);
        rpta_price.setEditable(true);
        buttonJoin.setDisable(false);
        buttonLeave.setDisable(false);
        buttonRegister.setDisable(true);
        buttonDelete.setDisable(false);
        rpta_date.getEditor().setEditable(false);
        rpta_ruc.setEditable(false);
        rpta_dni.setEditable(false);
    }

    private void offProducts(){
        rpta_stock.setEditable(false);
        rpta_price.setEditable(false);
        buttonJoin.setDisable(true);
        buttonLeave.setDisable(true);
        buttonRegister.setDisable(false);
        buttonDelete.setDisable(true);
        rpta_date.getEditor().setEditable(true);
        rpta_ruc.setEditable(true);
        rpta_dni.setEditable(true);
    }

    private void cleanAll(){
        rpta_date.getEditor().setText("");
        rpta_ruc.setValue("");
        rpta_dni.setValue("");
        rpta_stock.setText("");
        rpta_price.setText("");
        for(int i = 0; i < listSelected.getItems().size(); i++) {
            DorisRooms.getInstance().getOrderTable().deleteOrderProduct(listSelected.getItems().get(i).substring(0, 5));
        }

        listSelected.getItems().clear();
        listProducts.setItems(DorisRooms.getInstance().getOrderTable().listProducts());

    }

    @FXML
    private void changeDate(){
        rpta_date.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalDate localDate) {
                return (rpta_date != null) ? formatter.format(localDate) : "";
            }

            @Override
            public LocalDate fromString(String s) {
                return (s != null && !s.isEmpty())
                        ? LocalDate.parse(s, formatter) : null;
            }
        });
    }

    @FXML
    public void registerOrder(){
        if(rpta_date.getEditor().getText().isEmpty() || rpta_dni.getValue().isEmpty() || rpta_ruc.getValue().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }

        if(DorisRooms.getInstance().getEmployeeTable().findEmployee(rpta_dni.getValue().substring(0, 8)) == null &&
            DorisRooms.getInstance().getProvedTable().findProved(rpta_ruc.getValue().substring(0, 11)) == null){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("OrderMenu_NotFindTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("OrderMenu_NotFindDescription").getAsString());
            return;
        }

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setIdOrder(DorisRooms.getInstance().getOrderProductTemp().getIdOrder());
        orderEntity.setDateOrder(java.sql.Date.valueOf(rpta_date.getEditor().getText()));
        orderEntity.setDniEmployee(rpta_dni.getValue().substring(0, 8));
        orderEntity.setRucProved(rpta_ruc.getValue().substring(0, 11));
        DorisRooms.getInstance().getOrderTable().insertOrder(orderEntity);

        onProducts();
    }


    @FXML
    public void addProduct(){
        if(listProducts.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        if(rpta_price.getText().isEmpty() || rpta_stock.getText().isEmpty()) {
            return;
        }
        String selectedItem = listProducts.getSelectionModel().getSelectedItem();
        listProducts.getItems().remove(selectedItem);
        listSelected.getItems().add(selectedItem);

        for(int i = 0; i < listSelected.getItems().size(); i++){
            OrderProductEntity orderProductEntity = new OrderProductEntity();
            orderProductEntity.setIdOrderProd(DorisRooms.getInstance().getUtilities().orderUUID());
            orderProductEntity.setPriceOrderProd(Double.parseDouble(rpta_price.getText()));
            orderProductEntity.setStockOrderProd(Integer.parseInt(rpta_stock.getText()));
            orderProductEntity.setIdProduct(listSelected.getItems().get(i).substring(0, 5));
            orderProductEntity.setIdOrder(DorisRooms.getInstance().getOrderProductTemp().getIdOrder());
            DorisRooms.getInstance().getOrderTable().insertOrderProduct(orderProductEntity);
        }

        rpta_stock.setText("");
        rpta_price.setText("");
    }

    @FXML
    public void deleteProduct(){
        if(listSelected.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        String selectedItem = listSelected.getSelectionModel().getSelectedItem();
        for(int i = 0; i < listSelected.getItems().size(); i++) {
            DorisRooms.getInstance().getOrderTable().deleteOrderProduct(selectedItem.substring(0, 5));
        }
        listSelected.getItems().remove(selectedItem);
        listProducts.getItems().add(selectedItem);

    }

    @FXML
    public void removeOrder(){
        cleanAll();
        DorisRooms.getInstance().getOrderTable().deleteOrder(DorisRooms.getInstance().getOrderProductTemp().getIdOrder());
        offProducts();
    }



    @FXML
    public void closeSubMenu(){
        try {
            DorisRooms.getInstance().sceneManageProduct();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
