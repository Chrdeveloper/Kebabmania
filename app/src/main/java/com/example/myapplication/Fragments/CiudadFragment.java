package com.example.myapplication.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class CiudadFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    public CiudadFragment() {

    }
    public static CiudadFragment newInstance(String param1) {
        CiudadFragment fragment = new CiudadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        return inflater.inflate(R.layout.fragment_ciudad, container, false);
    }





}
