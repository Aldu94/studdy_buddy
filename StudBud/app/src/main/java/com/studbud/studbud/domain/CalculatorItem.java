package com.studbud.studbud.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Der Bar.de on 16.09.2016.
 */
public class CalculatorItem {
    public String title;
    public List<CalculatorItem> children;

    public CalculatorItem(){
        children = new ArrayList<CalculatorItem>();
    }
}
