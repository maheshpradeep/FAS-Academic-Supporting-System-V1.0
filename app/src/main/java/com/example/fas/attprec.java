package com.example.fas;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class attprec extends AppCompatActivity {

    private attdatabase dbHelper;
    private TextView attendancePercentageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attprec);

        dbHelper = new attdatabase(this);
        attendancePercentageTextView = findViewById(R.id.attendancePercentageTextView);

        displayAttendancePercentage();
    }

    private void displayAttendancePercentage() {
        StringBuilder percentageText = new StringBuilder();

        List<String> subjects = dbHelper.getAllSubjects();
        for (String subject : subjects) {
            int totalLectures = dbHelper.getTotalAttendance(subject);
            int attendedLectures = dbHelper.getAttendedDays(subject);
            int absentLectures = totalLectures - attendedLectures;

            if (totalLectures > 0) {
                double percentage = ((double) attendedLectures / totalLectures) * 100;
                percentageText.append("").append(subject)
                        .append(" - ").append(String.format("%.2f", percentage)).append("%\n");
            } else {
                percentageText.append(" - ").append(subject)
                        .append("No recorded yet.\n");
            }
        }

        attendancePercentageTextView.setText(percentageText.toString());
    }

}

