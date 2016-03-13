package com.example.stacjonarny.kodimanager.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.stacjonarny.kodimanager.R;


public class DodajOdcinekFragment extends Fragment {
    ListAdapter adapter;
    public DodajOdcinekFragment(){}




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myInflatedView = inflater.inflate(R.layout.fragment_dodaj_odcinek, container,false);


        return myInflatedView;
    }

}
