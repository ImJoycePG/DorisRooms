package net.imjoycepg.mc.controllers;

import com.dustinredmond.fxalert.FXAlert;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.RoomsRentalEntity;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class RoomsRentalMenu implements Initializable {

    @FXML
    private TableView<RoomsRentalEntity> table_model;
    @FXML
    private TableColumn<RoomsRentalEntity, String> table_column_idRental, table_column_joinDate, table_column_leaveDate, table_column_obs, table_column_dniEmployee, table_column_dniClient,table_column_idRooms, table_column_idMethod;

    @FXML
    private Label label_idRental, label_joinRental, label_leaveRental, label_observation, label_dniEmployee, label_dniClient, label_idRooms, label_idMethod;
    @FXML
    private TextField rpta_idRental, rpta_obsRental;
    @FXML
    private DatePicker rpta_joinRental, rpta_leaveRental;
    @FXML
    private ComboBox<String> rpta_dniEmployee, rpta_dniClient, rpta_idRooms, rpta_idMethod;
    @FXML
    private Button buttonRegister, buttonEdit, buttonUpdate, buttonDelete;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateLanguage();
        tableModel();
        ButtonDisable();

        rpta_dniEmployee.setItems(DorisRooms.getInstance().getEmployeeTable().listEmployee());
        rpta_dniClient.setItems(DorisRooms.getInstance().getClientTable().listClient());
        rpta_idRooms.setItems(DorisRooms.getInstance().getRoomsTable().listRooms());
        rpta_idMethod.setItems(DorisRooms.getInstance().getMethodTable().listMethod());
    }

    private void updateLanguage() {
        label_idRental.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_IDRentalLabel").getAsString());
        label_joinRental.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_JoinRentalLabel").getAsString());
        label_leaveRental.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_LeaveRentalLabel").getAsString());
        label_observation.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_ObsRentalLabel").getAsString());
        label_dniEmployee.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_DNIEmployeeLabel").getAsString());
        label_dniClient.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_DNIClientLabel").getAsString());
        label_idRooms.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_IDRoomsLabel").getAsString());
        label_idMethod.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_IDMethodLabel").getAsString());
        buttonRegister.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_ButtonRegisterText").getAsString());
        buttonEdit.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_ButtonEditText").getAsString());
        buttonUpdate.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_ButtonUpdateText").getAsString());
        buttonDelete.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_ButtonDeleteText").getAsString());
        table_column_idRental.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_TableIDRentalText").getAsString());
        table_column_joinDate.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_TableJoinDateText").getAsString());
        table_column_leaveDate.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_TableLeaveDateText").getAsString());
        table_column_obs.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_TableObsRentalText").getAsString());
        table_column_dniEmployee.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_TableDNIEmployeeText").getAsString());
        table_column_dniClient.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_TableDNIClientText").getAsString());
        table_column_idRooms.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_TableIDRoomsText").getAsString());
        table_column_idMethod.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_TableIDMethodText").getAsString());
    }

    private void tableModel(){
        table_column_idRental.setCellValueFactory(new PropertyValueFactory<>("idRental"));
        table_column_joinDate.setCellValueFactory(new PropertyValueFactory<>("dateJoin"));
        table_column_leaveDate.setCellValueFactory(new PropertyValueFactory<>("dateLeave"));
        table_column_obs.setCellValueFactory(new PropertyValueFactory<>("obsRental"));
        table_column_dniEmployee.setCellValueFactory(new PropertyValueFactory<>("dniEmployee"));
        table_column_dniClient.setCellValueFactory(new PropertyValueFactory<>("dniClient"));
        table_column_idRooms.setCellValueFactory(new PropertyValueFactory<>("idRooms"));
        table_column_idMethod.setCellValueFactory(new PropertyValueFactory<>("idMethod"));

        ObservableList<RoomsRentalEntity> loginObservableList = DorisRooms.getInstance().getRoomsRentalTable().showRentals();
        table_model.setItems(loginObservableList);
        table_model.refresh();
    }

    private void ButtonEnable(){
        buttonUpdate.setDisable(false);
        buttonDelete.setDisable(true);
        buttonEdit.setDisable(true);
        buttonRegister.setDisable(true);
    }
    private void ButtonDisable(){
        buttonUpdate.setDisable(true);
        buttonDelete.setDisable(false);
        buttonEdit.setDisable(false);
        buttonRegister.setDisable(false);
    }

    private void cleanText(){
        rpta_idRental.setText("");
        rpta_obsRental.setText("");
        rpta_joinRental.getEditor().setText("");
        rpta_leaveRental.getEditor().setText("");
        rpta_dniEmployee.setValue("");
        rpta_dniClient.setValue("");
        rpta_idRooms.setValue("");
        rpta_idMethod.setValue("");
        rpta_idRental.requestFocus();
    }

    @FXML
    private void changeDateJoin(){
        rpta_joinRental.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalDate localDate) {
                return (rpta_joinRental != null) ? formatter.format(localDate) : "";
            }

            @Override
            public LocalDate fromString(String s) {
                return (s != null && !s.isEmpty())
                        ? LocalDate.parse(s, formatter) : null;
            }
        });
    }

    @FXML
    private void changeDateLeave(){
        rpta_leaveRental.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalDate localDate) {
                return (rpta_leaveRental != null) ? formatter.format(localDate) : "";
            }

            @Override
            public LocalDate fromString(String s) {
                return (s != null && !s.isEmpty())
                        ? LocalDate.parse(s, formatter) : null;
            }
        });
    }

    @FXML
    public void registerRental(){
        if(rpta_idRental.getText().isEmpty() || rpta_obsRental.getText().isEmpty() || rpta_joinRental.getEditor().getText().isEmpty() ||
                rpta_leaveRental.getEditor().getText().isEmpty() || rpta_dniClient.getValue().isEmpty() || rpta_dniEmployee.getValue().isEmpty() ||
                rpta_idRooms.getValue().isEmpty() || rpta_idMethod.getValue().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }

        String idRental = rpta_idRental.getText();
        if(DorisRooms.getInstance().getRoomsRentalTable().findRental(idRental) != null){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_FindTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_FindDescription").getAsString());
            cleanText();
            return;
        }

        RoomsRentalEntity roomsRentalEntity = new RoomsRentalEntity();
        roomsRentalEntity.setIdRental(idRental);
        roomsRentalEntity.setDateJoin(Date.valueOf(rpta_joinRental.getEditor().getText()));
        roomsRentalEntity.setDateLeave(Date.valueOf(rpta_leaveRental.getEditor().getText()));
        roomsRentalEntity.setObsRental(rpta_obsRental.getText());
        roomsRentalEntity.setDniEmployee(rpta_dniEmployee.getValue().substring(0, 8));
        roomsRentalEntity.setDniClient(rpta_dniClient.getValue().substring(0, 8));
        roomsRentalEntity.setIdRooms(rpta_idRooms.getValue().substring(0, 5));
        roomsRentalEntity.setIdMethod(rpta_idMethod.getValue().substring(0, 5));
        DorisRooms.getInstance().getRoomsRentalTable().insertRoomsRental(roomsRentalEntity);

        tableModel();
        cleanText();
    }

    @FXML
    public void modifyRental(){
        if(rpta_idRental.getText().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }

        String idRental = rpta_idRental.getText();
        if(DorisRooms.getInstance().getRoomsRentalTable().findRental(idRental) == null){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_NotFindTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("RoomsRentalMenu_NotFindDescription").getAsString());
            cleanText();
            return;
        }
        RoomsRentalEntity roomsRentalEntity = DorisRooms.getInstance().getRoomsRentalTable().findRental(idRental);
        rpta_joinRental.getEditor().setText(String.valueOf(roomsRentalEntity.getDateJoin()));
        rpta_leaveRental.getEditor().setText(String.valueOf(roomsRentalEntity.getDateLeave()));
        rpta_obsRental.setText(roomsRentalEntity.getObsRental());
        rpta_dniEmployee.getEditor().setText(roomsRentalEntity.getDniEmployee());
        rpta_dniClient.getEditor().setText(roomsRentalEntity.getDniClient());
        rpta_idRooms.getEditor().setText(roomsRentalEntity.getIdRooms());
        rpta_idMethod.getEditor().setText(roomsRentalEntity.getIdMethod());

        ButtonEnable();
    }

    @FXML
    public void updateRental(){
        if(rpta_idRental.getText().isEmpty() || rpta_obsRental.getText().isEmpty() || rpta_joinRental.getEditor().getText().isEmpty() ||
                rpta_leaveRental.getEditor().getText().isEmpty() || rpta_dniClient.getValue().isEmpty() || rpta_dniEmployee.getValue().isEmpty() ||
                rpta_idRooms.getValue().isEmpty() || rpta_idMethod.getValue().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }

        String idRental = rpta_idRental.getText();

        RoomsRentalEntity roomsRentalEntity = new RoomsRentalEntity();
        roomsRentalEntity.setIdRental(idRental);
        roomsRentalEntity.setDateJoin(Date.valueOf(rpta_joinRental.getEditor().getText()));
        roomsRentalEntity.setDateLeave(Date.valueOf(rpta_leaveRental.getEditor().getText()));
        roomsRentalEntity.setObsRental(rpta_obsRental.getText());
        roomsRentalEntity.setDniEmployee(rpta_dniEmployee.getValue().substring(0, 8));
        roomsRentalEntity.setDniClient(rpta_dniClient.getValue().substring(0, 8));
        roomsRentalEntity.setIdRooms(rpta_idRooms.getValue().substring(0, 5));
        roomsRentalEntity.setIdMethod(rpta_idMethod.getValue().substring(0, 5));
        DorisRooms.getInstance().getRoomsRentalTable().updateRental(roomsRentalEntity);

        tableModel();
        cleanText();
    }

    @FXML
    public void deleteRental(){
        if(table_model.getSelectionModel().getSelectedItem() == null){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("SelectTableViewTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("SelectTableViewDescription").getAsString());
            return;
        }

        String idRental = table_model.getSelectionModel().getSelectedItem().getIdRental();
        DorisRooms.getInstance().getRoomsRentalTable().deleteRental(idRental);
        tableModel();
    }

    @FXML
    public void closeSubMenu(){
        try {
            cleanText();
            DorisRooms.getInstance().sceneManageRooms();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
