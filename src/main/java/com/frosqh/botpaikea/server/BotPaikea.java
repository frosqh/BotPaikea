package com.frosqh.botpaikea.server;

import com.frosqh.botpaikea.server.core.DataBase.DataBase;
import com.frosqh.botpaikea.server.core.Player;
import com.frosqh.botpaikea.server.core.Server;
import com.frosqh.botpaikea.server.core.Session;
import com.frosqh.botpaikea.server.core.ts3api.TS3Api;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class BotPaikea extends Application{

    private final static Logger log = LogManager.getLogger(BotPaikea.class);

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(BotPaikea.class.getResource("/com/frosqh/botpaikea/server/views/Home.fxml"));
        Session.setStage(primaryStage);
        primaryStage.setTitle("BotPaikea");
        primaryStage.setScene(new Scene(root));
        //primaryStage.show();
    }


    public static void main(String[] args) {
        /*Mixer.Info[] mixerInfo =  AudioSystem.getMixerInfo();

        for(int i = 0; i < mixerInfo.length; i++)
        {
            System.out.println(mixerInfo[i].getName());
        }*/
        System.setProperty( "file.encoding", "UTF-8" );
        log.debug("Executing main() method");
        File properties = new File("./server.properties");
        Session.setInit(properties.exists());
        DataBase db = new DataBase();
        db.refreshSongs();
        Player player = new Player();
        Session.setPlayer(player);
        Session.setTs3Api(new TS3Api());
        new Thread(Server::main).start();
        launch(args);
    }
}