package com.example.nuroapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nuroapp.DrawingUtils.Text;

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
         TextView bodyView = findViewById(R.id.description);
         TextView titleView = findViewById(R.id.title);
         MediaPlayer BackSound = MediaPlayer.create(this,R.raw.bck);
         MediaPlayer ForwardSound = MediaPlayer.create(this,R.raw.frwrd);
         MediaPlayer SkipSound = MediaPlayer.create(this,R.raw.click);

        AtomicInteger count = new AtomicInteger(1); // count the steps in image detection. we have 7 steps in the google doc

        buttonBack.setOnClickListener(v -> {
            count.getAndDecrement();
            switch (count.intValue()) {
                case 1:
                    titleView.setText(getString(R.string.MLModuleWelcomePageTitle));
                    bodyView.setText(getString(R.string.MLModuleWelcomePage));
                    imageView.setImageResource(R.drawable.intro);
                    break;
                case 2:
                    titleView.setText(getString(R.string.MLDefinitionTitle));
                     bodyView.setText(getString(R.string.MLDefinition));

                    break;
                case 3:
                    titleView.setText(getString(R.string.IngredientsofMLTitle));
                     bodyView.setText(getString(R.string.IngredientsofML));
                    break;
                case 4:
                    titleView.setText(getString(R.string.MLTypesTitle));
                     bodyView.setText(getString(R.string.MLTypes));
                    break;
                case 5:
                    titleView.setText(getString(R.string.MlSteps1Title));
                     bodyView.setText(getString(R.string.MlSteps1));
                    break;
                case 6:
                    titleView.setText(getString(R.string.MlSteps2Title));
                     bodyView.setText(getString(R.string.MlSteps2));
                    break;
                case 7:
                    count.set(1);
                    break;

            }
            BackSound.start();

        });

        buttonNext.setOnClickListener(v -> {
            count.getAndIncrement();
            switch (count.intValue()) {
                case 1:
                    titleView.setText(getString(R.string.MLModuleWelcomePageTitle));
                    bodyView.setText(getString(R.string.MLModuleWelcomePage));
                    imageView.setImageResource(R.drawable.intro);
                    break;
                case 2:
                    titleView.setText(getString(R.string.MLDefinitionTitle));
                     bodyView.setText(getString(R.string.MLDefinition));
                    break;
                case 3:
                    titleView.setText(getString(R.string.IngredientsofMLTitle));
                     bodyView.setText(getString(R.string.IngredientsofML));
                    break;
                case 4:
                    titleView.setText(getString(R.string.MLTypesTitle));
                     bodyView.setText(getString(R.string.MLTypes));
                    break;
                case 5:
                    titleView.setText(getString(R.string.MlSteps1Title));
                     bodyView.setText(getString(R.string.MlSteps1));
                    break;
                case 6:
                    titleView.setText(getString(R.string.MlSteps2Title));
                     bodyView.setText(getString(R.string.MlSteps2));
                    break;
                default:
                    count.set(7); //to not get back to 8 when back is pressed
                    Intent intent= new Intent(StepsActivity.this, QuizActivity.class);
                    startActivity(intent);
                    break;
            }
            ForwardSound.start();
        });

        buttonSkip.setOnClickListener(v -> {
            Intent intent = new Intent(StepsActivity.this, QuizActivity.class);
            startActivity(intent);
            SkipSound.start();


        });
    }
}
