package com.studbud.studbud.TimeTable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.studbud.studbud.R;

import java.util.StringTokenizer;

public class AddCourseToTimeTable extends AppCompatActivity {

    private EditText courseTitle;
    private EditText room;
    private Button addButton;
    private String courseName = "";
    private String courseRoom = "";


    private String title;
    private String roomToStudy;

    private String currentCourseInfo;
    private int currentPosition;
    private int currentAccessibility;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_to_schedule);
        setupUI();
        onAddButtonClicked();
        getExistingString();
    }

    private void getExistingString() {
        Bundle extras = getIntent().getExtras();
        currentPosition = extras.getInt("Position");

        currentCourseInfo = extras.getString("Info");
        splitString(currentCourseInfo);
    }


    private void splitString(String string) {
        StringTokenizer token = new StringTokenizer(string, " ");
        if (token.countTokens()== 0) {
            courseName = " ";
            roomToStudy = " ";
        }else if(token.countTokens()==1){
            courseName = token.nextToken();
        } else {
            courseName = token.nextToken();
            roomToStudy = token.nextToken();
        }
    }

    private void setupUI(){
        courseTitle = (EditText)findViewById(R.id.title_text);
        room = (EditText)findViewById(R.id.room_text);
        addButton = (Button)findViewById(R.id.add_course_button);

    }
    private void onAddButtonClicked(){
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                readUserInput();
                Intent i = new Intent(AddCourseToTimeTable.this, Timetable.class);
                i.putExtra("Info", title+ " "+ roomToStudy);
                i.putExtra("Position", currentPosition);
                startActivity(i);
                finish();
            }
        });
    }

    private void readUserInput(){
        title = courseTitle.getText().toString();
        if(title.equals("")){
            title = " ";
        }
        roomToStudy = room.getText().toString();
        if(roomToStudy.equals("")){
            roomToStudy = " ";
        }
    }
}

