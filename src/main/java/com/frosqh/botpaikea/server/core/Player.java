package com.frosqh.botpaikea.server.core;

import com.frosqh.botpaikea.server.core.DataBase.SongDAO;
import com.frosqh.botpaikea.server.models.Song;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private MediaPlayer mediaPlayer;
    private List<Song> history;
    private List<Song> queue;

    public Player(MediaPlayer player){
        mediaPlayer = player;
        mediaPlayer.setVolume(50);
        mediaPlayer.setOnStopped(() -> next());
    }

    public void next(){
        mediaPlayer.stop();
        history.add(queue.remove(0));
        if (queue.isEmpty()){ //Choose a random song in our song List;
            SongDAO songDAO = new SongDAO();
            ArrayList<Song> songList = songDAO.getList();
            Song nextSong = songList.get((int) (Math.random()*(songList.size()-1)));
        } else {

        }
    }

    public String getTimeCode(){
        return mediaPlayer.getCurrentTime().toString();
    }

    public double getVolume(){
        return mediaPlayer.getVolume();
    }
}
