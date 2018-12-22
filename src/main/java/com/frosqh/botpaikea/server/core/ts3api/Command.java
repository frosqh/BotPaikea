package com.frosqh.botpaikea.server.core.ts3api;

import com.frosqh.botpaikea.server.core.Session;
import com.frosqh.botpaikea.server.core.ts3api.spellchecker.LevenshteinDistance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public abstract class Command {

    private final static Logger log = LogManager.getLogger(Command.class);

    private static final String[] base_commands = {"paikea", "next", "play", "pause", "prev","toggleautoplay","info"};

    private static final String[] easter_eggs = {Session.locale.EASTER_SHIT(), "ok google", "><", "nan", "no", "nope", "nan", "niet", "nein", "pong", "ping", "plop"};

    private static final String[] complex_commands = {"help", "setvolume"};

    public static boolean isBase(String str){
        return Arrays.asList(base_commands).contains(str.substring(1)) && str.charAt(0)=='!';
    }

    public static boolean isEaster(String str){
        return Arrays.asList(easter_eggs).contains(str);
    }

    public static boolean isComplex(String str){
        return Arrays.asList(complex_commands).contains(str.substring(1)) && str.charAt(0)=='!';
    }

    public static boolean isHelpable(String str){
        return isBase(str) || isComplex(str);
    }

    public static boolean isCommand(String str) {
        return isEaster(str) || ((isBase(str.substring(1)) || isComplex(str.substring(1))) && str.charAt(0)=='!');
    }

    public static String isAlmostACommand(String str){
        str = str.substring(1);
        for (String base : base_commands)
            if (LevenshteinDistance.getDistance(base, str) < 2)
                return base;

        for (String complex : complex_commands)
            if (LevenshteinDistance.getDistance(complex,str)<2)
                return complex;
        return null;
    }

    public static String getUsage(String cmd){
        try {
            Method m = Session.locale.getClass().getDeclaredMethod("USAGE_"+cmd.toUpperCase(),null);
            Object c = m.invoke(Session.locale,null);
            return (String) c;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            Session.throwError(log,15,cmd);
            return "/!\\ Undefined/!\\";
        }
    }

    public static String execBase(String cmd){
        String rep=null;
        switch (cmd.substring(1)){
            case "help":
                try {
                    rep = Session.locale.LIST();
                    for (String command : base_commands) {
                        String desc = (String) Session.locale.getClass().getDeclaredMethod("DESC_" + command.toUpperCase(), null).invoke(Session.locale, null);
                        rep += "      • !" + command +" : "+desc+"\n";
                    }
                    for (String command : complex_commands) {
                        String desc = (String) Session.locale.getClass().getDeclaredMethod("DESC_" + command.toUpperCase(), null).invoke(Session.locale, null);
                        rep += "      • !" + command +" : "+desc+"\n";
                    }
                    rep += Session.locale.SEE_MORE();
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    Session.throwError(log,16,e.getMessage().substring(e.getMessage().lastIndexOf('.')+1));
                    return "/!\\ Undefined/!\\";
                }
                break;
            case "paikea":
                rep=Session.locale.PAIKEA_SONG;
                break;
            case "next":
                Session.getPlayer().next();
                rep = Session.locale.NOW_PLAYING(Session.getPlayer().getPlaying().getTitle(),Session.getPlayer().getPlaying().getArtist());
                break;
            case "prev":
                Session.getPlayer().getPlaying();
                if (Session.getPlayer().prev())
                    rep = Session.locale.NOW_PLAYING(Session.getPlayer().getPlaying().getTitle(),Session.getPlayer().getPlaying().getArtist());
                else
                    rep = Session.locale.NO_PREV();
                break;
            case "pause":
                if (Session.getPlayer().isPlaying()) {
                    Session.getPlayer().pause();
                    rep = "";
                }
                else
                    rep = Session.locale.ERROR_PAUSE();
                break;
            case "play":
                if (!Session.getPlayer().isPlaying())
                    if (Session.getPlayer().play())
                        rep = Session.locale.SUCC_PLAY();
                    else
                        rep = Session.locale.ERROR_ON_PLAY();
                else
                    rep = Session.locale.ERROR_PLAY();
                break;
            case "toggleautoplay":
                Session.getPlayer().toggleAutoPlay();
                if (Session.getPlayer().isAutoPlayActive())
                    rep = Session.locale.TOGGLE_AUTOPLAY_ON();
                else
                    rep = Session.locale.TOGGLE_AUTOPLAY_OFF();
                break;
            case "info":
                rep = Session.getPlayer().getInfos();
                break;
            default:
                rep = Session.locale.UNDEFINED_BEHAVIOR();
        }
        return rep;
    }

    public static String execEaster(String cmd){
        if (cmd.equals(Session.locale.EASTER_SHIT())){
            return Session.locale.EASTER_SHIT_RESPONSE();
        }
        switch (cmd){
            case "ok google":
                return Session.locale.EASTER_GOOGLE_RESPONSE();
            case "><":
                return ":D";
            case "nan":
            case "nope":
            case "no":
            case "niet":
            case "non":
            case "nein":
                return Session.locale.EASTER_NO_RESPONSE();
            case "pong":
                return "Ping ?";
            case "ping":
                return "Pong !";
            case "plop":
                return Session.locale.EASTER_PLOP_RESPONSE();
            default:
                return Session.locale.EASTER_UNDEFINED_BEHAVIOUR();

        }
    }

    public static String execComplex(String[] args) {
        switch (args[0].substring(1)){
            case "help":
                if (args.length<2){
                    return Session.locale.USAGE_HELP();
                } else {
                    String toDetail = args[1];
                    if (Command.isHelpable("!"+toDetail)) return Command.getUsage(toDetail);
                    else return Session.locale.NOT_FOUND(toDetail);
                }
            case "setvolume":
                if (args.length<2)
                    return Session.locale.USAGE_SETVOLUME();
                else{
                    Session.getPlayer().setVolume(Double.parseDouble(args[1]));
                    return Session.locale.WIP();
                }
            default:
                return Session.locale.UNDEFINED_BEHAVIOR();
        }
    }
}
