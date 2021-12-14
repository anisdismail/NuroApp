package com.example.nuroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private QuestionLibrary myQuestionLibrary = new QuestionLibrary();
    TextView myScoreview;
    TextView QuestionView;
    Button choice1Btn;
    Button choice2Btn;
    Button choice3Btn;
    Button choice4Btn;

    String Answer;
    int Score = 0;
    int QuestionNmbr = 0;



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
                if(choice1Btn.getText() == Answer){
                    Score = Score +1;
                    updateScore(Score);
                    updateQuestion();
                    Toast.makeText(QuizActivity.this,"Correct!!", Toast.LENGTH_SHORT ).show();
                }
                else{
                    Toast.makeText(QuizActivity.this,"Incorrect", Toast.LENGTH_SHORT ).show();
                    updateQuestion();
                }
            }
        });
        choice2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(choice2Btn.getText() == Answer){
                    Score = Score +1;
                    updateScore(Score);
                    updateQuestion();
                    Toast.makeText(QuizActivity.this,"Correct!!", Toast.LENGTH_SHORT ).show();
                }
                else{
                    Toast.makeText(QuizActivity.this,"Incorrect", Toast.LENGTH_SHORT ).show();
                    updateQuestion();
                }
            }
        });
        choice3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(choice3Btn.getText() == Answer){
                    Score = Score +1;
                    updateScore(Score);
                    updateQuestion();
                    Toast.makeText(QuizActivity.this,"Correct!!", Toast.LENGTH_SHORT ).show();
                }
                else{
                    Toast.makeText(QuizActivity.this,"Incorrect", Toast.LENGTH_SHORT ).show();
                    updateQuestion();
                }
            }
        });
        choice4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(choice4Btn.getText() == Answer){
                    Score = Score +1;
                    updateScore(Score);
                    updateQuestion();
                    Toast.makeText(QuizActivity.this,"Correct!!", Toast.LENGTH_SHORT ).show();
                }
                else{
                    Toast.makeText(QuizActivity.this,"Incorrect", Toast.LENGTH_SHORT ).show();
                    updateQuestion();
                }
            }
        });


    }
    private void updateQuestion(){
        QuestionView.setText(myQuestionLibrary.getQuestion(QuestionNmbr));
        choice1Btn.setText(myQuestionLibrary.getChoice1(QuestionNmbr));
        choice2Btn.setText(myQuestionLibrary.getChoice2(QuestionNmbr));
        choice3Btn.setText(myQuestionLibrary.getChoice3(QuestionNmbr));
        choice4Btn.setText(myQuestionLibrary.getChoice4(QuestionNmbr));

        Answer = myQuestionLibrary.getCorrectAnswer(QuestionNmbr);
        QuestionNmbr++;
    }

    private void updateScore(int point){
        myScoreview.setText(""+ Score);
    }
}