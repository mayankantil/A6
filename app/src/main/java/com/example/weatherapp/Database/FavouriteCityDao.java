package com.example.weatherapp.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavouriteCityDao {
    @Insert
    void insert(FavouriteCity city);
    @Query("SELECT * FROM favourite_cities ORDER BY id DESC")
    LiveData<List<FavouriteCity>> getAllFavouriteCities();
}

