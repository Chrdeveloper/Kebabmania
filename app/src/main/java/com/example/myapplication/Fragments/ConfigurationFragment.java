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
    private View view;
    RestClient client;

    private OpinionList  opinionList;

    RecyclerView recyclerViewOpinion;
    //Setea el recyclerview
    public void setOpinionList(){

        recyclerViewOpinion = view.findViewById(R.id.recyclerOpinions);
        recyclerViewOpinion.setAdapter(new OpinionAdapter(opinionList));
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
        view = inflater.inflate(R.layout.fragment_configuation, container, false);


        context = getContext();
        SharedPreferences preferences = context.getSharedPreferences("KEBAB_PREFS", MODE_PRIVATE);
        client = RestClient.getInstance(context);
        //Vincular elementos del xml
        nombreUser = view.findViewById(R.id.nombreConfig);

        nombreUser.setText(preferences.getString("userName","Missing"));

        recyclerViewOpinion = view.findViewById(R.id.recyclerOpinions);

        client.getOpinion(view,  new Response.Listener<JSONArray>() {
            //Pilla el recylerview
            @Override
            public void onResponse(JSONArray response) {
                System.out.println(response);
                if (response.length() != 0) {
                    try {
                        opinionList = new OpinionList(response);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    setOpinionList();
                }

            }
        },                 new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Codigos de error
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
