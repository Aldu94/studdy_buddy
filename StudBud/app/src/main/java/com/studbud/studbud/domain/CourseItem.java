package com.studbud.studbud.domain;

/**
 * Created by Der Bar.de on 16.08.2016.
 */

public class CourseItem implements Comparable<CourseItem>{
    private String mark;
    private String name;
    private String module;
    private String status;
    private String submodule;

    public CourseItem(String module, String submodule, String name, String status, String mark){
        this.name = name;
        this.status = status;
        this.mark = mark;
        this.module = module;
        this.submodule = submodule;
    }

    /* gibt dem Nutzer den Namen des CourseItems zurück */
    public String getName(){
        return name;
    }

    /* Wenn der Nutzer dem CourseItem einen Namen geben will, kann er diese Methode aufrufen */
    public void setName(String name) {
        this.name = name;
    }

    /* Hier lässt sich die Note des CourseItems speichern, wenn es geändert wurde */
    public void setMark(String mark){
        this.mark = mark;
    }

    /* Die methode gibt die Note des Courseitem zurück */
    public String getMark() {
        return mark;
    }
    public String getModule(){
        return module;
    }

    public void setModule(String module){
        this.module = module;
    }

    public String getSubmodule(){
        return submodule;
    }

    public void setSubmodule(String submodule){
        this.submodule = submodule;
    }
    /* Mit dieser Methode kann die App die CourseItems anhand der Namen vergleichen. Kann für die Sortierung der CourseItems verwendet werden */
    @Override
    public int compareTo(CourseItem another){
        return getModule().compareTo(another.getModule());
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}



