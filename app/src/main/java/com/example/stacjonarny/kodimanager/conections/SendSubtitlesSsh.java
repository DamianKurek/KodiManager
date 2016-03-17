package com.example.stacjonarny.kodimanager.conections;


import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.stacjonarny.kodimanager.MainActivity;
import com.example.stacjonarny.kodimanager.SubRename;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class SendSubtitlesSsh extends AsyncTask<Void,Integer,Integer> {

    public String path_sub;
    public ProgressBar progres;
    public File napisy;
    public MainActivity mainactivity;
    public String error;

    public SendSubtitlesSsh(String path_sub, ProgressBar progres, File napisy, MainActivity mainactivity) {
        this.path_sub = path_sub;
        this.progres = progres;
        this.napisy = napisy;
        this.mainactivity = mainactivity;
    }

    //@Override
    protected void onPostExecute(Integer e) {
        super.onPostExecute(e);
        progres.setVisibility(View.GONE);
        mainactivity.Toasto(error);
        mainactivity.Toasto(napisy.getPath());
        progres.setVisibility(View.VISIBLE);
        mainactivity.Toasto("wysłałem ,Sub rename");
        new SubRename(progres,path_sub,mainactivity).execute();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(MainActivity.USSERNAME_SSH, MainActivity.HOST_SSH, MainActivity.PORT_SSH);
            session.setPassword(MainActivity.PASSWORD_SSH);
            // Avoid asking for key confirmation
            Properties prop = new Properties();
            prop.put("StrictHostKeyChecking", "no");
            session.setConfig(prop);
            session.connect();
            //sftp
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp channelSftp = (ChannelSftp) channel;
            channelSftp.cd(path_sub);
            //uznip
            ZipFile zipFile = new ZipFile(napisy.getPath());
            zipFile.extractAll("/storage/emulated/0/Download/temp_napisy");
           // while(plik.exists()) {
               // channelSftp.put(new FileInputStream(plik), plik.getName());
               // plik = zipFile.getFile();
           // }
            String path = "/storage/emulated/0/Download/temp_napisy";
            File f = new File(path);
            File file[] = f.listFiles();
            for(File plik : file){
                channelSftp.put(new FileInputStream(plik), plik.getName());
                plik.delete();
            }

            /////////


            //channelSftp.put(new FileInputStream(napisy), napisy.getName());


            return 0;
        } catch (JSchException e) {
            error = e.toString();
            return 1;
        } catch (SftpException e) {
            e.printStackTrace();
            error = e.toString();
            return 2;
        }catch (ZipException e) {
            e.printStackTrace();
            error = e.toString();
            return 3;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            error = e.toString();
            return 4;
        }

    }
}
