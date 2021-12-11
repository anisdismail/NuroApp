package com.example.nuroapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicInteger;

public class SuccessActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_success);
        getSupportActionBar().hide();

        Button buttonReturn = findViewById(R.id.buttonReturn);
        Button buttonNext = findViewById(R.id.buttonNext);

        ImageView imageView = findViewById(R.id.imageView);
        TextView textView = findViewById(R.id.description);
buttonNext.setOnClickListener(v -> {
    Intent intent;
    intent= new Intent(SuccessActivity.this, MainActivity.class);
    startActivity(intent);
});
        buttonReturn.setOnClickListener(v -> {
            Intent intent;
            intent= new Intent(SuccessActivity.this, DrawingActivity.class);
            startActivity(intent);
        });

    }
}
