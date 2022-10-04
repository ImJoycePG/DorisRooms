package net.imjoycepg.mc.utils.tables;

import com.dustinredmond.fxalert.FXAlert;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.LoginEntity;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginTable {

    private ResultSet rs = null;

    public LoginEntity loginUser(String user, String password){
        PreparedStatement ps = null;
        LoginEntity loginEntity = null;
        FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());

        try{
            String loginUser = "SELECT * FROM LoginTable WHERE userLogin=? and passLogin=?;";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(loginUser);
            ps.setString(1, user);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if(rs.next()){
                FXAlert.showInfo(DorisRooms.getInstance().getLanguage().getConfig().get("LoginMenu_MatchTitle").getAsString(),
                        null, DorisRooms.getInstance().getLanguage().getConfig().get("LoginMenu_MatchDescription").getAsString());
                loginEntity = new LoginEntity(user, password);
                DorisRooms.getInstance().sceneMainMenu();


            }else{
                FXAlert.showError(DorisRooms.getInstance().getLanguage().getConfig().get("LoginMenu_MatchErrorTitle").getAsString(),
                        null, DorisRooms.getInstance().getLanguage().getConfig().get("LoginMenu_MatchErrorDescription").getAsString());
            }

        }catch (SQLException ex) {
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString());
        } catch (IOException e) {
            FXAlert.showException(e, "");
        }
        return loginEntity;
    }
}
