package net.imjoycepg.mc.controllers;

import com.dustinredmond.fxalert.FXAlert;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import net.imjoycepg.mc.DorisRooms;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateLanguage();
    }

    private void updateLanguage(){

    }


    @FXML
    public void manageEmployees(){

        if(DorisRooms.getInstance().getLoginEntity().checkAdminOrRoot()){
            try {
                DorisRooms.getInstance().sceneManageEmployees();
            } catch (IOException e) {
                FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
                FXAlert.showException(e, "");
            }
        }else{
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("NoAccessAdministratorMenuTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("NoAccessAdministratorMenuDescription").getAsString());
        }
    }

    @FXML
    public void manageCustomers(){
        FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
        FXAlert.showInfo("Mantenimiento / Maintenance ");
    }

    @FXML
    public void manageSuppliers(){
        FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
        FXAlert.showInfo("Mantenimiento / Maintenance ");


    }

    @FXML
    public void manageProducts(){
        FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
        FXAlert.showInfo("Mantenimiento / Maintenance ");


    }

    @FXML
    public void manageRooms(){

        FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
        FXAlert.showInfo("Mantenimiento / Maintenance ");

    }

    @FXML
    public void exitButton(){
        System.exit(0);
    }
}
