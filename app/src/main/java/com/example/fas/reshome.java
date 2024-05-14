package com.example.fas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class reshome extends AppCompatActivity {


    CardView youtube;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reshome);

        youtube = findViewById(R.id.youtube);
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(reshome.this, reslinks.class);
                startActivity(intent);

            }
        });

    }}




