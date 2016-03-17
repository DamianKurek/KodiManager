package com.example.stacjonarny.kodimanager.conections;


import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.stacjonarny.kodimanager.MainActivity;

import java.net.MalformedURLException;
import java.util.ArrayList;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

public class SmbListDirectory extends AsyncTask<ArrayList<Object>,ArrayList<Object>,ArrayList<Object>>{
    ArrayList<String> list_folder = new ArrayList<String>();
    TextView text;
    public ProgressBar progres;
    MainActivity mainactivity;
    ListView listviev;

    public SmbListDirectory(ArrayList<String> lista,ProgressBar progres) {
        this.list_folder = new ArrayList<>(lista);

        this.progres = progres;
    }

    public SmbListDirectory(MainActivity main,ListView l,ArrayList<String> lista,ProgressBar progres) {
        this.list_folder = new ArrayList<>(lista);
        this.progres = progres;
        this.mainactivity = main;
        this.listviev = l;
    }

    @Override
    protected ArrayList doInBackground(ArrayList... params) {

        String user = MainActivity.USSERNAME_SMB + ":" + MainActivity.PASSWORD_SMB;
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(user);
        ArrayList<String> temp_list_folder = new ArrayList<String>();
        try {
            String path = MainActivity.SAMBA_IP + MainActivity.SAMBA_ROOT_DIRECTORY;
            SmbFile sFile = new SmbFile(path, auth);

            SmbFile[] foldery = sFile.listFiles();
            for (SmbFile folder : foldery) {
                temp_list_folder.add(folder.getName());
            }

        } catch (MalformedURLException ex) {
            return null;
        } catch (SmbException ex) {

            return null;
        }
        return temp_list_folder;
    }



    protected void onPostExecute(ArrayList result)
        {
            list_folder = new ArrayList(result);
            if(!list_folder.isEmpty()) {
                progres.setVisibility(View.GONE);
            }
            listviev.setAdapter(new ArrayAdapter<String>(mainactivity,
                    android.R.layout.simple_expandable_list_item_1, list_folder));

        }
    }

