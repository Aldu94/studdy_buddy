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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String toString() {
        return id + " " + name;
    }
}
