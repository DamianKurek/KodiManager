package com.example.stacjonarny.kodimanager;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.lang.Object;import java.lang.Override;import java.lang.String;import java.net.MalformedURLException;
import java.util.ArrayList;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

public class SubRename extends AsyncTask {
    public ProgressBar progres;
    public String dir_to_rename_sub;
    MainActivity mainactivity;
    public String log;

    public SubRename(ProgressBar progres, String dir_to_rename_sub, MainActivity mainactivity) {
        this.progres = progres;
        this.dir_to_rename_sub = dir_to_rename_sub;
        this.mainactivity = mainactivity;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progres.setVisibility(View.GONE);
        mainactivity.Toasto(log);
    }

    @Override
    protected Integer doInBackground(Object[] params) {
        ArrayList<String> list_folder = new ArrayList<String>();
        String user = MainActivity.USSERNAME_SMB + ":" + MainActivity.PASSWORD_SMB;
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(user);
        try {
            String path = MainActivity.SAMBA_IP + MainActivity.SAMBA_ROOT_DIRECTORY;
            //SmbFile sFile = new SmbFile(path, auth);
            //System.out.println(sFile.getPath());
/*
            SmbFile[] seriale = sFile.listFiles();
            //System.out.println(seriale.length);
            String path2;
            for (SmbFile serial_episode_folder : seriale) {
                //podfolder
                path2 = path + serial_episode_folder.getName();

                sFile = new SmbFile(path2, auth);
                SmbFile[] podfoldery = sFile.listFiles();
                for (SmbFile folder2 : podfoldery) {
                    list_folder.add(folder2.getPath());
                }

            }*/
            dir_to_rename_sub = MainActivity.SAMBA_IP+dir_to_rename_sub.replace(
                    mainactivity.PATCH_DOWNLOAD_TORRENT,
                    mainactivity.SAMBA_ROOT_DIRECTORY);
            SmbFile sFile = new SmbFile(path, auth);
            SmbFile[] podfoldery = sFile.listFiles();
            list_folder.add(dir_to_rename_sub);
            for (String biezacy : list_folder) {
                String path2 = biezacy;
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
            log="ok";
            return 1;
        } catch (MalformedURLException ex) {
            log=ex.toString();
            return 0;
        } catch (SmbException ex) {
            log = ex.toString();
            return 0;
        }

    }

}
