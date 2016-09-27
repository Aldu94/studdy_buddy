package com.studbud.studbud.TimeTable;

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

    private String[] scheduleContent = new String[]{
            "Zeit", "MO", "DI", "MI", "DO", "FR",
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
            "19:00", " ", " ", " ", " ", " ",
            "20:00", " ", " ", " ", " ", " "};

    private final String[] forbiddenPositions = new String[]{
            "0", "1", "2", "3", "4", "5", "6", "12", "18", "24", "30", "36", "42", "48", "54", "60", "66", "72", "78", "84"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        db = new TimetableDataBase(this);
        initiateDb();
        getInfoOfCourseToAdd();
        getSchedule();
    }

    private void initiateDb(){
        db.open();
        if(db.countScheduleDbEntries()==0){
            db.createScheduleDbItem(scheduleName, convertArrayForDatabase(scheduleContent));
        }
        db.close();
    }

    private void deleteScheduleDbEntries(int id) {
        db.open();
        db.deleteScheduleDbItem(new ScheduleDbItem("", "", id));
        db.close();
        Log.d(LOG, "Entries deleted");

    }

    private void showAllData() {
        List<ScheduleDbItem> scheduleDbItemList = db.getAllScheduleDbItems();
    }

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

    private void getInfoOfCourseToAdd() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String info = (String) extras.get("Info");
            int position = (int) extras.get("Position");
            updateScheduleItem(info, position);
        }

    }

    private String[] convertDatabaseInfoToStringArray(ScheduleDbItem scheduleDbItem) {
        String[] scheduleData = scheduleDbItem.getContent().split(saveDataSeparator);
        return scheduleData;
    }

    public void updateScheduleItem(String updatedInfo, int position) {
        db.open();
        String[] newUpdate = convertDatabaseInfoToStringArray(db.getSchedule());
        newUpdate[position] = updatedInfo;
        db.updateScheduleDbItem(db.getSchedule().getId(), convertArrayForDatabase(newUpdate));
        db.close();
    }

    private void setupUI(ScheduleDbItem scheduleDbItem) {
        gridView = (GridView) findViewById(R.id.timetableGridView);
        gridView.setAdapter(new TimetableGridViewAdapter(Timetable.this, scheduleDbItem.getContent().split(saveDataSeparator)));

        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClick(position);
            }
        });
    }

    public void onClick(int position) {
        currentPosition = position;
        Intent i = new Intent(Timetable.this, AddCourseToTimeTable.class);
        int note = position;
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


