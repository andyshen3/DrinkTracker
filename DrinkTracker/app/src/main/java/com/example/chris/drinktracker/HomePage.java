package com.example.chris.drinktracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {

    public static int BACRatio = 0;
    public static double BAC = 0.00;
    public static boolean checkOnce= false;

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
        progBar.setProgress(BACRatio);
        //Set Max as the max BAC for the user, default to 100 for now
        progBar.setMax(100);
        progBar.setProgressDrawable(drawable);

        final TextView progValueTV = (TextView) findViewById(R.id.tv);
        progValueTV.setText(String.valueOf(BAC));

        TableLayout tl = (TableLayout) findViewById(R.id.tableHeader);
        TableRow tr = new TableRow(this);
        TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
        tr.setLayoutParams(params);
        TextView tv0 = new TextView(this);
        tv0.setText(" Sl.No ");
        tr.addView(tv0);
        tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));


        //Run the timer handler just once
        if(checkOnce == false) {

            //Handler for keeping track of time for BAC decay at a rate of 0.015/hr -> 0.00025/min
            final Handler timer=new Handler();
            timer.post(new Runnable(){
                @Override
                public void run() {
                    if(HomePage.BAC > 0){
                        System.out.println(HomePage.BAC);
                        HomePage.BAC -= 0.00025;
                        System.out.println("DECREASED");
                        System.out.println(HomePage.BAC);

                    }
                    // set time here to refresh textView, currently 1 minute delay
                    timer.postDelayed(this,60 * 1000);

                }
            });
            checkOnce = true;

        }

        //Handler to update the UI
        final Handler handleUI = new Handler();
        handleUI.post(new Runnable(){
            @Override
                    public void run(){
                progValueTV.setText(String.valueOf(AddDrink.truncateBAC(BAC)));
                BACRatio = (int)(AddDrink.truncateBAC(BAC) * 200);
                progBar.setProgress(BACRatio);
                handleUI.postDelayed(this, 60 * 1000);
            }

        });

    }

    public void goToSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void goToAddDrink(View view) {
        Intent intent = new Intent(this, AddDrink.class);
        startActivity(intent);
    }





}
