package com.frosqh.botpaikea.server;

import com.frosqh.botpaikea.server.core.DataBase.DataBase;
import com.frosqh.botpaikea.server.core.Session;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class BotPaikea extends Application{

    final static Logger log = LogManager.getLogger(BotPaikea.class);

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/com/frosqh/botpaikea/server/views/Home.fxml"));
        primaryStage.setTitle("BotPaikea");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        File properties = new File("./server.properties");
        Session.setInit(properties.exists());
        log.debug("Executing main() method");
        DataBase db = new DataBase();
        launch(args);
    }
}