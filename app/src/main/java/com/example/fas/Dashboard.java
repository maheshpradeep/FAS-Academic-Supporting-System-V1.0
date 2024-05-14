package com.example.fas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Dashboard extends AppCompatActivity {


    CardView shrotnotes;
    CardView lms;

    CardView attendance;

    CardView gpa;

    CardView timetable;

    CardView resources;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        shrotnotes = findViewById(R.id.shrotnotes);
        shrotnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Dashboard.this, drive.class);
                startActivity(intent);

            }
        });

        gpa = findViewById(R.id.gpa);
        gpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Dashboard.this, calhome.class);
                startActivity(intent);

            }
        });

        lms = findViewById(R.id.lms);
        lms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Dashboard.this, lms.class);
                startActivity(intent);

            }
        });


        attendance = findViewById(R.id.attendance);
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Dashboard.this, atthome.class);
                startActivity(intent);

            }
        });

        timetable = findViewById(R.id.timetable);
        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Dashboard.this, timetable.class);
                startActivity(intent);

            }
        });
        resources = findViewById(R.id.resources);
        resources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Dashboard.this, reshome.class);
                startActivity(intent);

            }
        });
    }}




