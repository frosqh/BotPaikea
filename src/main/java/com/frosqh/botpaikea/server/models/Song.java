package com.frosqh.botpaikea.server.models;

public class Song {

    private int id;
    private String title;
    private String artist;
    private String localurl;
    private String weburl;

    public Song(int id, String title, String artist, String localurl, String webur) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.localurl = localurl;
        this.weburl = webur;
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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getLocalurl() {
        return localurl;
    }

    public void setLocalurl(String localurl) {
        this.localurl = localurl;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String webur) {
        this.weburl = webur;
    }
}
