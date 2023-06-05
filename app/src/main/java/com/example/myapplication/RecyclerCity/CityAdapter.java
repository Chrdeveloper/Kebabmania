package com.example.myapplication.RecyclerCity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class CityAdapter extends RecyclerView.Adapter<CityViewHolder> {
    private CityList cityList;

    public CityAdapter(CityList cityList) {
        this.cityList = cityList;
    }


    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewcell = inflater.inflate(R.layout.city_celda, parent, false);

        CityViewHolder cityViewHolder = new CityViewHolder(viewcell);


        return cityViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        City city = this.cityList.getCities().get(position);
        holder.showCity(city);
    }

    @Override
    public int getItemCount() {
        int cities = this.cityList.getCities().size();

        return cities;
    }
}
