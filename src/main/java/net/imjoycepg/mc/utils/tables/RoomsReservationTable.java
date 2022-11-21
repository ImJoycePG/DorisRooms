package net.imjoycepg.mc.utils.tables;

import com.dustinredmond.fxalert.FXAlert;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.RoomsReservationEntity;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RoomsReservationTable {


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
}
