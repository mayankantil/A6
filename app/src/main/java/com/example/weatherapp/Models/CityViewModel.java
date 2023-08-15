package com.example.weatherapp.Models;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.weatherapp.Database.AppDatabase;
import com.example.weatherapp.Database.FavouriteCity;
import com.example.weatherapp.Database.FavouriteCityDao;
import com.example.weatherapp.Database.RecentCity;
import com.example.weatherapp.Database.RecentCityDao;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CityViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;
    private final RecentCityDao recentCityDao;
    private final FavouriteCityDao favouriteCityDao;
    private final LiveData<List<RecentCity>> allRecentCities;
    private final LiveData<List<FavouriteCity>> allFavouriteCities;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public CityViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(application);
        recentCityDao = appDatabase.recentCityDao();
        favouriteCityDao = appDatabase.favouriteCityDao();
        allRecentCities = recentCityDao.getAllRecentCities();
        allFavouriteCities = favouriteCityDao.getAllFavouriteCities();
    }

    public LiveData<List<RecentCity>> getAllRecentCities() {
        return allRecentCities;
    }
    public LiveData<List<FavouriteCity>> getAllFavouriteCities() {
        return allFavouriteCities;
    }

    public void insertRecentCity(RecentCity recentCity) {
        // Use a background thread to insert the recent city
        executor.execute(() -> {
            recentCityDao.insert(recentCity);
        });
    }
    public void insertFavouriteCity(FavouriteCity favouriteCity) {
        executor.execute(() -> {
            favouriteCityDao.insert(favouriteCity);
        });
    }
}


