package com.frosqh.botpaikea.server.core;

import com.frosqh.botpaikea.server.core.DataBase.SongDAO;
import com.frosqh.botpaikea.server.models.Song;

import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiskFileExplorer {

    private String initialPath;
    private Boolean recursivePath;
    private int fileCount = 0;
    private int dirCount = 0;
    private static final List<String> authExt = Arrays.asList("mp3","wav");

    public DiskFileExplorer(String path, Boolean subFolder){
        initialPath = path;
        recursivePath = subFolder;
    }

    public String list(){
        return listDirectory(initialPath);
    }

    private String listDirectory(String dir){
        StringBuilder s = new StringBuilder();
        String s2;
        String ext;
        File file = new File(dir);
        File[] files = file.listFiles();
        if (files==null) return "";
        for (File f : files) {
            ext = "";
            s2 = f.getName();
            int j = s2.lastIndexOf(".");
            if (j > 0)
                ext = s2.substring(j + 1);
            if (authExt.contains(ext)) {
                s.append(s2).append("\n");
                fileCount++;
            }
            if (f.isDirectory() && recursivePath) {
                s.append(listDirectory(f.getAbsolutePath()));
                dirCount++;
            }
        }
        return s.toString();
    }

    public void refreshDataBase(){
        SongDAO songDAO = new SongDAO();
        ArrayList<Song> songs = songDAO.getList();
        ArrayList<Song> toDelete = new ArrayList<>();
        ArrayList<Song> toAdd = new ArrayList<>();
        List<String> paths = Arrays.asList(listDirectory(initialPath).split("\n"));
        /*File f = new File(songs.get(0).getLocalurl());
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(f.toURI().toString()));
        Player player = new Player(mediaPlayer);
        player.play();*/
        //TODO Move this to appropriate area (and create a thread, should be better :p)

        for (Song s : songs){
            String URL = s.getLocalurl();
            if (URL!=null && !paths.contains(URL)){
                songDAO.delete(s);
            } else {
                paths.remove(URL);
            }
        }
        LocalDateTime now = LocalDateTime.now();
        if (paths.size()>0){
            for (String p : paths){
                Song temp = new Song(-1,p.substring(0,p.indexOf("_-_")).replace("_"," "),p.substring(p.indexOf("_-_")+3).replace("_"," "),initialPath+"\\"+p,null);
                //TODO Afficher une fenêtre
                songDAO.create(temp);
            }
        }
        LocalDateTime then = LocalDateTime.now();
        System.out.println(ChronoUnit.SECONDS.between(now,then)+"s");
    }
}
