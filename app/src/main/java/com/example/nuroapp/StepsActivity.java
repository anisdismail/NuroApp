package com.example.nuroapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicInteger;

public class StepsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        getSupportActionBar().hide();

        Button buttonBack = findViewById(R.id.buttonBack);
        Button buttonNext = findViewById(R.id.buttonNext);
        Button buttonSkip = findViewById(R.id.buttonSkip);

        ImageView imageView = findViewById(R.id.imageView);
        TextView textView = findViewById(R.id.description);

        AtomicInteger count = new AtomicInteger(1); // count the steps in image detection. we have 7 steps in the google doc

        buttonBack.setOnClickListener(v -> {
            count.getAndDecrement();
            switch (count.intValue()) {
        //        case 0:
          //          Intent intent = new Intent(StepsActivity.this, MainActivity.class);
           //         startActivity(intent);
           //         break;
                case 1:
                    //imageView.setImageDrawable();         // change image
                    //textView.setText();                   // change text
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
            }

        });

        buttonNext.setOnClickListener(v -> {
            count.getAndIncrement();
            switch (count.intValue()) {
                case 1:
                    //imageView.setImageDrawable();
                    //textView.setText();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                default:
                    count.set(7); //to not get back to 8 when back is pressed
                    Intent intent = new Intent(StepsActivity.this, DrawingActivity.class);
                    startActivity(intent);
                    break;
            }
        });

        buttonSkip.setOnClickListener(v -> {
            Intent intent = new Intent(StepsActivity.this, DrawingActivity.class);
            startActivity(intent);
        });
    }
}
