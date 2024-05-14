package com.example.fas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fas.R;

public class caladdsub extends AppCompatActivity {

    EditText  subcodeInput, creditInput, resultInput;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caladdsub);

        subcodeInput = findViewById(R.id.subcode_input);
        creditInput = findViewById(R.id.credits_input);
        resultInput = findViewById(R.id.results_input);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caldatabase myDB = new caldatabase(caladdsub.this);
                myDB.addsub(subcodeInput.getText().toString().trim(),
                        creditInput.getText().toString().trim(),
                        resultInput.getText().toString().trim());


                clearInputFields();
            }
        });
    }


    private void clearInputFields() {
        subcodeInput.setText("");
        creditInput.setText("");
        resultInput.setText("");
    }
}
