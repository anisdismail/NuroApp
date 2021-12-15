package com.example.nuroapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText firstName, lastName, emailText, passwordText, userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ActionBar actionBar = getSupportActionBar();
        mAuth = FirebaseAuth.getInstance();
        firstName = (EditText) findViewById(R.id.signup_first_name);
        lastName = (EditText) findViewById(R.id.signup_last_name);
        emailText = (EditText) findViewById(R.id.signup_email);
        passwordText = (EditText) findViewById(R.id.signup_password);
        userName = (EditText) findViewById(R.id.signup_user_name);
    }

    public void signUp(View view) {
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    public void backToLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }
}