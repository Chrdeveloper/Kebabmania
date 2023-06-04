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
import com.example.myapplication.RecyclerKebab.KebabAdapter;
import com.example.myapplication.RecyclerKebab.KebabList;
import com.example.myapplication.RestClient;

import org.json.JSONArray;
import org.json.JSONException;

public class KebabFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private KebabList kebabList;
    private Context context;
    RestClient client;
    private RecyclerView recyclerViewKebab;
    public KebabFragment() {
        // Required empty public constructor
    }

    public void setKebabList(KebabList kebabList, View view){
        this.kebabList = kebabList;
        recyclerViewKebab = view.findViewById(R.id.RecyclerCity);
        KebabAdapter kebabAdapter = new KebabAdapter(kebabList);
        recyclerViewKebab.setAdapter(kebabAdapter);
        recyclerViewKebab.setLayoutManager(new LinearLayoutManager(context));

    }


    public static KebabFragment newInstance(String param1) {
        KebabFragment fragment = new KebabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kebab, container, false);
        context = getContext();
        client = RestClient.getInstance(context);
        recyclerViewKebab = view.findViewById(R.id.recyclerOferta);

        client.getKebab(view,  new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response.length() != 0) {
                    KebabList cityList = null;
                    try {
                        kebabList = new KebabList(response);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    setKebabList(kebabList, view);
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
