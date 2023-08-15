package com.example.weatherapp.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recent_cities")
public class RecentCity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "city_name")
    public String cityName;

    public RecentCity(String cityName) {
        this.cityName = cityName;
    }
}
