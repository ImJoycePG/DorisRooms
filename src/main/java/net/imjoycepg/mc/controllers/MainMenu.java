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
                DorisRooms.getInstance().sceneManageEmployee();
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
        try {
            DorisRooms.getInstance().sceneManageClient();
        } catch (IOException e) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(e, "");
        }
    }

    @FXML
    public void manageSuppliers(){
        try {
            DorisRooms.getInstance().sceneManageProved();
        } catch (IOException e) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(e, "");
        }


    }

    @FXML
    public void manageProducts(){
        if(DorisRooms.getInstance().getLoginEntity().checkAdminOrRoot()){
            try {
                DorisRooms.getInstance().sceneManageProduct();
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
    public void manageRooms(){

        if(DorisRooms.getInstance().getLoginEntity().checkAdminOrRoot()){
            try {
                DorisRooms.getInstance().sceneManageRooms();
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
    public void manageLogin(){
        if(DorisRooms.getInstance().getLoginEntity().checkAdminOrRoot()){
            try {
                DorisRooms.getInstance().sceneManageAdministrator();
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
    public void exitButton(){
        try {
            DorisRooms.getInstance().sceneLoginAgain();
        } catch (IOException e) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(e, "");
        }
    }
}
