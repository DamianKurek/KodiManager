package com.example.stacjonarny.kodimanager.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.app.DialogFragment;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stacjonarny.kodimanager.R;

//import com.example.stacjonarny.kodimanager.R;

/**
 * Created by stacjonarny on 18.03.2016.
 */
public class MyDialogInfo extends DialogFragment {
    private TextView logi;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View viev = inflater.inflate(R.layout.layout_dialog, null);
        builder.setView(viev);
        Dialog dialog = builder.create();
        return dialog;

    }


}
