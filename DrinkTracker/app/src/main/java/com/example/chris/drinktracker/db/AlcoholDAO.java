package com.example.chris.drinktracker.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Chris on 12/30/18.
 */

@Dao
public interface AlcoholDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertDrink(DrinkEntity drink);

    @Query("SELECT * FROM drinks WHERE alcohol = :drinkName")
    public DrinkEntity[] loadDrink(String drinkName);

    @Query("SELECT alcohol FROM drinks")
    public List<DrinkName> loadDrinkNames();

    @Query("SELECT * FROM drinks")
    public DrinkEntity[] loadAllDrinks();
}
