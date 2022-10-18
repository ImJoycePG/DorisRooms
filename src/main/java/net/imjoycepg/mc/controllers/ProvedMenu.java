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
import net.imjoycepg.mc.utils.entity.ProvedEntity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

public class ProvedMenu implements Initializable {
    @FXML
    private TableView<ProvedEntity> table_model;
    @FXML
    private TableColumn<ProvedEntity, String> table_column_ruc, table_column_names, table_column_surnames, table_column_phone, table_column_email;
    @FXML
    private TextField rpta_ruc, rpta_names, rpta_surnames, rpta_phone, rpta_email;
    @FXML
    private Button buttonLoad, buttonRegister, buttonEdit, buttonUpdate, buttonDelete;
    @FXML
    private Label label_ruc, label_name, label_surnames, label_phone, label_email;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateLanguage();
        tableModel();
        ButtonDisable();
    }

    private void updateLanguage(){
        label_ruc.setText(DorisRooms.getInstance().getLanguage().getConfig().get("ProvedMenu_RUCLabel").getAsString());
        label_name.setText(DorisRooms.getInstance().getLanguage().getConfig().get("ProvedMenu_NamesLabel").getAsString());
        label_surnames.setText(DorisRooms.getInstance().getLanguage().getConfig().get("ProvedMenu_SurnamesLabel").getAsString());
        label_phone.setText(DorisRooms.getInstance().getLanguage().getConfig().get("ProvedMenu_PhoneLabel").getAsString());
        label_email.setText(DorisRooms.getInstance().getLanguage().getConfig().get("ProvedMenu_EmailLabel").getAsString());
        buttonLoad.setText(DorisRooms.getInstance().getLanguage().getConfig().get("ProvedMenu_ButtonLoadText").getAsString());
        buttonRegister.setText(DorisRooms.getInstance().getLanguage().getConfig().get("ProvedMenu_ButtonRegisterText").getAsString());
        buttonEdit.setText(DorisRooms.getInstance().getLanguage().getConfig().get("ProvedMenu_ButtonEditText").getAsString());
        buttonUpdate.setText(DorisRooms.getInstance().getLanguage().getConfig().get("ProvedMenu_ButtonUpdateText").getAsString());
        buttonDelete.setText(DorisRooms.getInstance().getLanguage().getConfig().get("ProvedMenu_ButtonDeleteText").getAsString());
        table_column_ruc.setText(DorisRooms.getInstance().getLanguage().getConfig().get("ProvedMenu_TableRUCText").getAsString());
        table_column_names.setText(DorisRooms.getInstance().getLanguage().getConfig().get("ProvedMenu_TableNamesText").getAsString());
        table_column_surnames.setText(DorisRooms.getInstance().getLanguage().getConfig().get("ProvedMenu_TableSurnamesText").getAsString());
        table_column_phone.setText(DorisRooms.getInstance().getLanguage().getConfig().get("ProvedMenu_TablePhoneText").getAsString());
        table_column_email.setText(DorisRooms.getInstance().getLanguage().getConfig().get("ProvedMenu_TableEmailText").getAsString());
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
        table_column_ruc.setCellValueFactory(new PropertyValueFactory<>("rucProved"));
        table_column_names.setCellValueFactory(new PropertyValueFactory<>("namesProved"));
        table_column_surnames.setCellValueFactory(new PropertyValueFactory<>("surnamesProved"));
        table_column_phone.setCellValueFactory(new PropertyValueFactory<>("phoneProved"));
        table_column_email.setCellValueFactory(new PropertyValueFactory<>("emailProved"));

        ObservableList<ProvedEntity> loginObservableList = DorisRooms.getInstance().getProvedTable().showProved();
        table_model.setItems(loginObservableList);
        table_model.refresh();
    }

    private void cleanText(){
        rpta_ruc.setText("");
        rpta_names.setText("");
        rpta_surnames.setText("");
        rpta_phone.setText("");
        rpta_email.setText("");
        rpta_ruc.requestFocus();
    }

    @FXML
    public void registerProved(){
        if(rpta_ruc.getText().isEmpty() || rpta_names.getText().isEmpty() || rpta_surnames.getText().isEmpty()
                || rpta_phone.getText().isEmpty() || rpta_email.getText().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }
        String rucProved = rpta_ruc.getText();
        if(DorisRooms.getInstance().getProvedTable().findProved(rucProved) != null){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("ProvedMenu_FindTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("ProvedMenu_FindDescription").getAsString());
            cleanText();
            return;
        }

        ProvedEntity proved = new ProvedEntity();
        proved.setRucProved(rucProved);
        proved.setNamesProved(rpta_names.getText());
        proved.setSurnamesProved(rpta_surnames.getText());
        proved.setPhoneProved(rpta_phone.getText());
        proved.setEmailProved(rpta_email.getText());
        DorisRooms.getInstance().getProvedTable().insertProved(proved);

        tableModel();
        cleanText();
    }

    @FXML
    public void modifyProved(){
        if(rpta_ruc.getText().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }
        String dniEmployee = rpta_ruc.getText();
        if(DorisRooms.getInstance().getEmployeeTable().findEmployee(dniEmployee) == null){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("ProvedMenu_NotFindTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("ProvedMenu_NotFindDescription").getAsString());
            cleanText();
            return;
        }

        EmployeeEntity employee = DorisRooms.getInstance().getEmployeeTable().findEmployee(dniEmployee);
        rpta_names.setText(employee.getNamesEmployee());
        rpta_surnames.setText(employee.getSurnamesEmployee());
        ButtonEnable();
    }

    @FXML
    public void updateProved(){
        if(rpta_ruc.getText().isEmpty() || rpta_names.getText().isEmpty() || rpta_surnames.getText().isEmpty()
                || rpta_phone.getText().isEmpty() || rpta_email.getText().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }

        ProvedEntity employee = new ProvedEntity(rpta_ruc.getText(), rpta_names.getText(), rpta_surnames.getText(), rpta_phone.getText(), rpta_email.getText());
        DorisRooms.getInstance().getProvedTable().updateProved(employee);
        FXAlert.showInfo(DorisRooms.getInstance().getLanguage().getConfig().get("ProvedMenu_AlertUpdateTitle").getAsString(),
                null, DorisRooms.getInstance().getLanguage().getConfig().get("ProvedMenu_AlertUpdateDescription").getAsString());
        tableModel();
        ButtonDisable();
        cleanText();
    }

    @FXML
    public void deleteEmployee(){
        String dniEmployee = rpta_ruc.getText();
        DorisRooms.getInstance().getProvedTable().deleteProved(dniEmployee);
        FXAlert.showInfo(DorisRooms.getInstance().getLanguage().getConfig().get("ProvedMenu_AlertDeleteTitle").getAsString(),
                null, DorisRooms.getInstance().getLanguage().getConfig().get("ProvedMenu_AlertDeleteDescription").getAsString());

        tableModel();
        ButtonDisable();
        cleanText();
    }

    @FXML
    public void loadProved(){
        if(rpta_ruc.getText().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }

        String link = "https://api.apis.net.pe/v1/ruc?numero=" + rpta_ruc.getText();

        try{
            URL url = new URL(link);
            URLConnection request = url.openConnection();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject rootOBJ = root.getAsJsonObject();
            if(root.isJsonObject()) {
                String name = rootOBJ.get("nombre").getAsString();
                String[] parts = name.split(" ");
                rpta_names.setText(parts[2] + " " + parts[3]);
                rpta_surnames.setText(parts[0] + " " + parts[1]);
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
