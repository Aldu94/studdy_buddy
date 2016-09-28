package com.studbud.studbud;

/**
 * This is the MainSubject object
 */


public enum MainSubject {
     INF, MI;

    /*
     * in this method we can find out which string we have to return depending on the string
     * given to the fromString method
     */
    public String getName() {
        switch (this) {
            case MI: return "Medieninformatik";
            case INF: return "Informationswissenschaft";
            default: return "Invalid MainSubject";
        }
    }

    /*
     * this method calls the getName method depending on the String we provide
     */
    public static MainSubject fromString(String string) {
        if(string.equals(MI.getName())){
            return MI;
        }
        else{
            return INF;
        }
    }
}
