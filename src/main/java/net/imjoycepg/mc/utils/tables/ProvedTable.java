package net.imjoycepg.mc.utils.tables;

import com.dustinredmond.fxalert.FXAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.ProvedEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProvedTable {

    private ResultSet rs = null;

    public void insertProved(ProvedEntity proved){
        PreparedStatement ps = null;
        try{
            String insert = "INSERT INTO ProvedTable(rucProved, namesProved, surnamesProved, phoneProved, emailProved) VALUES (?,?,?,?,?)";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(insert);
            ps.setString(1, proved.getRucProved());
            ps.setString(2 , proved.getNamesProved());
            ps.setString(3, proved.getSurnamesProved());
            ps.setString(4, proved.getPhoneProved());
            ps.setString(5, proved.getEmailProved());
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public ProvedEntity findProved(String proved){
        PreparedStatement ps = null;
        ProvedEntity login = null;
        try{
            String find = "SELECT * FROM ProvedTable WHERE rucProved=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(find);
            ps.setString(1, proved);
            rs = ps.executeQuery();

            if(rs.next()){
                String namesEmployee = rs.getString("namesProved");
                String surnamesEmployee = rs.getString("surnamesProved");
                String phoneProved = rs.getString("phoneProved");
                String emailProved = rs.getString("emailProved");
                login = new ProvedEntity(proved, namesEmployee, surnamesEmployee, phoneProved, emailProved);
            }
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
        return login;
    }

    public void deleteProved(String user){
        PreparedStatement ps = null;

        try{
            String table = "DELETE FROM ProvedTable WHERE rucProved=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            ps.setString(1, user);
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public void updateProved(ProvedEntity proved){
        PreparedStatement ps = null;
        try{
            String table = "UPDATE ProvedTable SET namesProved=?, surnamesProved=?, phoneProved=?, emailProved=? WHERE rucProved=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            ps.setString(5, proved.getRucProved());
            ps.setString(1, proved.getNamesProved());
            ps.setString(2, proved.getSurnamesProved());
            ps.setString(3, proved.getPhoneProved());
            ps.setString(4, proved.getEmailProved());
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }


    public ObservableList<ProvedEntity> showProved(){
        PreparedStatement ps = null;
        ObservableList<ProvedEntity> loginObservableList = FXCollections.observableArrayList();
        try{
            String table = "SELECT * FROM ProvedTable;";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            rs = ps.executeQuery();
            while(rs.next()){
                loginObservableList.add(new ProvedEntity(
                        rs.getString("rucProved"),
                        rs.getString("namesProved"),
                        rs.getString("surnamesProved"),
                        rs.getString("phoneProved"),
                        rs.getString("emailProved")
                ));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return loginObservableList;
    }
}
