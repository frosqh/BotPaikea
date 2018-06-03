package com.frosqh.botpaikea.server.core;

import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Set;

public class Session {

    private static boolean init;
    private static Stage stage;
    private static HashMap<String,String> settings;
    private static int errno;
    private static String errmsg;
    final static Logger log = LogManager.getLogger(Session.class);

    private static final String properties =
            "#Bot Paikea Server Properties\n" +
            "database=BotPaikea.db\n" +
            "";

    public static boolean isInit() {
        return init;
    }

    public static void setInit(boolean init) {
        if (init)
            loadSettings();
        else
            createSettings();
        Session.init = init;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        Session.stage = stage;
    }

    public static HashMap<String, String> getSettings() {
        return settings;
    }

    private static void loadSettings() {
        settings = new HashMap<>();
        try {
            FileReader propertiesReader = new FileReader("./server.properties");
            BufferedReader bufRead = new BufferedReader(propertiesReader);
            String line = null;
            while ((line = bufRead.readLine())!=null){
                if (!line.startsWith("#")) {
                    String[] prop = line.split("=");
                    String key = prop[0];
                    String value = prop[1];
                    settings.put(key,value);
                }
            }
            bufRead.close();
            propertiesReader.close();
        } catch (FileNotFoundException e) {
            errno=301;
            errmsg="Couldn't load properties from file \"server.properties\" : 'File not Found'";
            throwError(log,true);
        } catch (IOException e) {
            errno=304;
            errmsg="Couldn't load properties from file \"server.properties\" : "+e.getMessage();
            throwError(log,true);
        }
        if (!checkSettingsIntegrity()){
            throwError(log,true);
        }
    }

    private static void createSettings() {
        try {
            FileWriter propertiesWriter = new FileWriter("./server.properties");
            BufferedWriter bufferedWriter = new BufferedWriter(propertiesWriter);
            bufferedWriter.write(properties);
            bufferedWriter.close();
            propertiesWriter.close();
            loadSettings();
        } catch (IOException e) {
            errno=305;
            errmsg="Couldn't save properties into file \"server.properties\" : "+e.getMessage();
            throwError(log,true);
        }

    }
    
    private static boolean checkSettingsIntegrity(){
        Set<String> keySet = settings.keySet();
        for (String key : keySet){
            switch (key){
                //TODO Check for each int requiring setting
            }
        }
        if (!keySet.contains("database")) {
            if (errno==302 )
                errmsg += ", database";
            else
                errmsg = "Missing properties's field : database";
            errno = 302;
            return false;
        }
        return true;
    }

    public static void saveSettings(){
        try {
            FileWriter propertiesWriter = new FileWriter("./server.properties");
            BufferedWriter bufferedWriter = new BufferedWriter(propertiesWriter);
            bufferedWriter.write("#Bot Paika Server Properties\n");
            for (String key : settings.keySet()){
                bufferedWriter.write(key+"="+settings.get(key)+"\n");
            }
            bufferedWriter.close();
            propertiesWriter.close();
        } catch (IOException e) {
            errno=305;
            errmsg="Couldn't save properties into file \"server.properties\" : "+e.getMessage();
            throwError(log,true);
        }


    }

    public static void throwError(Logger logg, int eno, String emsg){
        errno=eno;
        errmsg=emsg;
        throwError(logg,false);
    }

    public static void throwError(Logger logg, boolean critical, int eno, String emsg){
        errno=eno;
        errmsg=emsg;
        throwError(logg,critical);
    }

    public static void throwError(Logger logg, boolean critical){
        logg.error("["+errno+"] "+errmsg);
        if (critical)
            System.exit(errno);
    }

    public static void throwError(Logger logg){
        throwError(logg,false);
    }
}
