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
    Context context;
    TextView nazwa;
    TextView opis;
    ArrayList<String> lista_nazwa;
    ArrayList<String> lista_opis;


    public MyListAdapter(Context context, ArrayList nazwy, ArrayList opisy) {
        super(context, R.layout.list_layout,R.id.serial_nazwa,nazwy);
        this.context=context;
        this.lista_nazwa = nazwy;
        this.lista_opis = opisy;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.list_layout,parent,false);
        nazwa = (TextView) row.findViewById(R.id.serial_nazwa);
        opis = (TextView) row.findViewById(R.id.serial_opis);

        nazwa.setText(lista_nazwa.get(position));
        opis.setText(lista_opis.get(position));


        return row;
    }
}
