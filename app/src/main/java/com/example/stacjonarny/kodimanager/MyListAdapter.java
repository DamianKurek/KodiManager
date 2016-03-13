package com.example.stacjonarny.kodimanager;

import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stacjonarny.kodimanager.fragments.DodajOdcinekFragment;

import java.util.ArrayList;

/**
 * Created by stacjonarny on 13.03.2016.
 */
public class MyListAdapter extends ArrayAdapter<String> {
    public MyListAdapter(Context context, ArrayList resource) {
        super(context, R.layout.list_layout);

    }

   /* @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View viev = inflater.inflate(R.layout.list_layout, parent, false);

        String folder = getItem(position);
        DodajOdcinekFragment fragment = (DodajOdcinekFragment) getFragmentManager().findFragmentById(R.id.fragment_conteiner);
        ListView listviev = (ListView)fragment.getView().findViewById(R.id.thelistviev);
        TextView textviev =(TextView) viev.findViewById(R.id.nazwa_folder);
        textviev.setText(folder);

        return viev;
    }*/


}
