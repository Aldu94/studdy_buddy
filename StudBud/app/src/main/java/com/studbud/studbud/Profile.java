package com.studbud.studbud;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    private Button weiterButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupUI();
        onWeiterButtonClicked();
    }


    private void setupUI(){
        weiterButton = (Button)findViewById(R.id.weiter_button);
    }

    private void onWeiterButtonClicked(){
        weiterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile.this,AddProfile.class);
                startActivity(i);
            }
        });
    }

}
