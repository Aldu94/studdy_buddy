package com.studbud.studbud;

    /*
     * In this class, the user attributes can be defined by the set methods or
     * retrieved by the get methods
     */
public class User {

    /*
     * Here we indicate the variables used by the class
     */
    private String name;
    private MainSubject mainSubject;
    private int numberOfSemester;
    private int score;
    /*
     * standard constructor of the class to initiate the variables
     */
    public User(String name, int numberOfSemester, MainSubject mainSubject, int score) {
        this.name = name;
        this.mainSubject = mainSubject;
        this.numberOfSemester = numberOfSemester;
        this.score = score;
    }


    /*
     * This method returns the name of the user
     */
    public String getName(){
        return name;
    }

    /*
     * This method sets the name of the user to a specified value
     */
    public void setName(String name) {
        this.name = name;
    }


    /*
     * This method returns the ID of the MainSubject (0 for INF, 1 for MI)
     */
    public MainSubject getMainSubject(){
        return mainSubject;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

    /*
     * This method returns the semester the user is currently in
     */
    public int getNumberOfSemester(){
        return numberOfSemester;
    }

}
