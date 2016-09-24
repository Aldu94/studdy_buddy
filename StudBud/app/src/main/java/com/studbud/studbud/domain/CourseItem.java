package com.studbud.studbud.domain;

import java.util.ArrayList;

/**
 * Created by Der Bar.de on 16.08.2016.
 */

public class CourseItem implements Comparable<CourseItem>{
    private double mark;
    private String name;
    private int module;
    private int submodule;
    private double weight;

    public CourseItem(String name, int module, int submodule, double mark, double weight) {
        this.name = name;
        this.mark = mark;
        this.module = module;
        this.submodule = submodule;
        this.weight = weight;
    }

    /* gibt dem Nutzer den Namen des CourseItems zurück */
    public String getName(){
        return name;
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

    /* Die methode gibt die Note des Courseitem zurück */
    public double getMark() {
        return mark;
    }

    public int getModule(){
        return module;
    }

    public void setModule(int module){
        this.module = module;
    }

    public int getSubmodule(){
        return submodule;
    }

    public void setSubmodule(int submodule){
        this.submodule = submodule;
    }

    /* Mit dieser Methode kann die App die CourseItems anhand der Namen vergleichen. Kann für die Sortierung der CourseItems verwendet werden */
    @Override
    public int compareTo(CourseItem another){
        return getName().compareTo(another.getName());
    }
}