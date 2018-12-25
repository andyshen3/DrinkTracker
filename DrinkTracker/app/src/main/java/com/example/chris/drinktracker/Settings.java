package com.example.chris.drinktracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String username = prefs.getString("username", null);
        String gender = prefs.getString("gender", null);
        Float weight = prefs.getFloat("weight", 0f);
        final EditText userField = (EditText) findViewById(R.id.nameField);
        final EditText weightField = (EditText) findViewById(R.id.weightField);
        userField.setText(username);
        weightField.setText(weight.toString());
        switch(gender) {
            case "Male":
                RadioButton maleRadio = (RadioButton) findViewById(R.id.maleRadioSettings);
                maleRadio.setChecked(true);
                break;
            case "Female":
                RadioButton femaleRadio = (RadioButton) findViewById(R.id.femaleRadioSettings);
                femaleRadio.setChecked(true);
        }
        final Context context = this;
        RadioGroup genderGroup = (RadioGroup) findViewById(R.id.genderSelectSettings);
        final RadioButton genderButton = (RadioButton) findViewById(genderGroup.getCheckedRadioButtonId());
        Button confirmButton = (Button) findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", userField.getText().toString());
                editor.putString("gender", genderButton.getText().toString());
                editor.putFloat("weight", Float.valueOf(weightField.getText().toString()));
                editor.apply();
                Intent intent = new Intent(context, HomePage.class);
                startActivity(intent);
            }
        });
    }

}
