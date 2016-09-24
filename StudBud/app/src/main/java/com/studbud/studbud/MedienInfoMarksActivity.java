package com.studbud.studbud;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.studbud.studbud.domain.CourseItem;

public class MedienInfoMarksActivity extends AppCompatActivity {

    private static int KEY_MAIN_SUBJECT;

    private EditText mei1_1;
    private EditText mei1_2;
    private EditText mei3_1;
    private EditText mei3_2;
    private EditText mei3_3;
    private EditText mei4_1;
    private EditText mei4_2;
    private EditText mei4_3;
    private EditText mei5_1;
    private EditText mei5_2;
    private EditText mei5_3;
    private EditText mei8_1;
    private EditText mei8_2;
    private EditText mei10_1;
    private EditText mei10_2;
    private EditText mei10_3;

    private TextView markModule1;
    private TextView markModule3;
    private TextView markModule4;
    private TextView markModule5;
    private TextView markModule8;
    private TextView markModule10;


    private Button button;
    private Calculator calculator;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medien_info_marks);
        initDB();
        initCalculator();
        setupUI();
        onSafeButtonClicked();
    }

    private void initDB(){
        db = new Database(MedienInfoMarksActivity.this);

        db.getAllToDoItems();
    }

    private void initCalculator(){
        calculator = new Calculator();
    }

    private void setupUI(){
        mei1_1 = (EditText)findViewById(R.id.mei_1_1_mark);
        mei1_2 = (EditText)findViewById(R.id.mei_1_2_mark);

        mei3_1 = (EditText)findViewById(R.id.mei_3_1_mark);
        mei3_2 = (EditText)findViewById(R.id.mei_3_2_mark);
        mei3_3 = (EditText)findViewById(R.id.mei_3_3_mark);

        mei4_1 = (EditText)findViewById(R.id.mei_4_1_mark);
        mei4_2 = (EditText)findViewById(R.id.mei_4_2_mark);
        mei4_3 = (EditText)findViewById(R.id.mei_4_3_mark);

        mei5_1 = (EditText)findViewById(R.id.mei_5_1_mark);
        mei5_2 = (EditText)findViewById(R.id.mei_5_2_mark);
        mei5_3 = (EditText)findViewById(R.id.mei_5_3_mark);

        mei8_1 = (EditText)findViewById(R.id.mei_8_1_mark);
        mei8_2 = (EditText)findViewById(R.id.mei_8_2_mark);

        mei10_1 = (EditText)findViewById(R.id.mei_10_1_mark);
        mei10_2 = (EditText)findViewById(R.id.mei_10_2_mark);
        mei10_3 = (EditText)findViewById(R.id.mei_10_3_mark);

        


        markModule1 = (TextView)findViewById(R.id.module_mei_01_mark);
        markModule3 = (TextView)findViewById(R.id.module_mei_03_mark);
        markModule4 = (TextView)findViewById(R.id.module_mei_04_mark);
        markModule5 = (TextView)findViewById(R.id.module_mei_05_mark);
        markModule8 = (TextView)findViewById(R.id.module_mei_08_mark);
        markModule10 = (TextView)findViewById(R.id.module_mei_10_mark);

        button = (Button)findViewById(R.id.medieninfo_safe_button);
    }

    private void onSafeButtonClicked(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateModule1();
                calculateModule3();
                calculateModule4();
                calculateModule5();
                calculateModule8();
                calculateModule10();

                db.open();
                db.getAllToDoItems();

                mei1_1.setText(courses[0].getMark());

            }
        });
    }

    private void calculateModule1(){
        double[] mei01 = new double[2];
        if (mei1_1.getText().length() == 0){
            mei1_1.setText("4.0");
            mei01[0] = Double.parseDouble(mei1_1.getText().toString());
            db.updateMark(1, 1, 1.0);
        }
        if (mei1_2.getText().length() == 0){
            mei1_2.setText("4.0");
            mei01[1] = Double.parseDouble(mei1_2.getText().toString());
            db.updateMark(1, 2, 1.0);
        }
        else{
            mei01[0] = Double.parseDouble(mei1_1.getText().toString());
            mei01[1] = Double.parseDouble(mei1_2.getText().toString());
        }

        double meiModule1Mark = calculator.calculateMediInfoM01(mei01);
        markModule1.setText(String.valueOf(meiModule1Mark));
        // create new course item
        //db.addCourseItem();
    }


    private void calculateModule3(){
        double[] mei03 = new double[3];

        if (mei3_1.getText().length() == 0){
            mei3_1.setText("4.0");
            mei03[0] = Double.parseDouble(mei3_1.getText().toString());
        }
        if (mei3_2.getText().length() == 0){
            mei3_2.setText("4.0");
            mei03[1] = Double.parseDouble(mei3_2.getText().toString());
        }
        if (mei3_3.getText().length() == 0){
            mei3_3.setText("4.0");
            mei03[1] = Double.parseDouble(mei3_3.getText().toString());
        }
        else{
            mei03[0] = Double.parseDouble(mei3_1.getText().toString());
            mei03[1] = Double.parseDouble(mei3_2.getText().toString());
            mei03[2] = Double.parseDouble(mei3_3.getText().toString());
        }

        double meiModule3Mark = calculator.calculateMediInfoM03(mei03);
        markModule3.setText(String.valueOf(meiModule3Mark));

    }


    private void calculateModule4(){
        double[] mei04 = new double[3];

        if (mei4_1.getText().length() == 0){
            mei4_1.setText("4.0");
            mei04[0] = Double.parseDouble(mei4_1.getText().toString());
        }
        if (mei4_2.getText().length() == 0){
            mei4_2.setText("4.0");
            mei04[1] = Double.parseDouble(mei4_2.getText().toString());
        }
        if (mei4_3.getText().length() == 0){
            mei4_3.setText("4.0");
            mei04[2] = Double.parseDouble(mei4_3.getText().toString());
        }
        else{
            mei04[0] = Double.parseDouble(mei4_1.getText().toString());
            mei04[1] = Double.parseDouble(mei4_2.getText().toString());
            mei04[2] = Double.parseDouble(mei4_3.getText().toString());
        }

        double meiModule4Mark = calculator.calculateMediInfoM04(mei04);
        markModule4.setText(String.valueOf(meiModule4Mark));

    }


    private void calculateModule5(){
        double[] mei05 = new double[3];

        if (mei5_1.getText().length() == 0){
            mei5_1.setText("4.0");
            mei05[0] = Double.parseDouble(mei5_1.getText().toString());
        }
        if (mei5_2.getText().length() == 0){
            mei5_2.setText("4.0");
            mei05[1] = Double.parseDouble(mei5_2.getText().toString());
        }
        if (mei5_3.getText().length() == 0){
            mei5_3.setText("4.0");
            mei05[2] = Double.parseDouble(mei5_3.getText().toString());
        }
        else{
            mei05[0] = Double.parseDouble(mei5_1.getText().toString());
            mei05[1] = Double.parseDouble(mei5_2.getText().toString());
            mei05[2] = Double.parseDouble(mei5_3.getText().toString());
        }

        double meiModule5Mark = calculator.calculateMediInfoM05(mei05);
        markModule5.setText(String.valueOf(meiModule5Mark));
    }


    private void calculateModule8(){
        double[] mei08 = new double[2];

        if (mei8_1.getText().length() == 0){
            mei8_1.setText("4.0");
            mei08[0] = Double.parseDouble(mei8_1.getText().toString());
        }
        if (mei8_2.getText().length() == 0){
            mei8_2.setText("4.0");
            mei08[1] = Double.parseDouble(mei8_2.getText().toString());
        }
        else{
            mei08[0] = Double.parseDouble(mei8_1.getText().toString());
            mei08[1] = Double.parseDouble(mei8_2.getText().toString());
        }

        double meiModule8Mark = calculator.calculateMediInfoM08(mei08);
        markModule8.setText(String.valueOf(meiModule8Mark));
    }


    private void calculateModule10(){
        double[] mei10 = new double[3];

        if (mei10_1.getText().length() == 0){
            mei10_1.setText("4.0");
            mei10[0] = Double.parseDouble(mei10_1.getText().toString());
        }
        if (mei10_2.getText().length() == 0){
            mei10_2.setText("4.0");
            mei10[1] = Double.parseDouble(mei10_2.getText().toString());
        }
        if (mei10_3.getText().length() == 0){
            mei10_3.setText("4.0");
            mei10[2] = Double.parseDouble(mei10_3.getText().toString());
        }
        else{
            mei10[0] = Double.parseDouble(mei10_1.getText().toString());
            mei10[1] = Double.parseDouble(mei10_2.getText().toString());
            mei10[2] = Double.parseDouble(mei10_3.getText().toString());
        }

        double meiModule10Mark = calculator.calculateMediInfoM10(mei10);
        markModule10.setText(String.valueOf(meiModule10Mark));
    }

}
