package net.imjoycepg.mc.controllers;

import com.dustinredmond.fxalert.FXAlert;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.MethodEntity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MethodMenu implements Initializable {
    @FXML
    private TableView<MethodEntity> table_model;
    @FXML
    private TableColumn<MethodEntity, String> table_column_id, table_column_type, table_column_desc;
    @FXML
    private Button buttonRegister, buttonDelete;
    @FXML
    private Label label_idMethod, label_typeMethod, label_descMethod;

    @FXML
    private TextField rpta_idMethod, rpta_typeMethod, rpta_descMethod;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateLanguage();
        tableModel();
    }

    private void updateLanguage(){
        label_idMethod.setText(DorisRooms.getInstance().getLanguage().getConfig().get("MethodMenu_IDMethodLabel").getAsString());
        label_typeMethod.setText(DorisRooms.getInstance().getLanguage().getConfig().get("MethodMenu_TypeMethodLabel").getAsString());
        label_descMethod.setText(DorisRooms.getInstance().getLanguage().getConfig().get("MethodMenu_DescMethodLabel").getAsString());
        buttonRegister.setText(DorisRooms.getInstance().getLanguage().getConfig().get("MethodMenu_ButtonRegisterText").getAsString());
        buttonDelete.setText(DorisRooms.getInstance().getLanguage().getConfig().get("MethodMenu_ButtonDeleteText").getAsString());
        table_column_id.setText(DorisRooms.getInstance().getLanguage().getConfig().get("MethodMenu_TableIDMethodText").getAsString());
        table_column_type.setText(DorisRooms.getInstance().getLanguage().getConfig().get("MethodMenu_TableTypeMethodText").getAsString());
        table_column_desc.setText(DorisRooms.getInstance().getLanguage().getConfig().get("MethodMenu_TableDescMethodText").getAsString());
    }

    private void tableModel(){
        table_column_id.setCellValueFactory(new PropertyValueFactory<>("idMethod"));
        table_column_type.setCellValueFactory(new PropertyValueFactory<>("typeMethod"));
        table_column_desc.setCellValueFactory(new PropertyValueFactory<>("descMethod"));

        ObservableList<MethodEntity> loginObservableList = DorisRooms.getInstance().getMethodTable().showMethod();
        table_model.setItems(loginObservableList);
        table_model.refresh();
    }

    private void cleanText(){
        rpta_idMethod.setText("");
        rpta_typeMethod.setText("");
        rpta_descMethod.setText("");
        rpta_idMethod.requestFocus();
    }

    @FXML
    public void registerMethod(){
        if(rpta_idMethod.getText().isEmpty() || rpta_descMethod.getText().isEmpty() || rpta_typeMethod.getText().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }

        if(DorisRooms.getInstance().getMethodTable().findMethod(rpta_idMethod.getText()) != null){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("MethodMenu_FindTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("MethodMenu_FindDescription").getAsString());
            cleanText();
            return;
        }

        MethodEntity methodEntity = new MethodEntity(rpta_idMethod.getText(), rpta_typeMethod.getText(), rpta_descMethod.getText());
        DorisRooms.getInstance().getMethodTable().insertMethod(methodEntity);

        cleanText();
        tableModel();
    }

    @FXML
    private void deleteMethod(){
        if(table_model.getSelectionModel().getSelectedItem() == null){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("SelectTableViewTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("SelectTableViewDescription").getAsString());
            return;
        }

        String idRooms = table_model.getSelectionModel().getSelectedItem().getIdMethod();
        DorisRooms.getInstance().getMethodTable().deleteMethod(idRooms);
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
