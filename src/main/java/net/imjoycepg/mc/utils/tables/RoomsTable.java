package net.imjoycepg.mc.utils.tables;

import com.dustinredmond.fxalert.FXAlert;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.RoomsEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomsTable {
    private ResultSet rs = null;

    public void insertRooms(RoomsEntity roomsEntity){
        PreparedStatement ps = null;
        try{
            String insert = "INSERT INTO RoomsTable(idRooms, descRooms, priceRooms, obsRooms, idTypeRooms) VALUES (?,?,?,?,?)";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(insert);
            ps.setString(1, roomsEntity.getIdRooms());
            ps.setString(2, roomsEntity.getDescRooms());
            ps.setDouble(3, roomsEntity.getPriceRooms());
            ps.setString(4, roomsEntity.getObsRooms());
            ps.setString(5, roomsEntity.getIdTypeRooms());
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public RoomsEntity findRooms(String client){
        PreparedStatement ps = null;
        RoomsEntity login = null;
        try{
            String find = "SELECT * FROM RoomsTable WHERE idRooms=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(find);
            ps.setString(1, client);
            rs = ps.executeQuery();

            if(rs.next()){
                login = new RoomsEntity(client,
                        rs.getString("descRooms"),
                        rs.getDouble("priceRooms"),
                        rs.getString("obsRooms"),
                        rs.getString("idTypeRooms"));
            }
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
        return login;
    }
}
