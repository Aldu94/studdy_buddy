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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medien_info_marks);
        calculator = new Calculator();
        setupUI();
        onSafeButtonClicked();
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
            }
        });
    }

    private void calculateModule1(){
        double first = Double.parseDouble(mei1_1.getText().toString());
        double second = Double.parseDouble(mei1_2.getText().toString());
        double meiModule1Mark = calculator.calculateMediInfoM01(first,second);
        markModule1.setText(String.valueOf(meiModule1Mark));
        Log.d("++++++++++++",String.valueOf(meiModule1Mark));
    }



}
