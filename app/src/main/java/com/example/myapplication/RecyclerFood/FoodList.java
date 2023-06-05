package com.example.myapplication.RecyclerFood;

import com.example.myapplication.RecyclerOpinion.Opinion;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class FoodList {
    private List<Food> foods;


    public FoodList(JSONArray jsonArray) throws JSONException {
        foods = new ArrayList<>();
        for(int i = 0;i < jsonArray.length(); i++){
            Food aFood = new Food(jsonArray.getJSONObject(i));
            foods.add(aFood);
        }

    }
    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

}
