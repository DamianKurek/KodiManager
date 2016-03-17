package com.example.stacjonarny.kodimanager.conections;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.stacjonarny.kodimanager.MainActivity;

import java.net.MalformedURLException;
import java.util.ArrayList;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

/**
 * Created by stacjonarny on 17.03.2016.
 */
public class SmbListEpisode extends AsyncTask<ArrayList<String>,ArrayList<String>,ArrayList<String>> {
    public MainActivity mainactivity;
    public String show_name;
    public String warunek;
    public ProgressBar spinner;
    public String path;

    public SmbListEpisode(MainActivity mainactivity, String show_name,ProgressBar s) {
        this.mainactivity = mainactivity;
        this.show_name = show_name;
        this.spinner = s;
    }

    @Override
    protected ArrayList doInBackground(ArrayList... params) {
        String user = MainActivity.USSERNAME_SMB + ":" + MainActivity.PASSWORD_SMB;
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(user);
        ArrayList<String> temp_list_folder = new ArrayList<String>();
        ArrayList<String> temp_list_episode = new ArrayList<String>();
        try {
            warunek="error";
            path = MainActivity.SAMBA_IP + MainActivity.SAMBA_ROOT_DIRECTORY;
            SmbFile sFile = new SmbFile(path, auth);

            SmbFile[] foldery = sFile.listFiles();
            for (SmbFile folder : foldery) {
                temp_list_folder.add(folder.getName());
            }
            for(String n : temp_list_folder){
                if(n.equals(show_name)) {
                    //warunek = "znalazłem : "+show_name;
                    path = MainActivity.SAMBA_IP + MainActivity.SAMBA_ROOT_DIRECTORY+show_name;
                    sFile = new SmbFile(path, auth);
                    SmbFile[] episody = sFile.listFiles();
                    for (SmbFile episod : episody) {
                        //lista odcinków
                        temp_list_episode.add(episod.getName());
                    }

                    //for(String odcinek_folder : temp_list_episode){
                     //   warunek = warunek+odcinek_folder+"\n";
                    //}
                }

            }


        } catch (MalformedURLException ex) {
            return null;
        } catch (SmbException ex) {

            return null;
        }
        return temp_list_episode;
    }
    protected void onPostExecute(ArrayList<String> result) {
        //Toast.makeText(mainactivity, path, Toast.LENGTH_LONG).show();
        //Toast.makeText(mainactivity, warunek, Toast.LENGTH_LONG).show();
        spinner.setVisibility(View.GONE);
        mainactivity.temp_list_episode.clear();
        for(String x : result){
            mainactivity.temp_list_episode.add(x);
        }
        mainactivity.temp_show_name = show_name;
        mainactivity.ShowDialogEpisode();

    }

}
