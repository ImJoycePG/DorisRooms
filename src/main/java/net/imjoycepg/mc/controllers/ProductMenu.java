package net.imjoycepg.mc.controllers;

import com.dustinredmond.fxalert.FXAlert;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.NewProductEntity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductMenu implements Initializable {

    @FXML
    private TableView<NewProductEntity> table_model;
    @FXML
    private TableColumn<NewProductEntity, String> table_column_idCategory, table_column_idProduct, table_column_nameProduct, table_column_stockProduct, table_column_dateJoin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableModel();
    }

    private void tableModel(){
        table_column_idProduct.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
        table_column_nameProduct.setCellValueFactory(new PropertyValueFactory<>("nameProduct"));
        table_column_stockProduct.setCellValueFactory(new PropertyValueFactory<>("stockProduct"));
        table_column_dateJoin.setCellValueFactory(new PropertyValueFactory<>("dateJoin"));
        table_column_idCategory.setCellValueFactory(new PropertyValueFactory<>("idCategory"));

        ObservableList<NewProductEntity> loginObservableList = DorisRooms.getInstance().getNewProductTable().showProducts();
        table_model.setItems(loginObservableList);
        table_model.refresh();
    }

    @FXML
    public void openNewProduct(){

        try {
            DorisRooms.getInstance().sceneManageNewProduct();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void modifyProduct() {
        if (table_model.getSelectionModel().getSelectedItem() == null) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("SelectTableViewTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("SelectTableViewDescription").getAsString());
            return;
        }

        String idProduct = table_model.getSelectionModel().getSelectedItem().getIdProduct();
        try {
            if (DorisRooms.getInstance().getNewProductTable().findProduct(idProduct) != null) {
                DorisRooms.getInstance().getProductTemp().setIdProduct(idProduct);
                DorisRooms.getInstance().getProductTemp().setNameProduct(table_model.getSelectionModel().getSelectedItem().getNameProduct());
                DorisRooms.getInstance().getProductTemp().setStockProduct(table_model.getSelectionModel().getSelectedItem().getStockProduct());
                DorisRooms.getInstance().getProductTemp().setDateJoin(table_model.getSelectionModel().getSelectedItem().getDateJoin());
                DorisRooms.getInstance().getProductTemp().setIdCategory(table_model.getSelectionModel().getSelectedItem().getIdCategory());

                DorisRooms.getInstance().sceneManageEditProduct();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void deleteProduct(){
        if (table_model.getSelectionModel().getSelectedItem() == null) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("SelectTableViewTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("SelectTableViewDescription").getAsString());
            return;
        }

        String idProduct = table_model.getSelectionModel().getSelectedItem().getIdProduct();
        DorisRooms.getInstance().getNewProductTable().deleteProduct(idProduct);
        tableModel();
    }

    @FXML
    public void openCategoryMenu(){
        try {
            DorisRooms.getInstance().sceneManageCategoryMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void backMenu(){
        try {
            DorisRooms.getInstance().sceneMainMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
