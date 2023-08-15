package com.example.weatherapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.weatherapp.Database.FavouriteCity;
import com.example.weatherapp.Database.RecentCity;
import com.example.weatherapp.Models.CityViewModel;
import com.example.weatherapp.Models.WeatherViewModel;
import com.example.weatherapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class HomeFragment extends Fragment {

    private String city_name = "";
    private AutoCompleteTextView CityName;
    private TextView WeatherStatus;
    private TextView WindSpeed;
    private TextView WindDirection;
    private WeatherViewModel weatherViewModel;
    private CityViewModel cityViewModel;

    public HomeFragment() {
        city_name = "";
    }
    public HomeFragment(String cityName) {
        city_name = cityName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        // Get the widgets
        CityName = view.findViewById(R.id.CityName);
        WeatherStatus = view.findViewById(R.id.WeatherStatus);
        WindSpeed = view.findViewById(R.id.WindSpeed);
        WindDirection = view.findViewById(R.id.WindDirection);
        Button weatherButton = view.findViewById(R.id.WeatherButton);
        FloatingActionButton favouriteButton = view.findViewById(R.id.fab);

        weatherViewModel = new WeatherViewModel();
        cityViewModel = new ViewModelProvider(requireActivity()).get(CityViewModel.class);

        if (city_name.length() != 0) {
            CityName.setText(city_name);
            simulateWeatherButtonClick(city_name);
        }

        weatherButton.setOnClickListener(v -> {
            String cityName = CityName.getText().toString();
            if (cityName.length() == 0) return;

            cityViewModel.insertRecentCity(new RecentCity(cityName));
            weatherViewModel.fetchWeatherData(cityName);
        });

        favouriteButton.setOnClickListener(v -> {
            String cityName = CityName.getText().toString();
            if (cityName.length() == 0) return;

            cityViewModel.insertFavouriteCity(new FavouriteCity(cityName));
        });

        weatherViewModel.getWeatherData().observe(getViewLifecycleOwner(), weatherData -> {
            WeatherStatus.setText(weatherData.current.condition.text);
            WindDirection.setText(weatherData.current.wind_dir);
            WindSpeed.setText(String.format(Locale.US, "%.2f kph", weatherData.current.wind_kph));
        });

        return view;
    }

    private void simulateWeatherButtonClick(String cityName) {
        weatherViewModel.fetchWeatherData(cityName);
    }
}