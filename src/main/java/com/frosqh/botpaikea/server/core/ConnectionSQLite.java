package com.frosqh.botpaikea.server.core;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSQLite {

    private static String fileName = "BotPaikea.db";

    private static String url = "jdbc:sqlite:"+fileName;

    private static Connection connect;

    public static Connection getInstance(){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("JDBC Error");
            alert.setHeaderText("Error on startup");
            alert.setResizable(true);
            alert.setContentText("Error while loading the JDBC driver");
            alert.showAndWait();
            System.exit(0);
        }

        if (connect == null){
            try {
                connect = DriverManager.getConnection(url);
            } catch (SQLException e) {
                e.printStackTrace();
                /*Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("JDBC Error");
                alert.setHeaderText("Error on startup");
                alert.setResizable(true);
                alert.setContentText("Error while loading the database");
                alert.showAndWait();*/
                System.exit(0);
            }
        }
        return connect;
    }

}
