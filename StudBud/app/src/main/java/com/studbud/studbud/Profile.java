package com.studbud.studbud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class Profile extends AppCompatActivity {

    private ImageButton newProfileButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupUI();
        onNewProfileClicked();
    }


    // method to setup the user interface of the activity
    // initialise all elements

    private void setupUI(){
        newProfileButton = (ImageButton)findViewById(R.id.new_profile_button);
    }


    // fire an onClick-event while the plus button is clicked
    // start the AddProfile-Activity
    
    private void onNewProfileClicked(){
        newProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile.this, AddProfile.class);
                startActivity(i);
            }
        });
    }

}
