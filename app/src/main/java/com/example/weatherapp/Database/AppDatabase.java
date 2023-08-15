package com.example.weatherapp.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {RecentCity.class, FavouriteCity.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    public abstract RecentCityDao recentCityDao();
    public abstract FavouriteCityDao favouriteCityDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "recent_city_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}

