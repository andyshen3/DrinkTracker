package com.example.chris.drinktracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String text = prefs.getString("username", "No Name");
        TextView name = (TextView) findViewById(R.id.homePageName);
        name.setText(text);

        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circle);
        final ProgressBar progBar = (ProgressBar) findViewById(R.id.circularProgressbar);
        progBar.setProgress(1);
        //Set Max as the max BAC for the user, default to 100 for now
        progBar.setMax(100);
        progBar.setProgressDrawable(drawable);

    }

    public void goToSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    /**Widmark Formula: %BAC = (A x 5.14 / (W x r)) - .015 x H
     * A = liquid ounces of alcohol consumed
     * W = weight in pounds
     * r = gender constant (0.73 for men, 0.66 for women)
     * H = hours since first drink
    */
    public int calculateBAC(double A, int W, int r, double H)
    {
        return 0;
    }

    public double getAlcLiquidOunces(double percentAlc)
    {
        double volume = 0;

        return Math.round(percentAlc * volume) ;
    }

}
