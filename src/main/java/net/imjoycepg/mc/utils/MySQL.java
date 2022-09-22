package net.imjoycepg.mc.utils;

import com.dustinredmond.fxalert.FXAlert;
import net.imjoycepg.mc.DorisRooms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

    public Connection connectDatabase(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, "¡Error!");
        }

        try {

            final String host = DorisRooms.getInstance().getDatabase().getHost();
            final String port = DorisRooms.getInstance().getDatabase().getPort();
            final String database = DorisRooms.getInstance().getDatabase().getDatabase();
            final String user = DorisRooms.getInstance().getDatabase().getUsername();
            final String password = DorisRooms.getInstance().getDatabase().getPassword();

            final String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
            connection = DriverManager.getConnection(url, user, password);
            DorisRooms.getInstance().sceneMainMenu();
        } catch (SQLException ex) {
            FXAlert.setGlobalTitleBarIcon(DorisRooms.getInstance().getAlertImage());
            FXAlert.showException(ex, "¡Error!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
