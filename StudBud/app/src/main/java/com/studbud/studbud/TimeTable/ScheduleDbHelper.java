package com.studbud.studbud.TimeTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ScheduleDbHelper extends SQLiteOpenHelper {
    public static final String LOG = ScheduleDbHelper.class.getSimpleName();

    public static final String DATABASE_NAME = "scheduleItems1.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_SCHEDULE_ITEMS = "schedule_items";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CONTENT = "content";

    private static final String CREATE_DATABASE = "CREATE TABLE " + TABLE_SCHEDULE_ITEMS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT NOT NULL, " + COLUMN_CONTENT + " TEXT NOT NULL);";
    private static final String DATABASE_DROP = "DROP TABLE IF EXISTS " + TABLE_SCHEDULE_ITEMS;

    public ScheduleDbHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    // database is created here
    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(CREATE_DATABASE);
        }
        catch (Exception ex) {
            Log.e(LOG, "Fehler beim Erstellen der Tabelle: " + ex.getMessage());
        }
    }
    // method to handle actions on a database upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DATABASE_DROP);
        onCreate(db);
    }
}
