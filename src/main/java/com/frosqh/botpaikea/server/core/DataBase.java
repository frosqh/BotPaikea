package com.frosqh.botpaikea.server.core;

import java.io.File;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

    public DataBase(){
        File db = new File("BotPaikea.db");
        if (!db.exists()) {
            Connection connect = ConnectionSQLite.getInstance();

            String tableSong = "CREATE TABLE IF NOT EXISTS song (\n"
                    + "id INTEGER PRIMARY KEY,\n"
                    + "title TEXT NOT NULL,\n"
                    + "artist TEXT NOT NULL,\n"
                    + "localurl TEXT,\n"
                    + "weburl TEXT\n);";

            String tableUser = "CREATE TABLE IF NOT EXISTS user (\n"
                    + "id INTEGER PRIMARY KEY,\n"
                    + "username TEXT NOT NULL,\n"
                    + "password TEXT NOT NULL,\n"
                    + "mail TEXT NOT NULL,\n"
                    + "ytprofile TEXT,\n"
                    + "spprofile TEXT,\n"
                    + "deprofile TEXT\n);";

            String tablePlayList = "CREATE TABLE IF NOT EXISTS playlist (\n"
                    + "id INTEGER PRIMARY KEY,\n"
                    + "name TEXT NOT NULL,\n"
                    + "creator_id INTEGER NOT_NULL,\n"
                    + "FOREIGN KEY (creator_id) REFERENCES user(id)\n);";

            String tableSongByList = "CREATE TABLE IF NOT EXISTS songbylist (\n"
                    + "id INTEGER PRIMARY KEY,\n"
                    + "song_id INTEGER NOT NULL,\n"
                    + "list_id INTEGER NOT NULL,\n"
                    + "FOREIGN KEY (song_id) REFERENCES song(id),\n"
                    + "FOREIGN KEY (list_id) REFERENCES playlist(id)\n);";



            try {
                Statement stm = connect.createStatement();
                stm.execute(tableSong);
                stm.execute(tableUser);
                stm.execute(tablePlayList);
                stm.execute(tableSongByList);
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("DataBase could not be created, please try again");
                db.delete();
            }
        }
    }
}
