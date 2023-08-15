package com.example.weatherapp.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecentCityDao {
    @Insert
    void insert(RecentCity city);
    @Query("SELECT * FROM recent_cities ORDER BY id DESC")
    LiveData<List<RecentCity>> getAllRecentCities();
}

