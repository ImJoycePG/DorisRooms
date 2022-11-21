package net.imjoycepg.mc.utils.tables;

import com.dustinredmond.fxalert.FXAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.OrderEntity;
import net.imjoycepg.mc.utils.entity.OrderProductEntity;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderTable {
    private ResultSet rs = null;


    // Class OrderTable

    public void insertOrder(OrderEntity orderEntity){
        PreparedStatement ps = null;
        try{
            String insert = "INSERT INTO OrderTable(idOrder, dateOrder, dniEmployee, rucProved) VALUES (?,?,?,?)";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(insert);
            ps.setString(1, orderEntity.getIdOrder());
            ps.setDate(2, new Date(orderEntity.getDateOrder().getTime()));
            ps.setString(3, orderEntity.getDniEmployee());
            ps.setString(4, orderEntity.getRucProved());
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public void deleteOrder(String idOrder2){
        PreparedStatement ps = null;
        try{
            String table = "DELETE FROM OrderTable WHERE idOrder=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            ps.setString(1, idOrder2);
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public ObservableList<OrderEntity> showOrder() {
        PreparedStatement ps = null;
        ObservableList<OrderEntity> categoryProdEntities = FXCollections.observableArrayList();

        try {
            String table = "SELECT * FROM OrderTable;";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            rs = ps.executeQuery();

            while (rs.next()) {
                categoryProdEntities.add(new OrderEntity(
                        rs.getString("idOrder"),
                        rs.getDate("dateOrder"),
                        rs.getString("dniEmployee"),
                        rs.getString("rucProved")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryProdEntities;
    }

    // Class OrderProductTable

    public void insertOrderProduct(OrderProductEntity orderProductEntity){
        PreparedStatement ps = null;
        try{
            String insert = "INSERT INTO OrderProductTable(idOrderProd, priceOrderProd, stockOrderProd, idProduct, idOrder) VALUES (?,?,?,?,?)";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(insert);
            ps.setString(1, orderProductEntity.getIdOrderProd());
            ps.setDouble(2, orderProductEntity.getPriceOrderProd());
            ps.setInt(3, orderProductEntity.getStockOrderProd());
            ps.setString(4, orderProductEntity.getIdProduct());
            ps.setString(5, orderProductEntity.getIdOrder());
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public void deleteOrderProduct(String idOrderProd2){
        PreparedStatement ps = null;
        try{
            String table = "DELETE FROM OrderProductTable WHERE idProduct=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            ps.setString(1, idOrderProd2);
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }
    public ObservableList<String> listProducts(){
        PreparedStatement ps = null;
        ObservableList<String> list = FXCollections.observableArrayList();
        try{
            String table = "SELECT * FROM ProductTable;";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            rs = ps.executeQuery();

            while(rs.next()){
                list.add(rs.getString("idProduct") + " | " + rs.getString("nameProduct"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ObservableList<String> listEmployee(){
        PreparedStatement ps = null;
        ObservableList<String> list = FXCollections.observableArrayList();
        try{
            String table = "SELECT * FROM EmployeeTable;";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            rs = ps.executeQuery();

            while(rs.next()){
                list.add(rs.getString("dniEmployee") + " | " + rs.getString("namesEmployee"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ObservableList<String> listProved(){
        PreparedStatement ps = null;
        ObservableList<String> list = FXCollections.observableArrayList();
        try{
            String table = "SELECT * FROM ProvedTable;";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            rs = ps.executeQuery();

            while(rs.next()){
                list.add(rs.getString("rucProved") + " | " + rs.getString("namesProved"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
