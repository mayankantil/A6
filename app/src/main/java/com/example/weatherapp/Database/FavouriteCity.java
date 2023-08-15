package com.example.weatherapp.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourite_cities")
public class FavouriteCity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "city_name")
    public String cityName;

    public FavouriteCity(String cityName) {
        this.cityName = cityName;
    }
}
