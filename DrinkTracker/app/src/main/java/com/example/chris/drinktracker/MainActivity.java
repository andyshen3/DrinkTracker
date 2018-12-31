package com.example.chris.drinktracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.chris.drinktracker.db.AlcoholWebScraper;
import com.example.chris.drinktracker.db.AppDatabase;
import com.example.chris.drinktracker.db.DrinkEntity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final Map<String, Float> DRINKMAP = new HashMap<>();

    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrinkNamesAsyncTask task = new DrinkNamesAsyncTask(AppDatabase.getAppDatabase(getApplicationContext()));
        try {
            DrinkEntity[] result = task.execute().get();
            for (DrinkEntity drink : result) {
                DRINKMAP.put(drink.getAlcohol(), drink.getPercentage());
            }
        } catch (java.lang.InterruptedException e) {
            Log.d("EXCEPTION", "InterruptedException " + e.getMessage());
        } catch(java.util.concurrent.ExecutionException e) {
            Log.d("EXCEPTION", "ExecutionException " + e.getMessage());
        }
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

    public void sendMessage(View view)
    {

    }

    private static class DrinkNamesAsyncTask extends AsyncTask<Void, Void, DrinkEntity[]> {

        private final AppDatabase db;

        public DrinkNamesAsyncTask(AppDatabase db) {
            this.db = db;
        }

        @Override
        protected DrinkEntity[] doInBackground(Void... params) {
            return db.alcoholDAO().loadAllDrinks();
        }
    }
}
