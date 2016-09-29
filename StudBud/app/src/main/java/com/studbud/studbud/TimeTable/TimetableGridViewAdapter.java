package com.studbud.studbud.TimeTable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

import com.studbud.studbud.R;

/*
 * this adapter is used to setup the timetable in the TimeTable activity
 * Source: http://stackoverflow.com/questions/35257327/android-grid-view-order-and-alignment-mess-up-on-scrolling
 */
public class TimetableGridViewAdapter extends BaseAdapter {
    private Context context;
    private String[] scheduleItems;

    // the constructor initialising the scheduleItems string array
    public TimetableGridViewAdapter(Context context, String[] scheduleItems) {
        this.context = context;
        this.scheduleItems = scheduleItems;
    }

    /*
     * according to the information stored in the string array scheduleItems,
     * we initialize the gridview with the textviews and set the information from the array as text
     *
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View gridView;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            gridView = inflater.inflate(R.layout.timetable_item, null);
            TextView textView = (TextView) gridView.findViewById(R.id.timetable_button);
            textView.setText(scheduleItems[position]);

        }else{
            gridView = (View) convertView;
        }
        return gridView;
    }


    // method to get the number of strings in the scheduleItems array
    @Override
    public int getCount() {
        return scheduleItems.length;
    }

    //method to get the object at a given position
    @Override
    public Object getItem(int position) {
        //return scheduleItems[position];
        return null;
    }

    // method to get the positon of the scheduleItem within the string array
    @Override
    public long getItemId(int position) {
        return position;
    }

}
