package com.studbud.studbud.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Chronometer;

import com.studbud.studbud.domain.ScheduleItem;
/*
 * The scheduleDatabase class that is accessible from the mainActivity. It will create a list of
 * Dates which can be used as a planner for important dates. This class is taken from
 * the android course exercises no. 5
 */
public class ScheduleDatabase {
    private static final String DATABASE_NAME = "scheduleList1.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "scheduleItems";

    public static final String KEY_ID = "_id";
    public static final String KEY_TASK = "task";
    public static final String KEY_DATE = "date";

    public static final int COLUMN_TASK_INDEX = 1;
    public static final int COLUMN_DATE_INDEX = 2;

    private ToDoDBOpenHelper dbHelper;

    private SQLiteDatabase db;

    public ScheduleDatabase(Context context) {
        dbHelper = new ToDoDBOpenHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);
    }

    public void open() throws SQLException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close() {
        db.close();
    }

    public long insertScheduleItem(ScheduleItem item) {
        ContentValues values = new ContentValues();
        values.put(KEY_TASK, item.getTitle());
        values.put(KEY_DATE, item.getFormattedDate());
        return db.insert(DATABASE_TABLE, null, values);
    }

    public void removeScheduleItem(ScheduleItem item) {
        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID,
                KEY_TASK, KEY_DATE }, null, null, null, null, null);

        long startIndex = 0;

        if (cursor.moveToFirst()) {
            startIndex = Integer.parseInt(cursor.getString(0));
        }

        String databaseDeleteIndex = String.valueOf(startIndex + item.getID());

        db.delete(DATABASE_TABLE, KEY_ID + "=" + databaseDeleteIndex, null);
    }

    public ArrayList<ScheduleItem> getAllScheduleItems() {
        ArrayList<ScheduleItem> items = new ArrayList<ScheduleItem>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID,
                KEY_TASK, KEY_DATE }, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String task = cursor.getString(1);
                String date = cursor.getString(2);

                Date formatedDate = null;
                try {
                    formatedDate = new SimpleDateFormat("dd.MM.yyyy",
                            Locale.GERMAN).parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar cal = Calendar.getInstance(Locale.GERMAN);
                cal.setTime(formatedDate);

                items.add(new ScheduleItem(task, cal.get(Calendar.DAY_OF_MONTH),
                        cal.get(Calendar.MONTH), cal.get(Calendar.YEAR)));

            } while (cursor.moveToNext());
        }
        return items;
    }

    private class ToDoDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_TASK
                + " text not null, " + KEY_DATE + " text);";

        public ToDoDBOpenHelper(Context c, String dbname,
                                SQLiteDatabase.CursorFactory factory, int version) {
            super(c, dbname, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
