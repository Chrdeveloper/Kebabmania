package com.example.myapplication.RecyclerKebab;

import org.json.JSONException;
import org.json.JSONObject;

public class Kebab {
    String nombre;
    String lugar;
    int notaMedia;
    String id;

    public Kebab(JSONObject jsonObject) {
        try {
            this.notaMedia = jsonObject.getInt("notaMedia");
            this.nombre = jsonObject.getString("nombre");
            this.lugar = jsonObject.getString("lugar");
            this.id = jsonObject.getString("id");

        }catch (JSONException jsonException){
            throw new RuntimeException(jsonException);
        }
    }
    public int getNotaMedia() {
        return notaMedia;
    }

    public void setNotaMedia(int notaMedia) {
        this.notaMedia = notaMedia;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
