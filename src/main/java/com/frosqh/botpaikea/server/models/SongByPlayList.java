package com.frosqh.botpaikea.server.models;

public class SongByPlayList {

    private int id;
    private Song song_id;
    private PlayList list_id;

    public SongByPlayList(int id, Song song_id, PlayList list_id) {
        this.id = id;
        this.song_id = song_id;
        this.list_id = list_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Song getSong_id() {
        return song_id;
    }

    public void setSong_id(Song song_id) {
        this.song_id = song_id;
    }

    public PlayList getList_id() {
        return list_id;
    }

    public void setList_id(PlayList list_id) {
        this.list_id = list_id;
    }
}
