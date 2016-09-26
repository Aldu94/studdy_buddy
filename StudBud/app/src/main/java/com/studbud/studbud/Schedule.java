package com.studbud.studbud;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.studbud.studbud.domain.ScheduleDatabase;
import com.studbud.studbud.domain.ScheduleItem;
import com.studbud.studbud.domain.ScheduleListAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Schedule extends AppCompatActivity {

    private ArrayList<ScheduleItem> tasks;
    private ScheduleListAdapter adapter;
    private ScheduleDatabase db;

    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Log.d("init Task list: ", " go for it");
        initTaskList();
        Log.d("init Task list: ", " was succesfull");
        Log.d("init UI: ", " go for it");
        initUI();
        Log.d("init UI: ", " was successfull");
        initDateField();
        initDatabase();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    private void initDatabase() {
        db = new ScheduleDatabase(this);
        db.open();
        updateList();
    }

    private void initTaskList() {
        tasks = new ArrayList<ScheduleItem>();
    }

    private void initUI() {
        Log.d("Init Task Button:", "go");
        initTaskButton();
        initListView();
        initListAdapter();
    }


    private void initTaskButton() {
        addButton = (Button)findViewById(R.id.add_task_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText) findViewById(R.id.todo_edit_input);
                EditText dateEdit = (EditText) findViewById(R.id.todo_edit_date);
                String task = edit.getText().toString();
                String date = dateEdit.getText().toString();

                if (task.equals("") || date.equals("")) {
                    return;
                } else {
                    edit.setText("");
                    dateEdit.setText("");

                    addNewTask(task, date);
                }
            }
        });
    }

    private void initListView() {
        ListView list = (ListView) findViewById(R.id.todo_list);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                removeTaskAtPosition(position);
                return false;
            }
        });
    }

    private void initListAdapter() {
        ListView list = (ListView) findViewById(R.id.todo_list);
        adapter = new ScheduleListAdapter(this, tasks);
        list.setAdapter(adapter);
    }

    private void updateList() {
        tasks.removeAll(tasks);
        tasks.addAll(db.getAllScheduleItems());
        adapter.notifyDataSetChanged();
    }

    private void addNewTask(String task, String date) {
        Date dueDate = getDateFromString(date);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(dueDate);

        ScheduleItem newTask = new ScheduleItem(task, cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));

        db.insertScheduleItem(newTask);
        updateList();
    }

    private void initDateField() {
        EditText dateEdit = (EditText) findViewById(R.id.todo_edit_date);
        dateEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showDatePickerDialog(arg0);

            }
        });

    }

    private void removeTaskAtPosition(int position) {
        if (tasks.get(position) == null) {
            return;
        } else {
            ScheduleItem itemToDelete = tasks.get(position);
            itemToDelete.setID(position);
            db.removeScheduleItem(itemToDelete);
            updateList();
        }
    }

    private Date getDateFromString(String dateString) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                Locale.GERMANY);
        try {
            return df.parse(dateString);
        } catch (ParseException e) {
            // return current date as fallback
            return new Date();
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getFragmentManager(), "datePicker");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_sort:
                sortList();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }*/
/*
    private void sortList() {
        Collections.sort(tasks);
        adapter.notifyDataSetChanged();

    }
*/
    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            TextView textView = (TextView) getActivity().findViewById(R.id.todo_edit_date);

            GregorianCalendar date = new GregorianCalendar(year, month, day);
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                    Locale.GERMANY);
            String dateString = df.format(date.getTime());

            textView.setText(dateString);
        }
    }




}
