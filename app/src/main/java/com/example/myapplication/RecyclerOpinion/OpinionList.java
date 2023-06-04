package com.example.myapplication.RecyclerOpinion;

import com.example.myapplication.RecyclerCity.City;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class OpinionList {

        private List<Opinion> opiniones;


    public OpinionList(JSONArray jsonArray) throws JSONException {
        opiniones = new ArrayList<>();
        for(int i = 0;i < jsonArray.length(); i++){
            Opinion aOpinion = new Opinion(jsonArray.getJSONObject(i));
            opiniones.add(aOpinion);
        }

    }
        public List<Opinion> getOpiniones() {
        return opiniones;
    }

        public void setOpiniones(List<Opinion> opiniones) {
        this.opiniones = opiniones;
    }


}
