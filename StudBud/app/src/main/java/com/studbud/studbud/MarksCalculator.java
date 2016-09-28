package com.studbud.studbud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import com.studbud.studbud.BachelorDB.BachelorItemDB;

import java.text.DecimalFormat;

public class MarksCalculator extends AppCompatActivity {


    private TextView infWiss;
    private TextView medienInfo;
    private TextView bachelorWork;
    private TextView bachelorGrade;

    private TextView infWissMark;
    private TextView medienInfoMark;
    private TextView bachelorWorkMark;
    private TextView bachelorGradeMark;

    private ListContent lc;
    private int subjectID;
    private User user;
    public double markOne;
    public double markTwo;
    public double markBachelor;
    public double markBachelorGrade;
    private static final String bachelorMark = "bachelorMark";
    private Button calculateButton;
    private double[] inf01;
    private Database db;
    private BachelorItemDB bDb;

    /*
     * we open the database, setup the content to view in this activity and setup the user
     * interface
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markscalculator);
        db = new Database(MarksCalculator.this);
        bDb = new BachelorItemDB(MarksCalculator.this);
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
        getDataFromMedienInfoMarkActivity();
        getDataFromInfWissMarkActivity();
        getDataForBachelorMark();
    }

    /*
     * we set up the user interface with some textviews
     */
    private void setupUI() {
        infWiss = (TextView)findViewById(R.id.informationswissenschaft_text_view);
        medienInfo = (TextView)findViewById(R.id.medieninformatik_text_view);
        bachelorWork = (TextView)findViewById(R.id.bachelorarbeit_text_view);
        bachelorGrade = (TextView)findViewById(R.id.bachelornote_text_view);

        infWissMark = (TextView)findViewById(R.id.informationswissenschaft_mark);
        medienInfoMark = (TextView)findViewById(R.id.medieninformatik_mark);
        bachelorWorkMark = (TextView)findViewById(R.id.bachelorarbeit_mark);
        bachelorGradeMark = (TextView)findViewById(R.id.bachelornote_mark);


    }

    private void getData(){
        getDataFromBachelorMarkActivity();
        getDataFromInfWissMarkActivity();
        getDataFromMedienInfoMarkActivity();
        getDataForBachelorMark();
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
        DecimalFormat decimal = new DecimalFormat("#.#");
        bDb.open();
        bachelorWorkMark.setText(decimal.format(bDb.getBachelorItem().getMark()));
        bDb.close();
    }

    /*
     * this method collects the data shown in the TextField bachelormark
     */
    private void getDataForBachelorMark(){
        DecimalFormat decimal = new DecimalFormat("#.#");
        double bachelornote = 0.0;
        double mediInfo = Double.parseDouble(medienInfoMark.getText().toString());
        double infwiss = Double.parseDouble(infWissMark.getText().toString());
        double bachelor = Double.parseDouble(bachelorWorkMark.getText().toString());
        if(db.getUser().getMainSubject().getName().equals("Informationswissenschaft")){
            if(mediInfo != 0.0) {
                bachelornote += mediInfo * 0.3;
            }
            if(infwiss != 0.0) {
                bachelornote += infwiss * 0.5;
            }
            if(bachelor != 0.0) {
                bachelornote += bachelor * 0.2;
            }

            bachelorGradeMark.setText(decimal.format(bachelornote));
        }else{
            if(mediInfo != 0.0) {
                bachelornote += mediInfo * 0.5;
            }
            if(infwiss != 0.0) {
                bachelornote += infwiss * 0.3;
            }
            if(bachelor != 0.0) {
                bachelornote += bachelor * 0.2;
            }
            bachelorGradeMark.setText(decimal.format(bachelornote));
        }
    }

    /*
     * this method collects the data shown in the TextField medienInfoMark
     */
    private void getDataFromMedienInfoMarkActivity(){
        DecimalFormat decimal = new DecimalFormat("#.#");
        medienInfoMark.setText(decimal.format(db.getUserMedInfMark()));
    }

    /*
     * this method collects the data that is provided in the extras Bundle from the
     * activity InfWissMarksActivity
     */
    private void getDataFromInfWissMarkActivity(){
        DecimalFormat decimal = new DecimalFormat("#.#");
        infWissMark.setText(decimal.format(db.getUserInfwissMark()));
    }
}

