package com.frosqh.botpaikea.server.core.DataBase;

import com.frosqh.botpaikea.server.core.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Permet l'instanciation d'une connection SQLite vers la base de données.
 * Garantit également l'unicité de la connexion.
 * @author Frosqh
 * @version 0.1
 */
public class ConnectionSQLite {

    public static String fileName = "BotPaikea.db";

    private static final String url = "jdbc:sqlite:"+fileName;

    private static Connection connect;

    private final static Logger log = LogManager.getLogger(ConnectionSQLite.class);

    /**
     * Garantit le singleton de la connexion SQLite.
     * @return La connexion SQLite déja établie ou une nouvelle connexion  si aucune n'existe.
     * @since 0.1
     */
    static Connection getInstance(){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            Session.throwError(log,1,true);
        }
        if (connect == null){
            try {
                connect = DriverManager.getConnection(url);
            } catch (SQLException e) {
                Session.throwError(log, 2,true);
            }
        }
        return connect;
    }

}
