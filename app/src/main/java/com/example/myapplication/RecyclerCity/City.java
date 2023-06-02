package com.example.myapplication.RecyclerCity;

import org.json.JSONException;
import org.json.JSONObject;

public class City {
    int id;

    String nombre;

    public City(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getInt("id");
            this.nombre = jsonObject.getString("nombre");


        }catch (JSONException jsonException){
            throw new RuntimeException(jsonException);
        }
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



}
