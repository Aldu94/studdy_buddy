package com.studbud.studbud.persistence;

/**
 * Created by Der Bar.de on 16.08.2016.
 */
import java.util.ArrayList;
import com.studbud.studbud.domain.CourseItem;

public class Repository {

    public void open() {

    }

    public void close() {

    }

    public long addCourse(CourseItem courseItem) {
        return 0;
    }

    public CourseItem getFoodieItem(String courseItemID) {
        return null;
    }

    public ArrayList<CourseItem> getAllCourseItems() {

        return null;
    }

    public long updateRating(String courseItemID, float rating) {

        return 0;
    }

    public long updateTitle(String courseItemID, String title) {

        return 0;
    }

    public int deleteCourseItem(String courseItemID) {

        return 0;
    }
}
