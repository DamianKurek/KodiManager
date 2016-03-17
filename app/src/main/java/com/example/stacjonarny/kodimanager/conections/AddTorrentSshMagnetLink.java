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

public class AddTorrentSshMagnetLink extends AsyncTask <Void,Integer,Integer>{

    public MainActivity activity;
    public ProgressBar progres;
    public String downalod_dir;
    public String magnet_link;

    public AddTorrentSshMagnetLink(ProgressBar progres, String download, String torrent) {
        this.downalod_dir=download;
        this.progres = progres;
        this.magnet_link = torrent;
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
            commander.println("transmission-remote --download-dir " + downalod_dir + " --add " + magnet_link +" >> wynik");
            in.reset();
            channel.disconnect();
            session.disconnect();
        }
        catch (JSchException e){
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }
    protected void onPostExecute(Integer result)
    {
        if(result==1){
            progres.setVisibility(View.GONE);
        }

    }


}
