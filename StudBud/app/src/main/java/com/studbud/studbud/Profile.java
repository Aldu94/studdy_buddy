package com.studbud.studbud;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    private EditText name;
    private EditText semester;
    private Spinner mainSubjectSpinner;
    private ImageButton safeButton;

    private ArrayAdapter<CharSequence> spinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupUI();
        onSafeButtonClicked();
    }


    // method to setup the user interface to work with user input

    private void setupUI(){
        name = (EditText)findViewById(R.id.name_editText);
        semester = (EditText)findViewById(R.id.semester_editText);
        mainSubjectSpinner = (Spinner)findViewById(R.id.main_subject_spinner);
        safeButton = (ImageButton)findViewById(R.id.safe_button);

        spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.main_subject, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainSubjectSpinner.setAdapter(spinnerAdapter);
    }


    // method to fire an onClick-event for the safe button
    // safe UserItem in database to get values for calculation

    private void onSafeButtonClicked(){
        safeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = getUserInputAndCreateItem();
                Toast.makeText(Profile.this, String.valueOf(user.getName()) + " ist im " + String.valueOf(user.getNumberOfSemester()) + ". Semester und studiert den Studiengang mit der ID: " + String.valueOf(user.getMainSubjectID()), Toast.LENGTH_SHORT).show();
                //db.saveUserItem(user)
            }
        });
    }


    // method to get the user input from the whole window

    private User getUserInputAndCreateItem(){
        String nameOfUser = name.getText().toString();
        int semesterOfUser = Integer.parseInt(semester.getText().toString());
        long subjectId = mainSubjectSpinner.getSelectedItemId();

        User user = new User(nameOfUser,semesterOfUser,subjectId);

        return user;
    }

}
