package com.example.myapplication.RecyclerCity;

import org.json.JSONException;
import org.json.JSONObject;

public class City {
   String id;

    String nombre;

    public City(JSONObject jsonObject) {
        try {
            this.id = String.valueOf(jsonObject.getInt("id"));
            this.nombre = jsonObject.getString("nom_ciudad");


        }catch (JSONException jsonException){
            throw new RuntimeException(jsonException);
        }
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



}
