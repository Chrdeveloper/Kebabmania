package com.example.myapplication.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerCity.CityAdapter;
import com.example.myapplication.RecyclerCity.CityList;
import com.example.myapplication.RecyclerOpinion.OpinionAdapter;
import com.example.myapplication.RecyclerOpinion.OpinionList;
import com.example.myapplication.RestClient;

import org.json.JSONArray;
import org.json.JSONException;

public class ConfigurationFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private Context context;
    private TextView nombreUser;
    RestClient client;

    private OpinionList  opinionList;

    RecyclerView recyclerViewOpinion;
    public void setOpinionList(OpinionList opinionList, View view){
        this.opinionList = opinionList;
        recyclerViewOpinion = view.findViewById(R.id.RecyclerCity);
        OpinionAdapter opinionAdapter = new OpinionAdapter(opinionList);
        recyclerViewOpinion.setAdapter(opinionAdapter);
        recyclerViewOpinion.setLayoutManager(new LinearLayoutManager(context));




    }
    public ConfigurationFragment() {
        // Required empty public constructor
    }
    public static ConfigurationFragment newInstance(String param1) {
        ConfigurationFragment fragment = new ConfigurationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuation, container, false);
        SharedPreferences preferences = context.getSharedPreferences("KEBAB_PREFS", MODE_PRIVATE);

        context = getContext();
        client = RestClient.getInstance(context);

        nombreUser = view.findViewById(R.id.nombreConfig);

        nombreUser.setText(preferences.getString("userName","Missing"));

        recyclerViewOpinion = view.findViewById(R.id.recyclerOpinion);

        client.getOpinion(view,  new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response.length() != 0) {
                    OpinionList opinionList = null;
                    try {
                        opinionList = new OpinionList(response);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    setOpinionList(opinionList, view);
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





        return view;
    }



}
