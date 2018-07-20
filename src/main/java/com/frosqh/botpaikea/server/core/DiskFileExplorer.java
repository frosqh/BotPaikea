package com.frosqh.botpaikea.server.core;

import com.frosqh.botpaikea.server.core.DataBase.SongDAO;
import com.frosqh.botpaikea.server.models.Song;

import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DiskFileExplorer {

    private final String initialPath;
    private Boolean recursivePath;
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
            }
            if (f.isDirectory() && recursivePath) {
                s.append(listDirectory(f.getAbsolutePath()));
            }
        }
        return s.toString();
    }

    public void refreshDataBase(){
        SongDAO songDAO = new SongDAO();
        ArrayList<Song> songs = songDAO.getList();
        List<String> paths = new LinkedList<>(Arrays.asList(listDirectory(initialPath).split("\n")));

        for (Song s : songs){
            String URL1 = s.getLocalurl();
            String URL = URL1.replace(initialPath,"");
            URL = URL.substring(1);
            if (!paths.contains(URL)){
                songDAO.delete(s);
            } else {
                paths.remove(URL);
            }
        }
        LocalDateTime now = LocalDateTime.now();
        if (paths.size()>0){
            for (String p : paths){
                Song temp = new Song(-1,p.substring(p.indexOf("_-_")+3).replace("_"," "),p.substring(0,p.indexOf("_-_")).replace("_"," "),initialPath+"\\"+p,null);
                //TODO Afficher une fenêtre
                songDAO.create(temp);
            }
        }
        LocalDateTime then = LocalDateTime.now();
        System.out.println(ChronoUnit.SECONDS.between(now,then)+"s");

    }
}
