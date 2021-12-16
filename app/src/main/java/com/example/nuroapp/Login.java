package com.example.nuroapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextInputEditText email, password;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.sign_in_email);
        password = findViewById(R.id.sign_in_password);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        ActionBar actionBar = getSupportActionBar();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null) {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View, String>(findViewById(R.id.login_button_main), "transition_main_login");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
            startActivity(intent, options.toBundle());

            this.finish();

        }
    }

    /*
        @Override
        protected void onStart(){
            super.onStart();
            FirebaseUser user = mAuth.getCurrentUser();
            if(user == null){
                startActivity(new Intent ());
            }
        }
    */
    public void callSignUp(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }

    public void signIn(View view) {
        if (email.getText().toString().length() > 0) {
            if (!TextUtils.isEmpty(email.getText().toString()) && Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {

            } else {
                email.setError("Enter a Valid Email");
                email.requestFocus();
                return;
            }}else{
            email.setError("Enter an Email");
            email.requestFocus();
        }

        if(password.getText().toString().length()<0){

                password.setError("Password is Empty");
                password.requestFocus();

        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
                    if(fbUser.isEmailVerified()){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        Pair[] pairs = new Pair[1];
                        pairs[0] = new Pair<View, String>(findViewById(R.id.login_button_main), "transition_main_login");
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                        startActivity(intent, options.toBundle());
                    }else{
                        fbUser.sendEmailVerification();
                        Toast.makeText(Login.this, "Check your Email", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        }
    }
