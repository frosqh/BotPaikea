package com.frosqh.botpaikea.server.core.DataBase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

    final static Logger log = LogManager.getLogger(DataBase.class);


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
                log.debug("Creating song table !");
                stm.execute(tableSong);
                log.debug("Creating user table !");
                stm.execute(tableUser);
                log.debug("Creating playlist table !");
                stm.execute(tablePlayList);
                log.debug("Creating songbylist table !");
                stm.execute(tableSongByList);
                log.debug("DataBase created");
            } catch (SQLException e) {
                log.error("Database could not be created !");
                db.delete();
            }
        }
    }
}
