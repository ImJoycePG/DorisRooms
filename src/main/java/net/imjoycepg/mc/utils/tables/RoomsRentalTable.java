package net.imjoycepg.mc.utils.tables;

import com.dustinredmond.fxalert.FXAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.RoomsRentalEntity;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomsRentalTable {
    private ResultSet rs = null;

    public void insertRoomsRental(RoomsRentalEntity roomsEntity){
        PreparedStatement ps = null;
        try{
            String insert = "INSERT INTO RoomsRentalTable(idRental, dateJoin, dateLeave, obsRental, dniEmployee, dniClient, idRooms, idMethod) VALUES (?,?,?,?,?,?,?,?)";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(insert);
            ps.setString(1, roomsEntity.getIdRental());
            ps.setDate(2, new Date(roomsEntity.getDateJoin().getTime()));
            ps.setDate(3, new Date(roomsEntity.getDateLeave().getTime()));
            ps.setString(4, roomsEntity.getObsRental());
            ps.setString(5, roomsEntity.getDniEmployee());
            ps.setString(6, roomsEntity.getDniClient());
            ps.setString(7, roomsEntity.getIdRooms());
            ps.setString(8, roomsEntity.getIdMethod());
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public RoomsRentalEntity findRental(String client){
        PreparedStatement ps = null;
        RoomsRentalEntity login = null;
        try{
            String find = "SELECT * FROM RoomsRentalTable WHERE idRental=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(find);
            ps.setString(1, client);
            rs = ps.executeQuery();

            if(rs.next()){
                java.util.Date dateJoin = rs.getDate("dateJoin");
                java.util.Date dateLeave = rs.getDate("dateLeave");
                String obsRental = rs.getString("obsRental");
                String dniEmployee = rs.getString("dniEmployee");
                String dniClient = rs.getString("dniClient");
                String idRooms = rs.getString("idRooms");
                String idMethod = rs.getString("idMethod");
                login = new RoomsRentalEntity(client, dateJoin, dateLeave, obsRental, dniEmployee, dniClient, idRooms, idMethod);
            }
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
        return login;
    }

    public ObservableList<RoomsRentalEntity> showRentals(){
        PreparedStatement ps = null;
        ObservableList<RoomsRentalEntity> loginObservableList = FXCollections.observableArrayList();
        try{
            String table = "SELECT * FROM RoomsRentalTable;";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            rs = ps.executeQuery();
            while(rs.next()){
                loginObservableList.add(new RoomsRentalEntity(
                        rs.getString("idRental"),
                        rs.getDate("dateJoin"),
                        rs.getDate("dateLeave"),
                        rs.getString("obsRental"),
                        rs.getString("dniEmployee"),
                        rs.getString("dniClient"),
                        rs.getString("idRooms"),
                        rs.getString("idMethod")
                ));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return loginObservableList;
    }
}
