package com.example.nuroapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    TextView myScoreview;
    TextView QuestionView;
    Button choice1Btn;
    Button choice2Btn;
    Button choice3Btn;
    Button choice4Btn;

    String Answer;
    int Score = 0;
    int QuestionNmbr = 0;
    AlertDialog successDialog;
    AlertDialog failedDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        myScoreview = findViewById(R.id.score);
        QuestionView = findViewById(R.id.question);
        choice1Btn = findViewById(R.id.choice1);
        choice2Btn = findViewById(R.id.choice2);
        choice3Btn = findViewById(R.id.choice3);
        choice4Btn = findViewById(R.id.choice4);

        updateQuestion();

        choice1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice1Btn.getText().equals(Answer)) {
                    Score = Score + 1;
                    successDialog.show();
                    updateQuestion();
                    updateScore(Score);

                } else {
                    failedDialog.show();
                }


            }
        });
        choice2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice2Btn.getText().equals(Answer)) {
                    Score = Score + 1;
                    successDialog.show();
                    updateQuestion();
                    updateScore(Score);

                } else {
                    failedDialog.show();
                }
            }
        });
        choice3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice3Btn.getText().equals(Answer)) {
                    Score = Score + 1;
                    successDialog.show();
                    updateQuestion();
                    updateScore(Score);
                } else {
                    failedDialog.show();
                }
            }
        });
        choice4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice4Btn.getText().equals(Answer)) {
                    Score = Score + 1;
                    successDialog.show();
                    updateQuestion();
                    updateScore(Score);
                } else {
                    failedDialog.show();
                }
            }
        });


    }

    private void updateQuestion() {
       failedDialog = new AlertDialog.Builder(this).setTitle("Incorrect :(").setMessage("Let's Try Again!").setPositiveButton("Later!", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent= new Intent(QuizActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }).create();
        switch (QuestionNmbr) {
            case 0:
                QuestionView.setText(getResources().getStringArray(R.array.Questions)[QuestionNmbr]);

                choice1Btn.setText(getResources().getStringArray(R.array.ChoicesQ1)[0]);
                choice2Btn.setText(getResources().getStringArray(R.array.ChoicesQ1)[1]);
                choice3Btn.setText(getResources().getStringArray(R.array.ChoicesQ1)[2]);
                choice4Btn.setText(getResources().getStringArray(R.array.ChoicesQ1)[3]);

                Answer = getResources().getStringArray(R.array.Answers)[QuestionNmbr];
                successDialog = new AlertDialog.Builder(this).setTitle("Correct!").setMessage("Let's go to the next question!").create();

                break;
            case 1:
                QuestionView.setText(getResources().getStringArray(R.array.Questions)[QuestionNmbr]);

                choice1Btn.setText(getResources().getStringArray(R.array.ChoicesQ2)[0]);
                choice2Btn.setText(getResources().getStringArray(R.array.ChoicesQ2)[1]);
                choice3Btn.setText(getResources().getStringArray(R.array.ChoicesQ2)[2]);
                choice4Btn.setText(getResources().getStringArray(R.array.ChoicesQ2)[3]);

                Answer = getResources().getStringArray(R.array.Answers)[QuestionNmbr];
                successDialog = new AlertDialog.Builder(this).setTitle("Correct!").setMessage("You have successfuly completed the quiz! " +
                        "Now, let's combine our learnings today in a big project!").setPositiveButton("Ready!", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent= new Intent(QuizActivity.this, DrawingActivity.class);
                        startActivity(intent);
                    }
                }).create();


                break;

        }
        QuestionNmbr++;
    }

    private void updateScore(int point) {
        myScoreview.setText("" + Score);
    }


}

