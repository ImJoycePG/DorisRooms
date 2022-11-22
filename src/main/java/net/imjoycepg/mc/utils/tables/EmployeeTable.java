package net.imjoycepg.mc.utils.tables;

import com.dustinredmond.fxalert.FXAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.imjoycepg.mc.DorisRooms;
import net.imjoycepg.mc.utils.entity.EmployeeEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeTable {
    private ResultSet rs = null;

    public void insertEmployee(EmployeeEntity employee){
        PreparedStatement ps = null;
        try{
            String insert = "INSERT INTO EmployeeTable(dniEmployee, namesEmployee, surnamesEmployee) VALUES (?,?,?)";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(insert);
            ps.setString(1, employee.getDniEmployee());
            ps.setString(2 , employee.getNamesEmployee());
            ps.setString(3, employee.getSurnamesEmployee());
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public EmployeeEntity findEmployee(String employee){
        PreparedStatement ps = null;
        EmployeeEntity login = null;
        try{
            String find = "SELECT * FROM EmployeeTable WHERE dniEmployee=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(find);
            ps.setString(1, employee);
            rs = ps.executeQuery();

            if(rs.next()){
                String namesEmployee = rs.getString("namesEmployee");
                String surnamesEmployee = rs.getString("surnamesEmployee");
                login = new EmployeeEntity(employee, namesEmployee, surnamesEmployee);
            }
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
        return login;
    }

    public void deleteEmployee(String user){
        PreparedStatement ps = null;

        try{
            String table = "DELETE FROM EmployeeTable WHERE dniEmployee=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            ps.setString(1, user);
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }

    public void updateEmployee(EmployeeEntity employee){
        PreparedStatement ps = null;
        try{
            String table = "UPDATE EmployeeTable SET namesEmployee=?, surnamesEmployee=? WHERE dniEmployee=?";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            ps.setString(3, employee.getDniEmployee());
            ps.setString(1, employee.getNamesEmployee());
            ps.setString(2, employee.getSurnamesEmployee());
            ps.executeUpdate();
        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }


    public ObservableList<EmployeeEntity> showEmployees(){
        PreparedStatement ps = null;
        ObservableList<EmployeeEntity> loginObservableList = FXCollections.observableArrayList();
        try{
            String table = "SELECT * FROM EmployeeTable;";
            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(table);
            rs = ps.executeQuery();
            while(rs.next()){
                loginObservableList.add(new EmployeeEntity(
                        rs.getString("dniEmployee"),
                        rs.getString("namesEmployee"),
                        rs.getString("surnamesEmployee")
                ));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return loginObservableList;
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
}

