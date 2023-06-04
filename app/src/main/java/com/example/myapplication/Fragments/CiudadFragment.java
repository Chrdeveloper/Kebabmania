package com.example.myapplication.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerCity.CityAdapter;
import com.example.myapplication.RecyclerCity.CityList;
import com.example.myapplication.RestClient;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;

public class CiudadFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private Context context;
    private RestClient client;

    private CityList  cityList;
    private RecyclerView recyclerViewCity;

    public CiudadFragment() {

    }
    public static CiudadFragment newInstance(String param1) {
        CiudadFragment fragment = new CiudadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }


    public void setCityList(CityList cityList, View view){
        this.cityList = cityList;
        recyclerViewCity = view.findViewById(R.id.RecyclerCity);
        CityAdapter cityAdapter = new CityAdapter(cityList);
        recyclerViewCity.setAdapter(cityAdapter);
        recyclerViewCity.setLayoutManager(new LinearLayoutManager(context));




    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ciudad, container, false);

        context = getContext();
        client = RestClient.getInstance(context);

        recyclerViewCity = view.findViewById(R.id.RecyclerCity);

        client.getCity(view,  new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response.length() != 0) {
                    CityList cityList = null;
                    try {
                        cityList = new CityList(response);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    setCityList(cityList, view);
                }

            }
        },                 new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse == null) {
                    Toast.makeText(view.getContext(), "No se pudo establecer la conexión", Toast.LENGTH_SHORT).show();
                } else {
                    int serverCode = error.networkResponse.statusCode;
                    switch (serverCode) {
                        case 401:
                            Toast.makeText(view.getContext(),"Peticion no autorizada.", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(view.getContext(), "Estado de respuesta: "+serverCode, Toast.LENGTH_SHORT).show();
                    }                }
            }
        });



        return inflater.inflate(R.layout.fragment_ciudad, container, false);
    }





}
