package com.example.myapplication.RecyclerOpinion;

import org.json.JSONException;
import org.json.JSONObject;

public class Opinion {
    String nombre;

    int nota;

    public Opinion(JSONObject jsonObject) {
        try {
            this.nota = jsonObject.getInt("id");
            this.nombre = jsonObject.getString("nombre");


        }catch (JSONException jsonException){
            throw new RuntimeException(jsonException);
        }
    }
    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



}
