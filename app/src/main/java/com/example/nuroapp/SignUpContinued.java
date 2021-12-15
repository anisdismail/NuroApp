package com.example.nuroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignUpContinued extends AppCompatActivity {
    User user;
    RadioGroup radioGroup;
    RadioButton radioButton2,radioButton3,radioButton;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_continued);
        user = getIntent().getParcelableExtra("User");
        radioGroup =  findViewById(R.id.radio_group);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton = findViewById(R.id.radioButton);
        datePicker = findViewById(R.id.date_picker);

    }

    public void signUpSecond(View view) {
        registerMoreInfo();

    }

    public void registerMoreInfo(){
        if(radioGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Please choose a gender", Toast.LENGTH_SHORT).show();
            return;
        }

        // check if radiobutton for what gender

        //other
        if(radioButton.isChecked()){
            user.setGender("Other");
        }
        //male
        if(radioButton2.isChecked()){
            user.setGender("Male");
        }
        //female
        if(radioButton3.isChecked()){
            user.setGender("Female");
        }
        int   day  = datePicker.getDayOfMonth();
        int   month= datePicker.getMonth();
        int   year = datePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formatedDate = sdf.format(calendar.getTime());


        Intent intent = new Intent(getApplicationContext(),SignUpContinuedLast.class);
        intent.putExtra("User",user);
        startActivity(intent);

    }

    public void backToLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUpContinued.class);
        startActivity(intent);
    }
}