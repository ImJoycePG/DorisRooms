package net.imjoycepg.mc.controllers;

import com.dustinredmond.fxalert.FXAlert;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.RoomsTypeEntity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RoomsTypeMenu implements Initializable {

    @FXML
    private TableView<RoomsTypeEntity> table_model;
    @FXML
    private TableColumn<RoomsTypeEntity, String> table_column_idTypeRooms, table_column_nameTypeRooms;

    @FXML
    private Label label_idTypeRooms, label_name;
    @FXML
    private TextField rpta_idTypeRooms, rpta_name;
    @FXML
    private Button buttonRegister, buttonEdit, buttonUpdate, buttonDelete;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateLanguage();
        tableModel();
        ButtonDisable();
    }

    private void updateLanguage(){
        label_idTypeRooms.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsTypeMenu_IDTypeRoomsLabel").getAsString());
        label_name.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsTypeMenu_NameTypeRooms").getAsString());
        table_column_idTypeRooms.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsTypeMenu_TableIDTypeRooms").getAsString());
        table_column_nameTypeRooms.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsTypeMenu_TableNameTypeRooms").getAsString());
    }

    private void tableModel(){
        table_column_idTypeRooms.setCellValueFactory(new PropertyValueFactory<>("idTypeRooms"));
        table_column_nameTypeRooms.setCellValueFactory(new PropertyValueFactory<>("nameTypeRooms"));

        ObservableList<RoomsTypeEntity> loginObservableList = DorisRooms.getInstance().getRoomsTypeTable().showTypes();
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
        rpta_idTypeRooms.setText("");
        rpta_name.setText("");
        rpta_idTypeRooms.requestFocus();
    }

    @FXML
    public void registerType(){
        if(rpta_idTypeRooms.getText().isEmpty() || rpta_name.getText().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }

        String idTypeRooms = rpta_idTypeRooms.getText();

        if(DorisRooms.getInstance().getRoomsTypeTable().findType(idTypeRooms) != null){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsTypeMenu_FindTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("RoomsTypeMenu_FindDescription").getAsString());
            rpta_idTypeRooms.setText("");
            rpta_idTypeRooms.requestFocus();
        }

        RoomsTypeEntity roomsTypeEntity = new RoomsTypeEntity(idTypeRooms, rpta_name.getText());
        DorisRooms.getInstance().getRoomsTypeTable().insertRoomsType(roomsTypeEntity);

        tableModel();
        cleanText();
    }

    @FXML
    public void findType(){
        if(rpta_idTypeRooms.getText().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }

        String idTypeRooms = rpta_idTypeRooms.getText();

        if(DorisRooms.getInstance().getRoomsTypeTable().findType(idTypeRooms) == null){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsTypeMenu_NotFindTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("RoomsTypeMenu_NotFindDescription").getAsString());
            rpta_idTypeRooms.setText("");
            rpta_idTypeRooms.requestFocus();
        }

        RoomsTypeEntity roomsTypeEntity = DorisRooms.getInstance().getRoomsTypeTable().findType(idTypeRooms);
        rpta_name.setText(roomsTypeEntity.getNameTypeRooms());

        ButtonEnable();
    }

    @FXML
    public void updateType(){
        if(rpta_idTypeRooms.getText().isEmpty() || rpta_name.getText().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }

        RoomsTypeEntity roomsTypeEntity = new RoomsTypeEntity(rpta_idTypeRooms.getText(), rpta_name.getText());
        DorisRooms.getInstance().getRoomsTypeTable().updateType(roomsTypeEntity);

        cleanText();
        ButtonDisable();
        tableModel();
    }

    @FXML
    public void deleteType(){
        if(rpta_idTypeRooms.getText().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }

        DorisRooms.getInstance().getRoomsTypeTable().deleteType(rpta_idTypeRooms.getText());
        ButtonDisable();
        cleanText();
        tableModel();
    }

    @FXML
    public void closeSubMenu(){
        try {
            DorisRooms.getInstance().sceneManageRooms();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
