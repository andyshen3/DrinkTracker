package com.example.chris.drinktracker.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Chris on 12/31/18.
 */

@Database(entities = {DrinkEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    public abstract AlcoholDAO alcoholDAO();

    public static AppDatabase getAppDatabase(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "drinks")
                    .fallbackToDestructiveMigration()
                    .build();
            AlcoholWebScraper.populateAsync(sInstance);
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }
}