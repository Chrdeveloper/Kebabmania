package com.example.myapplication.RecyclerOpinion;

import org.json.JSONException;
import org.json.JSONObject;

public class Opinion {
    String nombre;

    String nota;

    public Opinion(JSONObject jsonObject) {
        try {
            this.nota = jsonObject.getString("nota");
            this.nombre = jsonObject.getString("nombre_kebab");


        }catch (JSONException jsonException){
            throw new RuntimeException(jsonException);
        }
    }
    public String getNota() {
        return this.nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }


    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



}
