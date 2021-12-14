package com.example.nuroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignUpContinued extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_continued);
    }

    public void signUpSecond(View view) {
        Intent intent = new Intent(getApplicationContext(),SignUpContinuedLast.class);
        startActivity(intent);
    }

    public void backToLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUpContinued.class);
        startActivity(intent);
    }
}