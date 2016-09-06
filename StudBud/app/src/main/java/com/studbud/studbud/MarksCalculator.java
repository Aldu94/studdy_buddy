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
    private ExpandableListAdapter listAdapter;
    private List<String> listHeadings;
    private HashMap<String, List<String>> childList;


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
        //checkForEmptyEditText();
        /*markOne = Double.parseDouble(editTextOne.getText().toString());
        markTwo = Double.parseDouble(editTextTwo.getText().toString());*/
        inf01[0] = markOne;
        inf01[1] = markTwo;

    }


    private void setupUI() {

        informationswissenschaft = (ExpandableListView)findViewById(R.id.Informationswissenschaft);

        addListData();
        ExpandableListAdapter adapter = new ExpandableListAdapter(this, listHeadings,childList);
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

    private void addListData(){
        listHeadings = new ArrayList<String>();
        childList = new HashMap<String,List<String>>();

        listHeadings.add("Informationswissenschaft");
        listHeadings.add("Medieninformatik");
        listHeadings.add("Bachelorarbeit");
        listHeadings.add("a");
        listHeadings.add("b");
        listHeadings.add("c");
        listHeadings.add("d");
        listHeadings.add("e");
        listHeadings.add("f");
        listHeadings.add("g");
        listHeadings.add("h");
        listHeadings.add("i");
        listHeadings.add("j");


        List<String> informationsWissenschaft = new ArrayList<String>();
        informationsWissenschaft.add("Einführung in die Informationswissenschaft");
        List<String> medienInformatik = new ArrayList<String>();
        medienInformatik.add("Einführung in die Informatik");
        List<String> bachelorArbeit= new ArrayList<String>();
        bachelorArbeit.add("Bachelorarbeit");
        List<String> a= new ArrayList<String>();
        a.add("Bachelorarbeit");
        List<String> b= new ArrayList<String>();
        b.add("Bachelorarbeit");
        List<String> c= new ArrayList<String>();
        c.add("Bachelorarbeit");
        List<String> d= new ArrayList<String>();
        d.add("Bachelorarbeit");
        List<String> e= new ArrayList<String>();
        e.add("Bachelorarbeit");
        List<String> f= new ArrayList<String>();
        f.add("Bachelorarbeit");
        List<String> g= new ArrayList<String>();
        g.add("Bachelorarbeit");
        List<String> h= new ArrayList<String>();
        h.add("Bachelorarbeit");
        List<String> i= new ArrayList<String>();
        i.add("Bachelorarbeit");
        List<String> j= new ArrayList<String>();
        j.add("Bachelorarbeit");


        childList.put(listHeadings.get(0), informationsWissenschaft);
        childList.put(listHeadings.get(1),medienInformatik);
        childList.put(listHeadings.get(2),bachelorArbeit);
        childList.put(listHeadings.get(3),a);
        childList.put(listHeadings.get(4),b);
        childList.put(listHeadings.get(5),c);
        childList.put(listHeadings.get(6),d);
        childList.put(listHeadings.get(7),e);
        childList.put(listHeadings.get(8),f);
        childList.put(listHeadings.get(9),g);
        childList.put(listHeadings.get(10),h);
        childList.put(listHeadings.get(11),i);
        childList.put(listHeadings.get(12),j);
    }


    /*private void checkForEmptyEditText(){
        if (editTextOne.length() < 1  || editTextTwo.length() < 1){
            editTextOne.setText("4.0");
            editTextTwo.setText("4.0");
            Toast.makeText(MarksCalculator.this, "We calculate the baddest result", Toast.LENGTH_SHORT).show();
        }

    }*/


}
