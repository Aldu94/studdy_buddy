package com.studbud.studbud.domain;

import java.util.ArrayList;

/**
 * Created by Aldu on 24.09.16.
 */
public class Module {

    ArrayList<CourseItem> courses;
    String name;
    private CourseItem courseItem;

    public Module(String name, ArrayList<CourseItem> courses) {
        this.name = name;
        this.courses = courses;
    }

    //setter method for arrayList with courseItems
    public Module(ArrayList<CourseItem> courses){
        this.courses = courses;
    }

    //getter method for arrayList with courseItems
    public ArrayList<CourseItem> getCourses() {
        return courses;
    }

    //getter method for CourseItems withing the specified ArrayList
    public CourseItem getCourseItem(){
        for (CourseItem courseItem : getCourses()){
            this.courseItem = courseItem;
        }
        return courseItem;
    }

    // method to calculate the grade of an array with CourseItems according to their weights
    public double calculateGrade() {
        double sum = 0;
        for (CourseItem course : getCourses()) {
            sum += course.getMark() * course.getWeight();
        }
        return sum;
    }

}