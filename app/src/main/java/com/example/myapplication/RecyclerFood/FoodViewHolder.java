package com.example.myapplication.RecyclerFood;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class FoodViewHolder extends RecyclerView.ViewHolder{
    private Food food;
    private TextView nombre;
    private TextView descripcion;


    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);
        nombre = itemView.findViewById(R.id.tituloComida);

        descripcion = itemView.findViewById(R.id.descripcionComida);




    }
    public void showFood(Food food){
        this.food = food;

        nombre.setText(food.getNombre());
        descripcion.setText(food.getDescripcion());
    }
}
