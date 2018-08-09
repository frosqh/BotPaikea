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
    private final List<Song> history;
    private List<Song> queue;
    private double vol = 50;

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    private boolean isPlaying;
    private final static Logger log = LogManager.getLogger(Player.class);

    public Player(){
        history = new ArrayList<>();
        queue = new ArrayList<>();
        isPlaying=false;
    }

    public Song getPlaying(){
        return queue.get(0);
    }

    public void next(){
        if (mediaPlayer!=null)
            mediaPlayer.stop();
        isPlaying=false;
        if (!queue.isEmpty())
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
        if (mediaPlayer!=null)
            mediaPlayer.dispose();
        mediaPlayer = new MediaPlayer(media);
        Session.getStage().setOnCloseRequest(windowEvent -> mediaPlayer.stop());
        initPlayer();
        mediaPlayer.play();
        isPlaying=true;
    }

    public void prev(){
        if (!history.isEmpty()){
            if (mediaPlayer!=null)
                mediaPlayer.stop();
            queue.add(0,history.remove(history.size()-1));
            Song nextSong = queue.get(0);
            log.info("♪ Now Playing - "+nextSong.getTitle()+" ♪");
            File f = new File(nextSong.getLocalurl());
            Media media = new Media(f.toURI().toString());
            if (mediaPlayer!=null)
                mediaPlayer.dispose();
            mediaPlayer = new MediaPlayer(media);
            Session.getStage().setOnCloseRequest(windowEvent -> mediaPlayer.stop());
            initPlayer();
            mediaPlayer.play();
            isPlaying=true;
        }
    }

    public double getDuration(){
        return mediaPlayer.getTotalDuration().toMillis();
    }

    public boolean play(){
        if (mediaPlayer != null) {
            if (!isPlaying) {
                mediaPlayer.play();
                isPlaying = true;
                return true;
            }
        }
        return false;
    }

    public boolean pause(){
        if (mediaPlayer != null) {
            if (isPlaying) {
                mediaPlayer.pause();
                isPlaying = false;
                return true;
            }
        }
        return false;
    }

    public double getTimeCode(){
        return mediaPlayer.getCurrentTime().toMillis();
    }

    public void setVolume(double volume){
        vol = volume;
        mediaPlayer.setVolume(vol);
    }

    public double getVolume(){
        return mediaPlayer.getVolume();
    }

    private void initPlayer(){
        mediaPlayer.setVolume(vol);
        mediaPlayer.setOnEndOfMedia(this::next);
    }

    public void add(Song newSong){
        queue.add(newSong);
    }

    public void toggleAutoPlay() {
        if (mediaPlayer.getOnEndOfMedia()==null){
            mediaPlayer.setOnEndOfMedia(this::next);
        } else {
            mediaPlayer.setOnEndOfMedia(null);
        }
    }
}
