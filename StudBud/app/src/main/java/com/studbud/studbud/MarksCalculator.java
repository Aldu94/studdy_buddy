package com.studbud.studbud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import com.studbud.studbud.BachelorDB.BachelorItemDB;

public class MarksCalculator extends AppCompatActivity {


    private TextView infWiss;
    private TextView medienInfo;
    private TextView bachelorWork;

    private TextView infWissMark;
    private TextView medienInfoMark;
    private TextView bachelorWorkMark;
    private ListContent lc;
    private int subjectID;
    private User user;
    private Calculator calculator;
    public double markOne;
    public double markTwo;
    public double markBachelor;
    private static final String bachelorMark = "bachelorMark";
    private Button calculateButton;
    private double[] inf01;
    private Database db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markscalculator);
        db = new Database(MarksCalculator.this);
        setupUI();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDataFromBachelorMarkActivity();
    }


    private void setupUI() {
        infWiss = (TextView)findViewById(R.id.informationswissenschaft_text_view);
        medienInfo = (TextView)findViewById(R.id.medieninformatik_text_view);
        bachelorWork = (TextView)findViewById(R.id.bachelorarbeit_text_view);

        infWissMark = (TextView)findViewById(R.id.informationswissenschaft_mark);
        medienInfoMark = (TextView)findViewById(R.id.medieninformatik_mark);
        bachelorWorkMark = (TextView)findViewById(R.id.bachelorarbeit_mark);


    }



    public void onInfwissClick(View v) {
        Intent i = new Intent(MarksCalculator.this,InfWissMarksActivity.class);
        startActivity(i);
    }


    public void onMedienInfoClick(View v) {
        Intent m = new Intent(MarksCalculator.this,MedienInfoMarksActivity.class);
        startActivity(m);
    }


    public void onBachelorWorkClick(View v) {
        Intent b = new Intent(MarksCalculator.this,BachelorMarkActivity.class);
        b.putExtra("Mark", markBachelor);
        startActivity(b);
    }

    private void getDataFromBachelorMarkActivity(){
        Bundle bachelorExtras = getIntent().getExtras();
        if(bachelorExtras != null) {
            bachelorWorkMark.setText(String.valueOf(bachelorExtras.getDouble("Mark")));
        }
    }

    private void getDataFromMedienInfoMarkActivity(){
        Intent m = getIntent();
        Bundle medienInfoExtras = m.getExtras();
       // double miMarkData = m.getDoubleExtra("",);
        //medienInfoMark.setText(String.valueOf(miMarkData));

    }

    private void getDataFromInfWissMarkActivity(){
        Intent i = getIntent();
        Bundle infWissExtras = i.getExtras();
    }
}

