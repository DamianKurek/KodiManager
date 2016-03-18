package com.example.stacjonarny.kodimanager.conections;


import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stacjonarny.kodimanager.MainActivity;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.Properties;

public class AddTorrentSshFile extends AsyncTask <Void,Integer,Integer>{
    public String log_error;
    public MainActivity mainactivity;
    public ProgressBar progres;
    public String downalod_dir;
    public String torrent_name;


    public AddTorrentSshFile(MainActivity activity,ProgressBar progres, String download, String torrent) {
        this.downalod_dir=download;
        this.progres = progres;
        this.torrent_name = torrent;
        this.mainactivity = activity;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        try {

            StringBuilder outputBuffer = new StringBuilder();
            JSch jsch = new JSch();
            Session session = jsch.getSession(MainActivity.USSERNAME_SSH, MainActivity.HOST_SSH, MainActivity.PORT_SSH);
            session.setPassword(MainActivity.PASSWORD_SSH);
            // Avoid asking for key confirmation
            Properties prop = new Properties();
            prop.put("StrictHostKeyChecking", "no");
            session.setConfig(prop);
            session.connect();
            ChannelShell channel = (ChannelShell) session.openChannel("shell");
            BufferedReader in=new BufferedReader(new InputStreamReader(channel.getInputStream()));
            OutputStream inputstream_for_the_channel = channel.getOutputStream();
            PrintStream commander = new PrintStream(inputstream_for_the_channel, true);
            channel.setOutputStream(System.out, true);
            channel.connect();
            commander.println("transmission-remote --download-dir "  + downalod_dir + " --add " + MainActivity.PATCH_TORRENT_FOLDER + torrent_name +" >> wynik");
            in.reset();
            channel.disconnect();
            session.disconnect();
            log_error="ok";
            return 1;
        }
        catch (JSchException e){
            log_error=e.toString();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            log_error="ok";
            return 1;
        }

    }
    protected void onPostExecute(Integer result)
    {
        mainactivity.Dialogo(log_error);
        if(result==1){
            progres.setVisibility(View.GONE);
        }

    }


}
