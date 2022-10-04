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
            addRootUser();

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
