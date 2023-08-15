package com.example.weatherapp;

import android.util.Log;

import com.example.weatherapp.WeatherData.CitySuggestion;
import com.example.weatherapp.WeatherData.WeatherData;
import com.google.gson.Gson;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;

public class WeatherApi {
    private static final String API_KEY = "25794b777df842e787e143911231108";
    private final String BASE_URL = "http://api.weatherapi.com/v1";

    public String[] fetchSuggestions(String userInput) {
        String apiUrl = BASE_URL + String.format("/search.json?key=%s&q=%s", API_KEY, userInput);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(apiUrl);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                if (response.getCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        Gson g = new Gson();
                        CitySuggestion[] suggestions = g.fromJson(EntityUtils.toString(entity), CitySuggestion[].class);
                        ArrayList<String> city_names = new ArrayList<>();
                        for (CitySuggestion sug : suggestions) {
                            city_names.add(sug.getName());
                            Log.i("Suggestion", sug.getName());
                        }
                        return (String[]) city_names.toArray();
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public WeatherData getWeatherStatus(String city_name) throws IOException, ParseException {
        Log.i("WeatherApi", city_name);
        String apiUrl = BASE_URL + String.format("/current.json?key=%s&q=%s&aqi=no", API_KEY, city_name.strip().replace(" ", "%20"));

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(apiUrl);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                Log.i("MyApp", "Fetching");
                HttpEntity entity = response.getEntity();
                Log.i("MyApp", "Fetched");
                if (entity != null) {
                    Log.i("MyApp", "Fetching completed");
                    Gson g = new Gson();
                    return g.fromJson(EntityUtils.toString(entity), WeatherData.class);
                }
            }
        }
        Log.i("MyApp", "Fetching failed");
        return null;
    }

}
