package com.example.fas;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class attadd extends AppCompatActivity {

    private attdatabase dbHelper;
    private ListView attendanceListView;
    private Button selectDateButton;
    private Button presentButton;
    private Button absentButton;
    private Spinner subjectSpinner;
    private Calendar selectedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attadd);

        dbHelper = new attdatabase(this);

        selectDateButton = findViewById(R.id.selectDateButton);
        presentButton = findViewById(R.id.presentButton);
        absentButton = findViewById(R.id.absentButton);
        attendanceListView = findViewById(R.id.attendanceListView);
        subjectSpinner = findViewById(R.id.subjectSpinner);


        List<String> subjects = dbHelper.getAllSubjects();
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subjects);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner.setAdapter(subjectAdapter);


        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                displayAttendance(); // Refresh attendance details when subject is selected
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        presentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordAttendance(true);
                displayAttendance();
            }
        });

        absentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordAttendance(false);
                displayAttendance();
            }
        });

        displayAttendance();
    }



    private void showDatePicker() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedDate = Calendar.getInstance();
                        selectedDate.set(Calendar.YEAR, year);
                        selectedDate.set(Calendar.MONTH, month);
                        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDateButton();
                    }
                },
                year, month, dayOfMonth);
        datePickerDialog.show();
    }

    private void updateDateButton() {
        if (selectedDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            selectDateButton.setText(sdf.format(selectedDate.getTime()));
        }
    }

    private void recordAttendance(boolean attended) {
        if (selectedDate == null) {
            Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
            return;
        }

        String subject = "";
        if (subjectSpinner.getSelectedItem() != null) {
            subject = subjectSpinner.getSelectedItem().toString();
        } else {
            Toast.makeText(this, "Please select a subject", Toast.LENGTH_SHORT).show();
            return;
        }

        dbHelper.recordAttendance(subject, selectedDate.getTime(), attended);
        Toast.makeText(this, "Attendance recorded", Toast.LENGTH_SHORT).show();
    }

    private void displayAttendance() {
        if (subjectSpinner.getSelectedItem() == null) {
            Toast.makeText(this, "Please select a subject", Toast.LENGTH_SHORT).show();
            return;
        }

        String subject = subjectSpinner.getSelectedItem().toString();
        List<attrecord> attendanceList = dbHelper.getAllAttendance(subject);
        List<String> displayList = new ArrayList<>();

        for (attrecord record : attendanceList) {
            String dateString = record.getDate();
            String status = record.isAttended() ? "Present" : "Absent";
            displayList.add(dateString + " - " + status);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        attendanceListView.setAdapter(adapter);
    }

}

