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

    private static final String KEY_USER = "user";
    private static final String KEY_MODULE = "module";
    private static final String KEY_SUBMODULE = "submodule";
    private static final String KEY_NAME = "name";
    private static final String KEY_ID = "_id";
    private static final String KEY_MARK = "rating";
    private static final String KEY_STATUS ="status";

    /* Hier werden die Spalten Nummern vergeben */
    public static final int COLUMN_USER_INDEX = 1;
    public static final int COLUMN_MODULE_INDEX = 2;
    public static final int COLUMN_SUBMODULE_INDEX = 3;
    public static final int COLUMN_NAME_INDEX = 4;
    public static final int COLUMN_STATUS_INDEX = 5;
    public static final int COLUMN_MARK_INDEX = 6;

    private CourseDBOpenHelper dbHelper;
    private SQLiteDatabase db;
    public String[] referenceArray;

    public Database(Context context) {
        dbHelper = new CourseDBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    /* Legt ein CourseItem in der Datenbank mit den Informationen KEY_NAME, KEY_STATUS, KEY_RATING ab */
    public long addCourseItem(CourseItem item) {
        ContentValues newCourseValue = new ContentValues();

        newCourseValue.put(KEY_NAME, item.getName());
        newCourseValue.put(KEY_STATUS, item.getStatus());
        newCourseValue.put(KEY_MARK, item.getMark());

        return db.insert(DATABASE_TABLE, null, newCourseValue);
    }

    /* prüft, ob ein Eintrag mit dem gewünschten Wert bereits in der Datenbank liegt */
    public boolean checkForExistingEntry(String dbField, String fieldValue){
        open();
        String Query = "Select * from " + DATABASE_NAME + "where " + dbField + " = " + fieldValue;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        close();
        return true;
    }

    /*gibt eine ArrayList mit allen CourseItems und den entsprechenden Werten aus der Datenbank zurück*/
    public ArrayList<CourseItem> getAllCourseItems() {
        open();
        ArrayList<CourseItem> courseItems = new ArrayList<CourseItem>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_USER, KEY_MODULE, KEY_SUBMODULE, KEY_NAME, KEY_STATUS, KEY_MARK }, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String user = cursor.getString(COLUMN_USER_INDEX);
                String module = cursor.getString(COLUMN_MODULE_INDEX);
                String submodule = cursor.getString(COLUMN_SUBMODULE_INDEX);
                String name = cursor.getString(COLUMN_NAME_INDEX);
                String status = cursor.getString(COLUMN_STATUS_INDEX);
                String mark = cursor.getString(COLUMN_MARK_INDEX);

                courseItems.add(new CourseItem(module, submodule, name, status, mark));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return courseItems;
    }
    /*Erstellt einen String Array, der die Pfade der in der Datenbank abgelegten Bilder speichert*/
    public String[] referenceArray(){
        open();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] {KEY_STATUS}, null, null, null, null, null);
        cursor.moveToFirst();
        for(int i = 0; i <= getNumberOfImages()-1; i++){
            String filepath = cursor.getString(COLUMN_STATUS_INDEX);
            referenceArray[i] = filepath;
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return referenceArray;
    }

    public void updateMark(CourseItem course, float mark) {
        if(checkForExistingEntry(course.getName(), Float.toString(mark)) == true){
            ContentValues cv = new ContentValues();
            cv.put(KEY_MARK, Float.toString(mark));
            db.update(DATABASE_TABLE, cv, KEY_ID + " = ?", new String[]{String.valueOf(course.getName())});
        }
        addCourseItem(course);
    }

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
        open();
        String countQuery = "Select * FROM " + DATABASE_TABLE;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int x = cursor.getCount();
        cursor.close();
        close();
        return x;
    }

    /* Der CourseDBOpenHelper erstellt eine Datenbank mit den gewünschten Spalten und dem Datenbanknamen*/
    private class CourseDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" +KEY_ID + " integer primary key autoincrement, " + KEY_USER + " text," + KEY_MODULE + " text," + KEY_SUBMODULE + " text," +
                KEY_NAME + " text," + KEY_STATUS + " text," + KEY_MARK + " text);";

        public CourseDBOpenHelper(Context context, String dbname, SQLiteDatabase.CursorFactory factory, int version){
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