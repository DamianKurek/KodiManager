package com.example.stacjonarny.kodimanager.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stacjonarny.kodimanager.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubFragment extends Fragment {

    public SubFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       return inflater.inflate(R.layout.fragment_sub, container, false);

    }

}
