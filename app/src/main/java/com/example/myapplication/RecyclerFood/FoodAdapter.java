package com.example.myapplication.RecyclerFood;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.RecyclerCity.CityViewHolder;
import com.example.myapplication.RecyclerKebab.Kebab;
import com.example.myapplication.RecyclerOpinion.OpinionList;
import com.example.myapplication.RecyclerOpinion.OpinionViewHolder;

public class FoodAdapter extends RecyclerView.Adapter<FoodViewHolder>{

    private FoodList foodList;

    public FoodAdapter(FoodList foodList) {

    }



    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewcell = inflater.inflate(R.layout.food_offer, parent, false);

        FoodViewHolder foodViewHolder = new FoodViewHolder(viewcell);


        return foodViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food= this.foodList.getFoods().get(position);
        holder.showFood(food);
    }

    @Override
    public int getItemCount() {
        return this.foodList.getFoods().size();
    }
}
