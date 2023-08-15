package com.example.weatherapp.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.Fragments.Adapters.RecentsAdapter;
import com.example.weatherapp.MainActivity;
import com.example.weatherapp.Models.CityViewModel;
import com.example.weatherapp.R;


public class RecentsFragment extends Fragment {

    private RecentsAdapter recentsAdapter;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recents_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recentsAdapter = new RecentsAdapter();
        recentsAdapter.setOnItemClickListener(city -> {
            MainActivity mainActivity = (MainActivity) requireActivity();
            mainActivity.loadFragment(new HomeFragment(city.cityName));
        });

        recyclerView.setAdapter(recentsAdapter);

        CityViewModel cityViewModel = new ViewModelProvider(requireActivity()).get(CityViewModel.class);
        cityViewModel.getAllRecentCities().observe(getViewLifecycleOwner(), recentCities -> {
            recentsAdapter.setData(recentCities);
            Log.i("MyApp", String.format("%d", recentCities.size()));
            recentsAdapter.notifyDataSetChanged();
        });
        return view;
    }
}

