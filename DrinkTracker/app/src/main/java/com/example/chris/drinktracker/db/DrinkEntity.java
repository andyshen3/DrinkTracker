package com.example.chris.drinktracker.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Chris on 12/30/18.
 */

@Entity(tableName = "drinks")
public class DrinkEntity {

    @PrimaryKey
    @NonNull
    private String alcohol;

    @NonNull
    private Float percentage;

    public String getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }
}
