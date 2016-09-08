package com.studbud.studbud;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class Profile extends AppCompatActivity {

    private EditText name;
    private EditText semester;
    private Spinner mainSubject;
    private ImageButton safeButton;

    private ArrayAdapter<CharSequence> spinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupUI();
    }

    private void setupUI(){
        name = (EditText)findViewById(R.id.name_editText);
        semester = (EditText)findViewById(R.id.semester_editText);
        mainSubject = (Spinner)findViewById(R.id.main_subject_spinner);
        safeButton = (ImageButton)findViewById(R.id.safe_button);

        spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.main_subject, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainSubject.setAdapter(spinnerAdapter);
    }

    private void onSafeButtonClicked(){
        safeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //safe in database
            }
        });
    }

}
