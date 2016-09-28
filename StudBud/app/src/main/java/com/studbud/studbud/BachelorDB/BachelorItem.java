package com.studbud.studbud.BachelorDB;

/**
 * Created by Der Bar.de on 27.09.2016.
 */
public class BachelorItem {
    private String name;
    private long id;
    private Double mark;

    public BachelorItem(String name, long id, Double mark) {
        this.name = name;
        this.mark = mark;
        this.id = id;
    }


    // getter method for the name of the bachelorItem
    public String getName() {
        return name;
    }


    // getter method for the name of the bachelorItem
    public void setName(String name) {
        this.name = name;
    }


    // getter method for the mark of the bachelorItem
    public Double getMark() {
        return mark;
    }


    // setter method for the mark of the bachelorItem
    public void setMark(Double mark) {
        this.mark = mark;
    }


    // getter method for the id of the bachelorItem
    public long getId() {
        return id;
    }


    // setter method for the id of the bachelorItem
    public void setId(long id) {
        this.id = id;
    }


    // method to create a string from the values id and name of the BachelorItem
    public String toString() {
        return id + " " + name;
    }
}
