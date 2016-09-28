package com.studbud.studbud.domain;

import com.studbud.studbud.MainSubject;

/**
 * This is the ModuleItem class, which handles all actions concerning the attributes of a ModuleItem
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

    /*
     * public method returning the name of the moduleItem, so other classes can access the info
     */
    public String getName(){
        return name;
    }

    /*
     * public method returng the Subject of the moduleItem
     */
    public MainSubject getSubject(){
        return subject;
    }

    /*
     *  Here we return the weight of the ModuleItem, which is needed in order to calculate the ModuleMark
     */
    public double getWeight() {
        return weight;
    }

    /*
     * This method allows to change the name of the ModuleItem
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * standard setter in order to change or set the mark of the ModuleItem
     */
    public void setMark(int mark){
        this.mark = mark;
    }

    /*
     * this method returns the mark of the ModuleItem
     */
    public double getMark() {
        return mark;
    }

    /*
     * this method returns the module of the ModuleItem
     */
    public int getModule(){
        return module;
    }

    /*
     * using this method, the module of the ModuleItem can be set or changed
     */
    public void setModule(int module){
        this.module = module;
    }

    /*
     * the method allows to compare ModuleItems, so the user can sort a number of ModuleItems
     * according to the name attribute
     */
    public int compareTo(ModuleItem another){
        return getName().compareTo(another.getName());
    }
}
