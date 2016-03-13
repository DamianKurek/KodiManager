package com.example.stacjonarny.kodimanager.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stacjonarny.kodimanager.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
    TextView t;
    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myInflatedView = inflater.inflate(R.layout.fragment_setting, container,false);


        return myInflatedView;
    }


}
