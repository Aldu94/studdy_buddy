package com.studbud.studbud;

/**
 * Created by Aldu on 31.08.16.
 */


enum MainSubject {
    MI, INF;

    public String getName() {
        switch (this) {
            case MI: return "Medieninformatik";
            case INF: return "Informationswissenschaft";
            default: return "Invalid MainSubject";
        }
    }
}


public class User {

    private String name;
    private MainSubject mainSubject;
    private int numberOfSemester;


    public User(String name, int numberOfSemester, MainSubject mainSubject) {
        this.name = name;
        this.mainSubject = mainSubject;
        this.numberOfSemester = numberOfSemester;
    }


    //gibt Namen des Nutzers zurück

    public String getName(){
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    //gibt ID des Hauptfachs zurück (0/1)

    public MainSubject getMainSubject(){
        return mainSubject;
    }


    //gibt Semesteranzahl zurück

    public int getNumberOfSemester(){
        return numberOfSemester;
    }

}
