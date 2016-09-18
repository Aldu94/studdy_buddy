package com.studbud.studbud;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BachelorMarkActivity extends AppCompatActivity {

    private EditText bachelorMark;
    private Button safeButton;

    private double finalBachelorWorkMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bachelor_mark);
        setupUI();
        onSafeButtonClick();
    }


    private void setupUI(){
        bachelorMark = (EditText)findViewById(R.id.ba_mark);
        safeButton = (Button)findViewById(R.id.ba_mark_safe_button);
    }

    private void getUserInput(){
        String mark = bachelorMark.getText().toString();
        finalBachelorWorkMark = Double.parseDouble(mark);
    }

    private void onSafeButtonClick(){
        safeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInput();
                Intent intent = new Intent(BachelorMarkActivity.this, MarksCalculator.class);
                intent.putExtra("bachelorWorkMark",finalBachelorWorkMark);
                startActivity(intent);
            }
        });
    }

}
