package com.studbud.studbud.domain;

/**
 * Created by Der Bar.de on 16.08.2016.
 */

public class CourseItem implements Comparable<CourseItem>{
    private String rating;
    private String name;
    private String path;
    private String status;

    public CourseItem(String name, String status, String rating){
        this.name = name;
        this.path = status;
        this.rating = rating;
    }

    /* gibt dem Nutzer den Namen des CourseItems zurück */
    public String getName(){
        return name;
    }

    /* Wenn der Nutzer dem CourseItem einen Namen geben will, kann er diese Methode aufrufen */
    public void setName(String name) {
        this.name = name;
    }

    /* Hier lässt sich das Rating des CourseItems speichern, wenn es geändert wurde */
    public void setRating(String rating){
        this.rating = rating;
    }

    /* Die methode gibt das aktuelle Rating des Courseitem zurück */
    public String getRating() {
        return rating;
    }

    /* Mit dieser Methode kann die App die CourseItems anhand der Namen vergleichen. Kann für die Sortierung der FoodieItems innerhalb der Gallery verwendet werden */
    @Override
    public int compareTo(CourseItem another){
        return getName().compareTo(another.getName());
    }

    public String getStatus() {
        return status;
    }
}



