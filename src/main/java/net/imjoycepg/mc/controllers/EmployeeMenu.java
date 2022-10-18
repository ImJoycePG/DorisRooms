package net.imjoycepg.mc.controllers;

import com.dustinredmond.fxalert.FXAlert;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.EmployeeEntity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

public class EmployeeMenu implements Initializable {

    @FXML
    private TableView<EmployeeEntity> table_model;
    @FXML
    private TableColumn<EmployeeEntity, String> table_column_dni, table_column_names, table_column_surnames;
    @FXML
    private TextField rpta_dni, rpta_names, rpta_surnames;
    @FXML
    private Button buttonLoad, buttonRegister, buttonEdit, buttonUpdate, buttonDelete;
    @FXML
    private Label label_dni, label_name, label_surnames;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateLanguage();
        tableModel();
        ButtonDisable();
    }

    private void updateLanguage(){
        label_dni.setText(DorisRooms.getInstance().getLanguage().getConfig().get("EmployeeMenu_DNILabel").getAsString());
        label_name.setText(DorisRooms.getInstance().getLanguage().getConfig().get("EmployeeMenu_NamesLabel").getAsString());
        label_surnames.setText(DorisRooms.getInstance().getLanguage().getConfig().get("EmployeeMenu_SurnamesLabel").getAsString());
        buttonLoad.setText(DorisRooms.getInstance().getLanguage().getConfig().get("EmployeeMenu_ButtonLoadText").getAsString());
        buttonRegister.setText(DorisRooms.getInstance().getLanguage().getConfig().get("EmployeeMenu_ButtonRegisterText").getAsString());
        buttonEdit.setText(DorisRooms.getInstance().getLanguage().getConfig().get("EmployeeMenu_ButtonEditText").getAsString());
        buttonUpdate.setText(DorisRooms.getInstance().getLanguage().getConfig().get("EmployeeMenu_ButtonUpdateText").getAsString());
        buttonDelete.setText(DorisRooms.getInstance().getLanguage().getConfig().get("EmployeeMenu_ButtonDeleteText").getAsString());
        table_column_dni.setText(DorisRooms.getInstance().getLanguage().getConfig().get("EmployeeMenu_NamesLabel").getAsString());
        table_column_names.setText(DorisRooms.getInstance().getLanguage().getConfig().get("EmployeeMenu_NamesLabel").getAsString());
        table_column_surnames.setText(DorisRooms.getInstance().getLanguage().getConfig().get("EmployeeMenu_NamesLabel").getAsString());
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

    private void tableModel(){
        table_column_dni.setCellValueFactory(new PropertyValueFactory<>("dniEmployee"));
        table_column_names.setCellValueFactory(new PropertyValueFactory<>("namesEmployee"));
        table_column_surnames.setCellValueFactory(new PropertyValueFactory<>("surnamesEmployee"));

        ObservableList<EmployeeEntity> loginObservableList = DorisRooms.getInstance().getEmployeeTable().showEmployees();
        table_model.setItems(loginObservableList);
        table_model.refresh();
    }

    private void cleanText(){
        rpta_dni.setText("");
        rpta_names.setText("");
        rpta_surnames.setText("");
        rpta_dni.requestFocus();
    }

    @FXML
    public void registerEmployee(){
        if(rpta_dni.getText().isEmpty() || rpta_names.getText().isEmpty() || rpta_surnames.getText().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }
        String dniEmployee = rpta_dni.getText();
        if(DorisRooms.getInstance().getEmployeeTable().findEmployee(dniEmployee) != null){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("EmployeeMenu_FindTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("EmployeeMenu_FindDescription").getAsString());
            cleanText();
            return;
        }

        EmployeeEntity employee = new EmployeeEntity();
        employee.setDniEmployee(dniEmployee);
        employee.setNamesEmployee(rpta_names.getText());
        employee.setSurnamesEmployee(rpta_surnames.getText());
        DorisRooms.getInstance().getEmployeeTable().insertEmployee(employee);

        tableModel();
        cleanText();
    }

    @FXML
    public void modifyEmployee(){
        if(rpta_dni.getText().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }
        String dniEmployee = rpta_dni.getText();
        if(DorisRooms.getInstance().getEmployeeTable().findEmployee(dniEmployee) == null){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("EmployeeMenu_NotFindTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("EmployeeMenu_NotFindDescription").getAsString());
            cleanText();
            return;
        }

        EmployeeEntity employee = DorisRooms.getInstance().getEmployeeTable().findEmployee(dniEmployee);
        rpta_names.setText(employee.getNamesEmployee());
        rpta_surnames.setText(employee.getSurnamesEmployee());
        ButtonEnable();
    }

    @FXML
    public void updateEmployee(){
        if(rpta_dni.getText().isEmpty() || rpta_names.getText().isEmpty() || rpta_surnames.getText().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }

        EmployeeEntity employee = new EmployeeEntity(rpta_dni.getText(), rpta_names.getText(), rpta_surnames.getText());
        DorisRooms.getInstance().getEmployeeTable().updateEmployee(employee);
        FXAlert.showInfo(DorisRooms.getInstance().getLanguage().getConfig().get("EmployeeMenu_AlertUpdateTitle").getAsString(),
                null, DorisRooms.getInstance().getLanguage().getConfig().get("EmployeeMenu_AlertUpdateDescription").getAsString());
        tableModel();
        ButtonDisable();
        cleanText();
    }

    @FXML
    public void deleteEmployee(){
        String dniEmployee = rpta_dni.getText();
        DorisRooms.getInstance().getEmployeeTable().deleteEmployee(dniEmployee);
        FXAlert.showInfo(DorisRooms.getInstance().getLanguage().getConfig().get("EmployeeMenu_AlertDeleteTitle").getAsString(),
                null, DorisRooms.getInstance().getLanguage().getConfig().get("EmployeeMenu_AlertDeleteDescription").getAsString());

        tableModel();
        ButtonDisable();
        cleanText();
    }

    @FXML
    public void loadEmployee(){
        if(rpta_dni.getText().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }

        String link = "https://api.apis.net.pe/v1/dni?numero=" + rpta_dni.getText();

        try{
            URL url = new URL(link);
            URLConnection request = url.openConnection();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject rootOBJ = root.getAsJsonObject();
            if(root.isJsonObject()) {
                rpta_names.setText(rootOBJ.get("nombres").getAsString());
                rpta_surnames.setText(rootOBJ.get("apellidoPaterno").getAsString() + " " + rootOBJ.get("apellidoMaterno").getAsString());
            }
        }catch (Exception ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, "");
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
