package net.imjoycepg.mc.utils.tables;

import com.dustinredmond.fxalert.FXAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.RoomsTypeEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomsTypeTable {

    private ResultSet rs = null;

    public void insertRoomsType(RoomsTypeEntity roomsTypeEntity){
        PreparedStatement ps = null;
        try{
            String insert = "INSERT INTO RoomsTypeTable(idTypeRooms, nameTypeRooms) VALUES (?,?)";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(insert);
            ps.setString(1, roomsTypeEntity.getIdTypeRooms());
            ps.setString(2, roomsTypeEntity.getNameTypeRooms());
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public RoomsTypeEntity findType(String id){
        PreparedStatement ps = null;
        RoomsTypeEntity login = null;
        try{
            String find = "SELECT * FROM RoomsTypeTable WHERE idTypeRooms=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(find);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if(rs.next()){
                String nameTypeRooms = rs.getString("nameTypeRooms");
                login = new RoomsTypeEntity(id, nameTypeRooms);
            }
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
        return login;
    }

    public void updateType(RoomsTypeEntity client){
        PreparedStatement ps = null;
        try{
            String table = "UPDATE RoomsTypeTable SET nameTypeRooms=? WHERE idTypeRooms=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            ps.setString(2, client.getIdTypeRooms());
            ps.setString(1, client.getNameTypeRooms());
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public void deleteType(String id){
        PreparedStatement ps = null;

        try{
            String table = "DELETE FROM RoomsTypeTable WHERE idTypeRooms=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            ps.setString(1, id);
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public ObservableList<RoomsTypeEntity> showTypes(){
        PreparedStatement ps = null;
        ObservableList<RoomsTypeEntity> loginObservableList = FXCollections.observableArrayList();
        try{
            String table = "SELECT * FROM RoomsTypeTable;";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            rs = ps.executeQuery();
            while(rs.next()){
                loginObservableList.add(new RoomsTypeEntity(
                        rs.getString("idTypeRooms"),
                        rs.getString("nameTypeRooms")
                ));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return loginObservableList;
    }

    public ObservableList<String> listTypes(){
        PreparedStatement ps = null;
        ObservableList<String> list = FXCollections.observableArrayList();
        try{
            String table = "SELECT * FROM RoomsTypeTable;";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            rs = ps.executeQuery();

            while(rs.next()){
                list.add(rs.getString("idTypeRooms") + " | " + rs.getString("nameTypeRooms"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
