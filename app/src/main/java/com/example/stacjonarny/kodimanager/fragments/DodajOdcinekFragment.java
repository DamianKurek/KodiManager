package com.example.stacjonarny.kodimanager.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.stacjonarny.kodimanager.MainActivity;
import com.example.stacjonarny.kodimanager.R;


public class DodajOdcinekFragment extends Fragment {
    ListAdapter adapter;
    ListView list;
    Button buton;
    ProgressBar spinner;
    TextView wiadomosc;
    public DodajOdcinekFragment(){}




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myInflatedView = inflater.inflate(R.layout.fragment_dodaj_odcinek, container, false);
        list = (ListView) myInflatedView.findViewById(R.id.thelistviev);
        buton = (Button) myInflatedView.findViewById(R.id.buttoninfragment);


        //wiadomosc.setText("test");
        /*spinner = (ProgressBar) myInflatedView.findViewById(R.id.progressBar1);*/

        //registerForContextMenu(buton);
       // registerForContextMenu(list);
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
                Intent intent = new Intent(getActivity(), FilePickerActivity.class);
                intent.putExtra(FilePickerActivity.ARG_FILE_FILTER, Pattern.compile(".*\\.torrent"));
                //intent.putExtra(FilePickerActivity.ARG_FILE_FILTER, Pattern.compile(".*"));
                intent.putExtra(FilePickerActivity.ARG_START_PATH, "/storage/emulated/0/Download");

                intent.putExtra(FilePickerActivity.ARG_DIRECTORIES_FILTER, false);
                //intent.putExtra(FilePickerActivity.ARG_SHOW_HIDDEN, true);
                startActivityForResult(intent, 1);
                return true;
            case R.id.add_sub:
                Toast.makeText(getActivity(), "ad new sub"+item.getTitle(), Toast.LENGTH_SHORT).show();
                spinner=(ProgressBar)getActivity().findViewById(R.id.progressBar1);
                spinner.setVisibility(View.VISIBLE);
                //new SendTorrentSsh(spinner).execute();
                return true;
            case R.id.rename_sub:
                //Toast.makeText(getActivity(), item.toString(), Toast.LENGTH_SHORT).show();
                //
               // spinner=(ProgressBar)getActivity().findViewById(R.id.progressBar1);
               // spinner.setVisibility(View.VISIBLE);

                return true;

            default:
                return super.onContextItemSelected(item);


        }

    }*/
  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data !=null) {

            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            ///if(!filePath.isEmpty()) {
            Toast.makeText(getActivity(), filePath, Toast.LENGTH_SHORT).show();
            File torrent = new File(filePath);
            spinner=(ProgressBar)getActivity().findViewById(R.id.progressBar1);
            spinner.setVisibility(View.VISIBLE);
            new SendTorrentSsh(spinner,torrent).execute();
            //}
        }
    }*/
}



