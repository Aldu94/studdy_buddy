package com.studbud.studbud;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    private TextView nameView;
    private TextView semesterView;
    private TextView mainSubjectView;
    private ImageButton newProfileButton;
    private ImageButton collectPointsButton;
    private GPSLocator locator;
    private TextView scoreView;
    private int score;
    public static boolean isOnCampus = false;
    private static final int scoreAmount = 5;

    String currentDay = Calendar.YEAR + " " + Calendar.MONTH + " " + Calendar.DAY_OF_MONTH;


    private Database db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        db = new Database(this);
        locator = new GPSLocator(this);
        setupUI();
        onNewProfileClicked();
        onCollectPointsClicked();
        getDataFromAddedProfile();
    }

    public void setIsOnCampus(boolean boole){
        this.isOnCampus = boole;
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
            scoreView.setText(""+user.getScore());
        }

    }


    // method to setup the user interface of the activity
    // initialise all elements

    private void setupUI(){
        newProfileButton = (ImageButton)findViewById(R.id.new_profile_button);
        nameView = (TextView)findViewById(R.id.profile_name);
        semesterView = (TextView)findViewById(R.id.profile_semester);
        mainSubjectView = (TextView)findViewById(R.id.profile_main_subject);
        scoreView = (TextView)findViewById(R.id.profile_score);
        collectPointsButton = (ImageButton)findViewById(R.id.collect_points);
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

    /*
     * Here we can check if the User is on the University campus (for this app, we use
     * the location of University Regensburg) and if he already has collected points on this day
     */
    private boolean checkForAvailablePoints() {
        Log.d("Location", ""+isOnCampus);
        if(!db.getScoreDate().equals(currentDay) &&isOnCampus){
            Toast.makeText(Profile.this, "There you go, "+ scoreAmount + " Points!",Toast.LENGTH_SHORT).show();
            return true;
        }else {
            Toast.makeText(Profile.this, "Already collected today!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /*
     * This is the Listener to our little Point-Selector Button. Have fun!
     */
    private void onCollectPointsClicked() {
        collectPointsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locator.getLocation(Profile.this);
                if (checkForAvailablePoints()) {
                    score = Integer.parseInt(db.getUserScore());
                    score += scoreAmount;
                    db.setScoreDate(db.getUser().getName(), currentDay);
                    db.updateUserScore(db.getUser().getName(), String.valueOf(score));
                    scoreView.setText(db.getUserScore());
                }

            }
        });
    }

}
