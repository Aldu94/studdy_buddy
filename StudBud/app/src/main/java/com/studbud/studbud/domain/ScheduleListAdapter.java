package com.studbud.studbud.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.studbud.studbud.R;

import java.util.ArrayList;


public class ScheduleListAdapter extends ArrayAdapter<ScheduleItem> {
    private ArrayList<ScheduleItem> scheduleList;
    private Context context;

    public ScheduleListAdapter(Context context, ArrayList<ScheduleItem> items) {
        super(context, R.layout.schedule_item, items);

        this.context = context;
        this.scheduleList = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.schedule_item, null);

        }

        ScheduleItem item = scheduleList.get(position);

        if (item != null) {
            TextView taskName = (TextView) v.findViewById(R.id.task_name);
            TextView taskDate = (TextView) v.findViewById(R.id.task_date);

            taskName.setText(item.getTitle());
            taskDate.setText(item.getFormattedDate());
        }

        return v;
    }

}