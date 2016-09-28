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

import java.text.DecimalFormat;
import java.util.ArrayList;

public class InfWissMarksActivity extends AppCompatActivity {

    private EditText inf1_1;
    private EditText inf1_2;
    private EditText inf2_1;
    private EditText inf2_2;
    private EditText inf2_3;
    private EditText inf3_1;
    private EditText inf3_2;
    private EditText inf3_3;
    private EditText inf3_4;
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
    private TextView markModule3;
    private TextView markModule4;
    private TextView markModule5;
    private TextView markModule6;
    private TextView markModule7;

    private Button saveButton;

    private Database db;
    private ArrayList<CourseItem> courses;
    private ArrayList<ModuleItem> modules;

    /*
     * the method defines what happens when the activity is started
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_wiss_marks);
        initDB();
        setupUI();
        onButtonClicked();
    }

    /*
     * the database will be referenced here so the class methods can interact with the
     * database
     */
    private void initDB() {
        db = new Database(InfWissMarksActivity.this);
        courses = new ArrayList<>();
        Log.d("InfWissMarksActivity: ", "Database getAllCourseItems() angefragt");
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

        Log.d("Courses", "Infwiss" + courses.size());
        Log.d("Modules", "Infwiss " + modules.size());
    }

    /*
     * here we set up the user interface for activity. in particular we initiate all the textviews
     * and editText views
     */
    private void setupUI(){
        inf1_1 = (EditText)findViewById(R.id.inf_1_1_mark);
        inf1_2 = (EditText)findViewById(R.id.inf_1_2_mark);
        inf2_1 = (EditText)findViewById(R.id.inf_2_1_mark);
        inf2_2 = (EditText)findViewById(R.id.inf_2_2_mark);
        inf2_3 = (EditText)findViewById(R.id.inf_2_3_mark);
        inf3_1 = (EditText)findViewById(R.id.inf_3_1_mark);
        inf3_2 = (EditText)findViewById(R.id.inf_3_2_mark);
        inf3_3 = (EditText)findViewById(R.id.inf_3_3_mark);
        inf3_4 = (EditText)findViewById(R.id.inf_3_4_mark);
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
        markModule3 = (TextView)findViewById(R.id.module_inf_03_mark);
        markModule4 = (TextView)findViewById(R.id.module_inf_04_mark);
        markModule5 = (TextView)findViewById(R.id.module_inf_05_mark);
        markModule6 = (TextView)findViewById(R.id.module_inf_06_mark);
        markModule7 = (TextView)findViewById(R.id.module_inf_07_mark);


        saveButton = (Button)findViewById(R.id.saveButtonInfwiss);
        setCourseMarks();
        updateModuleMarks();
    }

    /*
     * This method gets all modules and submodules for multiple Courseitems
     */
    private CourseItem getCourse(int module, int submodule) {
        for (CourseItem course : courses) {
            if (module == course.getModule() && submodule == course.getSubmodule()) {
                return course;
            }
        }
        return null;
    }

    /*
     * Here we can define what the button will actually do. here, the marks will be updated
     * first and then the new values will be put into the database for the single Coursemarks
     * and ModuleMarks
     */
    private void onButtonClicked(){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCourseMarks();
                courses = db.getAllCourseItems();
                updateModuleMarks();
                calculateSubjectMark();
                Log.d("Courses: ", "" + courses.size());
            }
        });
    }

    //private double calculateSubjectNote

    /*
     * in order to visualize the module marks, we update the Texts of the corresponding
     * module textfields
     */
    private void updateModuleMarks() {
        DecimalFormat decimal = new DecimalFormat("#.#");
        markModule1.setText(String.valueOf(decimal.format(calculateModuleMark(1))));
        markModule2.setText(String.valueOf(decimal.format(calculateModuleMark(2))));
        markModule3.setText(String.valueOf(decimal.format(calculateModuleMark(3))));
        markModule4.setText(String.valueOf(decimal.format(calculateModuleMark(4))));
        markModule5.setText(String.valueOf(decimal.format(calculateModuleMark(5))));
        markModule6.setText(String.valueOf(decimal.format(calculateModuleMark(6))));
        markModule7.setText(String.valueOf(decimal.format(calculateModuleMark(7))));
    }

    /*
     * this method sets the courseMarks for each course
     */
    private void setCourseMarks() {
        inf1_1.setText("" + getCourse(1, 1).getMark());
        inf1_2.setText("" + getCourse(1, 2).getMark());

        inf2_1.setText("" + getCourse(2, 1).getMark());
        inf2_2.setText("" + getCourse(2, 2).getMark());
        inf2_3.setText("" + getCourse(2, 3).getMark());

        inf3_1.setText("" + getCourse(3, 1).getMark());
        inf3_2.setText("" + getCourse(3, 2).getMark());
        inf3_3.setText("" + getCourse(3, 3).getMark());
        inf3_4.setText("" + getCourse(3, 4).getMark());

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

    /*
     * here we update the courseMarks in the database
     */
    private void updateCourseMarks(){
        db.updateMark(1, 1, getMarkFromEditText(inf1_1), MainSubject.INF);
        db.updateMark(1, 2, getMarkFromEditText(inf1_2), MainSubject.INF);

        db.updateMark(2, 1, getMarkFromEditText(inf2_1), MainSubject.INF);
        db.updateMark(2, 2, getMarkFromEditText(inf2_2), MainSubject.INF);
        db.updateMark(2, 3, getMarkFromEditText(inf2_3), MainSubject.INF);

        db.updateMark(3, 1, getMarkFromEditText(inf3_1), MainSubject.INF);
        db.updateMark(3, 2, getMarkFromEditText(inf3_2), MainSubject.INF);
        db.updateMark(3, 3, getMarkFromEditText(inf3_3), MainSubject.INF);
        db.updateMark(3, 4, getMarkFromEditText(inf3_4), MainSubject.INF);

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

    /*
     * this is the method, that lets the calculation magic happen for each Module
     */
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

    /*
     * This method retrieves the data from the markField. if the field is empty, we
     * insert a dummy value
     */
    private double getMarkFromEditText(EditText editText) {
        if (editText.getText().length() == 0){
            editText.setText("0.0");
        }

        return Double.parseDouble(editText.getText().toString());
    }

    private double getMarkFromTextView(TextView textView){
        double [] moduleMarksArray = new double[5];

        return Double.parseDouble(textView.getText().toString());
    }

    private MainSubject getMainSubject(){
        MainSubject mainsubject = db.getUser().getMainSubject();
        return mainsubject;
    }

    private int setDivisorMain(){
        int divisor = 7;
        if(calculateModuleMark(1)==0.0){
            divisor--;
        }
        if(calculateModuleMark(2)==0.0){
            divisor--;
        }
        if(calculateModuleMark(3)==0.0){
            divisor--;
        }
        if(calculateModuleMark(4)==0.0){
            divisor--;
        }
        if(calculateModuleMark(5)==0.0){
            divisor--;
        }
        if(calculateModuleMark(6)==0.0){
            divisor--;
        }
        if(calculateModuleMark(7)==0.0){
            divisor--;
        }
        if(divisor == 0){
            divisor = 1;
        }
        return divisor;
    }

    private int setDivisorSecondary(){
        int divisor = 5;
        if(calculateModuleMark(1) == 0.0){
            divisor--;
        }
        if(calculateModuleMark(2) == 0.0){
            divisor--;
        }
        if(calculateModuleMark(4) == 0.0 && calculateModuleMark(5) == 0.0 && calculateModuleMark(6) == 0.0 && calculateModuleMark(7) == 0.0) {
            divisor -= 3;
            if (divisor == 0) {
                divisor = 1;
            }
            Log.d("InfwissCalcInner", " "+divisor);
            return divisor;
        }
        if(calculateModuleMark(4) == 0.0){
            divisor--;
        }
        if(calculateModuleMark(5) == 0.0){
            divisor--;
        }
        if(calculateModuleMark(6) == 0.0){
            divisor--;
        }
        if(calculateModuleMark(7) == 0.0){
            divisor--;
        }
        if(divisor <= 0){
            divisor = 1;
        }
        Log.d("InfwissCalc", " "+ divisor);
        return divisor;
    }

    public double calculateSubjectMark(){
        User user = db.getUser();
        double sum = 0;

        if(user.getMainSubject().getName().equals("Informationswissenschaft")){
            Log.d("Subject", " Informationswissenschaft");
            int divisor = setDivisorMain();
            sum += calculateModuleMark(1);
            sum += calculateModuleMark(2);
            sum += calculateModuleMark(3);
            sum += calculateModuleMark(4);
            sum += calculateModuleMark(5);
            sum += calculateModuleMark(6);
            sum += calculateModuleMark(7);
            Log.d("Summe: ", " "+sum);
            sum = sum / divisor;
            Log.d("Summe: ", " "+sum);

        }else {
            Log.d("Subject", " Medieninformatik");
            int divisor = setDivisorSecondary();
            sum += calculateModuleMark(1);
            sum += calculateModuleMark(2);
            if(calculateModuleMark(4) == 0.0){
                sum += calculateModuleMark(5);
                sum += calculateModuleMark(6);
                sum += calculateModuleMark(7);
                sum /= divisor;
                db.updateUserMedInfMark(user.getName(), ""+sum);
                return sum;
            }else if(calculateModuleMark(5) == 0.0){
                sum += calculateModuleMark(5);
                sum += calculateModuleMark(6);
                sum += calculateModuleMark(7);
                sum /= divisor;
                db.updateUserMedInfMark(user.getName(), ""+sum);
                return sum;
            }else if(calculateModuleMark(6) == 0.0){
                sum += calculateModuleMark(5);
                sum += calculateModuleMark(6);
                sum += calculateModuleMark(7);
                sum /= divisor;
                db.updateUserMedInfMark(user.getName(), ""+sum);
                return sum;
            }else if(calculateModuleMark(7) == 0.0){
                sum += calculateModuleMark(4);
                sum += calculateModuleMark(5);
                sum += calculateModuleMark(6);
                sum /= divisor;
                db.updateUserMedInfMark(user.getName(), ""+sum);
                return sum;
            }
        }
        db.updateUserInfwissMark(user.getName(), ""+sum);
        return sum;
    }

}
