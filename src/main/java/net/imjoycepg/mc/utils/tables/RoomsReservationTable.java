package net.imjoycepg.mc.utils.tables;

import com.dustinredmond.fxalert.FXAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.RoomsReservationEntity;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomsReservationTable {

    private ResultSet rs = null;


    public void insertRoomsRental(RoomsReservationEntity roomsEntity){
        PreparedStatement ps = null;
        try{
            String insert = "INSERT INTO RoomsReservationTable(idReservation, dateJoin, dateLeave, formReservation, dniEmployee, dniClient, idRooms) VALUES (?,?,?,?,?,?,?)";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(insert);
            ps.setString(1, roomsEntity.getIdReservation());
            ps.setDate(2, new Date(roomsEntity.getDateJoin().getTime()));
            ps.setDate(3, new Date(roomsEntity.getDateLeave().getTime()));
            ps.setString(4, roomsEntity.getFormReservation());
            ps.setString(5, roomsEntity.getDniEmployee());
            ps.setString(6, roomsEntity.getDniClient());
            ps.setString(7, roomsEntity.getIdRooms());
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public RoomsReservationEntity findReservation(String client){
        PreparedStatement ps = null;
        RoomsReservationEntity login = null;
        try{
            String find = "SELECT * FROM RoomsReservationTable WHERE idReservation=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(find);
            ps.setString(1, client);
            rs = ps.executeQuery();

            if(rs.next()){
                java.util.Date dateJoin = rs.getDate("dateJoin");
                java.util.Date dateLeave = rs.getDate("dateLeave");
                String formReservation = rs.getString("formReservation");
                String dniEmployee = rs.getString("dniEmployee");
                String dniClient = rs.getString("dniClient");
                String idRooms = rs.getString("idRooms");
                login = new RoomsReservationEntity(client, dateJoin, dateLeave, formReservation, dniEmployee, dniClient, idRooms);
            }
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
        return login;
    }

    public void updateReservation(RoomsReservationEntity roomsEntity){
        PreparedStatement ps = null;
        try{
            String table = "UPDATE RoomsReservationTable SET dateJoin=?, dateLeave=?, formReservation=?, dniEmployee=?, dniClient=?, idRooms=? WHERE idReservation=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            ps.setString(7, roomsEntity.getIdReservation());
            ps.setDate(1, new Date(roomsEntity.getDateJoin().getTime()));
            ps.setDate(2, new Date(roomsEntity.getDateLeave().getTime()));
            ps.setString(3, roomsEntity.getFormReservation());
            ps.setString(4, roomsEntity.getDniEmployee());
            ps.setString(5, roomsEntity.getDniClient());
            ps.setString(6, roomsEntity.getIdRooms());
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public void deleteReservation(String user){
        PreparedStatement ps = null;

        try{
            String table = "DELETE FROM RoomsReservationTable WHERE idReservation=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            ps.setString(1, user);
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public ObservableList<RoomsReservationEntity> showReservations(){
        PreparedStatement ps = null;
        ObservableList<RoomsReservationEntity> loginObservableList = FXCollections.observableArrayList();
        try{
            String table = "SELECT * FROM RoomsReservationTable;";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            rs = ps.executeQuery();
            while(rs.next()){
                loginObservableList.add(new RoomsReservationEntity(
                        rs.getString("idReservation"),
                        rs.getDate("dateJoin"),
                        rs.getDate("dateLeave"),
                        rs.getString("formReservation"),
                        rs.getString("dniEmployee"),
                        rs.getString("dniClient"),
                        rs.getString("idRooms")
                ));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return loginObservableList;
    }
}
