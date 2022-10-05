package net.imjoycepg.mc.utils.tables;

import com.dustinredmond.fxalert.FXAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.LoginEntity;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginTable {

    private ResultSet rs = null;

    public void insertEmployee(LoginEntity login){
        PreparedStatement ps = null;
        try{
            String insert = "INSERT INTO LoginTable(idLogin, userLogin, passLogin, roleLogin) VALUES (?,?,?,?)";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(insert);
            ps.setString(1, login.getIdLogin());
            ps.setString(2 , login.getUserLogin());
            ps.setString(3, login.getPassLogin());
            ps.setString(4, login.getRoleLogin());
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public LoginEntity findEmployee(String user){
        PreparedStatement ps = null;
        LoginEntity login = null;
        try{
            String find = "SELECT * FROM LoginTable WHERE userLogin=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(find);
            ps.setString(1, user);
            rs = ps.executeQuery();

            if(rs.next()){
                String pass1 = rs.getString("passLogin");
                String role = rs.getString("roleLogin");
                login = new LoginEntity(user, pass1, role);
            }
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
        return login;
    }

    public void deleteEmployee(String user){
        PreparedStatement ps = null;

        try{
            String table = "DELETE FROM LoginTable WHERE userLogin=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            ps.setString(1, user);
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public void updateUser(LoginEntity login){
        PreparedStatement ps = null;
        try{
            String table = "UPDATE LoginTable SET passLogin=?, roleLogin=? WHERE userLogin=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            ps.setString(3, login.getUserLogin());
            ps.setString(1, login.getPassLogin());
            ps.setString(2, login.getRoleLogin());
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }


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

    public String findRole(String user){
        PreparedStatement ps = null;
        String rol = null;
        FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
        try{
            String find = "SELECT * FROM LoginTable WHERE userLogin=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(find);
            ps.setString(1, user);
            rs = ps.executeQuery();
            if(rs.next()){
                String role = rs.getString("roleLogin");
                rol = role;
                DorisRooms.getInstance().getLoginEntity().setLoginUser(user);
                DorisRooms.getInstance().getLoginEntity().setRoleLogin(rol);
            }

        }catch (SQLException ex){
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString());
        }
        return rol;
    }

    public ObservableList<LoginEntity> showLoginUsers(){
        PreparedStatement ps = null;
        ObservableList<LoginEntity> loginObservableList = FXCollections.observableArrayList();
        try{
            String table = "SELECT * FROM LoginTable WHERE NOT roleLogin = 'root';";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            rs = ps.executeQuery();
            while(rs.next()){
                loginObservableList.add(new LoginEntity(
                        rs.getString("userLogin"),
                        rs.getString("passLogin"),
                        rs.getString("roleLogin")
                ));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return loginObservableList;
    }
}
