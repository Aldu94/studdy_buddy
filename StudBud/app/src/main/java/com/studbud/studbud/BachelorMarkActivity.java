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

    private final String bachelorName = "bachelorMark";

    /*
     * when the acitivity is created, we set the visible content, setp the user interface and
     * initiate the save button
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bachelor_mark);
        db = new BachelorItemDB(BachelorMarkActivity.this);
        setupUI();
        onSafeButtonClick();
    }

    /*
     * here we setup the user interface including the editText field, the save button and
     * we set the mark value for the editText field acording to the value stored in the
     * database
     */
    private void setupUI(){
        bachelorMark = (EditText)findViewById(R.id.ba_mark);
        safeButton = (Button)findViewById(R.id.ba_mark_safe_button);
        db.open();
        bachelorMark.setText(String.valueOf(db.getBachelorItem().getMark()));
        db.close();
    }

    /*
     * this method will parse the input of the user in the editText field
     */
    private void getUserInput(){
        if(bachelorMark.getText().toString().length() == 0){
            bachelorMark.setText("0.0");
        }
        double mark = Double.parseDouble(bachelorMark.getText().toString());
        if(mark < 1 ){
            bachelorMark.setText("0.0");
        }
        if(mark > 4){
            bachelorMark.setText("4.0");
        }
        db.open();
        db.updateBachelorItem(db.getBachelorItem().getId(), bachelorMark.getText().toString());
        db.close();
        }

    /*
     * Here we define what happens when the user clicks the save button.
     * we start the activity MarksCalculator and insert the value from the editText field
     */
    private void onSafeButtonClick(){
        safeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInput();
                finish();
            }
        });
    }

}
