package com.frosqh.botpaikea.server.core;

import com.frosqh.botpaikea.server.BotPaikea;
import com.frosqh.botpaikea.server.core.DataBase.ConnectionSQLite;
import com.frosqh.botpaikea.server.core.strings.Strings;
import com.frosqh.botpaikea.server.core.strings.fr_fr;
import com.frosqh.botpaikea.server.core.ts3api.TS3Api;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class Session {

    public static boolean debug = false;
    public static Strings locale;
    private static boolean init;
    private static Stage stage;
    private static HashMap<String,String> settings;
    private static HashMap<Integer,String> errors;
    private static int errno;
    private static String errmsg;
    private static Player player;
    private static TS3Api ts3Api;
    private final static Logger log = LogManager.getLogger(Session.class);
    private final static String[] keywords={"database","dirs","port","sv_address","sv_login","sv_password"};
    private final static String[] intExp = {"port"};
    private static final HashMap<String,Thread> clients = new HashMap<>();


    private static final String properties =
            "#Bot Paikea Server Properties\n" +
            "database=BotPaikea.db\n" +
            "dirs=C:\\Users\\Admin\\Music\n"+
            "port=2302\n"+
            "bot_name=Bot Paikea\n"+
            "sv_address=127.0.0.1\n"+
            "sv_login=login_quer\n"+
            "sv_password=password_query";

    public static boolean isInit() {
        return init;
    }

    public static void setInit(boolean init) {
        loadErrorMessages();
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
            String line;
            while ((line = bufRead.readLine())!=null){
                if (!line.startsWith("#")) {
                    String[] prop = line.split("=");
                    String key = prop[0];
                    String value = prop[1];
                    if (Arrays.asList(intExp).contains(key) && !Utils.isInteger(value))
                        throwError(log,12,true,"Integer expected",key);
                    if (key.equals("locale")){
                        switch(value){
                            case "fr_fr":
                                locale = new fr_fr();
                        }
                    } else settings.put(key,value);
                }
            }
            bufRead.close();
            propertiesReader.close();
        } catch (IOException e) {
            throwError(log,11,true,e.getMessage(),"server.properties");
        }
        String missings = checkSettingsIntegrity();
        if (!missings.equals(""))
            throwError(log,13,true,missings);
        ConnectionSQLite.fileName = settings.get("database");
    }

    private static void loadErrorMessages() {
        errors = new HashMap<>();
        try {
            String name;
            if (debug)
                name = "/com/frosqh/botpaikea/server/properties/error.properties/";
            else
                name = "properties/error.properties";
            BufferedReader bufRead = new BufferedReader(new InputStreamReader(BotPaikea.class.getResourceAsStream(name)));
            //FileReader errorReader = new FileReader(BotPaikea.class.getResource(name).getFile());
            //BufferedReader bufRead = new BufferedReader(errorReader);
            String line;
            while ((line = bufRead.readLine()) != null) {
                if (!line.startsWith("#") && !line.equals("")){
                    String[] prop = line.split(" = ");
                    Integer errcode = Integer.parseInt(prop[0]);
                    String errmessage = prop[1];
                    errors.put(errcode,errmessage);
                }
            }
            bufRead.close();
            //errorReader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(52);
            //throwError(log,11,true,e.getMessage(),"error.properties");
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
            throwError(log,10,true,e.getMessage(),"server.properties");
        }

    }
    
    private static String checkSettingsIntegrity(){
        Set<String> keySet = settings.keySet();
        ArrayList<String> errs = new ArrayList<>();
        for (String key : keywords){
            if (!keySet.contains(key))
                errs.add(key);
        }
        StringBuilder res= new StringBuilder();
        for (String e : errs)
            res.append(e).append(", ");
        if (!res.toString().equals(""))
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
            throwError(log,10,true,e.getMessage(),"server.properties");
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
            errno = -1;
        errmsg = errors.get(errno).replace("...",comp)+add;
        throwError(logg,critical);
    }

    private static void throwError(Logger logg, boolean critical){
        logg.error("\033[0;31m"+"["+errno+"] "+errmsg+"\033[0m");

        if (critical) {
            System.exit(errno);
            ts3Api.exit();
        }
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

    public static boolean hasClient(InetAddress inetAddress) {
        return clients.containsKey(inetAddress.toString());
    }

    public static void stopClient(InetAddress inetAddress) {
        clients.get(inetAddress.toString()).interrupt();
        clients.remove(inetAddress.toString());
    }

    public static void addClient(InetAddress inetAddress, Server thread) {
        clients.put(inetAddress.toString(),thread);
    }

    public static String getFromSettings(String str){
        return settings.get(str);
    }
}
