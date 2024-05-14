package com.example.fas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class calhome extends AppCompatActivity {

    CardView manageSubjects;
    CardView calculateGpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calhome);

        manageSubjects = findViewById(R.id.manageSubjects);
        calculateGpa = findViewById(R.id.calculateGpa);

        manageSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(calhome.this, calmain.class);
                startActivity(intent);
            }
        });

        calculateGpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(calhome.this, calcalculation.class);
                startActivity(intent);
            }
        });
    }
}
