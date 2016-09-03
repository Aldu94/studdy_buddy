package com.studbud.studbud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class Schedule extends AppCompatActivity {

        private ImageButton addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        setupUI();
        onAddButtonClicked();
    }

    private void setupUI(){
        addButton = (ImageButton)findViewById(R.id.add_course_button);
    }

    private void onAddButtonClicked(){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Schedule.this, AddCourseToSchedule.class);
                startActivity(i);
            }
        });
    }

}
