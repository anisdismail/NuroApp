package com.example.nuroapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputEditText firstName, lastName, emailText, passwordText, userName;
    private User user = new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ActionBar actionBar = getSupportActionBar();
        mAuth = FirebaseAuth.getInstance();
        firstName = (TextInputEditText) findViewById(R.id.signup_first_name);
        lastName = (TextInputEditText) findViewById(R.id.signup_last_name);
        emailText = (TextInputEditText) findViewById(R.id.signup_email);
        passwordText = (TextInputEditText) findViewById(R.id.signup_password);
        userName = (TextInputEditText) findViewById(R.id.signup_user_name);
    }

    public void goSecondSignUp(View view) {
        registerUser();

    }

    private void registerUser() {

        clearAllFocuses();
        if(firstName.getText().toString().length()<1 ){
            firstName.setError("Enter a Valid First Name");
            firstName.requestFocus();
            return;
        }else{
            user.setFirstName(firstName.getText().toString().trim());
        }

        if(lastName.getText().toString().length()>0 ){
            user.setLastName(lastName.getText().toString().trim());
        }else{
            lastName.setError("Enter a Valid First Name");
            lastName.requestFocus();
            return;
        }
        if(userName.getText().toString().length()>0 ){
            user.setUsername(userName.getText().toString().trim());
        }else{
            userName.setError("Enter a Valid Username");
            userName.requestFocus();
            return;
        }
        if(passwordText.getText().toString().length()<8){
            passwordText.setError("Password Needs to be longer");
            passwordText.requestFocus();
            return;
        }else{
            user.setPassword(passwordText.getText().toString().trim());
        }
        if(emailText.getText().toString().length()>0){
            if(!TextUtils.isEmpty(emailText.getText().toString()) && Patterns.EMAIL_ADDRESS.matcher(emailText.getText().toString()).matches()){
                user.setEmail(emailText.getText().toString().trim());
            }
            else{
                emailText.setError("Enter a Valid Email");
                emailText.requestFocus();
                return;
            }
        }  else{
            emailText.setError("Enter an Email");
            emailText.requestFocus();
            return;
        }

        Intent intent = new Intent(SignUp.this, SignUpContinued.class);
        intent.putExtra("First Name",user.firstName);
        intent.putExtra("Last Name",user.lastName);
        intent.putExtra("Username",user.username);
        intent.putExtra("Password",user.password);
        intent.putExtra("Email",user.email);
        startActivity(intent);

    }

    private void clearAllFocuses() {

        emailText.clearFocus();
        firstName.clearFocus();
        passwordText.clearFocus();
    }

    public void backToLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }

}