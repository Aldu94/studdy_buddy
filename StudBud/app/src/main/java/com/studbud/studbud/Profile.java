package com.studbud.studbud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    private TextView nameView;
    private TextView semesterView;
    private TextView mainSubjectView;
    private ImageButton newProfileButton;

    private Database db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        db = new Database(this);
        setupUI();
        onNewProfileClicked();
        getDataFromAddedProfile();
    }


    // method to read data from the AddProfile-activity
    // (create user object and safe it in database)

    private void getDataFromAddedProfile(){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null){
            String userName = extras.getString("username");
            nameView.setText(userName);
            int userSemester = extras.getInt("semester");
            semesterView.setText(String.valueOf(userSemester));
            MainSubject userMainSubject = MainSubject.values()[(int)extras.getLong("subjectID")];
            mainSubjectView.setText(userMainSubject.getName());
            db.open();
            db.updateUser(userName, userMainSubject, userSemester);
        }else{
            User user = db.getUser();
            nameView.setText(user.getName());
            semesterView.setText(""+user.getNumberOfSemester());
            mainSubjectView.setText(user.getMainSubject().getName());
        }

    }


    // method to setup the user interface of the activity
    // initialise all elements

    private void setupUI(){
        newProfileButton = (ImageButton)findViewById(R.id.new_profile_button);
        nameView = (TextView)findViewById(R.id.profile_name);
        semesterView = (TextView)findViewById(R.id.profile_semester);
        mainSubjectView = (TextView)findViewById(R.id.profile_main_subject);

    }


    // fire an onClick-event while the plus button is clicked
    // start the AddProfile-Activity

    private void onNewProfileClicked(){
        newProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile.this, AddProfile.class);
                User user = db.getUser();
                i.putExtra("user", user.getName());
                i.putExtra("semester", user.getNumberOfSemester());
                i.putExtra("mainsubject", user.getMainSubject().getName());
                startActivity(i);
                finish();
            }
        });
    }

}
