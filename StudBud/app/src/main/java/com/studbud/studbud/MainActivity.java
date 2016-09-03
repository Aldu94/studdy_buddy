package com.studbud.studbud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    private Database db = new Database(MainActivity.this);
    private CourseItem INF1 = new CourseItem("INF-M01.1","bestanden","2.3");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
        onScheduleClicked();
        onCalculatorClicked();
        onProfileClicked();
        onPreferencesClicked();
        db.open();
        
        db.close();


    }

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
