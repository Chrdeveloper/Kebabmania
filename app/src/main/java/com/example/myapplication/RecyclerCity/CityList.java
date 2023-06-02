package com.example.myapplication.RecyclerCity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class CityList {
    private List<City> cities;


    public CityList(JSONArray jsonArray) throws JSONException {
        cities = new ArrayList<>();
        for(int i = 0;i < jsonArray.length(); i++){
            City aCity = new City(jsonArray.getJSONObject(i));
            cities.add(aCity);
        }

    }
    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }


}
