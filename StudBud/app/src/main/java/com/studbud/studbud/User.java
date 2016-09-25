package com.studbud.studbud;


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
