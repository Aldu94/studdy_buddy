package com.studbud.studbud;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class InfWissMarksActivity extends AppCompatActivity {


    private Database db = new Database(this);
    private Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_wiss_marks);
        initCalculator();
        initDB();
        setupUI();
    }

    private void initDB() {
        db.open();
        Log.d("Check for open Database", "OPEN");
    }

    private void initCalculator() {
        calculator = new Calculator();
    }

    private void setupUI(){

    }

}
