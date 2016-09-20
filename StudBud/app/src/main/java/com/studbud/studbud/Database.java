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
import android.util.Log;

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

    private User karl = new User("Karl", 0, 2);
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
    public void addCourseItem(CourseItem item, String user) {
        ContentValues newCourseValue = new ContentValues();

        newCourseValue.put(KEY_NAME, item.getName());
        newCourseValue.put(KEY_STATUS, item.getStatus());
        newCourseValue.put(KEY_MARK, item.getMark());
        newCourseValue.put(KEY_MODULE, item.getModule());
        newCourseValue.put(KEY_SUBMODULE, item.getSubmodule());
        newCourseValue.put(KEY_USER, user);
        open();
        db.insertWithOnConflict(DATABASE_TABLE, null, newCourseValue, SQLiteDatabase.CONFLICT_REPLACE);
        close();
    }

    /* prüft, ob ein Eintrag mit dem gewünschten Wert bereits in der Datenbank liegt */
    public boolean checkForExistingEntry(String dbField, String fieldValue){
        open();
        String Query = "Select * from " + DATABASE_TABLE + " where " + dbField + " = " + fieldValue;
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
  /*  public ArrayList<CourseItem> getAllCourseItems() {
        open();
        ArrayList<CourseItem> courseItems = new ArrayList<CourseItem>();
        Cursor itemCursor = db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_USER, KEY_MODULE, KEY_SUBMODULE, KEY_NAME, KEY_STATUS, KEY_MARK }, null, null, null, null, null);

        if (itemCursor.moveToFirst()) {
            do {
                String user = itemCursor.getString(COLUMN_USER_INDEX);
                String module = itemCursor.getString(COLUMN_MODULE_INDEX);
                String submodule = itemCursor.getString(COLUMN_SUBMODULE_INDEX);
                String name = itemCursor.getString(COLUMN_NAME_INDEX);
                String status = itemCursor.getString(COLUMN_STATUS_INDEX);
                String mark = itemCursor.getString(COLUMN_MARK_INDEX);

                courseItems.add(new CourseItem(module, submodule, name, status, mark));
            } while (itemCursor.moveToNext());
        }
        itemCursor.close();
        close();
        return courseItems;
    }
<<<<<<< HEAD
    */
=======

    public String getUser(String userId){
        open();
        String userName = "";

            Cursor userCursor = db.rawQuery("SELECT * FROM " + DATABASE_TABLE +" WHERE " +KEY_NAME+ "=", new String[]{userId + ""});

            if(userCursor.getCount() > 0){
                userCursor.moveToFirst();
                userName = userCursor.getString(userCursor.getColumnIndex("Name"));
            }
            userCursor.close();
            close();
            return userName;

    }
    public void updateUser(String name, String mainSubject, String semesterCount){
        open();
        String sqlUpdate = "UPDATE "+DATABASE_TABLE+ " SET name='"+ name + "', status='" + mainSubject + "', module='" + semesterCount+ "' WHERE name=" +KEY_NAME +";";
        db.execSQL(sqlUpdate);
        close();
    }

    public void updateCourseName(CourseItem item, String name){
        open();
        String sqlUpdate = "UPDATE "+DATABASE_TABLE+ " SET name='"+ name + "',  WHERE name=" +KEY_NAME +";";
        db.execSQL(sqlUpdate);
        close();
    }
    public void updateCourseMark(CourseItem item, String mark){
        open();
        String sqlUpdate = "UPDATE "+DATABASE_TABLE+ " SET rating='"+ mark + "', WHERE name=" +KEY_NAME +";";
        db.execSQL(sqlUpdate);
        close();
    }
>>>>>>> f9cf897ad361c271ebba01025e2a89daf68941e1
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
        addCourseItem(course, karl.getName());
    }

     public void createSet() {
        ArrayList<CourseItem> itemSet = new ArrayList<CourseItem>();
         //Kurse der Medieninformatik
         itemSet.add(new CourseItem("MEI-M01", "01", "EIMI", null, null ));
         itemSet.add(new CourseItem("MEI-M01", "02", "Propäd.", null, null ));
         itemSet.add(new CourseItem("MEI-M02", "01", "Mathe I", null, null ));
         itemSet.add(new CourseItem("MEI-M02", "02", "Mathe II", null, null ));
         itemSet.add(new CourseItem("MEI-M03", "01", "OOP", null, null ));
         itemSet.add(new CourseItem("MEI-M03", "02", "ADP", null, null ));
         itemSet.add(new CourseItem("MEI-M03", "03", "Android", null, null ));
         itemSet.add(new CourseItem("MEI-M04", "01", "MMT", null, null ));
         itemSet.add(new CourseItem("MEI-M04", "02", "MIDBS", null, null ));
         itemSet.add(new CourseItem("MEI-M04", "03", "MME", null, null ));
         itemSet.add(new CourseItem("MEI-M05", "01", "HCI", null, null ));
         itemSet.add(new CourseItem("MEI-M05", "02", "Usability", null, null ));
         itemSet.add(new CourseItem("MEI-M05", "03", "Med.Gest.", null, null ));
         itemSet.add(new CourseItem("MEI-M10", "01", "Anw.Schw.", null, null ));
         itemSet.add(new CourseItem("MEI-M10", "02", "Anw.Modul", null, null ));
         itemSet.add(new CourseItem("MEI-M10", "03", "Projektsem.", null, null ));
         //Kurse der Informationswissenschaft
         itemSet.add(new CourseItem("INF-M01", "01", "a", null, null ));
         itemSet.add(new CourseItem("INF-M01", "02", "b", null, null ));
         itemSet.add(new CourseItem("INF-M02", "01", "c", null, null ));
         itemSet.add(new CourseItem("INF-M02", "02", "d", null, null ));
         itemSet.add(new CourseItem("INF-M03", "01", "e", null, null ));
         itemSet.add(new CourseItem("INF-M03", "02", "f", null, null ));
         itemSet.add(new CourseItem("INF-M03", "03", "g", null, null ));
         itemSet.add(new CourseItem("INF-M04", "01", "h", null, null ));
         itemSet.add(new CourseItem("INF-M04", "02", "i", null, null ));
         itemSet.add(new CourseItem("INF-M04", "03", "j", null, null ));
         itemSet.add(new CourseItem("INF-M05", "01", "k", null, null ));
         itemSet.add(new CourseItem("INF-M05", "02", "l", null, null ));
         itemSet.add(new CourseItem("INF-M05", "03", "m", null, null ));
         itemSet.add(new CourseItem("INF-M10", "01", "n", null, null ));
         itemSet.add(new CourseItem("INF-M10", "02", "o", null, null ));
         itemSet.add(new CourseItem("INF-M10", "03", "p", null, null ));

         addSetToDb(itemSet);
    }

    /* Erstellt pro User einen Datenbanksatz*/
    private void addSetToDb(ArrayList<CourseItem> set){
        for(CourseItem item: set){
                addCourseItem(item, karl.getName());
        }
    }

    public void addUserToDb(User user){
        ContentValues newUser = new ContentValues();

        newUser.put(KEY_NAME, user.getName());
        newUser.put(KEY_STATUS, user.getMainSubjectID());
        newUser.put(KEY_MARK, user.getNumberOfSemester());
        open();
        db.insertWithOnConflict(DATABASE_TABLE, null, newUser, SQLiteDatabase.CONFLICT_REPLACE);
        close();
    }

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
    public void deleteDB (){
        dbHelper.deleteDatabase(db, DATABASE_TABLE);
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

        public void deleteDatabase(SQLiteDatabase db, String table){
            db.execSQL("DELETE FROM" + table);
            db.close();
        }

    }

}