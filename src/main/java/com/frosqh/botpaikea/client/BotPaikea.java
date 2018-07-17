package com.frosqh.botpaikea.client;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

public class BotPaikea extends Application{

    private static final Logger log = LogManager.getLogger(BotPaikea.class);

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        log.debug("Executing main() method");
        InetAddress serveur = InetAddress.getByName("localhost");
        Socket socket = new Socket(serveur,2302);
        socket.setSoTimeout(0);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream out = new PrintStream(socket.getOutputStream());
        out.println("plop");
        String data;
        while ((data = in.readLine())==null){
            Thread.sleep(500);
        }
        out.println("next");
        Thread.sleep(5000);
        out.println("pp");
        while (true) {
            while ((data = in.readLine()) == null) {
                Thread.sleep(500);
            }
            System.out.println(Arrays.asList(data.split("▬")));
        }
    }
}
