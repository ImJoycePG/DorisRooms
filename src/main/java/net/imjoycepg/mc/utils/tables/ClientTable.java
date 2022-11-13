package net.imjoycepg.mc.utils.tables;

import com.dustinredmond.fxalert.FXAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.ClientEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientTable {

    private ResultSet rs = null;

    public void insertclient(ClientEntity client){
        PreparedStatement ps = null;
        try{
            String insert = "INSERT INTO ClientTable(dniClient, namesClient, surnamesClient) VALUES (?,?,?)";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(insert);
            ps.setString(1, client.getDniClient());
            ps.setString(2 , client.getNamesClient());
            ps.setString(3, client.getSurnamesClient());
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public ClientEntity findClient(String client){
        PreparedStatement ps = null;
        ClientEntity login = null;
        try{
            String find = "SELECT * FROM ClientTable WHERE dniClient=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(find);
            ps.setString(1, client);
            rs = ps.executeQuery();

            if(rs.next()){
                String namesClient = rs.getString("namesClient");
                String surnamesClient = rs.getString("surnamesClient");
                login = new ClientEntity(client, namesClient, surnamesClient);
            }
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
        return login;
    }

    public void deleteClient(String user){
        PreparedStatement ps = null;

        try{
            String table = "DELETE FROM ClientTable WHERE dniClient=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            ps.setString(1, user);
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public void updateClient(ClientEntity client){
        PreparedStatement ps = null;
        try{
            String table = "UPDATE ClientTable SET namesClient=?, surnamesClient=? WHERE dniClient=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            ps.setString(3, client.getDniClient());
            ps.setString(1, client.getNamesClient());
            ps.setString(2, client.getSurnamesClient());
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }


    public ObservableList<ClientEntity> showClients(){
        PreparedStatement ps = null;
        ObservableList<ClientEntity> loginObservableList = FXCollections.observableArrayList();
        try{
            String table = "SELECT * FROM ClientTable;";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            rs = ps.executeQuery();
            while(rs.next()){
                loginObservableList.add(new ClientEntity(
                        rs.getString("dniClient"),
                        rs.getString("namesClient"),
                        rs.getString("surnamesClient")
                ));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return loginObservableList;
    }
}
