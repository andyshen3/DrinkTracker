package com.example.chris.drinktracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class AddDrink extends AppCompatActivity {

    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        setContentView(R.layout.activity_add_drink);
    }

    public void goToHomePage(View view) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void addDrink(View view){
        EditText alcoholNameET = (EditText) findViewById(R.id.alcoholName);
        String alcoholName = alcoholNameET.getText().toString();

        Spinner alcoholUnitSP = (Spinner) findViewById(R.id.alcohol_spinner);
        String alcoholUnit = alcoholUnitSP.getSelectedItem().toString();

        EditText alcoholQuantityET = (EditText) findViewById(R.id.alcoholQuantity);
        String alcoholQuantity = alcoholQuantityET.getText().toString();

        System.out.println(alcoholName);
        System.out.println(alcoholUnit);
        System.out.println(alcoholQuantity);

        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("alcoholName", alcoholName);
        editor.putString("alcoholUnit", alcoholUnit);
        editor.putString("alcoholQuantity", alcoholQuantity);

        System.out.println(getAlcLiquidOunces());
    }

    /**Widmark Formula: %BAC = (A x 5.14 / (W x r)) - .015 x H
     * A = liquid ounces of alcohol consumed
     * W = weight in pounds
     * r = gender constant (0.73 for men, 0.66 for women)
     * H = hours since first drink
     */
    public int calculateBAC(double A, int W, int r, double H)
    {
        //TODO: Finish helper functions to calculate
        return 0;
    }

    public double getAlcLiquidOunces()
    {
        double volume;
        double percentAlc = 10;

        Spinner alcoholUnitSP = (Spinner) findViewById(R.id.alcohol_spinner);
        String alcoholUnit = alcoholUnitSP.getSelectedItem().toString();

        EditText alcoholQuantityET = (EditText) findViewById(R.id.alcoholQuantity);
        String alcoholQuantityString = alcoholQuantityET.getText().toString();
        double alcoholQuantity = Double.parseDouble(alcoholQuantityString);

        switch(alcoholUnit){
            case "Shot":
                volume = 1.25;
                break;

            case "Can":
                volume = 12.0;
                break;

            case "Bottle":
                volume = 25.4;
                break;

            case "Fifth":
                volume = 25.4;
                break;

            case "Handle":
                volume = 59.2;
                break;

            default:
                volume = 0;
                break;
        }

        return Math.round(percentAlc * volume * alcoholQuantity) ;
    }
}
