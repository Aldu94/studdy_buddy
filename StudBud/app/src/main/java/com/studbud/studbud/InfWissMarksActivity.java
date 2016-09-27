package com.studbud.studbud;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.studbud.studbud.domain.CourseItem;
import com.studbud.studbud.domain.Module;
import com.studbud.studbud.domain.ModuleItem;
import com.studbud.studbud.domain.Subject;

import java.util.ArrayList;

public class InfWissMarksActivity extends AppCompatActivity {

    private EditText inf1_1;
    private EditText inf1_2;
    private EditText inf2_1;
    private EditText inf2_2;
    private EditText inf2_3;
    private EditText inf4_1;
    private EditText inf4_2;
    private EditText inf4_3;
    private EditText inf5_1;
    private EditText inf5_2;
    private EditText inf6_1;
    private EditText inf6_2;
    private EditText inf6_3;
    private EditText inf7_1;
    private EditText inf7_2;


    private TextView markModule1;
    private TextView markModule2;
    private TextView markModule4;
    private TextView markModule5;
    private TextView markModule6;
    private TextView markModule7;

    private Button saveButton;

    private Database db;
    private ArrayList<CourseItem> courses;
    private ArrayList<ModuleItem> modules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_wiss_marks);
        initDB();
        setupUI();
        onButtonClicked();
    }

    private void initDB() {
        db = new Database(InfWissMarksActivity.this);
        courses = new ArrayList<>();
        Log.d("DEBUG: ", "Database getAllCourseItems() angefragt");
        for (CourseItem course : db.getAllCourseItems()) {
            if (course.getSubject() == MainSubject.INF) {
                courses.add(course);
            }
        }

        modules = new ArrayList<>();
        for (ModuleItem module : db.getAllModuleItems()){
            if (module.getSubject() == MainSubject.INF){
                modules.add(module);
            }
        }

        Log.d("Courses", "" + courses.size());
    }


    private void setupUI(){
        inf1_1 = (EditText)findViewById(R.id.inf_1_1_mark);
        inf1_2 = (EditText)findViewById(R.id.inf_1_2_mark);
        inf2_1 = (EditText)findViewById(R.id.inf_2_1_mark);
        inf2_2 = (EditText)findViewById(R.id.inf_2_2_mark);
        inf2_3 = (EditText)findViewById(R.id.inf_2_3_mark);
        inf4_1 = (EditText)findViewById(R.id.inf_4_1_mark);
        inf4_2 = (EditText)findViewById(R.id.inf_4_2_mark);
        inf4_3 = (EditText)findViewById(R.id.inf_4_3_mark);
        inf5_1 = (EditText)findViewById(R.id.inf_5_1_mark);
        inf5_2 = (EditText)findViewById(R.id.inf_5_2_mark);
        inf6_1 = (EditText)findViewById(R.id.inf_6_1_mark);
        inf6_2 = (EditText)findViewById(R.id.inf_6_2_mark);
        inf6_3 = (EditText)findViewById(R.id.inf_6_3_mark);
        inf7_1 = (EditText)findViewById(R.id.inf_7_1_mark);
        inf7_2 = (EditText)findViewById(R.id.inf_7_2_mark);

        markModule1 = (TextView)findViewById(R.id.module_inf_01_mark);
        markModule2 = (TextView)findViewById(R.id.module_inf_02_mark);
        markModule4 = (TextView)findViewById(R.id.module_inf_04_mark);
        markModule5 = (TextView)findViewById(R.id.module_inf_05_mark);
        markModule6 = (TextView)findViewById(R.id.module_inf_06_mark);
        markModule7 = (TextView)findViewById(R.id.module_inf_07_mark);


        saveButton = (Button)findViewById(R.id.saveButtonInfwiss);
        setCourseMarks();
        updateModuleMarks();
    }

    private CourseItem getCourse(int module, int submodule) {
        for (CourseItem course : courses) {
            if (module == course.getModule() && submodule == course.getSubmodule()) {
                return course;
            }
        }
        return null;
    }


    private void onButtonClicked(){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCourseMarks();
                courses = db.getAllCourseItems();
                updateModuleMarks();
                Log.d("Courses: ", "" + courses.size());
            }
        });
    }

    private void updateModuleMarks() {
        markModule1.setText(String.valueOf(calculateModuleMark(1)));
        markModule2.setText(String.valueOf(calculateModuleMark(2)));
        markModule4.setText(String.valueOf(calculateModuleMark(4)));
        markModule5.setText(String.valueOf(calculateModuleMark(5)));
        markModule6.setText(String.valueOf(calculateModuleMark(6)));
        markModule7.setText(String.valueOf(calculateModuleMark(7)));
    }

    private void setCourseMarks() {
        inf1_1.setText("" + getCourse(1, 1).getMark());
        inf1_2.setText("" + getCourse(1, 2).getMark());
        inf2_1.setText("" + getCourse(2, 1).getMark());
        inf2_2.setText("" + getCourse(2, 2).getMark());
        inf2_3.setText("" + getCourse(2, 3).getMark());
        inf4_1.setText("" + getCourse(4, 1).getMark());
        inf4_2.setText("" + getCourse(4, 2).getMark());
        inf4_3.setText("" + getCourse(4, 3).getMark());
        inf5_1.setText("" + getCourse(5, 1).getMark());
        inf5_2.setText("" + getCourse(5, 2).getMark());
        inf6_1.setText("" + getCourse(6, 1).getMark());
        inf6_2.setText("" + getCourse(6, 2).getMark());
        inf6_3.setText("" + getCourse(6, 3).getMark());
        inf7_1.setText("" + getCourse(7, 1).getMark());
        inf7_2.setText("" + getCourse(7, 2).getMark());
    }

    private void updateCourseMarks(){
        db.updateMark(1, 1, getMarkFromEditText(inf1_1), MainSubject.INF);
        db.updateMark(1, 2, getMarkFromEditText(inf1_2), MainSubject.INF);
        db.updateMark(2, 1, getMarkFromEditText(inf2_1), MainSubject.INF);
        db.updateMark(2, 2, getMarkFromEditText(inf2_2), MainSubject.INF);
        db.updateMark(2, 3, getMarkFromEditText(inf2_3), MainSubject.INF);
        db.updateMark(4, 1, getMarkFromEditText(inf4_1), MainSubject.INF);
        db.updateMark(4, 2, getMarkFromEditText(inf4_2), MainSubject.INF);
        db.updateMark(4, 3, getMarkFromEditText(inf4_3), MainSubject.INF);
        db.updateMark(5, 1, getMarkFromEditText(inf5_1), MainSubject.INF);
        db.updateMark(5, 2, getMarkFromEditText(inf5_2), MainSubject.INF);
        db.updateMark(6, 1, getMarkFromEditText(inf6_1), MainSubject.INF);
        db.updateMark(6, 2, getMarkFromEditText(inf6_2), MainSubject.INF);
        db.updateMark(6, 3, getMarkFromEditText(inf6_3), MainSubject.INF);
        db.updateMark(7, 1, getMarkFromEditText(inf7_1), MainSubject.INF);
        db.updateMark(7, 2, getMarkFromEditText(inf7_2), MainSubject.INF);
    }

    private double calculateModuleMark(int moduleId) {
        ArrayList<CourseItem> coursesInModule = new ArrayList<>();

        for (CourseItem course : courses) {
            if (course.getModule() == moduleId && course.getSubject() == MainSubject.INF) {
                coursesInModule.add(course);
            }
        }

        Module module = new Module(coursesInModule);

        Log.d("Module " + moduleId + ": ", "" + module.calculateGrade());

        return module.calculateGrade();
    }


    private double getMarkFromEditText(EditText editText) {
        if (editText.getText().length() == 0){
            editText.setText("4.0");
        }

        return Double.parseDouble(editText.getText().toString());
    }

    private double getMarkFromTextView(TextView textView){
        double [] moduleMarksArray = new double[5];

        return Double.parseDouble(textView.getText().toString());
    }


    public double calculateSubjectMark(){
        ArrayList<ModuleItem> modulesInSubject = new ArrayList<>();

        for (ModuleItem module : modules){
            if(module.getSubject() == MainSubject.INF){
                modulesInSubject.add(module);
            }
        }

        Subject subject = new Subject(modulesInSubject);

        return subject.calculateSubjectGrade();
    }

}
