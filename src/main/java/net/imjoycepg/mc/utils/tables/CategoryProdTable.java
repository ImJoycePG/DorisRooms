package net.imjoycepg.mc.utils.tables;

import com.dustinredmond.fxalert.FXAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.CategoryProdEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryProdTable {
    private ResultSet rs = null;

    public void insertCategory(CategoryProdEntity categoryProd){
        PreparedStatement ps = null;
        try{
            String insert = "INSERT INTO CategoryProdTable(idCategory, nameCategory, descCategory) VALUES (?,?,?)";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(insert);
            ps.setString(1 , categoryProd.getIdCategory());
            ps.setString(2 , categoryProd.getNameCategory());
            ps.setString(3, categoryProd.getDescCategory());
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public CategoryProdEntity findCategory(String categoryProd){
        PreparedStatement ps = null;
        CategoryProdEntity categoryProdEntity = null;
        try{
            String find = "SELECT * FROM CategoryProdTable WHERE idCategory=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(find);
            ps.setString(1, categoryProd);
            rs = ps.executeQuery();

            if(rs.next()){
                String nameCategory = rs.getString("nameCategory");
                String descCategory = rs.getString("descCategory");
                categoryProdEntity = new CategoryProdEntity(categoryProd, nameCategory, descCategory);
            }
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
        return categoryProdEntity;
    }

    public void deleteCategory(String categoryProd){
        PreparedStatement ps = null;
        try{
            String table = "DELETE FROM CategoryProdTable WHERE idCategory=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            ps.setString(1, categoryProd);
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public void updateCategory(CategoryProdEntity categoryProd){
        PreparedStatement ps = null;
        try{
            String table = "UPDATE CategoryProdTable SET nameCategory=?, descCategory=? WHERE idCategory=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            ps.setString(3, categoryProd.getIdCategory());
            ps.setString(1, categoryProd.getNameCategory());
            ps.setString(2, categoryProd.getDescCategory());
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public ObservableList<CategoryProdEntity> showCategory() {
        PreparedStatement ps = null;
        ObservableList<CategoryProdEntity> categoryProdEntities = FXCollections.observableArrayList();

        try {
            String table = "SELECT * FROM CategoryProdTable;";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            rs = ps.executeQuery();

            while (rs.next()) {
                categoryProdEntities.add(new CategoryProdEntity(
                        rs.getString("idCategory"),
                        rs.getString("nameCategory"),
                        rs.getString("descCategory")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryProdEntities;
    }
}
