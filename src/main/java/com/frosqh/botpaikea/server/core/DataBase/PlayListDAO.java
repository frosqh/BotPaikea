package com.frosqh.botpaikea.server.core.DataBase;

import com.frosqh.botpaikea.server.models.PlayList;
import com.frosqh.botpaikea.server.models.User;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PlayListDAO extends DAO<PlayList> {
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DataBase Error");
            alert.setHeaderText("Error while getting playlist from database");
            alert.setResizable(true);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        return null;
    }

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

            PreparedStatement prepare = this.connect.prepareStatement("INSERT INTO ");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public PlayList update(PlayList obj) {
        return null;
    }

    @Override
    public void delete(PlayList obj) {

    }

    @Override
    public ArrayList<PlayList> getList() {
        return null;
    }

    @Override
    public ArrayList<PlayList> filter(String[] args) {
        return null;
    }
}
