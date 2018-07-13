package com.frosqh.botpaikea.server.core;

import com.frosqh.botpaikea.server.BotPaikea;
import com.frosqh.botpaikea.server.core.DataBase.ConnectionSQLite;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Session {

    private static boolean init;
    private static Stage stage;
    private static HashMap<String,String> settings;
    private static HashMap<Integer,String> errors;
    private static int errno;
    private static String errmsg;
    private static Player player;
    private static TS3Api ts3Api;
    private final static Logger log = LogManager.getLogger(Session.class);
    private final static String[] keywords={"database"};

    private static final String properties =
            "#Bot Paikea Server Properties\n" +
            "database=BotPaikea.db\n" +
            "dirs=C:\\Users\\Admin\\Music\n"+
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
        loadErrorMessages();
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
            throwError(log,304,true,e.getMessage());
        } catch (IOException e) {
            throwError(log,304,false,e.getMessage());
        }
        String missings = checkSettingsIntegrity();
        if (!missings.equals(""))
            throwError(log,302, missings);
        ConnectionSQLite.fileName = settings.get("database");
    }

    private static void loadErrorMessages() {
        errors = new HashMap<>();
        try {
            FileReader errorReader = new FileReader(BotPaikea.class.getResource("/com/frosqh/botpaikea/server/properties/error.properties").getFile());
            BufferedReader bufRead = new BufferedReader(errorReader);
            String line = null;
            while ((line = bufRead.readLine()) != null) {
                if (!line.startsWith("#") && !line.equals("")){
                    String[] prop = line.split(" = ");
                    Integer errcode = Integer.parseInt(prop[0]);
                    String errmessage = prop[1];
                    errors.put(errcode,errmessage);
                }
            }
            bufRead.close();
            errorReader.close();
        } catch (FileNotFoundException e) {
            log.error("Couldn't load properties from file \"error.properties\" : 'File not Found'");
            System.exit(306);
        } catch (IOException e) {
            log.error("Couldn't load properties from file \"error.properties\" : "+e.getMessage());
            System.exit(306);
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
            throwError(log,305,true,e.getMessage());
        }

    }
    
    private static String checkSettingsIntegrity(){
        Set<String> keySet = settings.keySet();
        int tempno = 302;
        ArrayList<String> errs = new ArrayList<>();
        for (String key : keywords){
            if (!keySet.contains(key))
                errs.add(key);
        }
        String res="";
        for (String e : errs)
            res+=e+", ";
        if (!res.equals(""))
            return res.substring(0,res.length()-2);
        return "";
    }

    public static void saveSettings(){
        try {
            FileWriter propertiesWriter = new FileWriter("./server.properties");
            BufferedWriter bufferedWriter = new BufferedWriter(propertiesWriter);
            bufferedWriter.write("#Bot Paika Server Properties\n");
            for (String key : settings.keySet())
                bufferedWriter.write(key+"="+settings.get(key)+"\n");
            bufferedWriter.close();
            propertiesWriter.close();
        } catch (IOException e) {
            errno=305;
            errmsg="Couldn't save properties into file \"server.properties\" : "+e.getMessage();
            throwError(log,true);
        }


    }



    public static void throwError(Logger logg, int eno, boolean critical){
        throwError(logg,eno,critical,"");
    }

    public static void throwError(Logger logg, int eno, String add){
        throwError(logg,eno,false,add);
    }

    public static void throwError(Logger logg, int eno, boolean critical, String add){
        throwError(logg,eno,critical,add,"...");
    }

    public static void throwError(Logger logg, int eno, boolean critical, String add, String comp){
        errno = eno;
        if (!errors.containsKey(errno))
            errno = 520;
        errmsg = errors.get(errno).replace("...",comp)+add;
        throwError(logg,critical);
    }

    public static void throwError(Logger logg, boolean critical){
        logg.error("\033[0;31m"+"["+errno+"] "+errmsg+"\033[0m");
        if (critical)
            System.exit(errno);
    }

    public static void setPlayer(Player player) {
        Session.player = player;
    }

    public static Player getPlayer(){
        return player;
    }

    public static TS3Api getTs3Api() {
        return ts3Api;
    }

    public static void setTs3Api(TS3Api ts3Api) {
        Session.ts3Api = ts3Api;
    }
}
