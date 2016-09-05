package com.studbud.studbud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.studbud.studbud.persistence.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MarksCalculator extends AppCompatActivity {

    private ExpandableListView informationswissenschaft;

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
        inf01 = new double[2];
        user = new User("Karl",0,3);
        subjectID = user.getMainSubjectID();
        calculator = new Calculator();
        onCalClick();

    }


    private void readUserInput() {
        //checkForEmptyEditText();
        /*markOne = Double.parseDouble(editTextOne.getText().toString());
        markTwo = Double.parseDouble(editTextTwo.getText().toString());*/
        inf01[0] = markOne;
        inf01[1] = markTwo;

    }


    private void setupUI() {
        calculateButton = (Button)findViewById(R.id.calculateButton);
        informationswissenschaft = (ExpandableListView)findViewById(R.id.Informationswissenschaft);
        List<String> headings = new ArrayList<String>();
        List<String> L1 = new ArrayList<String>();
        List<String> L2 = new ArrayList<String>();
        HashMap<String,List<String>> childList = new HashMap<String,List<String>>();
        String[] headingItems = getResources().getStringArray(R.array.header_titles);
        String[] l1 = getResources().getStringArray(R.array.courses_titles);
        String[] l2 = getResources().getStringArray(R.array.INF_M01);

        for (String title : headingItems){
            headings.add(title);
        }

        for (String title : l1){
            L1.add(title);
        }
        for (String title : l2){
            L2.add(title);
        }

        childList.put(headings.get(0),L1);
        childList.put(headings.get(1),L2);

        ExpandableListAdapter adapter = new ExpandableListAdapter(this, headings,childList);
        informationswissenschaft.setAdapter(adapter);
    }


    private void onCalClick(){
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readUserInput();
                calculator.calculateMarks(subjectID, inf01);
                Intent resultPageIntent = new Intent(MarksCalculator.this, ResultActivity.class);
                startActivity(resultPageIntent);
            }
        });
    }


    /*private void checkForEmptyEditText(){
        if (editTextOne.length() < 1  || editTextTwo.length() < 1){
            editTextOne.setText("4.0");
            editTextTwo.setText("4.0");
            Toast.makeText(MarksCalculator.this, "We calculate the baddest result", Toast.LENGTH_SHORT).show();
        }

    }*/


}
