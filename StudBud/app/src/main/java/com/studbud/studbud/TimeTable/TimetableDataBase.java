package com.studbud.studbud.TimeTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TimetableDataBase {

    private static final String LOG = TimetableDataBase.class.getSimpleName();

    private String[] allColumns = {ScheduleDbHelper.COLUMN_ID, ScheduleDbHelper.COLUMN_NAME, ScheduleDbHelper.COLUMN_CONTENT};

    private ScheduleDbHelper dbHelper;
    private SQLiteDatabase db;

    // the standard constructor of the class
    public TimetableDataBase(Context context){
        dbHelper = new ScheduleDbHelper(context);
    }

    // this allows other classes to open the database
    public void open(){
        db = dbHelper.getWritableDatabase();
    }

    // this allows other classes to close the database
    public void close(){
        dbHelper.close();
    }

    /*
     * method to create and save a scheduleItem to the database. It will then be returned by the
     * method in order to do other interesting stuff with the schedule item
     */
    public ScheduleDbItem createScheduleDbItem(String name, String content){
        ContentValues scheduleValues = new ContentValues();
        scheduleValues.put(ScheduleDbHelper.COLUMN_NAME, name);
        scheduleValues.put(ScheduleDbHelper.COLUMN_CONTENT, content);

        long addId = db.insert(ScheduleDbHelper.TABLE_SCHEDULE_ITEMS, null, scheduleValues);

        Cursor cursor = db.query(ScheduleDbHelper.TABLE_SCHEDULE_ITEMS, allColumns, ScheduleDbHelper.COLUMN_ID + "=" + addId, null, null, null, null);

        cursor.moveToFirst();
        ScheduleDbItem scheduleDbItem = cursorToScheduleDbItem(cursor);
        cursor.close();

        return scheduleDbItem;
    }

    /*
     * method to retrieve the schedule string from the database. as this will be the only entry
     * in the database table, we just have to move the cursor to the first item to get the
     * necessary values
     */
    public ScheduleDbItem getSchedule(){
        Cursor cursor = db.query(ScheduleDbHelper.TABLE_SCHEDULE_ITEMS, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        ScheduleDbItem scheduleDbItem = cursorToScheduleDbItem(cursor);
        cursor.close();
        return scheduleDbItem;
    }

    /*
     * method to count the amount of scheduleItems stored in the database
     */
    public int countScheduleDbEntries(){
        Cursor cursor = db.rawQuery("Select * from " + ScheduleDbHelper.TABLE_SCHEDULE_ITEMS, null);
        int count = cursor.getCount();
        return count;
    }

    /*
     * method to fill a List with all scheduleItems in the database
     */
    public List<ScheduleDbItem> getAllScheduleDbItems(){
        List<ScheduleDbItem> scheduleDbItems = new ArrayList<>();

        Cursor cursor = db.query(ScheduleDbHelper.TABLE_SCHEDULE_ITEMS, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        ScheduleDbItem scheduleDbItem;

        while(!cursor.isAfterLast()){
            scheduleDbItem = cursorToScheduleDbItem(cursor);
            scheduleDbItems.add(scheduleDbItem);
            cursor.moveToNext();
        }

        cursor.close();

        return scheduleDbItems;
    }

    /*
     * here we use the provided cursor to collect the values from the selected row
     * then the values are put into a new scheduleItem and the scheduleItem is returned
     */
    private ScheduleDbItem cursorToScheduleDbItem(Cursor cursor){
        int idIndex = cursor.getColumnIndex(ScheduleDbHelper.COLUMN_ID);
        int idName = cursor.getColumnIndex(ScheduleDbHelper.COLUMN_NAME);
        int idContent = cursor.getColumnIndex(ScheduleDbHelper.COLUMN_CONTENT);

        long id = cursor.getLong(idIndex);
        String name = cursor.getString(idName);
        String content = cursor.getString(idContent);

        ScheduleDbItem scheduleDbItem = new ScheduleDbItem(name, content, id);

        return scheduleDbItem;
    }

    /*
     * this method allows us to delete a specified scheduleItem
     */
    public void deleteScheduleDbItem(ScheduleDbItem scheduleDbItem){
        long id = scheduleDbItem.getId();

        db.delete(ScheduleDbHelper.TABLE_SCHEDULE_ITEMS, ScheduleDbHelper.COLUMN_ID + "=" + id, null);
    }

    /*
     * method to update a given scheduleItem entry in the database. when the
     * scheduleItem entry has been updated, the method will return the scheduleItem
     */
    public ScheduleDbItem updateScheduleDbItem(long id, String newContent){
        ContentValues newScheduleValues = new ContentValues();
        newScheduleValues.put(ScheduleDbHelper.COLUMN_CONTENT, newContent);

        db.update(ScheduleDbHelper.TABLE_SCHEDULE_ITEMS, newScheduleValues, ScheduleDbHelper.COLUMN_ID + "=" + id, null);

        Cursor cursor = db.query(ScheduleDbHelper.TABLE_SCHEDULE_ITEMS, allColumns, ScheduleDbHelper.COLUMN_ID + "=" + id, null, null, null, null);

        cursor.moveToFirst();
        ScheduleDbItem scheduleDbItem = cursorToScheduleDbItem(cursor);
        cursor.close();

        return scheduleDbItem;
    }
}
