package com.example.myapplication.RecyclerCity;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class CityViewHolder extends RecyclerView.ViewHolder{

    private Button button;

    private City city;

    private String id;

    private String nombre;

    public CityViewHolder(@NonNull View itemView) {
        super(itemView);
        Context context = itemView.getContext();
        button = itemView.findViewById(R.id.cityButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = context.getSharedPreferences("KEBAB_PREFS",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                if (preferences.getString("cityId","-1").equalsIgnoreCase("-1")){

                    editor.putString("cityId", id);
                    editor.commit();

                }else{
                    editor.remove("cityId");
                    editor.commit();
                    editor.putString("cityId", id);
                    editor.commit();
                }

            }
        });




    }

    public void showCity(City city){
        this.city = city;
        this.id = city.getId();

        button.setText(city.getNombre());

    }

}
