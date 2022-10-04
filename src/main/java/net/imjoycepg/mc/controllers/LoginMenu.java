package net.imjoycepg.mc.controllers;

import com.dustinredmond.fxalert.FXAlert;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.Language;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginMenu implements Initializable {

    @FXML
    private ChoiceBox<String> language_select;

    @FXML
    private Label label_login;
    @FXML
    private Text label_username, label_password;
    @FXML
    private TextField rpta_user;
    @FXML
    private PasswordField rpta_password;
    @FXML
    private Button button_signIn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        language_select.getItems().addAll("English", "Spanish");
        language_select.setValue("English");
        language_select.valueProperty().addListener((observable, oldValue, newValue) -> {
            DorisRooms.getInstance().setLanguage(Language.valueOf(newValue.toUpperCase()));
            updateLanguage();
        });
    }

    private void updateLanguage(){
        label_login.setText(DorisRooms.getInstance().getLanguage().getConfig().get("LoginMenu_LoginLabel").getAsString());
        label_username.setText(DorisRooms.getInstance().getLanguage().getConfig().get("LoginMenu_LoginUsername").getAsString());
        label_password.setText(DorisRooms.getInstance().getLanguage().getConfig().get("LoginMenu_LoginPassword").getAsString());
        button_signIn.setText(DorisRooms.getInstance().getLanguage().getConfig().get("LoginMenu_ButtonLoginText").getAsString());
    }

    @FXML
    public void loginEvent(){
        if(rpta_user.getText().isEmpty() && rpta_password.getText().isEmpty()){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showWarning(DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyTitle").getAsString(),
                    null, DorisRooms.getInstance().getLanguage().getConfig().get("TextEmptyDescription").getAsString());
            return;
        }

        DorisRooms.getInstance().getLoginTable().loginUser(rpta_user.getText(), rpta_password.getText());
    }
}
