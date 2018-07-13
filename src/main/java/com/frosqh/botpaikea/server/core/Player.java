package com.frosqh.botpaikea.server.core;

import com.frosqh.botpaikea.server.core.DataBase.SongDAO;
import com.frosqh.botpaikea.server.models.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Player {

    private MediaPlayer mediaPlayer;
    private List<Song> history;
    private List<Song> queue;
    private final static Logger log = LogManager.getLogger(Player.class);

    public Player(MediaPlayer player){
        mediaPlayer = player;
        history = new ArrayList<>();
        queue = new ArrayList<>();
        initPlayer();
        Session.getStage().setOnCloseRequest(windowEvent -> {
            mediaPlayer.stop();
        });
    }

    public void next(){
        mediaPlayer.stop();
        history.add(queue.remove(0));
        Song nextSong;
        if (queue.isEmpty()){ //Choose a random song in our song List;
            SongDAO songDAO = new SongDAO();
            ArrayList<Song> songList = songDAO.getList();
            nextSong = songList.get((int) (Math.random()*(songList.size()-1)));
            queue.add(nextSong);
        }
        nextSong = queue.get(0);
        log.info("♪ Now Playing - "+nextSong.getTitle()+" ♪");
        File f = new File(nextSong.getLocalurl());
        Media media = new Media(f.toURI().toString());
        mediaPlayer.dispose();
        mediaPlayer = new MediaPlayer(media);
        initPlayer();
        mediaPlayer.play();
    }

    public void play(){
        mediaPlayer.play();
    }

    public void pause(){
        mediaPlayer.pause();
    }

    public String getTimeCode(){
        return mediaPlayer.getCurrentTime().toString();
    }

    public double getVolume(){
        return mediaPlayer.getVolume();
    }

    private void initPlayer(){
        mediaPlayer.setVolume(50);
        mediaPlayer.setOnEndOfMedia(() -> next());
    }

    public void add(Song newSong){
        queue.add(newSong);
    }
}
