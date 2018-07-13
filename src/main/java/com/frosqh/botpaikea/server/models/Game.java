package com.frosqh.botpaikea.server.models;

/**
 * Définition de l'objet "Game".
 * @author Rheoklash
 * @version 0.1
 */

public class Game {

    private int id;
    private String title;
    private String game_id;
    private String tags;

    public Game(int id, String title, String game_id, String tags) {
        this.id = id;
        this.title = title;
        this.game_id = game_id;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGameId() { return game_id; }

    public void setGameId(String game_id) {
        this.game_id = game_id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
