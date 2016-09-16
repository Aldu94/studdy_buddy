package com.studbud.studbud;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.studbud.studbud.persistence.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MarksCalculator extends AppCompatActivity {

    private ExpandableListView expandableListViewInfoWiss;
    private ExpandableListView expandableListViewMedieninfo;
    private List<String> listHeadingsInfoWiss;
    private HashMap<String, List<String>> childListInfowiss;
    private List<String> listHeadingsMedieninfo;
    private HashMap<String, List<String>> childListMedieninfo;



    private long subjectID;
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
        user = new User("Karl",0,3);
        subjectID = user.getMainSubjectID();
        calculator = new Calculator();

    }


    private void setupUI() {
        expandableListViewInfoWiss = (ExpandableListView)findViewById(R.id.inf);
        expandableListViewMedieninfo = (ExpandableListView)findViewById(R.id.medieninfo);
        addListDataInfwiss();
        addListDataMedieninfo();
        ExpandableListAdapter adapterInfwiss = new ExpandableListAdapter(this, listHeadingsInfoWiss, childListInfowiss);
        ExpandableListAdapter adapterMedieninfo = new ExpandableListAdapter(this,listHeadingsMedieninfo, childListMedieninfo);
        expandableListViewInfoWiss.setAdapter(adapterInfwiss);
        expandableListViewMedieninfo.setAdapter(adapterMedieninfo);
    }



    // method to fill the expandable listview with data out of the strings

    private void addListDataInfwiss(){
        listHeadingsInfoWiss = new ArrayList<String>();
        childListInfowiss = new HashMap<String,List<String>>();

        List<String> inf01List = new ArrayList<String>();
        List<String> inf02List = new ArrayList<String>();
        List<String> inf03List = new ArrayList<String>();
        List<String> inf04List = new ArrayList<String>();
        List<String> inf05List = new ArrayList<String>();
        List<String> inf06List = new ArrayList<String>();
        List<String> inf07List = new ArrayList<String>();



        String[] infwissHeadingItems = getResources().getStringArray(R.array.header_titles_infwiss);
        String[] inf01Items = getResources().getStringArray(R.array.INF_M01);
        String[] inf02Items = getResources().getStringArray(R.array.INF_M02);
        String[] inf04Items = getResources().getStringArray(R.array.INF_M04);
        String[] inf05Items = getResources().getStringArray(R.array.INF_M05);
        String[] inf06Items = getResources().getStringArray(R.array.INF_M06);
        String[] inf07Items = getResources().getStringArray(R.array.INF_M07);




        for(String title : infwissHeadingItems){
            listHeadingsInfoWiss.add(title);
        }

        for(String title : inf01Items){
            inf01List.add(title);

        }

        for(String title : inf02Items){
            inf02List.add(title);

        }

        for(String title : inf04Items){
            inf04List.add(title);

        }

        for(String title : inf05Items){
            inf05List.add(title);

        }

        for(String title : inf06Items){
            inf06List.add(title);

        }
        for(String title : inf07Items){
            inf07List.add(title);

        }




        childListInfowiss.put(listHeadingsInfoWiss.get(0), inf01List);
        childListInfowiss.put(listHeadingsInfoWiss.get(1), inf02List);
        childListInfowiss.put(listHeadingsInfoWiss.get(2), inf04List);
        childListInfowiss.put(listHeadingsInfoWiss.get(3), inf05List);
        childListInfowiss.put(listHeadingsInfoWiss.get(4), inf06List);
        childListInfowiss.put(listHeadingsInfoWiss.get(5), inf07List);



    }

    private void addListDataMedieninfo() {

        listHeadingsMedieninfo = new ArrayList<String>();
        childListMedieninfo = new HashMap<String,List<String>>();

        List<String> mei01List = new ArrayList<String>();
        List<String> mei03List = new ArrayList<String>();
        List<String> mei04List = new ArrayList<String>();
        List<String> mei05List = new ArrayList<String>();
        List<String> mei08List = new ArrayList<String>();
        List<String> mei10List = new ArrayList<String>();


        String[] medieninfoHeadingItems = getResources().getStringArray(R.array.header_titles_medinfo);
        String[] mei01Items = getResources().getStringArray(R.array.MEI_01);
        String[] mei03Items = getResources().getStringArray(R.array.MEI_03);
        String[] mei04Items = getResources().getStringArray(R.array.MEI_04_mei);
        String[] mei05Items = getResources().getStringArray(R.array.MEI_05);
        String[] mei08Items = getResources().getStringArray(R.array.MEI_08_inf);
        String[] mei10Items = getResources().getStringArray(R.array.MEI_10_mei);



        for(String title : medieninfoHeadingItems){
            listHeadingsMedieninfo.add(title);
        }

        for(String title : mei01Items){
            mei01List.add(title);

        }

        for(String title : mei03Items){
            mei03List.add(title);

        }

        for(String title : mei04Items){
            mei04List.add(title);

        }

        for(String title : mei05Items){
            mei05List.add(title);

        }

        for(String title : mei08Items){
            mei08List.add(title);

        }
        for(String title : mei10Items){
            mei10List.add(title);

        }


        childListMedieninfo.put(listHeadingsMedieninfo.get(0), mei01List);
        childListMedieninfo.put(listHeadingsMedieninfo.get(1), mei03List);
        childListMedieninfo.put(listHeadingsMedieninfo.get(2), mei04List);
        childListMedieninfo.put(listHeadingsMedieninfo.get(3), mei05List);
        childListMedieninfo.put(listHeadingsMedieninfo.get(4), mei08List);
        childListMedieninfo.put(listHeadingsMedieninfo.get(5), mei10List);



    }



    /*private void checkForEmptyEditText(){
        if (editTextOne.length() < 1  || editTextTwo.length() < 1){
            editTextOne.setText("4.0");
            editTextTwo.setText("4.0");
            Toast.makeText(MarksCalculator.this, "We calculate the baddest result", Toast.LENGTH_SHORT).show();
        }

    }*/

}

