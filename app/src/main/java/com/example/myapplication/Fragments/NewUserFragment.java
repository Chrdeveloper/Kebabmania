package com.example.myapplication.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerCity.CityList;
import com.example.myapplication.RestClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Random;

public class NewUserFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private Context context;
    private EditText telefono;
    private EditText nombre;
    private EditText code;
     Button smsSend;
     private View view;
     RestClient client;
    private Button register;
    public NewUserFragment() {
        // Required empty public constructor
    }
    public static NewUserFragment newInstance(String param1) {
        NewUserFragment fragment = new NewUserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        view = inflater.inflate(R.layout.fragment_configlogin, container, false);
        client = RestClient.getInstance(context);
        //Crea el codigo de seguridad del usuario
        final int min = 100;
        final int max = 999;
        final int random = new Random().nextInt((max - min) + 1) + min;




        telefono = view.findViewById(R.id.newTlfConfig);


        nombre = view.findViewById(R.id.newNameConfig);
        code = view.findViewById(R.id.codeConfig);
        smsSend = view.findViewById(R.id.botonSms);


        smsSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Una vez pulsa el boton envia metodo que hace el sms

               // Toast.makeText(context,String.valueOf(random),Toast.LENGTH_LONG).show();
                sendSMS(random);
            }
        });





        register = view.findViewById(R.id.botonNew);
        //Crea el usuario en la base de datos
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code.getText().toString().equalsIgnoreCase(String.valueOf(random))){
                    Toast.makeText(context,"Wrong code",Toast.LENGTH_LONG).show();

                }
                client.newUser(telefono.getText().toString(), nombre.getText().toString(),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //En caso de crear bien al usuario enviara otra peticion para conseguir el token y los datos
                                Toast.makeText(context, "Usuario registrado", Toast.LENGTH_LONG).show();
                                client.getToken(telefono.getText().toString(),
                                        new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        String receivedToken, receivedTelefono, receivedNombre;
                                        try {

                                            receivedToken = response.getString("user_session_token"); // Obtiene el token generado aleatoriamente
                                            receivedTelefono = response.getString("user_tlf"); // Obtiene el token generado aleatoriamente
                                            receivedNombre = response.getString("user_name"); // Obtiene el token generado aleatoriamente

                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                        //Escribe los datos en las sharedpreferences
                                        SharedPreferences preferences = context.getSharedPreferences("KEBAB_PREFS", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("userToken", receivedToken);
                                        editor.putString("userTelefono", receivedTelefono);
                                        editor.putString("userName", receivedNombre);


                                        editor.commit();

                                        Toast.makeText(context, "Vuelve a home", Toast.LENGTH_LONG).show();

                                    }
                                },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                                if (error.networkResponse == null) {
                                                    Toast.makeText(context, "No se pudo establecer la conexión", Toast.LENGTH_LONG).show();
                                                } else {
                                                    int serverCode = error.networkResponse.statusCode;
                                                    switch (serverCode) {
                                                        case 400:
                                                            Toast.makeText(context, "Faltan parámetros o no son válidos", Toast.LENGTH_LONG).show();
                                                            break;
                                                        case 401:
                                                            Toast.makeText(context, "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                                                            break;
                                                        case 404:
                                                            Toast.makeText(context, "El usuario introducido no existe", Toast.LENGTH_LONG).show();
                                                            break;
                                                        default:
                                                            Toast.makeText(context, "Estado de respuesta: " + serverCode, Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            }
                                        }

                                        );

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error.networkResponse == null) {
                                    Toast.makeText(context, "No se pudo establecer la conexión", Toast.LENGTH_LONG).show();
                                } else {
                                    int serverCode = error.networkResponse.statusCode;
                                    switch (serverCode) {
                                        case 400:
                                            Toast.makeText(context, "Faltan parámetros o no son válidos", Toast.LENGTH_LONG).show();
                                            break;
                                        case 409:
                                            Toast.makeText(context, "Ya existe un usuario con ese telefono", Toast.LENGTH_LONG).show();
                                            break;
                                        default:
                                            Toast.makeText(context, "Estado de respuesta: " + serverCode, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });




            }
        });

        return view;
    }





    protected void sendSMS(int random) {



        SmsManager smsManager = SmsManager.getDefault();
        ActivityCompat.requestPermissions(getActivity(),new String[] { Manifest.permission.SEND_SMS}, 1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            smsManager.sendTextMessage("+34"+telefono.getText().toString(), null, String.valueOf(random), null, null);
        }
        Toast.makeText(getActivity(),"SMS Sent", Toast.LENGTH_LONG).show();
    }

}
