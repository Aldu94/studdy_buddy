package com.studbud.studbud.TimeTable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

import com.studbud.studbud.R;

public class TimetableGridViewAdapter extends BaseAdapter {
    private Context context;
    private String[] scheduleItems;

    public TimetableGridViewAdapter(Context context, String[] scheduleItems) {
        this.context = context;
        this.scheduleItems = scheduleItems;
        //myClickListener = clickListener;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if(convertView == null){
            gridView = new View(context);
            gridView = inflater.inflate(R.layout.timetable_item, null);
            TextView textView = (TextView) gridView.findViewById(R.id.timetable_button);
            textView.setText(scheduleItems[position]);

        }else{
            gridView = (View) convertView;
        }
        return gridView;
    }



    @Override
    public int getCount() {
        return scheduleItems.length;
    }

    @Override
    public Object getItem(int position) {
        //return scheduleItems[position];
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
