package com.studbud.studbud;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.studbud.studbud.persistence.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MarksCalculator extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private ExpandableListAdapter listAdapter;
    private List<String> listHeadings;
    private HashMap<String, List<String>> childList;
    private HashMap <String,List<String>> grandChildList;


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
       // onCalClick();

    }


    private void readUserInput() {
        inf01[0] = markOne;
        inf01[1] = markTwo;

    }


    private void setupUI() {
        expandableListView = (ExpandableListView)findViewById(R.id.Informationswissenschaft);
        addListData();
        ExpandableListAdapter adapter = new ExpandableListAdapter(this, listHeadings,childList);
        expandableListView.setAdapter(adapter);
    }

/*
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
*/

    private void addListData(){
        listHeadings = new ArrayList<String>();
        childList = new HashMap<String,List<String>>();
        grandChildList = new HashMap<String,List<String>>();

        List<String> informationsWissenschaft = new ArrayList<String>();
        List<String> medienInformatik = new ArrayList<String>();
        List<String> bachelorArbeit = new ArrayList<String>();

        String[] headingItems = getResources().getStringArray(R.array.header_titles);
        String[] infwissItems = getResources().getStringArray(R.array.modules_titles_infwiss);
        String[] medinfoItems = getResources().getStringArray(R.array.modules_titles_medinfo);
        String bachelorItem = getResources().getString(R.string.bachelor_title);



        for(String title : headingItems){
            listHeadings.add(title);
        }

        for(String title : infwissItems){
            informationsWissenschaft.add(title);

        }

        for(String title : medinfoItems){
            medienInformatik.add(title);
        }

        bachelorArbeit.add(bachelorItem);




        childList.put(listHeadings.get(0), informationsWissenschaft);
        childList.put(listHeadings.get(1),medienInformatik);
        childList.put(listHeadings.get(2),bachelorArbeit);


    //    testPersistence();
    }

     //   private void testPersistence(){
     //       Log.i("course name: ", childList.toString());
    //    }

    /*private void checkForEmptyEditText(){
        if (editTextOne.length() < 1  || editTextTwo.length() < 1){
            editTextOne.setText("4.0");
            editTextTwo.setText("4.0");
            Toast.makeText(MarksCalculator.this, "We calculate the baddest result", Toast.LENGTH_SHORT).show();
        }

    }*/

}

