package net.imjoycepg.mc.utils.tables;

import com.dustinredmond.fxalert.FXAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.NewProductEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class NewProductTable {

    private ResultSet rs = null;

    public void insertProduct(NewProductEntity newProductEntity){
        PreparedStatement ps = null;
        try{
            String insert = "INSERT INTO ProductTable(idProduct, nameProduct, stockProduct, dateJoin, idCategory) VALUES (?,?,?,?,?)";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(insert);
            ps.setString(1 , newProductEntity.getIdProduct());
            ps.setString(2 , newProductEntity.getNameProduct());
            ps.setInt(3, newProductEntity.getStockProduct());
            ps.setDate(4, new java.sql.Date(newProductEntity.getDateJoin().getTime()));
            ps.setString(5, newProductEntity.getIdCategory());
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public NewProductEntity findProduct(String idProductNew){
        PreparedStatement ps = null;
        NewProductEntity categoryProdEntity = null;
        try{
            String find = "SELECT * FROM ProductTable WHERE idProduct=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(find);
            ps.setString(1, idProductNew);
            rs = ps.executeQuery();

            if(rs.next()){
                String nameProduct = rs.getString("nameProduct");
                int stockProduct = rs.getInt("stockProduct");
                Date dateJoin = rs.getDate("dateJoin");
                String idCategory = rs.getString("idCategory");
                categoryProdEntity = new NewProductEntity(idProductNew, nameProduct, stockProduct, dateJoin, idCategory);
            }
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
        return categoryProdEntity;
    }

    public void deleteProduct(String idProductNew){
        PreparedStatement ps = null;
        try{
            String table = "DELETE FROM ProductTable WHERE idProduct=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            ps.setString(1, idProductNew);
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public void updateProduct(NewProductEntity newProductEntity){
        PreparedStatement ps = null;
        try{
            String table = "UPDATE ProductTable SET nameProduct=?, stockProduct=?, dateJoin=?, idCategory=? WHERE idProduct=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            ps.setString(5, newProductEntity.getIdProduct());
            ps.setString(1, newProductEntity.getNameProduct());
            ps.setInt(2, newProductEntity.getStockProduct());
            ps.setDate(3, new java.sql.Date(newProductEntity.getDateJoin().getTime()));
            ps.setString(4, newProductEntity.getIdCategory());
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public ObservableList<NewProductEntity> showProducts() {
        PreparedStatement ps = null;
        ObservableList<NewProductEntity> categoryProdEntities = FXCollections.observableArrayList();

        try {
            String table = "SELECT * FROM ProductTable;";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            rs = ps.executeQuery();

            while (rs.next()) {
                categoryProdEntities.add(new NewProductEntity(
                        rs.getString("idProduct"),
                        rs.getString("nameProduct"),
                        rs.getInt("stockProduct"),
                        rs.getDate("dateJoin"),
                        rs.getString("idCategory")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryProdEntities;
    }

    public NewProductEntity findProductName(String nameProduct2){
        PreparedStatement ps = null;
        NewProductEntity newProductEntity = null;
        try{
            String find = "SELECT * FROM ProductTable WHERE nameProduct=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(find);
            ps.setString(1, nameProduct2);
            rs = ps.executeQuery();

            if(rs.next()){
                String nameProduct = rs.getString("nameProduct");
                int stockProduct = rs.getInt("stockProduct");
                Date dateJoin = rs.getDate("dateJoin");
                String idCategory = rs.getString("idCategory");
                newProductEntity = new NewProductEntity(nameProduct2, nameProduct, stockProduct, dateJoin, idCategory);
            }
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
        return newProductEntity;
    }
}
