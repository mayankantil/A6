package com.example.weatherapp.Models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.WeatherApi;
import com.example.weatherapp.WeatherData.WeatherData;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WeatherViewModel extends ViewModel {

    private final WeatherApi weatherApi = new WeatherApi();
    private final MutableLiveData<WeatherData> weatherData = new MutableLiveData<>();
    private final MutableLiveData<String[]> city_suggestions = new MutableLiveData<>();
    private final Executor executor = Executors.newSingleThreadExecutor();

    public LiveData<WeatherData> getWeatherData() {
        return weatherData;
    }

    public LiveData<String[]> getCitySuggestions() {
        return city_suggestions;
    }

    public void fetchSuggestions(String user_input) {
        executor.execute(() -> {
            try {
                String[] suggestions = weatherApi.fetchSuggestions(user_input);
                if (suggestions != null) {
                    city_suggestions.postValue(suggestions);
                    Log.i("WeatherViewModel", Arrays.toString(suggestions));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("WeatherViewModel", "Fetch error");
                Log.i("WeatherViewModel", Objects.requireNonNull(e.getMessage()));
                Log.i("WeatherViewModel", Objects.requireNonNull(e.toString()));
            }
        });
    }

    public void fetchWeatherData(String cityName) {
        executor.execute(() -> {
            try {
                WeatherData data = weatherApi.getWeatherStatus(cityName);
                if (data != null) {
                    weatherData.postValue(data);
                    Log.i("WeatherViewModel", data.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("WeatherViewModel", "Fetch error");
                Log.i("WeatherViewModel", Objects.requireNonNull(e.getMessage()));
                Log.i("WeatherViewModel", Objects.requireNonNull(e.toString()));
            }
        });
    }
}
