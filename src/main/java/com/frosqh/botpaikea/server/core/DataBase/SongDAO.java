package com.frosqh.botpaikea.server.core.DataBase;

import com.frosqh.botpaikea.server.core.Session;
import com.frosqh.botpaikea.server.models.Song;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class SongDAO extends DAO<Song>{

    private final static Logger log = LogManager.getLogger(SongDAO.class);

    @Override
    public Song find(int id) {
        try {
            Statement stm = this.connect.createStatement();
            String select = "SELECT * FROM song WHERE id = "+id;
            ResultSet result = stm.executeQuery(select);
            Song song = new Song(id, result.getString("title"), result.getString("artist"), result.getString("localurl"), result.getString("weburl"));
            stm.close();
            return song;
        } catch (SQLException e) {
            Session.throwError(log,5,false,e.getMessage(),"song");
        }
        return null;
    }

    @Override
    public Song create(Song obj) {
        try {
            Statement stm = this.connect.createStatement();
            String select = "SELECT MAX(id) AS max_id FROM song"; //Select *
            ResultSet result = stm.executeQuery(select);
            int id = 1;
            if (result.next()){
                id = result.getInt(1)+1;
            }
            stm.close();

            PreparedStatement prepare = this.connect.prepareStatement("INSERT INTO song (id, title, artist, localurl, weburl) VALUES (?,?,?,?,?)");
            prepare.setString(2,obj.getTitle());
            prepare.setString(3,obj.getArtist());
            if (obj.getLocalurl() != null)
                prepare.setString(4,obj.getLocalurl());
            if (obj.getWeburl() != null)
                prepare.setString(5,obj.getWeburl());

            prepare.executeUpdate();
            prepare.close();
            return find(id);
        } catch (SQLException e) {
            Session.throwError(log,6,false,e.getMessage(),"song");
        }
        return null;
    }

    @Override
    public Song update(Song obj) {
        try {
            Statement stm = this.connect.createStatement();
            String upd = "UDPATE song SET title = '"+obj.getTitle()+"', "
                    + "artist = '"+obj.getArtist()+"', "
                    + "localurl = '"+obj.getLocalurl()+"', "
                    + "weburl = '"+obj.getWeburl()+"' WHERE id="+obj.getId();
            stm.executeUpdate(upd);
            obj = this.find(obj.getId());
            stm.close();
            return obj;

        } catch (SQLException e) {
            Session.throwError(log,7,false,e.getMessage(),"song");
        }
        return null;
    }

    @Override
    public void delete(Song obj) {
        try {
            Statement stm = this.connect.createStatement();
            String del = "DELETE FROM song WHERE id = "+obj.getId();
            stm.executeUpdate(del);
            del = "DELETE FROM songbylist WHERE song_id = "+obj.getId();
            stm.executeUpdate(del);
            stm.close();
        } catch (SQLException e) {
            Session.throwError(log,8,false,e.getMessage(),"song");
        }
    }

    @Override
    public ArrayList<Song> getList() {
        ArrayList<Song> list = new ArrayList<>();
        try {
            Statement stm = this.connect.createStatement();
            String request = "SELECT id FROM song";
            ResultSet res = stm.executeQuery(request);
            SongDAO p = new SongDAO();
            while (res.next())
                list.add(p.find(res.getInt("id")));
            stm.close();
            return list;
        } catch (SQLException e) {
            Session.throwError(log,9,false,e.getMessage(),"song");
        }
        return null;
    }

    @Override
    public ArrayList<Song> filter(String[] args) {
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
        ArrayList<Song> list = new ArrayList<>();
        try {
            Statement stm = this.connect.createStatement();
            String request = "SELECT id FROM song WHERE "+where;
            ResultSet res = stm.executeQuery(request);
            SongDAO p = new SongDAO();
            while (res.next())
                list.add(p.find(res.getInt("id")));
            stm.close();
            return list;
        } catch (SQLException e) {
           Session.throwError(log,9,false,e.getMessage(),"song");
        }
        return null;
    }
}
