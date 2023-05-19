package com.example.myapplication.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class KebabFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    public KebabFragment() {
        // Required empty public constructor
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



        return inflater.inflate(R.layout.fragment_kebab, container, false);
    }


}
