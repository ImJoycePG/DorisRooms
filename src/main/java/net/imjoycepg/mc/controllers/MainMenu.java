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
        try {
            DorisRooms.getInstance().sceneManageEmployees();
        } catch (IOException e) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(e, "");
        }
    }

    @FXML
    public void manageCustomers(){
        try {
            DorisRooms.getInstance().sceneManageEmployees();
        } catch (IOException e) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(e, "");
        }


    }

    @FXML
    public void manageSuppliers(){
        try {
            DorisRooms.getInstance().sceneManageEmployees();
        } catch (IOException e) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(e, "");
        }


    }

    @FXML
    public void manageProducts(){
        try {
            DorisRooms.getInstance().sceneManageEmployees();
        } catch (IOException e) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(e, "");
        }


    }

    @FXML
    public void manageRooms(){

        try {
            DorisRooms.getInstance().sceneManageEmployees();
        } catch (IOException e) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(e, "");
        }

    }

    @FXML
    public void exitButton(){
        System.exit(0);
    }
}
