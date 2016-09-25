package com.studbud.studbud;

/**
 * Created by Aldu on 31.08.16.
 */


public enum MainSubject {
    MI, INF;

    public String getName() {
        switch (this) {
            case MI: return "Medieninformatik";
            case INF: return "Informationswissenschaft";
            default: return "Invalid MainSubject";
        }
    }

    public static MainSubject fromString(String string) {
        if(string.equals(MI.getName())){
            return MI;
        }
        else{
            return INF;
        }
    }
}
