package com.frosqh.botpaikea.server.core;

import javafx.scene.control.Alert;
import com.frosqh.botpaikea.server.models.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SongDAO extends DAO<Song>{


    @Override
    public Song find(int id) {
        try {
            Statement stm = this.connect.createStatement();
            String select = "SELECT * FROM song WHERE id = "+id;
            ResultSet result = stm.executeQuery(select);
            Song song = new Song(id, result.getString("title"), result.getString("artist"), result.getString("localurl"), result.getString("weburl"));
            return song;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Error while getting song from database");
            alert.setResizable(true);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        return null;
    }

    @Override
    public Song create(Song obj) {
        return null;
    }

    @Override
    public Song update(Song obj) {
        return null;
    }

    @Override
    public void delete(Song obj) {

    }

    @Override
    public ArrayList<Song> getList() {
        return null;
    }

    @Override
    public ArrayList<Song> filter(String[] args) {
        return null;
    }
}
