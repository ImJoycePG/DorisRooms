package net.imjoycepg.mc.utils;

import com.dustinredmond.fxalert.FXAlert;
import net.imjoycepg.mc.DorisRooms;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQL {

    private Connection connection = null;

    public Connection getConnection() {
        try {
            if (connection.isClosed()) {
                return connectDatabase();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

    public Connection connectDatabase() {
        try {
            final String host = DorisRooms.getInstance().getDatabase().getHost();
            final String port = DorisRooms.getInstance().getDatabase().getPort();
            final String database = DorisRooms.getInstance().getDatabase().getDatabase();
            final String user = DorisRooms.getInstance().getDatabase().getUsername();
            final String password = DorisRooms.getInstance().getDatabase().getPassword();

            final String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?createDatabaseIfNotExist=TRUE";


            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            DorisRooms.getInstance().sceneLoginMenu();
            LoginTable();
            EmployeeTable();
            ProvedTable();
            ClientTable();
            CategoryProduct();
            ProductTable();
            addRootUser();
            OrderTable();
            OrderProductTable();
            RoomsTypeTable();
            RoomsTable();
            MethodTable();
            RoomsRentalTable();
            RoomsReservationTable();

        } catch (ClassNotFoundException | SQLException | IOException ex) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString());
        }
        return connection;
    }


    private void LoginTable(){
        String table = "CREATE TABLE IF NOT EXISTS LoginTable(" +
                "idLogin varchar(8) not null," +
                "userLogin varchar(16) not null," +
                "passLogin varchar(36) not null," +
                "roleLogin varchar(16) not null," +
                "CONSTRAINT ID_LOGIN PRIMARY KEY(idLogin)" +
                ");";
        try{
            connection = this.getConnection();
            Statement statement = null;
            statement = connection.createStatement();
            statement.executeUpdate(table);
        }catch (SQLException ex) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString());
        }
    }

    private void EmployeeTable(){
        String table = "CREATE TABLE IF NOT EXISTS EmployeeTable(" +
                "dniEmployee varchar(8) not null," +
                "namesEmployee varchar(16) not null," +
                "surnamesEmployee varchar(36) not null," +
                "CONSTRAINT DNI_EMPLOYEE PRIMARY KEY(dniEmployee)" +
                ");";
        try{
            connection = this.getConnection();
            Statement statement = null;
            statement = connection.createStatement();
            statement.executeUpdate(table);
        }catch (SQLException ex) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString());
        }
    }

    private void ProvedTable(){
        String table = "CREATE TABLE IF NOT EXISTS ProvedTable(" +
                "rucProved varchar(11) not null," +
                "namesProved varchar(16) not null," +
                "surnamesProved varchar(36) not null," +
                "phoneProved varchar(36) not null," +
                "emailProved varchar(36) not null," +
                "CONSTRAINT RUC_PROVED PRIMARY KEY(rucProved)" +
                ");";
        try{
            connection = this.getConnection();
            Statement statement = null;
            statement = connection.createStatement();
            statement.executeUpdate(table);
        }catch (SQLException ex) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString());
        }
    }

    private void ClientTable(){
        String table = "CREATE TABLE IF NOT EXISTS ClientTable(" +
                "dniClient varchar(8) not null," +
                "namesClient varchar(16) not null," +
                "surnamesClient varchar(36) not null," +
                "CONSTRAINT DNI_CLIENT PRIMARY KEY(dniClient)" +
                ");";
        try{
            connection = this.getConnection();
            Statement statement = null;
            statement = connection.createStatement();
            statement.executeUpdate(table);
        }catch (SQLException ex) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString());
        }
    }

    private void CategoryProduct(){
        String table = "CREATE TABLE IF NOT EXISTS CategoryProdTable(" +
                "idCategory varchar(5) not null," +
                "nameCategory varchar(45) not null," +
                "descCategory varchar(45) not null," +
                "CONSTRAINT CATEGORY_ID_PRODUCT PRIMARY KEY(idCategory)" +
                ");";
        try{
            connection = this.getConnection();
            Statement statement = null;
            statement = connection.createStatement();
            statement.executeUpdate(table);
        }catch (SQLException ex) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString());
        }
    }

    private void ProductTable(){
        String table = "CREATE TABLE IF NOT EXISTS ProductTable(" +
                "idProduct varchar(5) not null," +
                "nameProduct varchar(45) not null," +
                "stockProduct int not null," +
                "dateJoin date not null," +
                "idCategory varchar(5) not null," +
                "CONSTRAINT ID_PRODUCT PRIMARY KEY(idProduct)," +
                "CONSTRAINT ID_CATEGORY_PRODUCT FOREIGN KEY(idCategory) references CategoryProdTable(idCategory)" +
                ");";
        try{
            connection = this.getConnection();
            Statement statement = null;
            statement = connection.createStatement();
            statement.executeUpdate(table);
        }catch (SQLException ex) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString());
        }
    }

    private void OrderTable(){
        String table = "CREATE TABLE IF NOT EXISTS OrderTable(" +
                "idOrder varchar(5) not null," +
                "dateOrder date not null," +
                "dniEmployee varchar(8) not null," +
                "rucProved varchar(11) not null," +
                "CONSTRAINT ID_ORDER PRIMARY KEY(idOrder)," +
                "CONSTRAINT DNI_EMPLOYEE_ORDER FOREIGN KEY(dniEmployee) references EmployeeTable(dniEmployee)," +
                "CONSTRAINT RUC_PROVED_ORDER FOREIGN KEY(rucProved) references ProvedTable(rucProved)" +
                ");";
        try{
            connection = this.getConnection();
            Statement statement = null;
            statement = connection.createStatement();
            statement.executeUpdate(table);
        }catch (SQLException ex) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString());
        }
    }

    private void OrderProductTable(){
        String table = "CREATE TABLE IF NOT EXISTS OrderProductTable(" +
                "idOrderProd varchar(5) not null," +
                "priceOrderProd double not null," +
                "stockOrderProd int not null," +
                "idProduct varchar(5) not null," +
                "idOrder varchar(5) not null," +
                "CONSTRAINT ID_ORDER_PRODUCT PRIMARY KEY(idOrderProd)," +
                "CONSTRAINT IDPRODUCT_ORDER FOREIGN KEY(idProduct) references ProductTable(idProduct)," +
                "CONSTRAINT IDORDER_PRODUCT FOREIGN KEY(idOrder) references OrderTable(idOrder)" +
                ");";
        try{
            connection = this.getConnection();
            Statement statement = null;
            statement = connection.createStatement();
            statement.executeUpdate(table);
        }catch (SQLException ex) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString());
        }
    }

    private void RoomsTypeTable(){
        String table = "CREATE TABLE IF NOT EXISTS RoomsTypeTable(" +
                "idTypeRooms varchar(5) not null," +
                "nameTypeRooms varchar(45) not null," +
                "CONSTRAINT ID_TYPE_ROOMS PRIMARY KEY(idTypeRooms)" +
                ");";
        try{
            connection = this.getConnection();
            Statement statement = null;
            statement = connection.createStatement();
            statement.executeUpdate(table);
        }catch (SQLException ex) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString());
        }
    }

    private void RoomsTable(){
        String table = "CREATE TABLE IF NOT EXISTS RoomsTable(" +
                "idRooms varchar(5) not null," +
                "descRooms varchar(45) not null," +
                "priceRooms double not null," +
                "obsRooms varchar(45) not null," +
                "idTypeRooms varchar(5) not null," +
                "CONSTRAINT ID_ROOMS PRIMARY KEY(idRooms)," +
                "CONSTRAINT IDTYPE_ROOMS FOREIGN KEY(idTypeRooms) references RoomsTypeTable(idTypeRooms)" +
                ");";
        try{
            connection = this.getConnection();
            Statement statement = null;
            statement = connection.createStatement();
            statement.executeUpdate(table);
        }catch (SQLException ex) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString());
        }
    }

    private void MethodTable(){
        String table = "CREATE TABLE IF NOT EXISTS MethodTable(" +
                "idMethod varchar(5) not null," +
                "typeMethod varchar(45) not null," +
                "descMethod varchar(45) not null," +
                "CONSTRAINT ID_METHOD PRIMARY KEY(idMethod)" +
                ");";
        try{
            connection = this.getConnection();
            Statement statement = null;
            statement = connection.createStatement();
            statement.executeUpdate(table);
        }catch (SQLException ex) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString());
        }
    }

    private void RoomsRentalTable(){
        String table = "CREATE TABLE IF NOT EXISTS RoomsRentalTable(" +
                "idRental varchar(5) not null," +
                "dateJoin date not null," +
                "dateLeave date not null," +
                "obsRental varchar (45) not null," +
                "dniEmployee varchar (8) not null," +
                "dniClient varchar (8) not null," +
                "idRooms varchar(5) not null," +
                "idMethod varchar (5) not null," +
                "CONSTRAINT IDRENTAL PRIMARY KEY(idRental),"+
                "CONSTRAINT DNIEMPLOYEE_RENTAL FOREIGN KEY(dniEmployee) references EmployeeTable(dniEmployee),"+
                "CONSTRAINT DNICLIENT_RENTAL FOREIGN KEY(dniClient)references ClientTable(dniClient),"+
                "CONSTRAINT IDROOMS_RENTAL FOREIGN KEY(idRooms)references RoomsTable(idRooms),"+
                "CONSTRAINT IDMETHOD_RENTAL FOREIGN KEY(idMethod) references MethodTable(idMethod)"+
                ");";
        try{
            connection = this.getConnection();
            Statement statement = null;
            statement = connection.createStatement();
            statement.executeUpdate(table);
        }catch (SQLException ex) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString());
        }
    }

    private void RoomsReservationTable(){
        String table = "CREATE TABLE IF NOT EXISTS RoomsReservationTable(" +
                "idReservation varchar(5) not null," +
                "dateJoin date not null," +
                "dateLeave date not null," +
                "formReservation varchar (45) not null," +
                "dniEmployee varchar (8) not null," +
                "dniClient varchar (8) not null," +
                "idRooms varchar(5) not null," +
                "CONSTRAINT IDRESERVATION PRIMARY KEY(idReservation),"+
                "CONSTRAINT DNIEMPLOYEE_RESERVATION FOREIGN KEY(dniEmployee) references EmployeeTable(dniEmployee),"+
                "CONSTRAINT DNICLIENT_RESERVATION FOREIGN KEY(dniClient)references ClientTable(dniClient),"+
                "CONSTRAINT IDROOMS_RESERVATION FOREIGN KEY(idRooms)references RoomsTable(idRooms)"+
                ");";
        try{
            connection = this.getConnection();
            Statement statement = null;
            statement = connection.createStatement();
            statement.executeUpdate(table);
        }catch (SQLException ex) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString());
        }
    }

    private void addRootUser(){
        PreparedStatement ps = null;
        try {
            String insert = "INSERT IGNORE INTO LoginTable(idLogin, userLogin, passLogin, roleLogin) VALUES ('5edb0346', 'Joyce', '12345', 'root');";

            ps = DorisRooms.getInstance().getMySQL().getConnection().prepareStatement(insert);
            ps.executeUpdate();

        }catch (SQLException ex){
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, DorisRooms.getInstance().getLanguage().getConfig().get("MySQL_ErrorConnect").getAsString(), null, null);
        }
    }
}
