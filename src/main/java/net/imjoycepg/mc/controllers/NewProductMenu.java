package net.imjoycepg.mc.controllers;

import com.dustinredmond.fxalert.FXAlert;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.NewProductEntity;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class NewProductMenu implements Initializable {

    @FXML
    private Label label_idProduct, label_name, label_stock, label_dateJoin, label_idCategory;
    @FXML
    private TextField rpta_idProduct, rpta_name, rpta_stock, rpta_dateJoin, rpta_idCategory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateLanguage();
        cleanText();
    }

    private void updateLanguage(){
        label_idProduct.setText(DorisRooms.getInstance().getLanguage().getConfig().get("NewProductMenu_IDProductLabel").getAsString());
        label_name.setText(DorisRooms.getInstance().getLanguage().getConfig().get("NewProductMenu_NameProductLabel").getAsString());
        label_stock.setText(DorisRooms.getInstance().getLanguage().getConfig().get("NewProductMenu_StockLabel").getAsString());
        label_dateJoin.setText(DorisRooms.getInstance().getLanguage().getConfig().get("NewProductMenu_DateJoinLabel").getAsString());
        label_idCategory.setText(DorisRooms.getInstance().getLanguage().getConfig().get("NewProductMenu_IDCategoryLabel").getAsString());
    }

    private void cleanText(){
        rpta_idProduct.setText("");
        rpta_name.setText("");
        rpta_stock.setText("");
        rpta_dateJoin.setText("");
        rpta_idCategory.setText("");
        rpta_idProduct.requestFocus();
    }

    @FXML
    public void registerButton(){
        if(rpta_idProduct.getText().isEmpty() || rpta_name.getText().isEmpty() ||
            rpta_stock.getText().isEmpty() || rpta_dateJoin.getText().isEmpty() ||
            rpta_idCategory.getText().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }

        String idProduct = rpta_idProduct.getText();
        if(DorisRooms.getInstance().getNewProductTable().findProduct(idProduct) != null){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("NewProductMenu_FindTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("NewProductMenu_FindDescription").getAsString());
            cleanText();
            return;
        }

        if(DorisRooms.getInstance().getCategoryProdTable().findCategory(rpta_idCategory.getText()) == null){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("NewProductMenu_NotFindTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("NewProductMenu_NotFindDescription").getAsString());
            rpta_idCategory.setText("");
            rpta_idCategory.requestFocus();
            return;
        }

        NewProductEntity newProductEntity = new NewProductEntity();
        newProductEntity.setIdProduct(idProduct);
        newProductEntity.setNameProduct(rpta_name.getText());
        newProductEntity.setStockProduct(Integer.parseInt(rpta_stock.getText()));
        newProductEntity.setDateJoin(Date.valueOf(rpta_dateJoin.getText()));
        newProductEntity.setIdCategory(rpta_idCategory.getText());
        DorisRooms.getInstance().getNewProductTable().insertProduct(newProductEntity);

        cleanText();
        closeSubMenu();
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
