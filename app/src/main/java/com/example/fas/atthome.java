package com.example.fas;

import static com.example.fas.R.id.viewmanasubeButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class atthome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atthome);

        CardView recordAttendanceButton = findViewById(R.id.recordAttendanceButton);
        recordAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(atthome.this, attadd.class));
            }
        });

        CardView viewPercentageButton = findViewById(R.id.viewPercentageButton);
        viewPercentageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(atthome.this, attprec.class));
            }
        });

        CardView manasubeButton = findViewById(viewmanasubeButton);
        manasubeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(atthome.this, attmanagesub.class));
            }
        });

    }
}
