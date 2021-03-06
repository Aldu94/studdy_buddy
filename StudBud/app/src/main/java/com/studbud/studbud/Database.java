package com.studbud.studbud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.studbud.studbud.domain.BachelorMarkItem;
import com.studbud.studbud.domain.CourseItem;
import com.studbud.studbud.domain.Module;
import com.studbud.studbud.domain.ModuleItem;

import java.util.ArrayList;
import java.util.Arrays;
/*
 * The Database is taken from the android course at the University of Regensburg in the SS 2015
 */
public class Database {

    /*
     * These statements contain the Name of the database as well as the database version
     */
    private static final String DATABASE_NAME = "courseItemDB.db";
    private static final int DATABASE_VERSION = 47;

    /*
     * The name of the table as well as some selection arrays are defined here
     */
    private static final String DATABASE_TABLE = "courseItems";
    private String[] USER_COLUMNS = {KEY_USER, KEY_SUBJECT, KEY_MARK};

    /*
     * Here we define the names of the columns used to create the Database for quick access
     */
    private static final String KEY_USER = "user";
    private static final String KEY_MODULE = "module";
    private static final String KEY_SUBMODULE = "submodule";
    private static final String KEY_NAME = "name";
    private static final String KEY_ID = "_id";
    private static final String KEY_MARK = "rating";
    private static final String KEY_WEIGHT ="weight";
    private static final String KEY_SUBJECT = "subject";
    public static final String KEY_MARK_INFWISS = "infwissmark";
    public static final String KEY_MARK_MEDINF = "medinfmark";
    public static final String KEY_SCORE = "score";
    public static final String KEY_LAST_SCORE_DATE = "scoredate";

    /*
     * Here we set some references for the databaseHelper and the SQLiteDatabase for less typing
     */
    private CourseDBOpenHelper dbHelper;
    private SQLiteDatabase db;
    public String[] referenceArray;

    /*
     * the standard constructor for the database
     */
    public Database(Context context) {
        dbHelper = new CourseDBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        if(countDataBaseEntries() == 0) {
            createSet();
        }
    }

    /*
     * This method allows other classes to open the Database
     */
    public void open() throws SQLException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
    }

    /*
     * This method allows other classes to close the Database
     */
    public void close() {
        db.close();
    }


    /*
     * Finds the User with the specified name within the Database, creates
     * a User object with the found values and returns the object
     */
    public User getUser() {
        open();

        Cursor userCursor = db.rawQuery("select * from " + DATABASE_TABLE + " where " + KEY_USER + " is not null", null);
        userCursor.moveToFirst();
        String name = userCursor.getString(userCursor.getColumnIndex(KEY_USER));
        String subject = userCursor.getString(userCursor.getColumnIndex(KEY_SUBJECT));
        int mark = userCursor.getInt(userCursor.getColumnIndex(KEY_MARK));
        int score = userCursor.getInt(userCursor.getColumnIndex(KEY_SCORE));

        userCursor.close();
        close();
        User user = new User(name, mark, MainSubject.fromString(subject), score);
        return user;
    }

    /*
     * With this method, we can add a user object to the database
     */
    public void addUserToDb(User user) {
        ContentValues newUser = new ContentValues();

        newUser.put(KEY_USER, user.getName());
        newUser.put(KEY_SUBJECT, user.getMainSubject().toString());
        newUser.put(KEY_MARK, user.getNumberOfSemester());
        newUser.put(KEY_MARK_MEDINF, "0.0");
        newUser.put(KEY_MARK_INFWISS, "0.0");
        newUser.put(KEY_SCORE, user.getScore());

        open();
        db.insertWithOnConflict(DATABASE_TABLE, null, newUser, SQLiteDatabase.CONFLICT_REPLACE);
        Log.d("User-Database", "User " + user.getName() + " in Database abgelegt " + user.getMainSubject().toString());
        close();
    }

    /*
     * This method will update the values of the specified user
     */
    public void updateUser(String user, MainSubject mainSubject, int semesterCount){
        open();
        ContentValues userContent = new ContentValues();
        userContent.put(KEY_USER, user);
        userContent.put(KEY_MARK, semesterCount);
        userContent.put(KEY_SUBJECT, mainSubject.getName());
        db.update(DATABASE_TABLE, userContent, "user is not null", null);
        Log.d("User-Database", "User: " + user + " " + " Werte überschrieben");
    }

    /*
     * method to save a new InfwissMark value to the database
     */
    public void updateUserInfwissMark(String user, String infwissmark) {
        open();
        String sqlUpdate = "UPDATE " + DATABASE_TABLE + " SET infwissmark='" + infwissmark + "' WHERE user= '" + user + "';";
        db.execSQL(sqlUpdate);
        close();
        Log.d("User-Database", "neue Note für Fach Inwiss für User " + user + " eingetragen");
    }

    /*
     * method to save a new Userscore value to the database for the Faming Function
     */
    public void updateUserScore(String user, String score){
        open();
        String scoreUpdate = "UPDATE " + DATABASE_TABLE + " SET score='" + score + "' WHERE user= '" + user + "';";
        db.execSQL(scoreUpdate);
        close();
        Log.d("Score", "Neuer Score: " +score + "für "+ user + "!");
    }

    /*
     * method to retrieve the Userscore value from the Database
     */
    public String getUserScore(){
        open();

        Cursor scoreCursor = db.rawQuery("select * from " + DATABASE_TABLE + " where " + KEY_USER + " is not null", null);
        scoreCursor.moveToFirst();
        String score = scoreCursor.getString(scoreCursor.getColumnIndex(KEY_SCORE));

        scoreCursor.close();
        close();
        return score;
    }

    /*
     * method to retrieve the InfwissMark value from the database
     */
    public double getUserInfwissMark(){
        open();

        Cursor userCursor = db.rawQuery("select * from " + DATABASE_TABLE + " where " + KEY_USER + " is not null", null );
        userCursor.moveToFirst();
        double InfwissMark = userCursor.getDouble(userCursor.getColumnIndex(KEY_MARK_INFWISS));

        userCursor.close();
        close();
        return InfwissMark;
    }

    /*
     * method to get a the last date when the score was increased
     */
    public String getScoreDate(){
        open();

        Cursor userCursor = db.rawQuery("select * from " + DATABASE_TABLE + " where " + KEY_USER + " is not null", null );
        userCursor.moveToFirst();
        String scoreDate = userCursor.getString(userCursor.getColumnIndex(KEY_LAST_SCORE_DATE));

        userCursor.close();
        close();
        return scoreDate;
    }

    /*
     * method to save a the last date when the score was increased
     */
    public void setScoreDate(String user, String scoredate){
        open();
        String scoreUpdate = "UPDATE " + DATABASE_TABLE + " SET scoredate='" + scoredate + "' WHERE user= '" + user + "';";
        db.execSQL(scoreUpdate);
        close();
    }

    /*
     * method to retrieve the MedInfMark value from the database
     */
    public double getUserMedInfMark(){
        open();

        Cursor userCursor = db.rawQuery("select * from " + DATABASE_TABLE + " where " + KEY_USER + " is not null", null );
        userCursor.moveToFirst();
        double medInfMark = userCursor.getDouble(userCursor.getColumnIndex(KEY_MARK_MEDINF));

        userCursor.close();
        close();
        return medInfMark;
    }

    /*
     * method to save the InfwissMark value to the database
     */
    public void updateUserMedInfMark(String user, String medinfomark){
        open();
        String sqlUpdate = "UPDATE " + DATABASE_TABLE + " SET medinfmark='" + medinfomark + "' WHERE user= '" + user + "';";
        db.execSQL(sqlUpdate);
        close();
        Log.d("User-Database", "neue Note für Fach medinfo für User " + user + " eingetragen");
    }

    /*
     * The method changes the mark of a CourseItem
     */
    public void updateMark(int module, int submodule, double newMark, MainSubject subject) {
        open();
        String sqlUpdate = "UPDATE "+ DATABASE_TABLE + " SET rating='" + newMark + "' WHERE module= '" + module + "' AND submodule= '" + submodule +"' AND subject= '" + subject.getName() + "';";
        db.execSQL(sqlUpdate);
        close();
    }

    /*
     * The method creates an arrayList and populates it with all ModuleItems
     */
    public ArrayList<ModuleItem> getAllModuleItems(){
        ArrayList<ModuleItem> items = new ArrayList<>();
        open();
        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID,
                KEY_NAME, KEY_MODULE, KEY_MARK, KEY_WEIGHT, KEY_SUBJECT},KEY_NAME + " is not null", null, null, null, null);

        int idName = cursor.getColumnIndex(KEY_NAME);
        int idModule = cursor.getColumnIndex(KEY_MODULE);
        int idMark = cursor.getColumnIndex(KEY_MARK);
        int idWeight = cursor.getColumnIndex(KEY_WEIGHT);
        int idSubject = cursor.getColumnIndex(KEY_SUBJECT);

        if(cursor.moveToFirst()){
            do {
                String name = cursor.getString(idName);
                int module = cursor.getInt(idModule);
                double mark = cursor.getDouble(idMark);
                double weight = cursor.getDouble(idWeight);
                String subject = cursor.getString(idSubject);

                items.add(new ModuleItem(name, module, mark, weight, MainSubject.fromString(subject)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return items;
    }


    /*
     * This method collects all CourseItem values from the Database and creates a CourseItem for
     * each set of values which is then stored in an ArrayList for easier access
     */
    public ArrayList<CourseItem> getAllCourseItems() {
        ArrayList<CourseItem> items = new ArrayList<>();
        open();
        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID,
                KEY_MODULE, KEY_SUBMODULE, KEY_MARK, KEY_NAME, KEY_WEIGHT, KEY_SUBJECT}, KEY_NAME + " is not null", null, null, null, null);

        int idModule = cursor.getColumnIndex(KEY_MODULE);
        int idSubmodule = cursor.getColumnIndex(KEY_SUBMODULE);
        int idMark = cursor.getColumnIndex(KEY_MARK);
        int idName = cursor.getColumnIndex(KEY_NAME);
        int idWeight = cursor.getColumnIndex(KEY_WEIGHT);
        int idSubject = cursor.getColumnIndex(KEY_SUBJECT);

        if (cursor.moveToFirst()) {
            do {
                int module = cursor.getInt(idModule);
                int submodule = cursor.getInt(idSubmodule);
                double mark = cursor.getDouble(idMark);
                String name = cursor.getString(idName);
                double weight = cursor.getDouble(idWeight);
                String subject = cursor.getString(idSubject);

                items.add(new CourseItem(name, module, submodule, mark, weight, MainSubject.fromString(subject)));
                Log.d("DATABASE: ", "added new CourseItem: " + name + " to String "+MainSubject.fromString(subject).getName());
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return items;

    }


    /*
     * With this method, we can save a Course item to the Database. It also has a checkfunktion
     * to see if this Courseitem is already present in the Database and will replace it if this is
     * the case
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

    /*
     * This method allows to delete a particular CourseItem
     */
    public long deleteCourseItem(String courseItemID) {
        String whereClause = KEY_ID + " = '" + courseItemID;
        db.delete(DATABASE_TABLE, whereClause, null);
        return 0;
    }

    /*
     * This method deletes the selected Database
     */
    public void deleteDB (){
        dbHelper.deleteDatabase(db, DATABASE_TABLE);
    }

    /*
     * method to count all entries of the Database
     */
    public int countDataBaseEntries() {
        open();
        Cursor cursor = db.rawQuery("Select " + KEY_ID + " from " + DATABASE_TABLE, null);
        int count = cursor.getCount();
        Log.d("DATABASE_ENTRIES", "Anzahl der Einträge: " + count);
        cursor.close();
        close();
        return count;
    }

    /*
     * here we create a set of CourseItems according to the Modules and subject they belong to
     * they will then be inserted into the database if this method is called
     */
    public void createSet() {
        ArrayList<Module> miModules = new ArrayList<Module>(Arrays.asList(
                new Module("MEI-M01", new ArrayList<CourseItem>(Arrays.asList(
                        new CourseItem("Einführung in die Informatik und Medieninformatik", 1, 1, 0, 0.7, MainSubject.MI),
                        new CourseItem("Einführung in das wissenschaftliche Schreiben", 1, 2, 0, 0.3,MainSubject.MI)
                ))),
                new Module("MEI-M03", new ArrayList<CourseItem>(Arrays.asList(
                        new CourseItem("Objektorientierte Programmierung", 3, 1, 0, 0.25,MainSubject.MI),
                        new CourseItem("Algorithmen,Datenstrukturen und Programmierung", 3, 2, 0, 0.25,MainSubject.MI),
                        new CourseItem("Android", 3, 3, 0, 0.5,MainSubject.MI)
                ))),
                new Module("MEI-M04", new ArrayList<CourseItem>(Arrays.asList(
                        new CourseItem("Multimedia Technology", 4, 1, 0, 0.25,MainSubject.MI),
                        new CourseItem("Multimediale Informationssysteme und Datenbanken", 4, 2, 0, 0.25,MainSubject.MI),
                        new CourseItem("Multimedia Engineering", 4, 3, 0, 0.5,MainSubject.MI)
                ))),
                new Module("MEI-M05", new ArrayList<CourseItem>(Arrays.asList(
                        new CourseItem("Human Computer Interaction", 5, 1, 0, 0.25,MainSubject.MI),
                        new CourseItem("Usability Engineering", 5, 2, 0, 0.25,MainSubject.MI),
                        new CourseItem("Projektseminar Mediengestaltung", 5, 3, 0, 0.5,MainSubject.MI)
                ))),
                new Module("MEI-M08", new ArrayList<CourseItem>(Arrays.asList(
                        new CourseItem("Multimedia Technology", 8, 1, 0, 0.5,MainSubject.MI),
                        new CourseItem("Multimediale Informationssysteme und Datenbanken", 8, 2, 0, 0.5,MainSubject.MI)
                ))),
                new Module("MEI-M10", new ArrayList<CourseItem>(Arrays.asList(
                        new CourseItem("Kurs 1", 10, 1, 0, 0.0,MainSubject.MI),
                        new CourseItem("Kurs 2", 10, 2, 0, 0.0,MainSubject.MI),
                        new CourseItem("Projektseminar", 10, 3, 0, 1,MainSubject.MI)
                )))
        ));


        ArrayList<Module> infModules = new ArrayList<Module>(Arrays.asList(
                new Module("INF-M01", new ArrayList<CourseItem>(Arrays.asList(
                        new CourseItem("Einführung in die Informationswissenschaft", 1, 1, 0, 0.5,MainSubject.INF),
                        new CourseItem("Informationstechnische Grundlagen", 1, 2, 0, 0.5,MainSubject.INF)
                ))),
                new Module("INF-M02", new ArrayList<CourseItem>(Arrays.asList(
                        new CourseItem("Mathematische Grundlagen", 2, 1, 0, 0.5,MainSubject.INF),
                        new CourseItem("Empirische Forschung", 2, 2, 0, 0.5,MainSubject.INF),
                        new CourseItem("Einführung in die Informationslinguistik", 2, 3, 4, 0.0,MainSubject.INF)
                ))),
                new Module("INF-M04", new ArrayList<CourseItem>(Arrays.asList(
                        new CourseItem("Information Retrieval", 4, 1, 0, 0.25,MainSubject.INF),
                        new CourseItem("Auszeichnungssprachen", 4, 2, 0, 0.25,MainSubject.INF),
                        new CourseItem("Vertiefungsseminar Information Retrieval", 4, 3, 0, 0.5,MainSubject.INF)
                ))),
                new Module("INF-M05", new ArrayList<CourseItem>(Arrays.asList(
                        new CourseItem("Grundlagen der Softwareergonomie", 5, 1, 0, 0.33,MainSubject.INF),
                        new CourseItem("Vertiefungsseminar Softwareergonomie", 5, 2, 0, 0.66,MainSubject.INF)
                ))),
                new Module("INF-M06", new ArrayList<CourseItem>(Arrays.asList(
                        new CourseItem("Datenbanksysteme", 6, 1, 0, 0.25,MainSubject.INF),
                        new CourseItem("Grundlagen: Informationssysteme", 6, 2, 0, 0.25,MainSubject.INF),
                        new CourseItem("Vertiefungsseminar Informationssysteme", 6, 3, 0, 0.5,MainSubject.INF)
                ))),
                new Module("INF-M07", new ArrayList<CourseItem>(Arrays.asList(
                        new CourseItem("Einführung in das Projektmanagement", 7, 1, 0, 0.0,MainSubject.INF),
                        new CourseItem("Praxisseminar", 7, 2, 0, 1,MainSubject.INF)
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
     * The DatabaseHandler class sets up a Database with the desired columns
     */
    private class CourseDBOpenHelper extends SQLiteOpenHelper {
        /*
         * the attributes for the Database to be created are set up in a string
         */
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " + KEY_USER + " text," + KEY_MODULE + " text," + KEY_SUBMODULE + " text," +
                KEY_NAME + " text," + KEY_MARK + " text," + KEY_WEIGHT + " text," + KEY_SUBJECT + " text," + KEY_MARK_MEDINF + " text," + KEY_MARK_INFWISS + " text," +
                KEY_SCORE + " text," + KEY_LAST_SCORE_DATE + " text);";

        public CourseDBOpenHelper(Context context, String dbname, SQLiteDatabase.CursorFactory factory, int version){
            super(context, dbname, factory, version);
        }

        /*
         * this method will generate the Database with the above String
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        /*
         * autoimplemented method onUpgrade let you decide what happens when a database version
         * update is taking place
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        /*
         * with this method, the Database can be deleted
         */
        public void deleteDatabase(SQLiteDatabase db, String table){
            db.execSQL("DELETE FROM" + table);
            db.close();
        }

    }

}