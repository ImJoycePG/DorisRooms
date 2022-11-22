package net.imjoycepg.mc.utils.tables;

import com.dustinredmond.fxalert.FXAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.MethodEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MethodTable {
    private ResultSet rs = null;


    public void insertMethod(MethodEntity roomsEntity){
        PreparedStatement ps = null;
        try{
            String insert = "INSERT INTO MethodTable(idMethod, typeMethod, descMethod) VALUES (?,?,?)";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(insert);
            ps.setString(1, roomsEntity.getIdMethod());
            ps.setString(2, roomsEntity.getTypeMethod());
            ps.setString(3, roomsEntity.getDescMethod());
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public MethodEntity findMethod(String client){
        PreparedStatement ps = null;
        MethodEntity login = null;
        try{
            String find = "SELECT * FROM MethodTable WHERE idMethod=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(find);
            ps.setString(1, client);
            rs = ps.executeQuery();

            if(rs.next()){
                String namesClient = rs.getString("typeMethod");
                String surnamesClient = rs.getString("descMethod");
                login = new MethodEntity(client, namesClient, surnamesClient);
            }
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
        return login;
    }

    public void deleteMethod(String user){
        PreparedStatement ps = null;

        try{
            String table = "DELETE FROM MethodTable WHERE idMethod=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            ps.setString(1, user);
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public ObservableList<MethodEntity> showMethod(){
        PreparedStatement ps = null;
        ObservableList<MethodEntity> loginObservableList = FXCollections.observableArrayList();
        try{
            String table = "SELECT * FROM MethodTable;";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            rs = ps.executeQuery();
            while(rs.next()){
                loginObservableList.add(new MethodEntity(
                        rs.getString("idMethod"),
                        rs.getString("typeMethod"),
                        rs.getString("descMethod")
                ));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return loginObservableList;
    }

    public ObservableList<String> listMethod(){
        PreparedStatement ps = null;
        ObservableList<String> list = FXCollections.observableArrayList();
        try{
            String table = "SELECT * FROM MethodTable;";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            rs = ps.executeQuery();

            while(rs.next()){
                list.add(rs.getString("idMethod") + " | " + rs.getString("typeMethod"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
