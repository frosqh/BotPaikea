package com.frosqh.botpaikea.server.core.DataBase;

import com.frosqh.botpaikea.server.models.User;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 */
public class UserDAO extends DAO<User>{

    /**
     *
     * @param id
     * @return
     */
    @Override
    public User find(int id) {
        try {
            Statement stm = this.connect.createStatement();
            String select = "SELECT * FROM user WHERE id = "+id;
            ResultSet result = stm.executeQuery(select);
            User user = new User(id,result.getString("username"),result.getString("password"),result.getString("mail"),result.getString("ytprofile"),result.getString("spprofile"),result.getString("deprofile"));
            stm.close();
            return user;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DataBase Error");
            alert.setHeaderText("Error while getting user from database");
            alert.setResizable(true);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        return null;
    }

    @Override
    public User create(User obj) {
        try {
            Statement stm = this.connect.createStatement();
            String select = "SELECT MAX(id) AS max_id FROM user"; //Getting current max id for auto_increment
            ResultSet result = stm.executeQuery(select);
            int id = 1;
            if (result.next()){
                id = result.getInt(1)+1;
            }
            stm.close();

            PreparedStatement prepare = this.connect.prepareStatement("INSERT INTO user (id, username, password, mail, ytprofile, spprofile, deprofile) VALUES (?,?,?,?,?,?,?)");
            prepare.setString(2,obj.getUsername());
            prepare.setString(3,obj.getPassword());
            prepare.setString(4,obj.getMail());
            if (obj.getYtprofile()!=null){
                prepare.setString(5,obj.getYtprofile());
            }
            if (obj.getSpprofile()!=null){
                prepare.setString(6,obj.getSpprofile());
            }
            if (obj.getDeprofile()!=null){
                prepare.setString(7,obj.getDeprofile());
            }
            prepare.executeUpdate();
            prepare.close();
            return find(id);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DataBase Error");
            alert.setHeaderText("Error while creating user in database");
            alert.setResizable(true);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        return null;
    }

    @Override
    public User update(User obj) {
        try {
            Statement stm = this.connect.createStatement();
            String upd = "UPDATE user SET username = '"+obj.getUsername()+"', password = '"+obj.getPassword()+"', mail = '"+obj.getMail()+"', ytprofile = '"+obj.getYtprofile()+"', spprofile = '"+obj.getSpprofile()+"', deprofile = '"+obj.getDeprofile()+"' WHERE id = "+obj.getId();
            stm.executeUpdate(upd);
            obj = this.find(obj.getId());
            stm.close();
            return obj;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DataBase Error");
            alert.setHeaderText("Error while updating user from database");
            alert.setResizable(true);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        return null;
    }

    @Override
    public void delete(User obj) {
        try {
            Statement stm = this.connect.createStatement();
            String del = "DELETE FROM user WHERE id = "+obj.getId();
            stm.executeUpdate(del);
            del = "DELETE FROM playlist WHERE creator_id = "+obj.getId(); //TODO update to handle songByPlayList delete
            stm.executeUpdate(del);
            stm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<User> getList() {
        ArrayList<User> list = new ArrayList<>();
        try {
            Statement stm = this.connect.createStatement();
            String request = "SELECT id FROM user";
            ResultSet res = stm.executeQuery(request);
            UserDAO p = new UserDAO();
            while (res.next())
                list.add(p.find(res.getInt("id")));
            stm.close();
            return list;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Error while getting users from database");
            alert.setResizable(true);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        return null;
    }

    @Override
    public ArrayList<User> filter(String[] args) {
        ArrayList<User> list = new ArrayList<>();
        try {
            Statement stm = this.connect.createStatement();
            String request = "SELECT id FROM user WHERE"+"BITE";
            ResultSet res = stm.executeQuery(request);
            UserDAO p = new UserDAO();
            while (res.next())
                list.add(p.find(res.getInt("id")));
            stm.close();
            return list;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Error while getting users from database");
            alert.setResizable(true);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        return null;
    }
}
