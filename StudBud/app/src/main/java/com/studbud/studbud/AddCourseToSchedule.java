package com.studbud.studbud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.studbud.studbud.domain.ScheduleItem;

public class AddCourseToSchedule extends AppCompatActivity {

    private EditText courseTitle;
    private EditText startTime;
    private EditText endTime;
    private EditText room;
    private Spinner daySpinner;
    private Button addButton;

    private ArrayAdapter<CharSequence> spinnerAdapter;

    private String title;
    private String timeStart;
    private String timeEnd;
    private String roomToStudy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_to_schedule);
        setupUI();
        //onAddButtonClicked();
    }

    private void setupUI(){
        courseTitle = (EditText)findViewById(R.id.title_text);
        startTime = (EditText)findViewById(R.id.start_time_text);
        endTime = (EditText)findViewById(R.id.end_time_text);
        room = (EditText)findViewById(R.id.room_text);
        daySpinner = (Spinner)findViewById(R.id.day_spinner);
        addButton = (Button)findViewById(R.id.add_course_button);

        spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.days_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(spinnerAdapter);

    }
/*
    private void onAddButtonClicked(){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readUserInput();
                Log.d("Course", title + " is during " + timeStart + " o'clock to " + timeEnd + " in " + roomToStudy);
                //open database
                ScheduleItem si = new ScheduleItem(title, timeStart, timeEnd, roomToStudy);
                //add schedule item to database
                Intent i = new Intent(AddCourseToSchedule.this, Schedule.class);
                //put extra to intent
                startActivity(i);
                //close database
            }
        });
    }
*/
    private void readUserInput(){
        title = courseTitle.getText().toString();
        timeStart = startTime.getText().toString();
        timeEnd = endTime.getText().toString();
        roomToStudy = room.getText().toString();
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


}
