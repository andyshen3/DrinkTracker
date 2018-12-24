package com.example.chris.drinktracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void hitConfirm(View view) {
        EditText username = (EditText) findViewById(R.id.userName);
        EditText weight = (EditText) findViewById(R.id.weightInput);
        String usernameValue = username.getText().toString();
        if (usernameValue.equals("")) {
            usernameValue = "Bob Joe Shen";
        }
        Float weightValue = 0f;
        if (weight.getText().toString().equals("")) {
            weightValue = 420f;
        } else {
            weightValue = Float.valueOf(weight.getText().toString());
        }
        RadioGroup genderGroup = (RadioGroup) findViewById(R.id.genderSelect);
        RadioButton genderButton = (RadioButton) findViewById(genderGroup.getCheckedRadioButtonId());
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putString("username", usernameValue);
        editor.putString("gender", genderButton.getText().toString());
        editor.putFloat("weight", weightValue);
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void sendMessage(View view)
    {

    }
}
