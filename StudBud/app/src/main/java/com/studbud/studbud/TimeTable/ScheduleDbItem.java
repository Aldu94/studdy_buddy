package com.studbud.studbud.TimeTable;

public class ScheduleDbItem {

    private String name;
    private String content;
    private long id;

    public ScheduleDbItem(String name, String content, long id){
        this.name = name;
        this.content = content;
        this.id = id;
    }

    // getter method for the name value
    public String getName(){
        return name;
    }

    // setter method for the name value
    public void setName(String name){
        this.name = name;
    }

    // getter method for the content
    public String getContent(){
        return content;
    }

    // setter method for the content
    public void setContent(String content){
        this.content = content;
    }

    // getter method for the id
    public long getId(){
        return id;
    }

    // setter method for the id
    public void setId(long id){
        this.id = id;
    }

    // creates a string from the values in id and name
    public String toString(){
        return id + " " + name;
    }
}
