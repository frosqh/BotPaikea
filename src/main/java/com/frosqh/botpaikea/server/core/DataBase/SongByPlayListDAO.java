package com.frosqh.botpaikea.server.core.DataBase;

import com.frosqh.botpaikea.server.core.Session;
import com.frosqh.botpaikea.server.models.PlayList;
import com.frosqh.botpaikea.server.models.Song;
import com.frosqh.botpaikea.server.models.SongByPlayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SongByPlayListDAO extends DAO<SongByPlayList> {

    private final static Logger log = LogManager.getLogger(PlayListDAO.class);

    /**
     * Renvoie, si elle existe, la table de correspondant liste/chansons ayant pour id la valeur passée en paramètre.
     * @param id L'id à trouver.
     * @return La table correspondant à id si elle existe, null sinon.
     * @since 0.1
     */
    @Override
    public SongByPlayList find(int id) {
        try {
            Statement stm = this.connect.createStatement();
            String select = "SELECT * FROM playlist WHERE id = "+id;
            ResultSet result = stm.executeQuery(select);
            SongDAO songDAO = new SongDAO();
            PlayListDAO playListDAO = new PlayListDAO();
            Song song = songDAO.find(result.getInt("song_id"));
            PlayList playlist = playListDAO.find(result.getInt("list_id"));
            SongByPlayList songByPlayList = new SongByPlayList(id,song,playlist);
            stm.close();
            return songByPlayList;
        } catch (SQLException e) {
            Session.throwError(log,5,false,e.getMessage(),"songbylist");
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
    public SongByPlayList create(SongByPlayList obj) {
        try {
            Statement stm = this.connect.createStatement();
            String select = "SELECT MAX(id) AS max_id FROM songbylist";
            ResultSet result = stm.executeQuery(select);
            int id =1;
            if (result.next())
                id = result.getInt(1)+1;
            stm.close();

            PreparedStatement prepare = this.connect.prepareStatement("INSERT INTO playlist (id, song_id, list_id) VALUES (?,?,?)");
            prepare.setInt(2,obj.getSong_id().getId());
            prepare.setInt(3,obj.getList_id().getId());
            prepare.executeUpdate();
            prepare.close();
            return find(id);
        } catch (SQLException e) {
            Session.throwError(log,6,false,e.getMessage(),"songbylist");
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
    public SongByPlayList update(SongByPlayList obj) {
        try {
            Statement stm = connect.createStatement();
            String upd = "UPDATE playlist SET song_id = '"+obj.getSong_id().getId()+", "
                    + "list_id = "+obj.getList_id().getId()+" WHERE id="+obj.getId();
            stm.executeUpdate(upd);
            obj = this.find(obj.getId());
            stm.close();
            return obj;
        } catch (SQLException e) {
            Session.throwError(log,7,false,e.getMessage(),"songbylist");
        }
        return null;
    }
    /**
     *
     * Supprime un objet de la base de données.
     * @param obj L'objet à supprimer.
     * @since 0.1
     */
    @Override
    public void delete(SongByPlayList obj) {
        try {
            Statement stm = connect.createStatement();
            String del = "DELETE FROM songbylist WHERE id = "+obj.getId();
            stm.executeUpdate(del);
            stm.close();
        } catch (SQLException e) {
            Session.throwError(log,8,false,e.getMessage(),"songbylist");
        }
    }

    /**
     * Récupère la liste des tables enregistrées dans la base de données.
     * @return La liste des tables enregistrées en base de données.
     * @since 0.1
     */
    @Override
    public ArrayList<SongByPlayList> getList() {
        ArrayList<SongByPlayList> list = new ArrayList<>();
        try {
            Statement stm = connect.createStatement();
            String request = "SELECT id FROM songbylist";
            ResultSet res = stm.executeQuery(request);
            SongByPlayListDAO p = new SongByPlayListDAO();
            while (res.next())
                list.add(p.find(res.getInt("id")));
            stm.close();
            return list;
        } catch (SQLException e) {
            Session.throwError(log,9,false,e.getMessage(),"songbylist");
        }
        return null;
    }

    /**
     * Filtre les objets avec des critères passés en paramètres.
     * @param args La liste des critère à appliquer, au format "SELECT * FROM ? WHERE arg1 AND arg2 AND ..."
     * @return La liste des objets en base de données correspondant aux critères.
     */
    @Override
    public ArrayList<SongByPlayList> filter(String[] args) {
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
        ArrayList<SongByPlayList> list = new ArrayList<>();
        try {
            Statement stm = connect.createStatement();
            String request = "SELECT id FROM songbylist WHERE "+where;
            ResultSet res = stm.executeQuery(request);
            SongByPlayListDAO p = new SongByPlayListDAO();
            while (res.next())
                list.add(p.find(res.getInt("id")));
            stm.close();
            return list;
        } catch (SQLException e) {
            Session.throwError(log,9,false,e.getMessage(),"songbylist");
        }
        return null;
    }
}
