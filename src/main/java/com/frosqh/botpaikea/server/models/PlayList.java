package com.frosqh.botpaikea.server.models;

public class PlayList {

    private int id;
    private String name;
    private User creator_id;

    public PlayList(int id, String name, User creator_id) {
        this.id = id;
        this.name = name;
        this.creator_id = creator_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(User creator_id) {
        this.creator_id = creator_id;
    }
}
