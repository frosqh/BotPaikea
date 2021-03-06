package com.frosqh.botpaikea.server.core.DataBase;

import com.frosqh.botpaikea.server.core.Session;
import com.frosqh.botpaikea.server.models.PlayList;
import com.frosqh.botpaikea.server.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Gestionnaire des champs correspondants aux playlists dans la base de données.
 * @author Frosqh
 * @version 0.1
 */
public class PlayListDAO extends DAO<PlayList> {

    private final static Logger log = LogManager.getLogger(PlayListDAO.class);

    /**
     * Renvoie, si elle existe, la playlist ayant pour id la valeur passée en paramètre.
     * @param id L'id à trouver.
     * @return La playlist correspondant à id si elle existe, null sinon.
     * @since 0.1
     */
    @Override
    public PlayList find(int id) {
        try {
            Statement stm = this.connect.createStatement();
            String select = "SELECT * FROM playlist WHERE id = "+id;
            ResultSet result = stm.executeQuery(select);
            UserDAO p = new UserDAO();
            User creator = p.find(result.getInt("creator_id"));
            PlayList playList = new PlayList(id, result.getString("name"),creator);
            stm.close();
            return playList;
        } catch (SQLException e) {
            Session.throwError(log,5,false,e.getMessage(),"playlist");
        }
        return null;
    }

    /**
     * Créer un nouveau élément dans la base de données, correspondant à l'objet passé en paramètre.
     * @param obj L'objet à insérer dans la base de données.
     * @return L'objet crée, directement récupérer de la base de données, donc avec id valide.
     * @since 0.1
     */
    @Override
    public PlayList create(PlayList obj) {
        try {
            Statement stm = this.connect.createStatement();
            String select = "SELECT MAX(id) AS max_id FROM playlist";
            ResultSet result = stm.executeQuery(select);
            int id =1;
            if (result.next())
                id = result.getInt(1)+1;
            stm.close();

            PreparedStatement prepare = this.connect.prepareStatement("INSERT INTO playlist (id, name, creator_id) VALUES (?,?,?)");
            prepare.setString(2,obj.getName());
            prepare.setInt(3,obj.getCreator_id().getId());

            prepare.executeUpdate();
            prepare.close();
            return find(id);
        } catch (SQLException e) {
            Session.throwError(log,6,false,e.getMessage(),"playlist");
        }
        return null;
    }

    /**
     * Harmonise un objet entre sa version "Java" et son existence dans la base de données.
     * @param obj L'objet Java à enregistrer.
     * @return L'objet tiré de la base de données après harmonisation.
     * @since 0.1
     */
    @Override
    public PlayList update(PlayList obj) {
        try {
            Statement stm = connect.createStatement();
            String upd = "UPDATE playlist SET name = '"+obj.getName()+", "
                    + "creator_id = "+obj.getCreator_id().getId()+" WHERE id="+obj.getId();
            stm.executeUpdate(upd);
            obj = this.find(obj.getId());
            stm.close();
            return obj;
        } catch (SQLException e) {
            Session.throwError(log,7,false,e.getMessage(),"playlist");
        }
        return null;
    }

    /**
     * Supprime un objet de la base de données.
     * @param obj L'objet à supprimer.
     * @since 0.1
     */
    @Override
    public void delete(PlayList obj) {
        try {
            Statement stm = connect.createStatement();
            String del = "DELETE FROM playlist WHERE id = "+obj.getId();
            stm.executeUpdate(del);
            del = "DELETE FROM songbyplaylist WHERE playlist_id = "+obj.getId();
            stm.executeUpdate(del);
            stm.close();
        } catch (SQLException e) {
            Session.throwError(log,8,false,e.getMessage(),"playlist");
        }

    }

    /**
     * Récupère la liste des playlists enregistrées dans la base de données.
     * @return La liste des playlists enregistrées en base de données.
     * @since 0.1
     */
    @Override
    public ArrayList<PlayList> getList() {
        ArrayList<PlayList> list = new ArrayList<>();
        try {
            Statement stm = connect.createStatement();
            String request = "SELECT id FROM playlist";
            ResultSet res = stm.executeQuery(request);
            PlayListDAO p = new PlayListDAO();
            while (res.next())
                list.add(p.find(res.getInt("id")));
            stm.close();
            return list;
        } catch (SQLException e) {
            Session.throwError(log,9,false,e.getMessage(),"playlist");
        }
        return null;
    }

    /**
     * Filtre les objets avec des critères passés en paramètres.
     * @param args La liste des critère à appliquer, au format "SELECT * FROM ? WHERE arg1 AND arg2 AND ..."
     * @return La liste des objets en base de données correspondant aux critères.
     */
    @Override
    public ArrayList<PlayList> filter(String[] args) {
        String where;
        {
            StringBuilder whereB = new StringBuilder();
            boolean b = true;
            for (String s : args){
                whereB.append(!b?"'":"").append(s).append(!b?"'":"").append(b?"=":"AND");
                b=!b;
            }
            where = whereB.toString().substring(0,whereB.lastIndexOf("A")-1);
        }
        ArrayList<PlayList> list = new ArrayList<>();
        try {
            Statement stm = connect.createStatement();
            String request = "SELECT id FROM playlist WHERE "+where;
            ResultSet res = stm.executeQuery(request);
            PlayListDAO p = new PlayListDAO();
            while (res.next())
                list.add(p.find(res.getInt("id")));
            stm.close();
            return list;
        } catch (SQLException e) {
            Session.throwError(log,9,false,e.getMessage(),"playlist");
        }
        return null;
    }
}
