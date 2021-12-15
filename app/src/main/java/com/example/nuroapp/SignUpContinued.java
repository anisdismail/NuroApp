package com.example.nuroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignUpContinued extends AppCompatActivity {
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_continued);
        user = getIntent().getParcelableExtra("User");
    }

    public void signUpSecond(View view) {
        registerMoreInfo();
        Intent intent = new Intent(getApplicationContext(),SignUpContinuedLast.class);
        startActivity(intent);
    }

    public void registerMoreInfo(){

    }

    public void backToLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUpContinued.class);
        startActivity(intent);
    }
}