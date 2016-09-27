package com.studbud.studbud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.studbud.studbud.BachelorDB.BachelorItemDB;

public class BachelorMarkActivity extends AppCompatActivity {

    private EditText bachelorMark;
    private Button safeButton;
    private BachelorItemDB db;

    private double finalBachelorWorkMark;
    private final String bachelorName = "bachelorMark";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bachelor_mark);
        db = new BachelorItemDB(this);
        setupUI();
        onSafeButtonClick();
    }


    private void setupUI(){
        bachelorMark = (EditText)findViewById(R.id.ba_mark);
        safeButton = (Button)findViewById(R.id.ba_mark_safe_button);
        db.open();
        bachelorMark.setText(String.valueOf(db.getBachelorItem().getMark()));
        db.close();
    }

    private void getUserInput(){
        String mark = bachelorMark.getText().toString();
        if(!mark.isEmpty()) {
            finalBachelorWorkMark = Double.parseDouble(mark);
            db.open();
            db.updateBachelorItem(db.getBachelorItem().getId(), mark);
            db.close();
        }
    }

    private void onSafeButtonClick(){
        safeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInput();
                Intent intent = new Intent(BachelorMarkActivity.this, MarksCalculator.class);
                intent.putExtra("Mark",finalBachelorWorkMark);
                startActivity(intent);
                finish();
            }
        });
    }

}
