package com.studbud.studbud;

import android.Manifest.permission;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.studbud.studbud.domain.CourseItem;

public class MainActivity extends AppCompatActivity {

    private Button scheduleButton;
    private Button calculatorButton;
    private Button profileButton;
    private Button preferencesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
        onScheduleClicked();
        onCalculatorClicked();
        onProfileClicked();
        onPreferencesClicked();
        updateLocation();
        //db.updateUser("Klaus", 1, 5);
        //db.addUserToDb(karl);
        //db.createSet();
       // showArray();
        //db.updateUser("Karl", "1", "lalalala");
        //showArray();
        //showArray();
        //checkForUser(karl.getName());
        //showUser(karl.getName());
        //this.deleteDatabase("courseData.db");
    }
    /*
    private void showUser(String data){
       String[] userData = db.updateUser(data);
        for(String member: userData){
            Log.i("UserData: ",member);
        }

    }*/

    /*private void showArray(){
    private void checkForUser(String data){
        if(db.checkForExistingUser(data) == true){
            Log.i("Note: ", "User exists!");
        }else{
            Log.i("Note: ", "User not found!");
        }

    }
    private void showArray(){
        for(CourseItem member: db.getAllCourseItems()){
            Log.i("Test ", "Name: "+ member.getName()+" ID: "+ member.getStatus()+" Module: "+member.getModule());
        }
    }*/
     /*for(User member: db.getUser()){
            Log.i("User ", "User: "+member.getName());
        }*/


    private void updateLocation(){
        if(!hasPermission(permission.ACCESS_FINE_LOCATION)){
            ActivityCompat.requestPermissions(this, new String[]{permission.ACCESS_FINE_LOCATION}, 12);
        }
    }

    private boolean hasPermission(String perm) {
        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            return(PackageManager.PERMISSION_GRANTED==checkSelfPermission(perm));
        }
        return false;
    }

 /*   private void collectGamePoints(){
        Log.i("MainActivity", "asking for GPS permission!");
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) != PackageManager.PERMISSION_GRANTED){
            requestGpsPermission();
        }
        else{
            Log.i("MainActivity", "GPS permission granted");
        }
    }
    private void requestGpsPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
        Snackbar.make(permLayout, "GPS muss für die Gaming Funktion eingeschaltet sein", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener()){
            @Override
                    public void onClick(View view){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
            }
        }
        }
     else {
        ActivityCompat.requestPermissions(this, new String[]{permission.ACCESS_FINE_LOCATION}, 0);
        }
    }*/

    // setup buttons on main screen
    private void setupUI(){
        scheduleButton = (Button)findViewById(R.id.schedule_button);
        calculatorButton = (Button)findViewById(R.id.calculator_button);
        profileButton = (Button)findViewById(R.id.profile_button);
        preferencesButton = (Button)findViewById(R.id.preferences_button);
    }

    private void onScheduleClicked(){
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scheduleIntent = new Intent(MainActivity.this, Schedule.class);
                startActivity(scheduleIntent);
            }
        });
    }

    private void onCalculatorClicked(){
        calculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calculatorIntent = new Intent(MainActivity.this, MarksCalculator.class);
                startActivity(calculatorIntent);
            }
        });
    }

    private void onProfileClicked(){
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(MainActivity.this, Profile.class);
                startActivity(profileIntent);
            }
        });
    }

    private void onPreferencesClicked(){
        preferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent preferencesIntent = new Intent(MainActivity.this, Preferences.class);
                startActivity(preferencesIntent);
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
