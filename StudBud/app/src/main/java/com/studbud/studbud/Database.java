package com.studbud.studbud;

/**
 * Created by Der Bar.de on 16.08.2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.studbud.studbud.domain.CourseItem;

import java.util.ArrayList;

public class Database {
    private static final String DATABASE_NAME = "courseData.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "courseItems";

    private static final String KEY_PATH = "path";
    private static final String KEY_NAME = "name";
    private static final String KEY_ID = "_id";
    private static final String KEY_RATING = "rating";
    private static final String KEY_STATUS ="status";

    /* Hier werden die Spalten Nummern vergeben */
    public static final int COLUMN_NAME_INDEX = 1;
    public static final int COLUMN_STATUS_INDEX = 2;
    public static final int COLUMN_RATING_INDEX = 3;

    private FoodieDBOpenHelper dbHelper;
    private SQLiteDatabase db;
    public String[] referenceArray;

    public Database(Context context) {
        dbHelper = new FoodieDBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    /* ermöglicht es anderen Klassen, die Datenbank zu öffnen */
    public void open() throws SQLException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
    }
    /* ermöglicht es den anderen Klassen, die Datenbank wieder zu schließen, wenn die Interaktion damit beendet ist */
    public void close() {
        db.close();
    }

    /* Legt ein FoodieItem in der Datenbank mit den Informationen KEY_NAME, KEY_PATH, KEY_RATING ab */
    public long addCourseItem(CourseItem item) {
        ContentValues newCourseValue = new ContentValues();

        newCourseValue.put(KEY_NAME, item.getName());
        newCourseValue.put(KEY_STATUS, item.getStatus());
        newCourseValue.put(KEY_RATING, item.getRating());

        return db.insert(DATABASE_TABLE, null, newCourseValue);
    }
    /*gibt eine ArrayList mit allen FoodieItems und den entsprechenden Werten aus der Datenbank zurück*/
    public ArrayList<CourseItem> getAllCourseItems() {
        ArrayList<CourseItem> courseItems = new ArrayList<CourseItem>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_NAME, KEY_STATUS, KEY_RATING }, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(COLUMN_NAME_INDEX);
                String status = cursor.getString(COLUMN_STATUS_INDEX);
                String rating = cursor.getString(COLUMN_RATING_INDEX);

                courseItems.add(new CourseItem(name, status, rating));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return courseItems;
    }
    /*Erstellt einen String Array, der die Pfade der in der Datenbank abgelegten Bilder speichert*/
    public String[] referenceArray(){
        Cursor cursor = db.query(DATABASE_TABLE, new String[] {KEY_STATUS}, null, null, null, null, null);
        cursor.moveToFirst();
        for(int i = 0; i <= getNumberOfImages()-1; i++){
            String filepath = cursor.getString(COLUMN_STATUS_INDEX);
            referenceArray[i] = filepath;
            cursor.moveToNext();
        }
        cursor.close();
        return referenceArray;
    }

//    public long updateRating(String foodieItemID, float rating) {
//        return 0;
//    }

    //    public long updateTitle(String foodieItemID, String title) {
//        return 0;
//    }
    /* Löscht einen Eintrag aus der Datenbank, wenn die Methode aufgerufen wird*/
    public long deleteCourseItem(String courseItemID) {
        String whereClause = KEY_ID + " = '" + courseItemID;
        db.delete(DATABASE_TABLE, whereClause, null);
        return 0;
    }
    /* Gibt die Anzahl der Datenbankeinträge zurück*/
    public int getNumberOfImages(){
        String countQuery = "Select * FROM " + DATABASE_TABLE;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();

        return cursor.getCount();
    }

    /* Der FoodieDBOpenHelper erstellt eine Datenbank mit den gewünschten Spalten und dem Datenbanknamen*/
    private class FoodieDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" +KEY_ID + " integer primary key autoincrement, " + KEY_NAME + " text," + KEY_STATUS + " text," + KEY_RATING + " text);";

        public FoodieDBOpenHelper(Context context, String dbname, SQLiteDatabase.CursorFactory factory, int version){
            super(context, dbname, factory, version);
        }
        /* Hier wird die Datenbank generiert */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }

}