package com.frosqh.botpaikea.server.core.DataBase;

import com.frosqh.botpaikea.server.core.Session;
import com.frosqh.botpaikea.server.models.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Définition des actions sur la table des différents jeux.
 * @see DAO
 * @see DataBase
 * @author Rheoklash
 * @version 0.1
 */

public class GameDAO extends DAO<Game>{

    private final static Logger log = LogManager.getLogger(GameDAO.class);

    @Override
    public Game find(int id) {
        try {
            Statement stm = this.connect.createStatement();
            String select = "SELECT * FROM game WHERE id = "+id;
            ResultSet result = stm.executeQuery(select);
            Game game = new Game(id, result.getString("title"), result.getString("game_id"), result.getString("tags"));
            stm.close();
            return game;
        } catch (SQLException e) {
            Session.throwError(log,5,false,e.getMessage(),"game");
        }
        return null;
    }

    @Override
    public Game create(Game obj) {
        try {
            Statement stm = this.connect.createStatement();
            String select = "SELECT MAX(id) AS max_id FROM game"; //Select *
            ResultSet result = stm.executeQuery(select);
            int id = 1;
            if (result.next()){
                id = result.getInt(1)+1;
            }
            stm.close();

            PreparedStatement prepare = this.connect.prepareStatement("INSERT INTO game (id, title, game_id, tags) VALUES (?,?,?,?)");
            prepare.setString(2,obj.getTitle());
            prepare.setString(3,obj.getGameId());
            if (obj.getTags() != null)
                prepare.setString(4,obj.getTags());

            prepare.executeUpdate();
            prepare.close();
            return find(id);
        } catch (SQLException e) {
            Session.throwError(log,6,false,e.getMessage(),"game");
        }
        return null;
    }

    @Override
    public Game update(Game obj) {
        try {
            Statement stm = this.connect.createStatement();
            String upd = "UDPATE game SET title = '"+obj.getTitle()+"', "
                    + "game_id = '"+obj.getGameId()+"', "
                    + "tags = '"+obj.getTags() + "' WHERE id="+obj.getId();
            stm.executeUpdate(upd);
            obj = this.find(obj.getId());
            stm.close();
            return obj;

        } catch (SQLException e) {
            Session.throwError(log,7,false,e.getMessage(),"game");
        }
        return null;
    }

    @Override
    public void delete(Game obj) {
        try {
            Statement stm = this.connect.createStatement();
            String del = "DELETE FROM game WHERE id = "+obj.getId();
            stm.executeUpdate(del);
            stm.close();
        } catch (SQLException e) {
            Session.throwError(log,8,false,e.getMessage(),"game");
        }
    }

    @Override
    public ArrayList<Game> getList() {
        ArrayList<Game> list = new ArrayList<>();
        try {
            Statement stm = this.connect.createStatement();
            String request = "SELECT id FROM game";
            ResultSet res = stm.executeQuery(request);
            GameDAO p = new GameDAO();
            while (res.next())
                list.add(p.find(res.getInt("id")));
            stm.close();
            return list;
        } catch (SQLException e) {
            Session.throwError(log,9,false,e.getMessage(),"game");
        }
        return null;
    }

    @Override
    public ArrayList<Game> filter(String[] args) {
        String where;

        {
            StringBuilder whereB= new StringBuilder();
            boolean b = true;
            for (String s : args) {
                whereB.append(!b?"'":"").append(s).append(!b?"'":"").append(b ? "=" : " AND ");
                b=!b;
            }
            where = whereB.toString().substring(0,whereB.lastIndexOf("A")-1);
        }
        ArrayList<Game> list = new ArrayList<>();
        try {
            Statement stm = this.connect.createStatement();
            String request = "SELECT id FROM game WHERE "+where;
            ResultSet res = stm.executeQuery(request);
            GameDAO p = new GameDAO();
            while (res.next())
                list.add(p.find(res.getInt("id")));
            stm.close();
            return list;
        } catch (SQLException e) {
            Session.throwError(log,9,false,e.getMessage(),"game");
        }
        return null;
    }

}
