package net.imjoycepg.mc.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import net.imjoycepg.mc.DorisRooms;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RoomsMenu implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
