package com.frosqh.botpaikea.server;

import com.frosqh.botpaikea.server.core.DataBase.DataBase;
import com.frosqh.botpaikea.server.core.DataBase.SongDAO;
import com.frosqh.botpaikea.server.core.Player;
import com.frosqh.botpaikea.server.core.Session;
import com.frosqh.botpaikea.server.models.Song;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BotPaikea extends Application{

    final static Logger log = LogManager.getLogger(BotPaikea.class);

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(BotPaikea.class.getResource("/com/frosqh/botpaikea/server/views/Home.fxml"));
        Session.setStage(primaryStage);


        SongDAO songDAO = new SongDAO();
        ArrayList<Song> songs = songDAO.getList();
        File f = new File(songs.get(35).getLocalurl());
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(f.toURI().toString()));
        Player player = new Player(mediaPlayer);
        player.add(songs.get(35));
        player.next();


        primaryStage.setTitle("BotPaikea");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        log.debug("Executing main() method");
        File properties = new File("./server.properties");
        Session.setInit(properties.exists());
        DataBase db = new DataBase();
        db.refreshSongs();
        launch(args);

    }
}