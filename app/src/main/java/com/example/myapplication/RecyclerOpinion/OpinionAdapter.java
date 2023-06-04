package com.example.myapplication.RecyclerOpinion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.RecyclerCity.City;
import com.example.myapplication.RecyclerCity.CityList;
import com.example.myapplication.RecyclerCity.CityViewHolder;

public class OpinionAdapter extends RecyclerView.Adapter<OpinionViewHolder>{
    private OpinionList opinionList;

    public OpinionAdapter(OpinionList opinionList) {

    }


    @NonNull
    @Override
    public OpinionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewcell = inflater.inflate(R.layout.opinion_celda, parent, false);

        OpinionViewHolder opinionHolder = new OpinionViewHolder(viewcell);


        return opinionHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OpinionViewHolder holder, int position) {
        Opinion opinion= this.opinionList.getOpiniones().get(position);
        holder.showOpinion(opinion);
    }

    @Override
    public int getItemCount() {
        return this.opinionList.getOpiniones().size();
    }
}
