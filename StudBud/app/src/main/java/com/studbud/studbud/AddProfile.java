package com.studbud.studbud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddProfile extends AppCompatActivity {

    private EditText name;
    private EditText semester;
    private Spinner mainSubjectSpinner;
    private Button saveButton;
    private Database db;

    private String user;
    private int semesternumber;
    private String mainsubject;
    private ArrayAdapter<CharSequence> spinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);
        db = new Database(AddProfile.this);
        setupUI();
        onSafeButtonClicked();
    }


    // method to setup the user interface for all elements in the activity

    private void setupUI(){
        name = (EditText)findViewById(R.id.name_editText);
        semester = (EditText)findViewById(R.id.semester_editText);
        mainSubjectSpinner = (Spinner)findViewById(R.id.main_subject_spinner);
        saveButton = (Button)findViewById(R.id.save_button);

        connectSpinnerAdapter();
        insertUserData();
    }

    /*private void insertUserData(){
        Bundle extra = getIntent().getExtras();
        user = extra.getString("user");
        semesternumber = extra.getInt("semester");
        mainsubject = extra.getString("mainsubject");

        name.setText(user);
        semester.setText(""+semesternumber);
        if(mainsubject.equals("Informationswissenschaft")){
            mainSubjectSpinner.setSelection(0);
        }else {
            mainSubjectSpinner.setSelection(1);
        }
    }*/

    private void insertUserData(){
        User user = db.getUser();
        name.setText(user.getName());
        semester.setText(""+user.getNumberOfSemester());
        if(user.getMainSubject().getName().equals("Informationswissenschaft")){
            mainSubjectSpinner.setSelection(0);
        }else {
            mainSubjectSpinner.setSelection(1);
        }
    }

    // method to connect the main subject spinner with the spinner adapter

    private void connectSpinnerAdapter(){
        spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.main_subject,android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainSubjectSpinner.setAdapter(spinnerAdapter);
    }


    // method to fire an OnClick-event while the safe button is clicked
    // create intent and put all parameters
    // give intent-data to Profile-Activity (in Profile: create user object and safe in database)
    // go back to profile-screen and show the profile

    private void onSafeButtonClicked(){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = getNameInput();
                int userSemester = getSemesterInput();
                String userMainSubject = getSpinnerInfo();
                db.updateUser(userName, MainSubject.fromString(userMainSubject), userSemester);
                Intent intent = new Intent(AddProfile.this, Profile.class);
                //intent.putExtra("username", userName);
                //intent.putExtra("semester", userSemester);
                //intent.putExtra("subjectID", userMainSubjectID);
                startActivity(intent);
                finish();
            }
        });
    }

    // method to convert the info from the Mainsubject spinner to a string with the mainsubject
    private String getSpinnerInfo(){
        if(mainSubjectSpinner.getSelectedItemPosition()==0){
            return "Informationswissenschaft";
        }else{
            return "Medieninformatik";
        }

    }
    // method to get the user input for the name

    private String getNameInput(){
        if(name.getText().toString().equals("")){
            return "Name";
        }
        return name.getText().toString();
    }


    // method to get the user input for the semester

    private int getSemesterInput() {
        if(semester.getText().toString().equals("")){
            return 0;
        }
        return Integer.parseInt(semester.getText().toString());
    }


    // method to get the user input for the subject id

    private long getSubjectInput(){
        return (int) mainSubjectSpinner.getSelectedItemId();
    }

}
