package net.imjoycepg.mc.controllers;

import com.dustinredmond.fxalert.FXAlert;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.LoginEntity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeesMenu implements Initializable {
    @FXML
    private TableView<LoginEntity> table_model;
    @FXML
    private TableColumn<LoginEntity, String> table_column_username, table_column_password, table_column_function;
    @FXML
    private ChoiceBox<String> role_select;
    @FXML
    private Button button_register, button_edit, button_update, button_delete;
    @FXML
    private Label label_username, label_password, label_rol;
    @FXML
    private TextField rpta_username, rpta_password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        role_select.getItems().addAll("Administrator", "Employee");
        role_select.setValue("Employee");
        updateLanguage();
        tableModel();
    }

    private void updateLanguage(){
        label_username.setText(DorisRooms.getInstance().getLanguage().getConfig().get("AdministratorMenu_AdminUsernameLabel").getAsString());
        label_password.setText(DorisRooms.getInstance().getLanguage().getConfig().get("AdministratorMenu_AdminPasswordLabel").getAsString());
        label_rol.setText(DorisRooms.getInstance().getLanguage().getConfig().get("AdministratorMenu_AdminRoleLabel").getAsString());
        button_register.setText(DorisRooms.getInstance().getLanguage().getConfig().get("AdministratorMenu_AdminButtonSaveText").getAsString());
        button_edit.setText(DorisRooms.getInstance().getLanguage().getConfig().get("AdministratorMenu_AdminButtonModifyText").getAsString());
        button_update.setText(DorisRooms.getInstance().getLanguage().getConfig().get("AdministratorMenu_AdminButtonUpdateText").getAsString());
        button_delete.setText(DorisRooms.getInstance().getLanguage().getConfig().get("AdministratorMenu_AdminButtonDeleteText").getAsString());
        table_column_username.setText(DorisRooms.getInstance().getLanguage().getConfig().get("AdministratorMenu_AdminTableViewUsername").getAsString());
        table_column_password.setText(DorisRooms.getInstance().getLanguage().getConfig().get("AdministratorMenu_AdminTableViewPassword").getAsString());
        table_column_function.setText(DorisRooms.getInstance().getLanguage().getConfig().get("AdministratorMenu_AdminTableViewRole").getAsString());
    }


    private void ButtonEnable(){
        button_update.setDisable(false);
        button_delete.setDisable(false);
        button_edit.setDisable(true);
        button_register.setDisable(true);
    }
    private void ButtonDisable(){
        button_update.setDisable(true);
        button_delete.setDisable(true);
        button_edit.setDisable(false);
        button_register.setDisable(false);
    }

    private void tableModel(){
        table_column_username.setCellValueFactory(new PropertyValueFactory<>("userLogin"));
        table_column_password.setCellValueFactory(new PropertyValueFactory<>("passLogin"));
        table_column_function.setCellValueFactory(new PropertyValueFactory<>("roleLogin"));

        ObservableList<LoginEntity> loginObservableList = DorisRooms.getInstance().getLoginTable().showLoginUsers();
        table_model.setItems(loginObservableList);
        table_model.refresh();
    }

    private void cleanText(){
        rpta_password.setText("");
        rpta_username.setText("");
        role_select.setValue("Employee");
        rpta_username.requestFocus();
    }

    @FXML
    public void ButtonAdminRegister() {
        if (rpta_username.getText().isEmpty() || rpta_password.getText().isEmpty()) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }
        String user = rpta_username.getText();
        if(DorisRooms.getInstance().getLoginTable().findEmployee(user) != null){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("AdministratorMenu_AdminFindTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("AdministratorMenu_AdminFindDescription").getAsString());
            cleanText();
            return;
        }

        LoginEntity login = new LoginEntity();
        login.setIdLogin(DorisRooms.getInstance().getUtilities().randomUUID());
        login.setUserLogin(rpta_username.getText());
        login.setPassLogin(rpta_password.getText());
        login.setRoleLogin(role_select.getValue());
        DorisRooms.getInstance().getLoginTable().insertEmployee(login);

        tableModel();
        cleanText();
    }

    @FXML
    public void ButtonAdminModify(){
        if(rpta_username.getText().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }
        String user = rpta_username.getText();
        if(DorisRooms.getInstance().getLoginTable().findEmployee(user) == null){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("AdministratorMenu_AdminNotFindTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("AdministratorMenu_AdminNotFindDescription").getAsString());
            cleanText();
            return;
        }
        LoginEntity login = DorisRooms.getInstance().getLoginTable().findEmployee(user);
        rpta_username.setText(login.getUserLogin());
        rpta_password.setText(login.getPassLogin());
        role_select.setValue(login.getRoleLogin());
        ButtonEnable();
    }

    @FXML
    public void ButtonAdminUpdate(){
        if (rpta_username.getText().isEmpty() || rpta_password.getText().isEmpty()) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }
        String user = rpta_username.getText();
        String pass = rpta_password.getText();
        String rol = role_select.getValue();
        LoginEntity login = new LoginEntity(user, pass, rol);
        DorisRooms.getInstance().getLoginTable().updateUser(login);
        FXAlert.showInfo(DorisRooms.getInstance().getLanguage().getConfig().get("AdministratorMenu_AdminAlertUpdateTitle").getAsString(),
                null, DorisRooms.getInstance().getLanguage().getConfig().get("AdministratorMenu_AdminAlertUpdateDescription").getAsString());
        tableModel();
        ButtonDisable();
        cleanText();
    }

    @FXML
    public void ButtonAdminDelete(){
        String user = rpta_username.getText();
        DorisRooms.getInstance().getLoginTable().deleteEmployee(user);

        FXAlert.showInfo(DorisRooms.getInstance().getLanguage().getConfig().get("AdministratorMenu_AdminAlertDeleteTitle").getAsString(),
                null, DorisRooms.getInstance().getLanguage().getConfig().get("AdministratorMenu_AdminAlertDeleteDescription").getAsString());

        tableModel();
        ButtonDisable();
        cleanText();
    }

    @FXML
    private void backMenu(){
        try {
            DorisRooms.getInstance().sceneMainMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
