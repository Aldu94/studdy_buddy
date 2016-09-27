package com.studbud.studbud.BachelorDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BachelorItemDB {

    private static final String LOG = BachelorItemDB.class.getSimpleName();

    private BachelorItemDBHelper dbHelper;
    private SQLiteDatabase db;

    private String[] allColumns = {dbHelper.COLUMN_ID, dbHelper.COLUMN_NAME, dbHelper.COLUMN_MARK};


    public BachelorItemDB(Context context) {
        dbHelper = new BachelorItemDBHelper(context);
        if(countBachelorItemDbEntries()==0){
            createBachelorItem("bachelorMark", "0.0");
        }
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public BachelorItem createBachelorItem(String name, String mark) {
        open();
        ContentValues scheduleValues = new ContentValues();
        scheduleValues.put(dbHelper.COLUMN_NAME, name);
        scheduleValues.put(dbHelper.COLUMN_MARK, mark);

        long addId = db.insert(dbHelper.TABLE_BACHELOR_ITEMS, null, scheduleValues);

        Cursor cursor = db.query(dbHelper.TABLE_BACHELOR_ITEMS, allColumns, dbHelper.COLUMN_ID + "=" + addId, null, null, null, null);

        cursor.moveToFirst();
        BachelorItem bachelorItem = cursorToBachelorItem(cursor);
        cursor.close();
        close();
        return bachelorItem;
    }

    public BachelorItem getBachelorItem() {
        Cursor cursor = db.query(dbHelper.TABLE_BACHELOR_ITEMS, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        BachelorItem bachelorItem = cursorToBachelorItem(cursor);
        cursor.close();
        return bachelorItem;
    }

    public int countBachelorItemDbEntries() {
        open();
        Cursor cursor = db.rawQuery("Select " + dbHelper.COLUMN_ID + " from " + dbHelper.TABLE_BACHELOR_ITEMS, null);
        int count = cursor.getCount();
        Log.d(" BACHELOR_ENTRIES", "Anzahl der Einträge: " + count);
        cursor.close();
        close();
        return count;
    }

    public List<BachelorItem> getAllBachelorItemDbItems() {
        List<BachelorItem> bachelorItems = new ArrayList<>();

        Cursor cursor = db.query(dbHelper.TABLE_BACHELOR_ITEMS, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        BachelorItem bachelorItem;

        while (!cursor.isAfterLast()) {
            bachelorItem = cursorToBachelorItem(cursor);
            bachelorItems.add(bachelorItem);
            cursor.moveToNext();
        }

        cursor.close();

        return bachelorItems;
    }

    private BachelorItem cursorToBachelorItem(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(dbHelper.COLUMN_ID);
        int idName = cursor.getColumnIndex(dbHelper.COLUMN_NAME);
        int idMark = cursor.getColumnIndex(dbHelper.COLUMN_MARK);

        long id = cursor.getLong(idIndex);
        String name = cursor.getString(idName);
        Double mark = cursor.getDouble(idMark);

        BachelorItem bachelorItem = new BachelorItem(name, id, mark);

        return bachelorItem;
    }

    public void deleteBachelorItem(BachelorItem bachelorItem) {
        long id = bachelorItem.getId();

        db.delete(dbHelper.TABLE_BACHELOR_ITEMS, dbHelper.COLUMN_ID + "=" + id, null);
    }

    public BachelorItem updateBachelorItem(long id, String newContent) {
        ContentValues newBachelorMark = new ContentValues();
        newBachelorMark.put(dbHelper.COLUMN_MARK, newContent);

        db.update(dbHelper.TABLE_BACHELOR_ITEMS, newBachelorMark, dbHelper.COLUMN_ID + "=" + id, null);

        Cursor cursor = db.query(dbHelper.TABLE_BACHELOR_ITEMS, allColumns, dbHelper.COLUMN_ID + "=" + id, null, null, null, null);

        cursor.moveToFirst();
        BachelorItem bachelorItem = cursorToBachelorItem(cursor);
        cursor.close();

        return bachelorItem;
    }
}
