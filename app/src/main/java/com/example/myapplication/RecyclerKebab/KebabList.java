package com.example.myapplication.RecyclerKebab;



import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class KebabList {

     private List<Kebab> kebabs;


    public KebabList(JSONArray jsonArray) throws JSONException {
          kebabs = new ArrayList<>();
        for(int i = 0;i < jsonArray.length(); i++){
            Kebab aKebab = new Kebab(jsonArray.getJSONObject(i));
            kebabs.add(aKebab);
        }

    }
    public List<Kebab> getKebabs() {
        return kebabs;
    }

    public void setKebabs(List<Kebab> kebabs) {
        this.kebabs = kebabs;
    }


}
