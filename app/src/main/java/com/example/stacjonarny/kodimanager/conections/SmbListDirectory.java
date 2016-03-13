package com.example.stacjonarny.kodimanager.conections;


import android.os.AsyncTask;
import android.widget.TextView;

import com.example.stacjonarny.kodimanager.MainActivity;

import java.net.MalformedURLException;
import java.util.ArrayList;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

public class SmbListDirectory extends AsyncTask<Void,ArrayList,ArrayList>{
    ArrayList<String> list_folder = new ArrayList<String>();
    TextView text;

    public SmbListDirectory(TextView text,ArrayList<String> lista) {
        this.list_folder = lista;
        this.text = text;
    }

    @Override
    protected ArrayList doInBackground(Void... params) {
        final String USER_NAME2 = "";
        final String PASSWORD2 = "";
        //e.g. Assuming your network folder is: \my.myserver.netsharedpublicphotos
        final String NETWORK_FOLDER2 = "smb://192.168.1.10/";
        String user = USER_NAME2 + ":" + PASSWORD2;
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(user);
        ArrayList<String> temp_list_folder = new ArrayList<String>();
        try {
            String path = NETWORK_FOLDER2 + "FreeAgent Drive/SERIALE/berzace/";
            SmbFile sFile = new SmbFile(path, auth);
            //System.out.println(sFile.getPath());

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
            //list_folder = result;
            String lista="";
            for ( Object folder : result){
                lista = lista.concat(folder.toString()+"\n");
            }
            text.setText(lista);
        }
    }

