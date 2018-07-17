package com.frosqh.botpaikea.server.core.DataBase;

import com.frosqh.botpaikea.server.core.DiskFileExplorer;
import com.frosqh.botpaikea.server.core.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Représente la base donnée, correspond majoritairement à sa création.
 * @see ConnectionSQLite
 * @see DAO
 * @author Frosqh
 * @author Rheoklash
 * @version 0.2
 */
public class DataBase {

    private final static Logger log = LogManager.getLogger(DataBase.class);

    /**
     * Permet la création de la base de données.
     * @since 0.1
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public DataBase(){
        File db = new File(Session.getSettings().get("database"));
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

            String tableGame = "CREATE TABLE IF NOT EXISTS game (\n"
                    + "id INTEGER PRIMARY KEY,\n"
                    + "title TEXT NOT NULL,\n"
                    + "game_id TEXT NOT NULL,\n"
                    + "tags TEXT\n);";

            Statement stm = null;
            try {
                stm = connect.createStatement();
            } catch (SQLException e) {
                Session.throwError(log,3,true," on database creation");
            }
            assert stm != null;
            log.debug("Creating song table !");
            try {
                stm.execute(tableSong);
            } catch (SQLException e) {
                db.delete();
                Session.throwError(log,4,true,"","Song");
            }
            log.debug("Creating user table !");
            try {
                stm.execute(tableUser);
            } catch (SQLException e) {
                db.delete();
                Session.throwError(log,4,true,"","User");
            }
            log.debug("Creating playlist table !");
            try {
                stm.execute(tablePlayList);
            } catch (SQLException e) {
                db.delete();
                Session.throwError(log,4,true,"","PlayList");
            }
            log.debug("Creating songbylist table !");
            try {
                stm.execute(tableSongByList);
            } catch (SQLException e) {
                db.delete();
                Session.throwError(log,4,true,"","SongByList");
            }
            log.debug("Creating game table !");
            try {
                stm.execute(tableGame);
            } catch (SQLException e) {
                db.delete();
                Session.throwError(log,4,true,"","Game");
            }
            log.debug("DataBase created");
        }
    }

    /**
     * Rafraîchit la base de données des chansons à partir des différents dossier précisés dans le 'server.properties'
     */
    public void refreshSongs() {
        String[] files = Session.getSettings().get("dirs").split(";");
        for (String f : files){
            DiskFileExplorer dfe = new DiskFileExplorer(f,true);
            dfe.refreshDataBase();
        }
    }
}
