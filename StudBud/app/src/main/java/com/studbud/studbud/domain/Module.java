package com.studbud.studbud.domain;

import java.util.ArrayList;

/**
 * Created by Aldu on 24.09.16.
 */
public class Module {

    ArrayList<CourseItem> courses;
    String name;

    public Module(String name, ArrayList<CourseItem> courses) {
        this.name = name;
        this.courses = courses;
    }

    public ArrayList<CourseItem> getCourses() {
        return courses;
    }

    public double calculateGrade() {
        double sum = 0;
        for (CourseItem course : getCourses()) {
            sum += course.getMark() * course.getWeight();
        }
        return sum;
    }

}