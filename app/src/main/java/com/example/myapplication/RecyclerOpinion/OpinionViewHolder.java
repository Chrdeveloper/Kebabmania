package com.example.myapplication.RecyclerOpinion;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.RecyclerCity.City;

public class OpinionViewHolder extends RecyclerView.ViewHolder{
    private TextView nombre;

    private  TextView nota;
    private Opinion opinion;

    public OpinionViewHolder(@NonNull View itemView) {
        super(itemView);
        Context context = itemView.getContext();
        nombre = itemView.findViewById(R.id.kebabOpinion);

        nota = itemView.findViewById(R.id.testOpinion);







    }

    public void showOpinion(Opinion opinion){
        this.opinion = opinion;

        nombre.setText(opinion.getNombre());
        nota.setText(opinion.getNota());

    }
}
