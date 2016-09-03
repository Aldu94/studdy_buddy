package com.studbud.studbud.domain;

/**
 * Created by Aldu on 01.09.16.
 */
public class ScheduleItem {

    private String title;
    private String startTime;
    private String endTime;
    private String room;

    public ScheduleItem(String title, String startTime, String endTime, String room){
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
    }


    // getter-methods

    public String getTitle(){
        return title;
    }

    public String getStartTime(){
        return startTime;
    }

    public String getEndTime(){
        return endTime;
    }

    public String getRoom(){
        return room;
    }


    //setter-methods

    public void setTitle(String title){
        this.title = title;
    }

    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }

    public void setRoom(String room){
        this.room = room;
    }
}
