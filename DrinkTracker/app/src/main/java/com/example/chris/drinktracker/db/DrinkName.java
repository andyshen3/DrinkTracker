package com.example.chris.drinktracker.db;

import android.arch.persistence.room.ColumnInfo;

/**
 * Created by Chris on 12/31/18.
 */

public class DrinkName {
    @ColumnInfo(name = "alcohol")
    public String alcohol;
}
