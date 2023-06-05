package com.example.myapplication.RecyclerFood;

import org.json.JSONException;
import org.json.JSONObject;

public class Food {
    String nombre;

    String descripcion;

    public Food(JSONObject jsonObject) {
        try {
            this.descripcion = jsonObject.getString("descripcion");
            this.nombre = jsonObject.getString("nombre");


        }catch (JSONException jsonException){
            throw new RuntimeException(jsonException);
        }
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
