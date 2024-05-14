package com.example.fas;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class calcalculation extends AppCompatActivity {

    private caldatabase dbHelper;
    private TextView gpaTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcalculation);

        dbHelper = new caldatabase(this);
        gpaTextView = findViewById(R.id.gpa_text_view);
        Button calculateButton = findViewById(R.id.calculate_button);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateGPA();
            }
        });
    }

    private void calculateGPA() {
        Cursor cursor = dbHelper.readsub();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No subjects found in the database", Toast.LENGTH_SHORT).show();
            return;
        }

        double totalGradePoints = 0;
        int totalCredits = 0;

        while (cursor.moveToNext()) {
            int credits = cursor.getInt(cursor.getColumnIndex(caldatabase.COLUMN_CREDITS));
            String result = cursor.getString(cursor.getColumnIndex(caldatabase.COLUMN_RESULT));


            double gradePoints = getResultGradePoints(result);
            if (gradePoints == -1) {
                Toast.makeText(this, "Invalid result for subject", Toast.LENGTH_SHORT).show();
                return;
            }

            totalGradePoints += gradePoints * credits;
            totalCredits += credits;
        }

        if (totalCredits == 0) {
            Toast.makeText(this, "Total credits cannot be zero", Toast.LENGTH_SHORT).show();
            return;
        }

        double gpa = totalGradePoints / totalCredits;
        gpaTextView.setText(String.format("Your GPA: %.2f", gpa));
    }

    private double getResultGradePoints(String result) {

        switch (result) {
            case "A+":
                return 4.0;
            case "A":
                return 4.0;
            case "A-":
                return 3.7;
            case "B+":
                return 3.3;
            case "B":
                return 3.0;
            case "B-":
                return 2.7;

            case "C+":
                return 2.3;
            case "C":
                return 2.0;
            case "C-":
                return 1.7;

            case "D+":
                return 1.3;
            case "D":
                return 1.0;

            case "E":
                return 0.0;


            default:
                return -1;
        }
    }
}
