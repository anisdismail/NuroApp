package com.example.nuroapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class SignUpContinuedLast extends AppCompatActivity {

    TextInputEditText phoneNumber;
    CountryCodePicker countryCodePicker;
    ProgressBar progressBar;
    User user;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_continued_last);
        user = getIntent().getParcelableExtra("User");
        phoneNumber = findViewById(R.id.phone_number);
        countryCodePicker = findViewById(R.id.country_code);
        progressBar = findViewById(R.id.simpleProgressBar);
        mAuth = FirebaseAuth.getInstance();
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
        if(phoneNumber.getText().toString().length()<6){
            phoneNumber.setError("Enter a Valid Phone Number");
            return;
        }
        user.setPhone(countryCodePicker.getSelectedCountryCodeWithPlus().toString() + phoneNumber.getText().toString());

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUpContinuedLast.this, "User created!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.VISIBLE);
                                    }else{
                                        Toast.makeText(SignUpContinuedLast.this, "User creation unsuccessful", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                    }
                });

    }
}