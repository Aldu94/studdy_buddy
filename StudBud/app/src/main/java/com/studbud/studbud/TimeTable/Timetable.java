package com.studbud.studbud.TimeTable;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;




import com.studbud.studbud.R;
import com.studbud.studbud.Schedule;

import java.util.Arrays;
import java.util.List;

public class Timetable extends AppCompatActivity {

    public final static String LOG = Schedule.class.getSimpleName();

    private final String scheduleName = "schedule";
    private int currentPosition = -1;
    private GridView gridView;
    private String saveDataSeparator = ",";
    private TimetableDataBase db;
    private TimetableGridViewAdapter tgva;

    /*
     * this array is used for the first saving process which will convert the array to a string and
     * put it into the database
     */
    private String[] scheduleContent = new String[]{
            "CLEARTABLE", "MO", "DI", "MI", "DO", "FR",
            "08:00", " ", " ", " ", " ", " ",
            "09:00", " ", " ", " ", " ", " ",
            "10:00", " ", " ", " ", " ", " ",
            "11:00", " ", " ", " ", " ", " ",
            "12:00", " ", " ", " ", " ", " ",
            "13:00", " ", " ", " ", " ", " ",
            "14:00", " ", " ", " ", " ", " ",
            "15:00", " ", " ", " ", " ", " ",
            "16:00", " ", " ", " ", " ", " ",
            "17:00", " ", " ", " ", " ", " ",
            "18:00", " ", " ", " ", " ", " ",
            "19:00", " ", " ", " ", " ", " "
            };

    //This string is used to know if the user actually klicked on a field in the timetable or
    // on one of the column and row titles
    private final String[] forbiddenPositions = new String[]{
            "0", "1", "2", "3", "4", "5", "6", "12", "18", "24", "30", "36", "42", "48", "54", "60", "66", "72", "78", "84"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        db = new TimetableDataBase(this);
        getInfoOfCourseToAdd();
        getSchedule();
        db.open();
        tgva = new TimetableGridViewAdapter(this, db.getSchedule().getContent().split(saveDataSeparator));
        db.close();
        initiateDb();

        getSchedule();
    }

    /*
     * the database is initialized for accessability purpose of other method working on the database
     */
    private void initiateDb(){
        db.open();
        if(db.countScheduleDbEntries()==0){
            db.createScheduleDbItem(scheduleName, convertArrayForDatabase(scheduleContent));
        }
        db.close();
    }

    /*
     * this method can delete a scheduleDatabase entry according to the id as primary key identifier
     */
    private void deleteScheduleDbEntries(int id) {
        db.open();
        db.deleteScheduleDbItem(new ScheduleDbItem("", "", id));
        db.close();
        Log.d(LOG, "Entries deleted");

    }

    /*
     * this will put the complete database entries to a list of scheduleDBItems
     */
    private void showAllData() {
        List<ScheduleDbItem> scheduleDbItemList = db.getAllScheduleDbItems();
    }

    /*
     * this methods wil return the content of the schedule stored in the database
     */
    private ScheduleDbItem getSchedule() {
        db.open();
        if (db.countScheduleDbEntries() == 0) {
            ScheduleDbItem newScheduleDbItem = db.createScheduleDbItem(scheduleName, convertArrayForDatabase(scheduleContent));
            setupUI(newScheduleDbItem);
            db.close();

            return newScheduleDbItem;
        } else {
            ScheduleDbItem scheduleDbItem = new ScheduleDbItem(scheduleName, db.getSchedule().getContent(), 1);
            setupUI(scheduleDbItem);
            showAllData();
            db.close();

            return scheduleDbItem;
        }

    }

    /*
     * the method uses the info from the AddCourseToTimeTable activity to update the timetable
     */
    private void getInfoOfCourseToAdd() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String info = (String) extras.get("Info");
            int position = (int) extras.get("Position");
            updateScheduleItem(info, position);
        }

    }

    /*
     * method to split a string from the database into a string array by using a specified
     * separator. the seprator is also used for converting that array back to a single string
     */
    private String[] convertDatabaseInfoToStringArray(ScheduleDbItem scheduleDbItem) {
        String[] scheduleData = scheduleDbItem.getContent().split(saveDataSeparator);
        return scheduleData;
    }

    /*
     * method to update the timetable using the information from the getInfoOfCourseToAdd method
     */
    public void updateScheduleItem(String updatedInfo, int position) {
        db.open();
        String[] newUpdate = convertDatabaseInfoToStringArray(db.getSchedule());
        newUpdate[position] = updatedInfo;
        db.updateScheduleDbItem(db.getSchedule().getId(), convertArrayForDatabase(newUpdate));
        db.close();
    }

    /*
     * method to setup the user interface with the gridview. the method also sets the
     * TimeTableGridViewAdapter for the gridview
     */
    private void setupUI(ScheduleDbItem scheduleDbItem) {
        gridView = (GridView) findViewById(R.id.timetableGridView);
        //gridView.setAdapter(new TimetableGridViewAdapter(Timetable.this, scheduleDbItem.getContent().split(saveDataSeparator)));
        gridView.setAdapter(tgva);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClick(position);
            }
        });
    }

    /*
     * the method will check what happens when we click on a textview in the gridview of the
     * timetable. If we click on a "forbidden position" it will just notify the user about the
     * illegal action. if the user clicks on a field in the timetable that can be edited, the
     * AddCourseToTimeTable activity will be started. the position is also put extra to the intent
     * in order to link the changes in the textview with the textview in the gridview
     */
    public void onClick(int position) {
        currentPosition = position;
        Intent i = new Intent(Timetable.this, AddCourseToTimeTable.class);
        int note = position;
        if (currentPosition == 0) {
            showDialog(this);
        } else {
            if (Arrays.asList(forbiddenPositions).contains("" + position)) {
                Toast.makeText(this, "geschütztes Feld!", Toast.LENGTH_SHORT).show();
            } else {
                db.open();
                String info = convertDatabaseInfoToStringArray(db.getSchedule())[position];
                db.close();
                i.putExtra("Position", note);
                if (info.equals(" ")) {
                    Log.d("TIMETABLE: ", "String ist leerzeichen");
                    i.putExtra("Info", info);
                } else {
                    Log.d("TIMETABLE: ", "String ist kein leerzeichen");
                    i.putExtra("Info", info);
                }
                startActivity(i);
                finish();
            }
        }
    }

    /*
     * This method will show an alertDialog when the user klicks on the CLEAR Button in the top left
     * corner of the gridView
     * Source: http://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
     */
    public void showDialog(Context context) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Do you want to reset the Timetable?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.open();
                        db.updateScheduleDbItem(db.getSchedule().getId(), convertArrayForDatabase(scheduleContent));
                        db.close();
                        tgva.notifyDataSetChanged();
                        finish();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    /*
     * method to convert a string array to a single string by using the specified separator
     */
    private String convertArrayForDatabase(String[] data) {
        String string = "";
        for (int i = 0; i < data.length; i++) {
            string = string + data[i];
            if (data.length - 1 != i) {
                string = string + saveDataSeparator;
            }
        }
        return string;
    }
}


