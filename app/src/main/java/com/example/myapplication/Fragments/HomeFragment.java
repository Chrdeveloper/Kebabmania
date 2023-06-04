package com.example.myapplication.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerCity.CityList;
import com.example.myapplication.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class HomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private View view;
    private RestClient client;
    private Context context;

    private TextView nombre;
    private TextView nombreKebabOpinion;
    private TextView nombreUsuarioOpinion;
    private TextView notaOpinion;


    private TextView ciudadKebab;
    private TextView nombreKebabKebab;
    private TextView lugarKebab;
    public HomeFragment() {
        // Required empty public constructor
    }
    public static HomeFragment newInstance(String param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        view = inflater.inflate(R.layout.fragment_ciudad, container, false);


        context= getActivity();
        client = RestClient.getInstance(context);
        nombreKebabOpinion = view.findViewById(R.id.nombreOpinion);
        nombreUsuarioOpinion = view.findViewById(R.id.nombreKebabOpinion);
        notaOpinion = view.findViewById(R.id.descripcionOpinion);
        ciudadKebab = view.findViewById(R.id.ciudadrecommendedKebab);
        nombreKebabKebab = view.findViewById(R.id.nombreKebab);
        lugarKebab = view.findViewById(R.id.lugarrecommendedKebab);

        client.getCity(view,  new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length() != 2){
                    Toast.makeText(context,"Wrong information recieved", Toast.LENGTH_LONG);
                }else{
                    JSONObject jsonObjectOpinion = new JSONObject();
                    JSONObject jsonObjectKebab = new JSONObject();
                    try {
                        jsonObjectOpinion = response.getJSONObject(0);
                        jsonObjectKebab = response.getJSONObject(1);

                        nombreKebabOpinion.setText(jsonObjectOpinion.getString("nombreKebab"));
                        nombreUsuarioOpinion.setText(jsonObjectOpinion.getString("nombreUsuario"));

                        notaOpinion.setText(jsonObjectOpinion.getString("nota"));

                        ciudadKebab.setText(jsonObjectKebab.getString("nom_ciudad"));
                        nombreKebabKebab.setText(jsonObjectKebab.getString("nombre"));
                        lugarKebab.setText(jsonObjectKebab.getString("lugar"));

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }



            }
        },                 new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse == null) {
                    System.out.println(error);
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