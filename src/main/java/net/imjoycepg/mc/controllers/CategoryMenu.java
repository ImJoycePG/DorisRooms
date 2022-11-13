package net.imjoycepg.mc.controllers;

import com.dustinredmond.fxalert.FXAlert;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.CategoryProdEntity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CategoryMenu implements Initializable {
    @FXML
    private TableView<CategoryProdEntity> table_model;
    @FXML
    private TableColumn<CategoryProdEntity, String> table_column_idCategory, table_column_name, table_column_desc;
    @FXML
    private Button buttonRegister, buttonEdit, buttonUpdate, buttonDelete;
    @FXML
    private Label label_idCategory, label_name, label_desc;
    @FXML
    private TextField rpta_name, rpta_idCategory, rpta_desc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateLanguage();
        tableModel();
        ButtonDisable();
    }

    private void updateLanguage(){
        label_idCategory.setText(DorisRooms.getInstance().getLanguage().getConfig().get("CategoryMenu_IDCategoryLabel").getAsString());
        label_name.setText(DorisRooms.getInstance().getLanguage().getConfig().get("CategoryMenu_NamesLabel").getAsString());
        label_desc.setText(DorisRooms.getInstance().getLanguage().getConfig().get("CategoryMenu_DescriptionLabel").getAsString());
        buttonRegister.setText(DorisRooms.getInstance().getLanguage().getConfig().get("CategoryMenu_ButtonRegisterText").getAsString());
        buttonEdit.setText(DorisRooms.getInstance().getLanguage().getConfig().get("CategoryMenu_ButtonEditText").getAsString());
        buttonUpdate.setText(DorisRooms.getInstance().getLanguage().getConfig().get("CategoryMenu_ButtonUpdateText").getAsString());
        buttonDelete.setText(DorisRooms.getInstance().getLanguage().getConfig().get("CategoryMenu_ButtonDeleteText").getAsString());
    }

    private void tableModel(){
        table_column_idCategory.setCellValueFactory(new PropertyValueFactory<>("idCategory"));
        table_column_name.setCellValueFactory(new PropertyValueFactory<>("nameCategory"));
        table_column_desc.setCellValueFactory(new PropertyValueFactory<>("descCategory"));

        ObservableList<CategoryProdEntity> loginObservableList = DorisRooms.getInstance().getCategoryProdTable().showCategory();
        table_model.setItems(loginObservableList);
        table_model.refresh();
    }

    private void ButtonEnable(){
        buttonUpdate.setDisable(false);
        buttonDelete.setDisable(false);
        buttonEdit.setDisable(true);
        buttonRegister.setDisable(true);
    }
    private void ButtonDisable(){
        buttonUpdate.setDisable(true);
        buttonDelete.setDisable(true);
        buttonEdit.setDisable(false);
        buttonRegister.setDisable(false);
    }

    private void cleanText(){
        rpta_idCategory.setText("");
        rpta_name.setText("");
        rpta_desc.setText("");
        rpta_idCategory.requestFocus();
    }

    @FXML
    public void registerCategory(){
        if(rpta_idCategory.getText().isEmpty() || rpta_name.getText().isEmpty() || rpta_desc.getText().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }
        String idCategory = rpta_idCategory.getText();
        if(DorisRooms.getInstance().getCategoryProdTable().findCategory(idCategory) != null){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("CategoryMenu_FindTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("CategoryMenu_FindDescription").getAsString());
            cleanText();
            return;
        }

        CategoryProdEntity categoryProd = new CategoryProdEntity();
        categoryProd.setIdCategory(idCategory);
        categoryProd.setNameCategory(rpta_name.getText());
        categoryProd.setDescCategory(rpta_desc.getText());
        DorisRooms.getInstance().getCategoryProdTable().insertCategory(categoryProd);
        tableModel();
        cleanText();
    }

    @FXML
    public void modifyCategory(){
        if(rpta_idCategory.getText().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }
        String idCategory = rpta_idCategory.getText();
        if(DorisRooms.getInstance().getCategoryProdTable().findCategory(idCategory) == null){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("CategoryMenu_NotFindTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("CategoryMenu_NotFindDescription").getAsString());
            cleanText();
            return;
        }

        CategoryProdEntity categoryProd = DorisRooms.getInstance().getCategoryProdTable().findCategory(idCategory);
        rpta_name.setText(categoryProd.getNameCategory());
        rpta_desc.setText(categoryProd.getDescCategory());
        ButtonEnable();
    }

    @FXML
    public void updateCategory(){
        if(rpta_idCategory.getText().isEmpty() || rpta_name.getText().isEmpty() || rpta_desc.getText().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }

        CategoryProdEntity categoryProd = new CategoryProdEntity(rpta_idCategory.getText(), rpta_name.getText(), rpta_desc.getText());
        DorisRooms.getInstance().getCategoryProdTable().updateCategory(categoryProd);
        FXAlert.showInfo(DorisRooms.getInstance().getLanguage().getConfig().get("CategoryMenu_AlertUpdateTitle").getAsString(),
                null, DorisRooms.getInstance().getLanguage().getConfig().get("CategoryMenu_AlertUpdateDescription").getAsString());
        ButtonDisable();
        cleanText();
        tableModel();
    }

    @FXML
    public void deleteCategory(){
        String idCategory = rpta_idCategory.getText();
        DorisRooms.getInstance().getCategoryProdTable().deleteCategory(idCategory);
        FXAlert.showInfo(DorisRooms.getInstance().getLanguage().getConfig().get("CategoryMenu_AlertDeleteTitle").getAsString(),
                null, DorisRooms.getInstance().getLanguage().getConfig().get("CategoryMenu_AlertDeleteDescription").getAsString());

        ButtonDisable();
        cleanText();
        tableModel();
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
