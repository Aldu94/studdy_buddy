package com.studbud.studbud;

import android.Manifest;
import android.Manifest.permission;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.studbud.studbud.TimeTable.Timetable;

public class MainActivity extends AppCompatActivity {

    private Button scheduleButton;
    private Button calculatorButton;
    private Button profileButton;
    private Button preferencesButton;
    private Button timetableButton;

    private int score = 0;

    private Database db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
        onScheduleClicked();
        onCalculatorClicked();
        onProfileClicked();
        onTimetableClicked();
        db = new Database(this);

        //updateLocation();
        checkFirstOpen();


    }

    // Check the first opening after the installation of the app
    // then create the course set, otherwise not
    private void checkFirstOpen() {
        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");
            // first time task
            db.addUserToDb(new User("Dummy", 0, MainSubject.fromString("Medieninformatik"), 0));
            //set ScoreDate
            String currentDay = Calendar.YEAR + " " + Calendar.MONTH + " " + (Calendar.DAY_OF_MONTH-1);
            db.setScoreDate(db.getUser().getName(), currentDay);
            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();
        }
    }


    /*
     * this method will request the permission from the user with a dialog in order to
     * be able to use the GPS sensor of the device
     */
    private void requestGpsPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            View permLayout = new View(this);
            Snackbar.make(permLayout, "GPS muss für die Gaming Funktion eingeschaltet sein", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
            }
        });
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{permission.ACCESS_FINE_LOCATION}, 0);
        }
    }


    // setup UI on main screen
    private void setupUI(){
        scheduleButton = (Button)findViewById(R.id.schedule_button);
        calculatorButton = (Button)findViewById(R.id.calculator_button);
        profileButton = (Button)findViewById(R.id.profile_button);
        timetableButton = (Button)findViewById(R.id.timetable_button);
    }


    //onClick-function for the schedule-button
    private void onScheduleClicked(){
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scheduleIntent = new Intent(MainActivity.this, Schedule.class);
                startActivity(scheduleIntent);
            }
        });
    }


    //onClick-function for the calculator-button
    private void onCalculatorClicked(){
        calculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calculatorIntent = new Intent(MainActivity.this, MarksCalculator.class);
                startActivity(calculatorIntent);
            }
        });
    }


    //onClick-function for the profil-button
    private void onProfileClicked(){
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(MainActivity.this, Profile.class);
                startActivity(profileIntent);
            }
        });
    }

    //onClick-function for the timetable-button
    private void onTimetableClicked(){
        timetableButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent timetableIntent = new Intent(MainActivity.this, Timetable.class);
                startActivity(timetableIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
