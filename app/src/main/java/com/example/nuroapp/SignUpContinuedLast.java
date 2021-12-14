package com.example.nuroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignUpContinuedLast extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_continued_last);
    }

    public void backToPrevious(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUpContinued.class);
        startActivity(intent);
    }

    public void backToLogin(View view){
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }

    public void signUpSecondLast(View view) {
    }
}