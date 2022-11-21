package net.imjoycepg.mc.utils.tables;

import com.dustinredmond.fxalert.FXAlert;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.MethodEntity;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MethodTable {


    public void insertRooms(MethodEntity roomsEntity){
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


}
