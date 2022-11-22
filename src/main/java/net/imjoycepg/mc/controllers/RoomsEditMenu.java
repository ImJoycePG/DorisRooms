package net.imjoycepg.mc.controllers;

import com.dustinredmond.fxalert.FXAlert;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.RoomsEntity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RoomsEditMenu implements Initializable {

    @FXML
    private Label label_idRooms, label_descRooms, label_price, label_obs, label_idType;
    @FXML
    private TextField rpta_id, rpta_desc, rpta_price, rpta_obs;
    @FXML
    private ComboBox<String> rpta_idType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateLanguage();
        joinData();
        rpta_idType.setItems(DorisRooms.getInstance().getRoomsTypeTable().listTypes());

    }

    private void updateLanguage(){
        label_idRooms.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsEditMenu_IDRoomsLabel").getAsString());
        label_descRooms.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsEditMenu_DescRoomsLabel").getAsString());
        label_price.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsEditMenu_PriceRoomsLabel").getAsString());
        label_obs.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsEditMenu_ObsRoomsLabel").getAsString());
        label_idType.setText(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsEditMenu_IDTypeRoomsLabel").getAsString());
    }

    private void joinData(){

        rpta_id.setText(DorisRooms.getInstance().getRoomsTemp().getIdRooms());
        rpta_desc.setText(DorisRooms.getInstance().getRoomsTemp().getDescRooms());
        rpta_obs.setText(String.valueOf(DorisRooms.getInstance().getRoomsTemp().getObsRooms()));
        rpta_price.setText(String.format("%,.2f", DorisRooms.getInstance().getRoomsTemp().getPriceRooms()).replace(",", "."));
        rpta_idType.setValue(DorisRooms.getInstance().getRoomsTemp().getIdTypeRooms());
    }

    @FXML
    public void modifyButton(){
        if(rpta_id.getText().isEmpty() || rpta_desc.getText().isEmpty() || rpta_obs.getText().isEmpty() || rpta_price.getText().isEmpty()
                || rpta_idType.getValue().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }

        String idRooms = rpta_id.getText();

        if(DorisRooms.getInstance().getRoomsTable().findRooms(idRooms) == null){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("RoomsEditMenu_NotFindTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("RoomsEditMenu_NotFindDescription").getAsString());
            return;
        }

        RoomsEntity roomsEntity = new RoomsEntity();
        roomsEntity.setIdRooms(idRooms);
        roomsEntity.setDescRooms(rpta_desc.getText());
        roomsEntity.setPriceRooms(Double.parseDouble(rpta_price.getText()));
        roomsEntity.setObsRooms(rpta_obs.getText());
        roomsEntity.setIdTypeRooms(rpta_idType.getValue().substring(0, 5));
        DorisRooms.getInstance().getRoomsTable().updateRooms(roomsEntity);

        closeSubMenu();
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
