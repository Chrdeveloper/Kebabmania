package com.example.myapplication.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.RestClient;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

public class DeleteFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    Button deleteButton;
    private RestClient client;
    private Context context;
    public DeleteFragment() {
        // Required empty public constructor
    }
    public static DeleteFragment newInstance(String param1) {
        DeleteFragment fragment = new DeleteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete, container, false);


        context = getContext();
        SharedPreferences preferences = context.getSharedPreferences("KEBAB_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        client = RestClient.getInstance(context);
        //Vinculacion de elementos del xml
        deleteButton = view.findViewById(R.id.botonDelete);

        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Si el usuario no tiene usuario no le dejara enviar la peticion
                if (preferences.getString("userTelefono", "0").equalsIgnoreCase("0")) {
                    Toast.makeText(context, "User unrecheable", Toast.LENGTH_LONG).show();
                } else {


                    Response.Listener<JSONObject> response = new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //Una vez el usuario borrado en la base de datos esto elimina los campos de las sharedpreferences
                            editor.remove("userTelefono");
                            editor.remove("userToken");
                            editor.remove("userName");
                            editor.commit();
                            Toast.makeText(context, "Usuario eliminado", Toast.LENGTH_LONG).show();

                        }
                    };


                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse == null) {
                            Toast.makeText(context, "No se pudo establecer la conexión", Toast.LENGTH_LONG).show();
                        } else {
                            int serverCode = error.networkResponse.statusCode;
                            switch (serverCode) {
                                case 404:
                                    Toast.makeText(context, "Hijo no encontrado.", Toast.LENGTH_LONG).show();
                                case 401:
                                    Toast.makeText(context, "Peticion no autorizada.", Toast.LENGTH_LONG).show();
                                default:
                                    Toast.makeText(context, "Estado de respuesta: " + serverCode, Toast.LENGTH_LONG).show();

                            }
                        }
                    }
                };

                client.deletingUser(preferences.getString("userTelefono", "-1"), response, errorListener);

            }
            }
        });

        return view;
    }




}
