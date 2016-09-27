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

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }
    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }

    public String toString(){
        return id + " " + name;
    }
}
