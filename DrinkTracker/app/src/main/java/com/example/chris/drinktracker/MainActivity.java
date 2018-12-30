package com.example.chris.drinktracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (sharedPrefs.getString("username", null) != null) {
            Intent intent = new Intent(this, HomePage.class);
            startActivity(intent);
        }
        else {
            setContentView(R.layout.activity_main);
        }
    }

    public void hitConfirm(View view) {
        EditText username = (EditText) findViewById(R.id.userName);
        EditText weight = (EditText) findViewById(R.id.weightInput);
        String usernameValue = username.getText().toString();
        Float weightValue = 0f;
        if (usernameValue.equals("") || weight.getText().toString().equals("")) {
            Toast.makeText(this, "Please complete the above information.", Toast.LENGTH_LONG).show();
        }
        else {
            weightValue = Float.valueOf(weight.getText().toString());
            RadioGroup genderGroup = (RadioGroup) findViewById(R.id.genderSelect);
            RadioButton genderButton = (RadioButton) findViewById(genderGroup.getCheckedRadioButtonId());
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("username", usernameValue);
            editor.putString("gender", genderButton.getText().toString());
            editor.putFloat("weight", weightValue);
            editor.apply();
            Intent intent = new Intent(this, HomePage.class);
            startActivity(intent);
        }
    }
}
