package com.frosqh.botpaikea.server.core;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class DiskFileExplorer {

    private String initialPath = "";
    private Boolean recursivePath = false;
    public int fileCount = 0;
    public int dirCount = 0;
    private static final List<String> authExt = Arrays.asList(new String[]{"mp3","wav"});

    public DiskFileExplorer(String path, Boolean subFolder){
        super();
        this.initialPath = path;
        this.recursivePath = subFolder;
    }

    public String list(){
        return listDirectory(initialPath);
    }

    private String listDirectory(String dir){
        String s ="";
        String s2;
        String ext="";
        File file = new File(dir);
        File[] files = file.listFiles();
        if (files!=null)
            for (File f : files){
                ext="";
                s2 = f.getName();
                int  j = s2.lastIndexOf(".");
                if (j>0)
                    ext = s2.substring(j+1);
                if (authExt.contains(ext)){
                    s+=s2.substring(0,j)+"\n";
                    fileCount++;
                }
                if (f.isDirectory() && recursivePath){
                    s+=listDirectory(f.getAbsolutePath());
                    dirCount++;
                }
            }
        return s;
    }
}
