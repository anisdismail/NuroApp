package com.example.nuroapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FailedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failed);
        getSupportActionBar().hide();

        Button buttonReturn = findViewById(R.id.buttonReturn);
        Button buttonTryAgain = findViewById(R.id.buttonTryAgain);

        ImageView imageView = findViewById(R.id.imageView);
        TextView textView = findViewById(R.id.description);

        buttonTryAgain.setOnClickListener(v -> {
            Intent intent;
            intent= new Intent(FailedActivity.this, DrawingActivity.class);
            startActivity(intent);
        });
        buttonReturn.setOnClickListener(v -> {
            Intent intent;
            intent= new Intent(FailedActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }
}
