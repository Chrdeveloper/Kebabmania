package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RestClient {

    private String BASE_REAL_URL = "http://10.0.2.2:8000/";

    private String BASE_URL = BASE_REAL_URL;

    private Context context;


    private static RestClient singleton = null;

    private RequestQueue queue;

    private RestClient(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    public static RestClient getInstance(Context context) {
        if (singleton == null) {
            singleton = new RestClient(context);
        }
        return singleton;
    }
    //Todas las peticiones se envian desde esta unica clase que tiene metodos que se llaman desde las otras clases

    public void getCity(View view, Response.Listener<JSONArray> respuesta, Response.ErrorListener errorResponse) {


        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                BASE_URL + "cities",
                null,
                respuesta,
                errorResponse
        );

        queue.add(request);


    }

    public void newUser(String telefono, String nombre, Response.Listener listener, Response.ErrorListener errorListener ) {


        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("nombre", nombre);
            requestBody.put("telefono", telefono);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        System.out.println(requestBody);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL + "user",
                requestBody,
                listener,
                errorListener
        );
        this.queue.add(request);



    }

    public void getToken(String telefono, Response.Listener listener, Response.ErrorListener errorListener) {


        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("telefono", telefono);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL + "login",
                requestBody,
                listener,errorListener




        );
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        this.queue.add(request);


    }

    public void getOpinion(View view, Response.Listener<JSONArray> respuesta, Response.ErrorListener errorResponse) {

        SharedPreferences preferences = context.getSharedPreferences("KEBAB_PREFS", MODE_PRIVATE);
        String token = preferences.getString("userToken", null);
        JsonArrayRequest request = new JsonArrayRequest (
                Request.Method.GET,
                BASE_URL +"opinion/"+ preferences.getString("userTelefono", null),
                null,
                respuesta,
                errorResponse

        ){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Session-token", token);
                return headers;
            }


        };

        queue.add(request);


    }

    public void deletingUser(String telefono, Response.Listener<JSONObject> respuesta, Response.ErrorListener errorResponse){
        SharedPreferences preferences = context.getSharedPreferences("KEBAB_PREFS", MODE_PRIVATE);
        String token = preferences.getString("userToken", null);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                BASE_URL + "user/" + telefono,
                null,
                respuesta,
                errorResponse
        ){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Session-token", token);
                return headers;
            }


        };
        this.queue.add(request);

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        this.queue.add(request);

        queue.add(request);

    }

    public void getKebab(View view, Response.Listener<JSONArray> respuesta, Response.ErrorListener errorResponse) {
        SharedPreferences preferences = context.getSharedPreferences("KEBAB_PREFS", MODE_PRIVATE);
        String idcity = String.valueOf(preferences.getString("cityId","0"));
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                BASE_URL + "kebabs/"+idcity,
                null,
                respuesta,
                errorResponse
        );

        queue.add(request);


    }


    public void getFood(Response.Listener<JSONArray> respuesta, Response.ErrorListener errorResponse) {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                BASE_URL + "food",
                null,
                respuesta,
                errorResponse
        );

        queue.add(request);


    }

    public void postOpinion(int nota, String id, Response.Listener<JSONObject> respuesta, Response.ErrorListener errorResponse){
        SharedPreferences preferences = context.getSharedPreferences("KEBAB_PREFS", MODE_PRIVATE);
        String token = preferences.getString("userToken", null);
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("nota", nota);
            requestBody.put("id_kebab", id);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        System.out.println(requestBody);
        JsonObjectRequest request = new JsonObjectRequest (
                Request.Method.POST,
                BASE_URL +"opinion/"+ preferences.getString("userTelefono", null),
                requestBody,
                respuesta,
                errorResponse

        ){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Session-token", token);
                return headers;
            }


        };

        queue.add(request);




    }
    public void getHome(View view, Response.Listener<JSONArray> respuesta, Response.ErrorListener errorResponse) {
        SharedPreferences preferences = context.getSharedPreferences("KEBAB_PREFS", MODE_PRIVATE);
        String token = preferences.getString("userToken", null);
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                BASE_URL + "home",
                null,
                respuesta,
                errorResponse
        ){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Session-token", token);
                return headers;
            }


        };

        queue.add(request);


    }




}