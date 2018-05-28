package com.frosqh.botpaikea.server.models;

public class User {

    private int id;
    private String username;
    private String password;
    private String mail;
    private String ytprofile;
    private String spprofile;
    private String deprofile;

    public User(int id, String username, String password, String mail, String ytprofile, String spprofile, String deprofile) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.ytprofile = ytprofile;
        this.spprofile = spprofile;
        this.deprofile = deprofile;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getYtprofile() {
        return ytprofile;
    }

    public void setYtprofile(String ytprofile) {
        this.ytprofile = ytprofile;
    }

    public String getSpprofile() {
        return spprofile;
    }

    public void setSpprofile(String spprofile) {
        this.spprofile = spprofile;
    }

    public String getDeprofile() {
        return deprofile;
    }

    public void setDeprofile(String deprofile) {
        this.deprofile = deprofile;
    }

    @Override
    public String toString() {
        return "User : "+username+" at "+mail;
    }




}
