package com.studbud.studbud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.studbud.studbud.domain.CalculatorItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private Button calculateButton;
    private double[] inf01;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markscalculator);
        setupUI();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDataFromBachelorMarkActivity();
    }


    private void readUserInput() {
        //checkForEmptyEditText();
        /*markOne = Double.parseDouble(editTextOne.getText().toString());
        markTwo = Double.parseDouble(editTextTwo.getText().toString());*/
        inf01[0] = markOne;
        inf01[1] = markTwo;
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
        startActivity(b);
    }

    private void getDataFromBachelorMarkActivity(){
        Intent b = getIntent();
        Bundle bachelorWorkExtras = b.getExtras();
        double markData = b.getIntExtra("bachelorWorkMark",4);
        bachelorWorkMark.setText(String.valueOf(markData));
    }

    private void getDataFromMedienInfoMarkActivity(){
        Intent m = getIntent();
        Bundle medienInfoExtras = m.getExtras();

    }

    private void getDataFromInfWissMarkActivity(){
        Intent i = getIntent();
        Bundle infWissExtras = i.getExtras();
    }
}

