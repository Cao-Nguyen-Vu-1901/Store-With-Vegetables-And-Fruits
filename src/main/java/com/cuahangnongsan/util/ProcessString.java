package com.cuahangnongsan.util;

public class ProcessString {
    public static String getFirstName(String name){
        if(name.indexOf(" ") <= 0){
            return name;
        }else {
            return name.substring(0, name.indexOf(" "));
        }
    }
}
