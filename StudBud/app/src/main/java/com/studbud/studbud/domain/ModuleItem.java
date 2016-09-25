package com.studbud.studbud.domain;

import com.studbud.studbud.MainSubject;

/**
 * Created by Aldu on 25.09.16.
 */
public class ModuleItem {

    private double mark;
    private String name;
    private int module;
    private double weight;
    private MainSubject subject;

    public ModuleItem(String name, int module, double mark, double weight, MainSubject subject) {
        this.name = name;
        this.mark = mark;
        this.module = module;
        this.weight = weight;
        this.subject = subject;
    }

    /* gibt dem Nutzer den Namen des CourseItems zurück */
    public String getName(){
        return name;
    }

    public MainSubject getSubject(){
        return subject;
    }

    public double getWeight() {
        return weight;
    }

    /* Wenn der Nutzer dem CourseItem einen Namen geben will, kann er diese Methode aufrufen */
    public void setName(String name) {
        this.name = name;
    }

    /* Hier lässt sich die Note des CourseItems speichern, wenn es geändert wurde */
    public void setMark(int mark){
        this.mark = mark;
    }

    public double getMark() {
        return mark;
    }

    public int getModule(){
        return module;
    }

    public void setModule(int module){
        this.module = module;
    }

    public int compareTo(CourseItem another){
        return getName().compareTo(another.getName());
    }
}
