package com.frosqh.botpaikea.server.core;

import com.frosqh.botpaikea.server.core.DataBase.SongDAO;
import com.frosqh.botpaikea.server.models.Song;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread {

    private static boolean stopServer = false;
    private final Socket socket;
    private boolean stopClient=false;
    private BufferedReader in;
    private PrintStream out;

    private static final Logger log = LogManager.getLogger(Server.class);

    private Server(Socket socketClient){
        socket = socketClient;
        new Thread(() -> {
            try {
                while (!stopClient){sleep(1000);update();}
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void run(){
        String receivedData;
        log.debug("New connection : "+socket.getInetAddress());
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintStream(socket.getOutputStream());
            while (!stopClient){
                while((receivedData = in.readLine())==null){
                    wait(200);
                }
                if (receivedData.equals("plop")){
                    SongDAO songDAO = new SongDAO();
                    ArrayList<Song> list = songDAO.getList();
                    out.println(list.toString());
                }
                if (receivedData.equals("next")){
                    Session.getPlayer().next();
                    update();
                }
                if (receivedData.equals("prev")){
                    Session.getPlayer().prev();
                    update();
                }
                if (receivedData.equals("pp")){
                    if (Session.getPlayer().isPlaying())
                        Session.getPlayer().pause();
                    else
                        Session.getPlayer().play();
                    update();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void update(){
        boolean isP = Session.getPlayer().isPlaying();
        Song songPl = Session.getPlayer().getPlaying();
        int t = (int) Session.getPlayer().getTimeCode();
        int v = (int) Session.getPlayer().getVolume();
        int d = (int) Session.getPlayer().getDuration();
        out.println("info▬"+isP+"▬"+songPl+"▬"+t+"▬"+v+"▬"+d+"▬");
    }

    public static void main(){
        int port = Integer.parseInt(Session.getSettings().get("port"));
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            log.info("Launching server");
            while(!stopServer){
                Socket socketClient = serverSocket.accept();
                if (Session.hasClient(socketClient.getInetAddress()))
                    Session.stopClient(socketClient.getInetAddress());
                Server thread = new Server(socketClient);
                Session.addClient(socketClient.getInetAddress(),thread);
                thread.start();
            }
            serverSocket.close();
        } catch (IOException e) {
            Session.throwError(log,14,true,e.getMessage());
        }

    }
}
