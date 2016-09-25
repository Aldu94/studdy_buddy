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
import com.studbud.studbud.domain.Module;

import java.util.ArrayList;
import java.util.Arrays;

public class Database {
    private static final String DATABASE_NAME = "courseItems36.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "courseItems";

    private static final String KEY_USER = "user";
    private static final String KEY_MODULE = "module";
    private static final String KEY_SUBMODULE = "submodule";
    private static final String KEY_NAME = "name";
    private static final String KEY_ID = "_id";
    private static final String KEY_MARK = "rating";
    private static final String KEY_WEIGHT ="weight";
    private static final String KEY_SUBJECT = "subject";


    /* Hier werden die Spalten Nummern vergeben */
    public static final int COLUMN_USER_INDEX = 1;
    public static final int COLUMN_MODULE_INDEX = 2;
    public static final int COLUMN_SUBMODULE_INDEX = 3;
    public static final int COLUMN_NAME_INDEX = 4;
    public static final int COLUMN_STATUS_INDEX = 5;
    public static final int COLUMN_MARK_INDEX = 6;
    private static final int COLUMN_WEIGHT_INDEX = 7;

    private User karl = new User("Karl", 0, MainSubject.MI);
    private CourseDBOpenHelper dbHelper;
    private SQLiteDatabase db;
    public String[] referenceArray;

    public Database(Context context) {
        dbHelper = new CourseDBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        //createSet();
    }

    /* ermöglicht es anderen Klassen, die Datenbank zu öffnen */
    public void open() throws SQLException {
        Log.d("DBHelper", dbHelper.toString());
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
    public long addCourseItem(CourseItem item, String user) {
        ContentValues newCourseValue = new ContentValues();

        newCourseValue.put(KEY_NAME, item.getName());
        newCourseValue.put(KEY_MODULE, item.getModule());
        newCourseValue.put(KEY_SUBMODULE, item.getSubmodule());
        newCourseValue.put(KEY_MARK, item.getMark());
        newCourseValue.put(KEY_SUBJECT, item.getSubject().getName());
        open();
        //db.insertWithOnConflict(DATABASE_TABLE, null, newCourseValue, SQLiteDatabase.CONFLICT_REPLACE);
        return db.insert(DATABASE_TABLE,null,newCourseValue);
        //close();
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
    public long updateUser(String Name, int MainSubject, int SemesterCount){
        open();
        ContentValues userContent = new ContentValues();
        userContent.put(KEY_NAME, Name);
        userContent.put(KEY_MARK, "" + SemesterCount);
        return db.update(DATABASE_TABLE, userContent, "name "+"="+Name, null);
    }

    public void updateCourseName(CourseItem item, String name){
        open();
        String sqlUpdate = "UPDATE "+DATABASE_TABLE+ " SET name='"+ name + "',  WHERE name=" +KEY_NAME +";";
        db.execSQL(sqlUpdate);
        close();
    }

    public void updateMark(int module, int submodule, double newMark, MainSubject subject) {
        open();
        String sqlUpdate = "UPDATE "+ DATABASE_TABLE + " SET rating='" + newMark + "' WHERE module= '" + module + "' AND submodule= '" + submodule +"' AND subject= '" + subject.getName() + "';";
        db.execSQL(sqlUpdate);
        close();
    }


    public ArrayList<CourseItem> getAllCourseItems() {
        ArrayList<CourseItem> items = new ArrayList<>();
        open();
        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID,
                KEY_MODULE, KEY_SUBMODULE, KEY_MARK, KEY_NAME, KEY_WEIGHT, KEY_SUBJECT}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int module = cursor.getInt(1);
                int submodule = cursor.getInt(2);
                double mark = cursor.getDouble(3);
                String name = cursor.getString(4);
                double weight = cursor.getDouble(5);
                String subject = cursor.getString(6);

                items.add(new CourseItem(name, module, submodule, mark, weight, MainSubject.fromString(subject)));
            } while (cursor.moveToNext());
        }
        return items;
    }

    private Cursor findUserData(String userName){
        Cursor usCurs = db.rawQuery("select * from " + DATABASE_TABLE + " where " + KEY_NAME + "=?".toString(), null);
        return usCurs;
    }

    public String[] updateUser(String data){
        open();
        String[] userData = new String[3];
        Cursor userCursor = findUserData(data);

        String name = userCursor.getString(userCursor.getColumnIndex(KEY_NAME));
        String subject = userCursor.getString(userCursor.getColumnIndex(""));
        String semesters = userCursor.getString(userCursor.getColumnIndex(KEY_MARK));
        userCursor.close();
        close();
        userData[0] = name;
        userData[1] = subject;
        userData[2] = semesters;

        return userData;
    }

    public boolean checkForExistingUser(String userName){
        open();
        String query = "Select * from " + DATABASE_TABLE + " where " + KEY_NAME + " = '" + userName + "'";
        Cursor bCursor = db.rawQuery(query, null);
            if(bCursor.getCount() <= 0){
                bCursor.close();
                close();
                return false;
            }
        bCursor.close();
        close();
        return true;
    }


     public void createSet() {
         ArrayList<Module> miModules = new ArrayList<Module>(Arrays.asList(
                 new Module("MEI-M01", new ArrayList<CourseItem>(Arrays.asList(
                         new CourseItem("Einführung in die Informatik und Medieninformatik", 1, 1, 4, 0.7, MainSubject.MI),
                         new CourseItem("Einführung in das wissenschaftliche Schreiben", 1, 2, 4, 0.3,MainSubject.MI)
                 ))),
                 new Module("MEI-M03", new ArrayList<CourseItem>(Arrays.asList(
                         new CourseItem("Objektorientierte Programmierung", 3, 1, 4, 0.25,MainSubject.MI),
                         new CourseItem("Algorithmen,Datenstrukturen und Programmierung", 3, 2, 4, 0.25,MainSubject.MI),
                         new CourseItem("Android", 3, 3, 4, 0.5,MainSubject.MI)
                 ))),
                 new Module("MEI-M04", new ArrayList<CourseItem>(Arrays.asList(
                         new CourseItem("Multimedia Technology", 4, 1, 4, 0.25,MainSubject.MI),
                         new CourseItem("Multimediale Informationssysteme und Datenbanken", 4, 2, 4, 0.25,MainSubject.MI),
                         new CourseItem("Multimedia Engineering", 4, 3, 4, 0.5,MainSubject.MI)
                 ))),
                 new Module("MEI-M05", new ArrayList<CourseItem>(Arrays.asList(
                         new CourseItem("Human Computer Interaction", 5, 1, 4, 0.25,MainSubject.MI),
                         new CourseItem("Usability Engineering", 5, 2, 4, 0.25,MainSubject.MI),
                         new CourseItem("Projektseminar Mediengestaltung", 5, 3, 4, 0.5,MainSubject.MI)
                 ))),
                 new Module("MEI-M08", new ArrayList<CourseItem>(Arrays.asList(
                         new CourseItem("Multimedia Technology", 8, 1, 4, 0.5,MainSubject.MI),
                         new CourseItem("Multimediale Informationssysteme und Datenbanken", 8, 2, 4, 0.5,MainSubject.MI)
                 ))),
                 new Module("MEI-M10", new ArrayList<CourseItem>(Arrays.asList(
                         new CourseItem("Kurs 1", 10, 1, 4, 0.0,MainSubject.MI),
                         new CourseItem("Kurs 2", 10, 2, 4, 0.0,MainSubject.MI),
                         new CourseItem("Projektseminar", 10, 3, 4, 1,MainSubject.MI)
                 )))
         ));


         ArrayList<Module> infModules = new ArrayList<Module>(Arrays.asList(
                 new Module("INF-M01", new ArrayList<CourseItem>(Arrays.asList(
                         new CourseItem("Einführung in die Informationswissenschaft", 1, 1, 4, 0.5,MainSubject.INF),
                         new CourseItem("Informationstechnische Grundlagen", 1, 2, 4, 0.5,MainSubject.INF)
                 ))),
                 new Module("INF-M02", new ArrayList<CourseItem>(Arrays.asList(
                         new CourseItem("Mathematische Grundlagen", 2, 1, 4, 0.5,MainSubject.INF),
                         new CourseItem("Empirische Forschung", 2, 2, 4, 0.5,MainSubject.INF),
                         new CourseItem("Einführung in die Informationslinguistik", 2, 3, 4, 0.0,MainSubject.INF)
                 ))),
                 new Module("INF-M04", new ArrayList<CourseItem>(Arrays.asList(
                         new CourseItem("Information Retrieval", 4, 1, 4, 0.25,MainSubject.INF),
                         new CourseItem("Auszeichnungssprachen", 4, 2, 4, 0.25,MainSubject.INF),
                         new CourseItem("Vertiefungsseminar Information Retrieval", 4, 3, 4, 0.5,MainSubject.INF)
                 ))),
                 new Module("INF-M05", new ArrayList<CourseItem>(Arrays.asList(
                         new CourseItem("Grundlagen der Softwareergonomie", 5, 1, 4, 0.33,MainSubject.INF),
                         new CourseItem("Vertiefungsseminar Softwareergonomie", 5, 2, 4, 0.66,MainSubject.INF)
                 ))),
                 new Module("INF-M06", new ArrayList<CourseItem>(Arrays.asList(
                         new CourseItem("Datenbanksysteme", 6, 1, 4, 0.25,MainSubject.INF),
                         new CourseItem("Grundlagen: Informationssysteme", 6, 2, 4, 0.25,MainSubject.INF),
                         new CourseItem("Vertiefungsseminar Informationssysteme", 6, 3, 4, 0.5,MainSubject.INF)
                 ))),
                 new Module("INF-M07", new ArrayList<CourseItem>(Arrays.asList(
                         new CourseItem("Einführung in das Projektmanagement", 7, 1, 4, 0.0,MainSubject.INF),
                         new CourseItem("Praxisseminar", 7, 2, 4, 1,MainSubject.INF)
                 )))
         ));

         open();

         for (Module module : miModules) {
             for (CourseItem course : module.getCourses()) {
                 saveCourseItem(course, false);
             }
         }

         for (Module module : infModules) {
             for (CourseItem course : module.getCourses()) {
                 saveCourseItem(course, false);
             }
         }
    }

    /*
    public CourseItem getCourseItem(int module, int submodule) {
        String whereClause = KEY_MODULE + " = '" + module + " and " + KEY_SUBMODULE + " = " + submodule;
    }
    */

    private void saveCourseItem(CourseItem course, boolean openDatabaseManually) {
        ContentValues entity = new ContentValues();
        entity.put(KEY_NAME, course.getName());
        entity.put(KEY_MODULE, course.getModule());
        entity.put(KEY_SUBMODULE, course.getSubmodule());
        entity.put(KEY_MARK, course.getMark());
        entity.put(KEY_WEIGHT, course.getWeight());
        entity.put(KEY_SUBJECT,course.getSubject().getName());

        if (openDatabaseManually) {
            open();
        }

        db.insertWithOnConflict(DATABASE_TABLE, null, entity, SQLiteDatabase.CONFLICT_REPLACE);

        if (openDatabaseManually) {
            close();
        }
         //addSetToDb(itemSet);
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
        newUser.put("", user.getMainSubject().toString());
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
                + DATABASE_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " + KEY_USER + " text," + KEY_MODULE + " text," + KEY_SUBMODULE + " text," +
                KEY_NAME + " text," + KEY_MARK + " text," + KEY_WEIGHT + " text," + KEY_SUBJECT + " text);";

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