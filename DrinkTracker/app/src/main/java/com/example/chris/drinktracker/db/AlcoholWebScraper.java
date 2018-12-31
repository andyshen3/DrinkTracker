package com.example.chris.drinktracker.db;

/**
 * Created by Chris on 12/29/18.
 */

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AlcoholWebScraper {

    public static void populateAsync(@NonNull final AppDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        generateDrinks(db);
    }

    public static void generateDrinks(AppDatabase db){
        try {
            String url = "http://getdrunknotfat.com";
            Document doc = Jsoup.connect(url).get();
            Elements tableRows = doc.getElementsByTag("tr");
            for (Element row : tableRows) {
                try {
                    DrinkEntity drink = new DrinkEntity();
                    String alcohol = row.child(0).text();
                    Float percentage = Float.valueOf(row.child(1).text());
                    drink.setAlcohol(alcohol);
                    drink.setPercentage(percentage);
                    db.alcoholDAO().insertDrink(drink);
                } catch (NumberFormatException e) {

                }
            }
        } catch (java.io.IOException e) {
            Log.d("EXCEPTION", "IOException: " + e.getMessage());
        }
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase db;

        PopulateDbAsync(AppDatabase db) {
            this.db = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            generateDrinks(db);
            return null;
        }
    }
}
