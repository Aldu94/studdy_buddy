package com.studbud.studbud.persistence;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.studbud.studbud.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Aldu on 05.09.16.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter{

    private List<String> headLine;
    private HashMap<String,List<String>> childTitles;
    private Context context;


    public ExpandableListAdapter(Context context,List<String> headLine,HashMap<String,List<String>> childTitles){
        this.context = context;
        this.headLine = headLine;
        this.childTitles = childTitles;
    }


    @Override
    public int getGroupCount() {
        return headLine.size();
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return childTitles.get(headLine.get(groupPosition)).size();
    }


    @Override
    public Object getGroup(int groupPosition) {
        return headLine.get(groupPosition);
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childTitles.get(headLine.get(groupPosition)).get(childPosition);
    }


    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title = (String)this.getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandable_list_heading_layout,null);
        }

        TextView textView = (TextView)convertView.findViewById(R.id.heading_item);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(title);

        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String title = (String)this.getChild(groupPosition,childPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandable_list_child_element_layout,null);
        }

        TextView textView = (TextView)convertView.findViewById(R.id.child_item);
        textView.setText(title);

        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
