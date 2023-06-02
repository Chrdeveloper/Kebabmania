package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class RestClient {
    private String BASE_MOCKAPI_URL = "https://afc1dd24-edbd-4fdb-8da8-8543e7132aa3.mock.pstmn.io";
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
}