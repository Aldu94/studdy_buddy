package com.studbud.studbud.BachelorDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Der Bar.de on 27.09.2016.
 */
public class BachelorItemDBHelper extends SQLiteOpenHelper {

    public static final String LOG = BachelorItemDBHelper.class.getSimpleName();

    public static final String DATABASE_NAME = "bachelorItem.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_BACHELOR_ITEMS = "bachelor_items";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_MARK = "mark";
    public static final String COLUMN_SEMESTER = "semester";
    public static final String COLUMN_MAINSUBJECT = "mainsubject";

    private static final String CREATE_DATABASE = "CREATE TABLE " + TABLE_BACHELOR_ITEMS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_MARK + " TEXT, " + COLUMN_SEMESTER + " TEXT, " + COLUMN_MAINSUBJECT + " TEXT NOT NULL);";
    private static final String DATABASE_DROP = "DROP TABLE IF EXISTS " + TABLE_BACHELOR_ITEMS;

    public BachelorItemDBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    // the database is created here
    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(CREATE_DATABASE);
        }
        catch (Exception ex) {
            Log.e(LOG, "Fehler beim Erstellen der Tabelle: " + ex.getMessage());
        }
    }

    // defines what to do when the database needs to be upgraded to a higher version number
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DATABASE_DROP);
        onCreate(db);
    }
}
