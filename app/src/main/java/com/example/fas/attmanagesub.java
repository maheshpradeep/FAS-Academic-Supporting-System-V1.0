package com.example.fas;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class attmanagesub extends AppCompatActivity {

    private EditText subjectNameEditText;
    private Button addSubjectButton;
    private ListView subjectsListView;
    private List<String> subjectsList;
    private ArrayAdapter<String> subjectsAdapter;
    private attdatabase dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attmanagesub);

        subjectNameEditText = findViewById(R.id.subjectNameEditText);
        addSubjectButton = findViewById(R.id.addSubjectButton);
        subjectsListView = findViewById(R.id.subjectsListView);
        subjectsList = new ArrayList<>();
        subjectsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subjectsList);
        subjectsListView.setAdapter(subjectsAdapter);
        dbHelper = new attdatabase(this);

        loadSubjects();

        addSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subjectName = subjectNameEditText.getText().toString().trim();
                if (!subjectName.isEmpty()) {
                    addSubject(subjectName);
                } else {
                    Toast.makeText(attmanagesub.this, "Please enter a subject name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addSubject(String subjectName) {
        boolean success = dbHelper.addSubject(subjectName);
        if (success) {
            Toast.makeText(this, "Subject added successfully", Toast.LENGTH_SHORT).show();
            loadSubjects();
            subjectNameEditText.setText("");
        } else {
            Toast.makeText(this, "Failed to add subject", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSubjects() {
        subjectsList.clear();
        subjectsList.addAll(dbHelper.getAllSubjects());
        subjectsAdapter.notifyDataSetChanged();
    }
}
