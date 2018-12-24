package com.example.chris.drinktracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String username = prefs.getString("username", null);
        String gender = prefs.getString("gender", null);
        Float weight = prefs.getFloat("weight", 0f);

        Button confirmButton = (Button) findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                Intent intent = new Intent(Settings.this, HomePage.class);
                startActivity(intent);
            }
        });
    }

}
