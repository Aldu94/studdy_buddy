package com.studbud.studbud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
<<<<<<< HEAD
import android.widget.TextView;
=======
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.studbud.studbud.domain.CalculatorItem;
import com.studbud.studbud.persistence.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
>>>>>>> f9cf897ad361c271ebba01025e2a89daf68941e1

public class MarksCalculator extends AppCompatActivity {


<<<<<<< HEAD
    private TextView infWiss;
    private TextView medienInfo;
    private TextView bachelorWork;

    private TextView infWissMark;
    private TextView medienInfoMark;
    private TextView bachelorWorkMark;
=======
    private ListContent lc;
    private int subjectID;
    private User user;
    private Calculator calculator;
    public double markOne;
    public double markTwo;
    private Button calculateButton;
    private double[] inf01;
>>>>>>> f9cf897ad361c271ebba01025e2a89daf68941e1


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

<<<<<<< HEAD

=======
    private void readUserInput() {
        //checkForEmptyEditText();
        /*markOne = Double.parseDouble(editTextOne.getText().toString());
        markTwo = Double.parseDouble(editTextTwo.getText().toString());*/
        inf01[0] = markOne;
        inf01[1] = markTwo;
>>>>>>> f9cf897ad361c271ebba01025e2a89daf68941e1

    private void setupUI() {
        infWiss = (TextView)findViewById(R.id.informationswissenschaft_text_view);
        medienInfo = (TextView)findViewById(R.id.medieninformatik_text_view);
        bachelorWork = (TextView)findViewById(R.id.bachelorarbeit_text_view);

        infWissMark = (TextView)findViewById(R.id.informationswissenschaft_mark);
        medienInfoMark = (TextView)findViewById(R.id.medieninformatik_mark);
        bachelorWorkMark = (TextView)findViewById(R.id.bachelorarbeit_mark);
    }

    /*private void addListData() {
        CalculatorItem obj = new CalculatorItem();
        obj.children = new ArrayList<CalculatorItem>();
        for(int i = 0; i < ListContent.state.length; i++){
            CalculatorItem parent = new CalculatorItem();
            parent.title = ListContent.state[i];
            parent.children = new ArrayList<CalculatorItem>();
            for(int j = 0; j < ListContent.parent.length; j++){
                CalculatorItem child = new CalculatorItem();
                child.title = ListContent.parent[i][j];
                child.children = new ArrayList<CalculatorItem>();
                for(int x = 0; x < ListContent.child.length; x++){
                    CalculatorItem grandchild = new CalculatorItem();
                    grandchild.title = ListContent.child[i][j][x];
                    child.children.add(grandchild);
                }
                parent.children.add(child);
            }
            obj.children.add(parent);
        }
    }*/
    /*private void initiateUI(){
        final ExpandableListView test = (ExpandableListView) findViewById(R.id.Informationswissenschaft);

        test.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {

                return true; /* or false depending on what you need
            }
        });
    ExpandableListView.OnGroupClickListener grpLst = new ExpandableListView.OnGroupClickListener() {
        @Override
        public boolean onGroupClick(ExpandableListView eListView, View view, int groupPosition,
                                    long id) {

            return true;/* or false depending on what you need
        }
    };


    ExpandableListView.OnChildClickListener childLst = new ExpandableListView.OnChildClickListener() {
        @Override
        public boolean onChildClick(ExpandableListView eListView, View view, int groupPosition,
                                    int childPosition, long id) {

            return true;/* or false depending on what you need
        }
    };

    ExpandableListView.OnGroupExpandListener grpExpLst = new ExpandableListView.OnGroupExpandListener() {
        @Override
        public void onGroupExpand(int groupPosition) {

        }
    };

    final ExpandableListAdapter adapter = new ExpandableListAdapter(this, CalculatorItem, grpLst, childLst, grpExpLst);
    test.setAdapter(adapter);
}*/


    public void onInfwissClick(View v) {
        Intent i = new Intent(MarksCalculator.this,InfWissMarksActivity.class);
        startActivity(i);
    }

<<<<<<< HEAD

    public void onMedienInfoClick(View v) {
        Intent m = new Intent(MarksCalculator.this,MedienInfoMarksActivity.class);
        startActivity(m);
    }
=======
   /* private void onCalClick(){
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readUserInput();
                calculator.calculateMarks(subjectID, inf01);
                Intent resultPageIntent = new Intent(MarksCalculator.this, ResultActivity.class);
                startActivity(resultPageIntent);
            }
        });
    }*/
>>>>>>> f9cf897ad361c271ebba01025e2a89daf68941e1

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

