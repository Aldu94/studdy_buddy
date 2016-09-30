package com.studbud.studbud;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.studbud.studbud.domain.CourseItem;
import com.studbud.studbud.domain.Module;
import com.studbud.studbud.domain.ModuleItem;
import com.studbud.studbud.domain.Subject;

import java.text.DecimalFormat;
import java.util.ArrayList;

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
    private Database db;
    private ArrayList<CourseItem> courses;
    private ArrayList<ModuleItem> modules;

    /*
     * the method defines what happens when the activity is started
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medien_info_marks);
        initDB();
        setupUI();
        onSafeButtonClicked();
    }

    /*
     * the database will be referenced here so the class methods can interact with the
     * database
     */
    private void initDB(){
        db = new Database(MedienInfoMarksActivity.this);
        courses = new ArrayList<>();
        for (CourseItem course : db.getAllCourseItems()) {
            if (course.getSubject() == MainSubject.MI) {
                courses.add(course);
            }
        }
        modules = new ArrayList<>();
        for (ModuleItem module : db.getAllModuleItems()){
            if (module.getSubject() == MainSubject.MI){
                modules.add(module);
            }
        }
        Log.d("Courses", "Medi " + courses.size());
        Log.d("Modules", "Medi " + modules.size());
    }

    /*
     * here we set up the user interface for activity. in particular we initiate all the textviews
     * and editText views
     */
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
     * Here we can define what the save button will actually do. here, the marks will be updated
     * first and then the new values will be put into the database for the single Coursemarks
     * and ModuleMarks
     */
    private void onSafeButtonClicked(){
        button.setOnClickListener(new View.OnClickListener() {
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

    /*
     * here we update the courseMarks in the database
     */
    private void updateCourseMarks(){
        db.updateMark(1, 1, getMarkFromEditText(mei1_1), MainSubject.MI);
        db.updateMark(1, 2, getMarkFromEditText(mei1_2), MainSubject.MI);
        db.updateMark(3, 1, getMarkFromEditText(mei3_1), MainSubject.MI);
        db.updateMark(3, 2, getMarkFromEditText(mei3_2), MainSubject.MI);
        db.updateMark(3, 3, getMarkFromEditText(mei3_3), MainSubject.MI);
        db.updateMark(4, 1, getMarkFromEditText(mei4_1), MainSubject.MI);
        db.updateMark(4, 2, getMarkFromEditText(mei4_2), MainSubject.MI);
        db.updateMark(4, 3, getMarkFromEditText(mei4_3), MainSubject.MI);
        db.updateMark(5, 1, getMarkFromEditText(mei5_1), MainSubject.MI);
        db.updateMark(5, 2, getMarkFromEditText(mei5_2), MainSubject.MI);
        db.updateMark(5, 3, getMarkFromEditText(mei5_3), MainSubject.MI);
        db.updateMark(8, 1, getMarkFromEditText(mei8_1), MainSubject.MI);
        db.updateMark(8, 2, getMarkFromEditText(mei8_2), MainSubject.MI);
        db.updateMark(10, 1, getMarkFromEditText(mei10_1), MainSubject.MI);
        db.updateMark(10, 2, getMarkFromEditText(mei10_2), MainSubject.MI);
        db.updateMark(10, 3, getMarkFromEditText(mei10_3), MainSubject.MI);
    }
    /*
     * in order to visualize the module marks, we update the Texts of the corresponding
     * module textfields
     */
    private void updateModuleMarks(){
        DecimalFormat decimal = new DecimalFormat("#.#");
        if(Double.parseDouble(mei1_1.getText().toString()) != 0 && Double.parseDouble(mei1_2.getText().toString()) !=0) {
            markModule1.setText(String.valueOf(decimal.format(calculateModuleMark(1))));
        }else{
            markModule1.setText("0.0");
        }
        if(Double.parseDouble(mei3_1.getText().toString()) != 0 && Double.parseDouble(mei3_2.getText().toString()) !=0 && Double.parseDouble(mei3_3.getText().toString()) != 0) {
            markModule3.setText(String.valueOf(decimal.format(calculateModuleMark(3))));
        }else{
            markModule3.setText("0.0");
        }
        if(Double.parseDouble(mei4_1.getText().toString()) != 0 && Double.parseDouble(mei4_2.getText().toString()) !=0 && Double.parseDouble(mei4_3.getText().toString()) != 0) {
            markModule4.setText(String.valueOf(decimal.format(calculateModuleMark(4))));
        }else{
            markModule4.setText("0.0");
        }
        if(Double.parseDouble(mei5_1.getText().toString()) != 0 && Double.parseDouble(mei5_2.getText().toString()) !=0 && Double.parseDouble(mei5_3.getText().toString()) != 0) {
            markModule5.setText(String.valueOf(decimal.format(calculateModuleMark(5))));
        }else{
            markModule5.setText("0.0");
        }
        if(Double.parseDouble(mei8_1.getText().toString()) != 0 && Double.parseDouble(mei8_2.getText().toString()) !=0) {
            markModule8.setText(String.valueOf(decimal.format(calculateModuleMark(8))));
        }else{
            markModule8.setText("0.0");
        }
        if(Double.parseDouble(mei10_3.getText().toString()) !=0) {
            markModule10.setText(String.valueOf(decimal.format(calculateModuleMark(10))));
        }else{
            markModule10.setText("0.0");
        }
    }


    /*
     * this method sets the courseMarks for each course
     */
    private void setCourseMarks(){
        mei1_1.setText("" + getCourse(1, 1).getMark());
        mei1_2.setText("" + getCourse(1, 2).getMark());
        mei3_1.setText("" + getCourse(3, 1).getMark());
        mei3_2.setText("" + getCourse(3, 2).getMark());
        mei3_3.setText("" + getCourse(3, 3).getMark());
        mei4_1.setText("" + getCourse(4, 1).getMark());
        mei4_2.setText("" + getCourse(4, 2).getMark());
        mei4_3.setText("" + getCourse(4, 3).getMark());
        mei5_1.setText("" + getCourse(5, 1).getMark());
        mei5_2.setText("" + getCourse(5, 2).getMark());
        mei5_3.setText("" + getCourse(5, 3).getMark());
        mei8_1.setText("" + getCourse(8, 1).getMark());
        mei8_2.setText("" + getCourse(8, 2).getMark());
        mei10_1.setText("" + getCourse(10, 1).getMark());
        mei10_2.setText("" + getCourse(10, 2).getMark());
        mei10_3.setText("" + getCourse(10, 3).getMark());
    }


    /*
     * this is the method, that lets the calculation magic happen for each Module
     */
    private double calculateModuleMark(int moduleId) {
        ArrayList<CourseItem> coursesInModule = new ArrayList<>();

        for (CourseItem course : courses) {
            if (course.getModule() == moduleId && course.getSubject() == MainSubject.MI) {
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
            return Double.parseDouble(editText.getText().toString());
        }
        if (Double.parseDouble(editText.getText().toString()) < 1){
            editText.setText("0.0");
        }
        if (Double.parseDouble(editText.getText().toString()) > 4){
            editText.setText("4.0");
        }
        return Double.parseDouble(editText.getText().toString());
    }

    /*
     * Counts the Modules that are not yet finished to exclude them from the final calculation
     * by adding 1 to a counter for each unfinished Module. This method is for Media Informatics
     * as the MainSubject
     */
    private int setDivisorMain(){
        int divisor = 5;
        if(Double.parseDouble(markModule1.getText().toString()) == 0) {
            divisor--;
        }
        if(Double.parseDouble(markModule3.getText().toString()) == 0) {
            divisor--;
        }
        if(Double.parseDouble(markModule4.getText().toString()) == 0) {
            divisor--;
        }
        if(Double.parseDouble(markModule5.getText().toString()) == 0) {
            divisor--;
        }
        if(Double.parseDouble(markModule10.getText().toString()) == 0) {
            divisor--;
        }
        if(divisor == 0){
            divisor = 1;
        }
        return divisor;
    }

    /*
     * Counts the Modules that are not yet finished to exclude them from the final calculation
     * by adding 1 to a counter for each unfinished Module. This method is for Media Informatics
     * as Second MainSubject
     */
    private int setDivisorSecondary(){
        int divisor = 4;
        if(Double.parseDouble(markModule1.getText().toString()) == 0){
            divisor--;
        }
        if(Double.parseDouble(markModule3.getText().toString()) == 0){
            divisor--;
        }
        if(Double.parseDouble(markModule5.getText().toString()) == 0){
            divisor--;
        }
        if(Double.parseDouble(markModule8.getText().toString()) == 0){
            divisor--;
        }
        if(divisor == 0){
            divisor = 1;
        }
        return divisor;
    }
    /*
     * Here we calculate the mark of the subject, depending on which modules are finished
     * and which mainSubject the User actually has set in his Profile
     */
    public double calculateSubjectMark(){
        User user = db.getUser();
        double sum = 0;
        if(!user.getMainSubject().getName().equals("Medieninformatik")){
            int divisor = setDivisorSecondary();
            if(Double.parseDouble(markModule1.getText().toString()) != 0) {
                sum += Double.parseDouble(markModule1.getText().toString());
            }
            if(Double.parseDouble(markModule3.getText().toString()) != 0) {
                sum += Double.parseDouble(markModule3.getText().toString());
            }
            if(Double.parseDouble(markModule5.getText().toString()) != 0) {
                sum += Double.parseDouble(markModule5.getText().toString());
            }
            if(Double.parseDouble(markModule8.getText().toString()) != 0) {
                sum += Double.parseDouble(markModule8.getText().toString());
            }
            sum = sum / divisor;
            Log.d("MedinfCalc", ""+divisor);

        }else {
            int divisor = setDivisorMain();
            if(Double.parseDouble(markModule1.getText().toString()) != 0) {
                sum += Double.parseDouble(markModule1.getText().toString());
            }
            if(Double.parseDouble(markModule3.getText().toString()) != 0) {
                sum += Double.parseDouble(markModule3.getText().toString());
            }
            if(Double.parseDouble(markModule4.getText().toString()) != 0) {
                sum += Double.parseDouble(markModule4.getText().toString());
            }
            if(Double.parseDouble(markModule5.getText().toString()) != 0) {
                sum += Double.parseDouble(markModule5.getText().toString());
            }
            if(Double.parseDouble(markModule10.getText().toString()) != 0) {
                sum += Double.parseDouble(markModule10.getText().toString());
            }
            sum = sum / divisor;
            Log.d("MedinfCalc", ""+divisor);

        }
        db.updateUserMedInfMark(user.getName(), ""+sum);
        return sum;
    }

}
