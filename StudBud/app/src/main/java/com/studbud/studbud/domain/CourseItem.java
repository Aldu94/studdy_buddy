package com.studbud.studbud.domain;

import com.studbud.studbud.MainSubject;

import javax.security.auth.Subject;

/**
 * Created by Der Bar.de on 16.08.2016.
 */

public class CourseItem implements Comparable<CourseItem>{
    private double mark;
    private String name;
    private int module;
    private int submodule;
    private double weight;
    private MainSubject subject;

    public CourseItem(String name, int module, int submodule, double mark, double weight, MainSubject subject) {
        this.name = name;
        this.mark = mark;
        this.module = module;
        this.submodule = submodule;
        this.weight = weight;
        this.subject = subject;
    }

    // getter method for the name of the courseItem
    public String getName(){
        return name;
    }

    // getter method for the subject of the courseItem
    public MainSubject getSubject(){
        return subject;
    }

    // getter method for the weight of the courseItem
    public double getWeight() {
        return weight;
    }

    // setter method to set the name of the courseItem
    public void setName(String name) {
        this.name = name;
    }

    // setter method to set the mark of the courseitem
    public void setMark(int mark){
        this.mark = mark;
    }

    // getter method for the mark of the courseItem
    public double getMark() {
        return mark;
    }

    // getter method for the module of the courseItem
    public int getModule(){
        return module;
    }

    // setter method for the module of the courseItem
    public void setModule(int module){
        this.module = module;
    }

    // getter method for the submodule of the courseItem
    public int getSubmodule(){
        return submodule;
    }

    // setter method for the submodule of the courseItem
    public void setSubmodule(int submodule){
        this.submodule = submodule;
    }

    // method to compare courseItems in order to be able to sort them within a list
    @Override
    public int compareTo(CourseItem another){
        return getName().compareTo(another.getName());
    }
}