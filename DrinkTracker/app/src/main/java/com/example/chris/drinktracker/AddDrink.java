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

        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("alcoholName", alcoholName);
        editor.putString("alcoholUnit", alcoholUnit);
        editor.putString("alcoholQuantity", alcoholQuantity);

        System.out.println("INCREASED: " + calculateBAC());
        System.out.println("ORIGINAL BAC: " + HomePage.BACRatio);
        HomePage.BACRatio += (int) ((truncateBAC(calculateBAC())) * 200);
        HomePage.BAC += truncateBAC(calculateBAC());

        System.out.println("BAC: " + HomePage.BAC);

        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    /**Widmark Formula: %BAC = (A x 5.14 / (W x r)) - .015 x H
     * A = liquid ounces of alcohol consumed
     * W = weight in pounds
     * r = gender constant (0.73 for men, 0.66 for women)
     * H = hours since first drink
     *
     * Method does not account for BAC decay yet
     */
    public double calculateBAC()
    {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Float weight = prefs.getFloat("weight", 0f);
        String gender = prefs.getString("gender", null);
        double genderConstant;

        switch(gender){
            case "Male":
                genderConstant = 0.73;
                break;

            case "Female":
                genderConstant = 0.66;
                break;

            default:
                genderConstant = 0;

        }

        return (getAlcLiquidOunces() * 5.14) / (weight * genderConstant);
    }

    /**
     * Calculates the amount of liquid ounces of alcohol consumed by multiplying the volume of
     * alcohol, percent alcohol of the drink, and the quantity of the drink consumed.
     *
     * @return liquid ounces of alcohol consumed
     */
    public double getAlcLiquidOunces()
    {
        double volume;
        double percentAlc;
        double alcoholQuantity;

        EditText alcoholNameET = (EditText) findViewById(R.id.alcoholName);
        String alcoholName = alcoholNameET.getText().toString();

        Spinner alcoholUnitSP = (Spinner) findViewById(R.id.alcohol_spinner);
        String alcoholUnit = alcoholUnitSP.getSelectedItem().toString();

        EditText alcoholQuantityET = (EditText) findViewById(R.id.alcoholQuantity);
        String alcoholQuantityString = alcoholQuantityET.getText().toString();

        alcoholQuantity = Double.parseDouble(alcoholQuantityString);
        percentAlc = MainActivity.DRINKMAP.get(alcoholName);

        //Convert to a decimal
        percentAlc = percentAlc / 100.0;

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

        return (percentAlc * volume * alcoholQuantity);
    }

    public double truncateBAC(double BAC){
        return Math.floor(BAC * 1000) / 1000;
    }
}
