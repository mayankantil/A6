package com.example.weatherapp.Fragments.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.Database.RecentCity;
import com.example.weatherapp.R;

import java.util.ArrayList;
import java.util.List;


public class RecentsAdapter extends RecyclerView.Adapter<RecentsAdapter.ViewHolder> {

    private static List<RecentCity> recentCities = new ArrayList<>();
    private static OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(RecentCity city);
    }
    public void setData(List<RecentCity> cities) {
        recentCities = cities;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecentCity city = recentCities.get(position);
        holder.bind(city);
    }

    @Override
    public int getItemCount() {
        return recentCities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            cityNameTextView = itemView.findViewById(R.id.cityNameTextView);

            itemView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        itemClickListener.onItemClick(recentCities.get(pos));
                    }
                }
            });

        }

        public void bind(RecentCity city) {
            if (cityNameTextView != null) {
                cityNameTextView.setText(city.cityName);
            }
        }
    }
}

