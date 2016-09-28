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
    public double markOne;
    public double markTwo;
    public double markBachelor;
    private static final String bachelorMark = "bachelorMark";
    private Button calculateButton;
    private double[] inf01;
    private Database db;

    /*
     * we open the database, setup the content to view in this activity and setup the user
     * interface
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markscalculator);
        db = new Database(MarksCalculator.this);
        setupUI();
    }

    /*
     * the method executes all tasks we have to do when one of the marks activities
     * call on this activity intent
     */
    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDataFromBachelorMarkActivity();
    }

    /*
     * we set up the user interface with some textviews
     */
    private void setupUI() {
        infWiss = (TextView)findViewById(R.id.informationswissenschaft_text_view);
        medienInfo = (TextView)findViewById(R.id.medieninformatik_text_view);
        bachelorWork = (TextView)findViewById(R.id.bachelorarbeit_text_view);

        infWissMark = (TextView)findViewById(R.id.informationswissenschaft_mark);
        medienInfoMark = (TextView)findViewById(R.id.medieninformatik_mark);
        bachelorWorkMark = (TextView)findViewById(R.id.bachelorarbeit_mark);


    }


    /*
     * here we define what happens when the button Infwiss is clicked.
     * we start the activity InfwissMarksActivity
     */
    public void onInfwissClick(View v) {
        Intent i = new Intent(MarksCalculator.this,InfWissMarksActivity.class);
        startActivity(i);
    }

    /*
     * here we define what happens when the button MedienInfo is clicked.
     * we start the MedienInfoMarksActivity
     */
    public void onMedienInfoClick(View v) {
        Intent m = new Intent(MarksCalculator.this,MedienInfoMarksActivity.class);
        startActivity(m);
    }

    /*
     * here we define what happens when the button bachelorWork is clicked.
     * we start the bachelorMarkActivity
     */
    public void onBachelorWorkClick(View v) {
        Intent b = new Intent(MarksCalculator.this,BachelorMarkActivity.class);
        b.putExtra("Mark", markBachelor);
        startActivity(b);
    }

    /*
     * this method collects the data given by the BachelorMarkActivity
     */
    private void getDataFromBachelorMarkActivity(){
        Bundle bachelorExtras = getIntent().getExtras();
        if(bachelorExtras != null) {
            bachelorWorkMark.setText(String.valueOf(bachelorExtras.getDouble("Mark")));
        }
    }

    /*
     * this method collects the data given by the MedienInfoMarksActivity
     */
    private void getDataFromMedienInfoMarkActivity(){
        Intent m = getIntent();
        Bundle medienInfoExtras = m.getExtras();
       // double miMarkData = m.getDoubleExtra("",);
        //medienInfoMark.setText(String.valueOf(miMarkData));

    }

    /*
     * this method collects the data that is provided in the extras Bundle from the
     * activity InfWissMarksActivity
     */
    private void getDataFromInfWissMarkActivity(){
        Intent i = getIntent();
        Bundle infWissExtras = i.getExtras();
    }
}

