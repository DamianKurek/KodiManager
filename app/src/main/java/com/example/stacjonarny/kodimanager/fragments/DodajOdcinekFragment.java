package com.example.stacjonarny.kodimanager.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stacjonarny.kodimanager.MainActivity;
import com.example.stacjonarny.kodimanager.R;
import com.example.stacjonarny.kodimanager.conections.SshConnect;


public class DodajOdcinekFragment extends Fragment {
    ListAdapter adapter;
    ListView list;
    Button buton;
    ProgressBar spinner;
    public DodajOdcinekFragment(){}




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myInflatedView = inflater.inflate(R.layout.fragment_dodaj_odcinek, container, false);
        list = (ListView) myInflatedView.findViewById(R.id.thelistviev);
        buton = (Button) myInflatedView.findViewById(R.id.buttoninfragment);
        /*spinner = (ProgressBar) myInflatedView.findViewById(R.id.progressBar1);*/

        registerForContextMenu(buton);
        registerForContextMenu(list);
        return myInflatedView;
    }
      @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
            MenuInflater menuinflater = getActivity().getMenuInflater();
            menuinflater.inflate(R.menu.context_menu2, menu);
        }
   /* @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.add_new_episode:
                Toast.makeText(getActivity(), "ad new episode: "+item.toString(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.add_sub:
                Toast.makeText(getActivity(), "ad new sub"+item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.rename_sub:
                Toast.makeText(getActivity(), "rename sun", Toast.LENGTH_SHORT).show();
                //
                spinner=(ProgressBar)getActivity().findViewById(R.id.progressBar1);
                spinner.setVisibility(View.VISIBLE);
                new SshConnect(spinner).execute();
                return true;

            default:
                return super.onContextItemSelected(item);


        }

    }*/
}



