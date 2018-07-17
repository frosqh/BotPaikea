package com.frosqh.botpaikea.server.core;

class Utils {
    public static boolean isInteger(String str){
        try{
            Integer.parseInt(str);
            return true;
        } catch (Exception e){
            return false;
        }
    }

}
