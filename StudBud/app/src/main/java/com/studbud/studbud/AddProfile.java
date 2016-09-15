package com.studbud.studbud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class AddProfile extends AppCompatActivity {

    private EditText name;
    private EditText semester;
    private Spinner mainSubjectSpinner;
    private ImageButton safeButton;

    private ArrayAdapter<CharSequence> spinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);
        setupUI();
        onSafeButtonClicked();
    }


    // method to setup the user interface for all elements in the activity

    private void setupUI(){
        name = (EditText)findViewById(R.id.name_editText);
        semester = (EditText)findViewById(R.id.semester_editText);
        mainSubjectSpinner = (Spinner)findViewById(R.id.main_subject_spinner);
        safeButton = (ImageButton)findViewById(R.id.safe_button);

        connectSpinnerAdapter();
    }


    // method to connect the main subject spinner with the spinner adapter

    private void connectSpinnerAdapter(){
        spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.main_subject,android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainSubjectSpinner.setAdapter(spinnerAdapter);
    }


    // method to fire an OnClick-event while the safe button is clicked
    // create new user object and safe it in the database
    // go back to profile-screen and show the profile

    private void onSafeButtonClicked(){
        safeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = getUserInputAndCreateItem();
                //safe User in database
                Intent intent = new Intent(AddProfile.this, Profile.class);
                startActivity(intent);
            }
        });
    }


    // method to get the user input from the whole window
    // create new user object and give parameters
    // give back an user object

    private User getUserInputAndCreateItem() {
        String nameOfUser = name.getText().toString();
        int semesterOfUser = Integer.parseInt(semester.getText().toString());
        long subjectId = mainSubjectSpinner.getSelectedItemId();


        User user = new User(nameOfUser, semesterOfUser, subjectId);
        return user;


    }


}
