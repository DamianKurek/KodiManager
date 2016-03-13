package com.example.stacjonarny.kodimanager.conections;

import android.os.AsyncTask;

import java.net.MalformedURLException;
import java.util.ArrayList;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

public class ConnectSMB extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] params) {
        final String USER_NAME2 = "";
        final String PASSWORD2 = "";
        //e.g. Assuming your network folder is: \my.myserver.netsharedpublicphotos
        final String NETWORK_FOLDER2 = "smb://192.168.1.10/";

        ArrayList<String> list_folder = new ArrayList<String>();
        String user = USER_NAME2 + ":" + PASSWORD2;
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(user);
        try {
            String path = NETWORK_FOLDER2 + "FreeAgent Drive/SERIALE/berzace/";
            SmbFile sFile = new SmbFile(path, auth);
            //System.out.println(sFile.getPath());

            SmbFile[] foldery = sFile.listFiles();
            //System.out.println(foldery.length);
            String path2;
            for (SmbFile folder : foldery) {
                //podfolder
                path2 = path + folder.getName();

                sFile = new SmbFile(path2, auth);
                SmbFile[] podfoldery = sFile.listFiles();
                for (SmbFile folder2 : podfoldery) {
                    list_folder.add(folder2.getPath());
                }

            }
            for (String biezacy : list_folder) {
                path2 = biezacy;
                sFile = new SmbFile(path2, auth);
                SmbFile[] podfoldery11 = sFile.listFiles();
                SmbFile docelowy = null;
                SmbFile baza = null;
                int test = 0;
                for (SmbFile folder : podfoldery11) {
                    String rozszerzenie = folder.getName();
                    if (rozszerzenie.endsWith(".srt")) {
                        test += 1;
                        docelowy = new SmbFile(folder.getPath(), auth);
                        //System.out.println("DOCELOWY : " + docelowy);
                    }
                    if (rozszerzenie.endsWith(".mp4") || rozszerzenie.endsWith(".mkv")) {
                        baza = new SmbFile(folder.getPath().substring(0, folder.getPath().length() - 4).concat(".srt"), auth);
                        //System.out.println("BAZA : " + baza);
                        test += 1;
                    }
                    if (baza != null && baza.exists()) {
                        // System.out.println("nazwy się zgadzaja");

                        break;
                    } else if (test == 2) {
                        docelowy.renameTo(baza);
                        test = 0;
                    }
                }
            }

        } catch (MalformedURLException ex) {
            return false;
        } catch (SmbException ex) {

            return false;
        }
        return true;
    }

}
