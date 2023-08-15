package com.example.weatherapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.example.weatherapp.Database.AppDatabase;
import com.example.weatherapp.Fragments.FavouritesFragment;
import com.example.weatherapp.Fragments.HomeFragment;
import com.example.weatherapp.Fragments.RecentsFragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    public static AppDatabase database ;

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize database
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database")
            .build();

        BottomAppBar bottomAppBar = findViewById(R.id.bottomAppBar);
        FloatingActionButton fab = findViewById(R.id.fab);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        setSupportActionBar(bottomAppBar);

        HomeFragment home = new HomeFragment();
        RecentsFragment recent = new RecentsFragment();
        FavouritesFragment favourites = new FavouritesFragment();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            // Handle item selection here
            Fragment fragment = null;
            if (item.getItemId() == R.id.menu_home) {
                fragment = home;
            } else if (item.getItemId() == R.id.menu_recents) {
                // Handle menu_recents item click
                fragment = recent;
            } else if (item.getItemId() == R.id.menu_favorites) {
                // Handle menu_favorites item click
                fragment = favourites;
            }

            if (fragment != null) {
                loadFragment(fragment);
            }
            return fragment != null;
        });

        // Load the default fragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MyApp", "Started");
    }
}