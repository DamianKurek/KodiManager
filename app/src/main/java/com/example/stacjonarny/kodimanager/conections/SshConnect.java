package com.example.stacjonarny.kodimanager.conections;


import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.stacjonarny.kodimanager.MainActivity;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.Properties;

public class SshConnect extends AsyncTask <Void,Integer,Integer>{
    public MainActivity activity;
    public ProgressBar progres;

    public SshConnect(MainActivity activity,ProgressBar bar) {
        this.activity = activity;
        this.progres = bar;
    }

    public SshConnect(ProgressBar progres) {
        this.progres = progres;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        try {

            String username = "root";
            String password = "openelec";
            String hostname = "192.168.1.10";


            JSch jsch = new JSch();
            Session session = jsch.getSession(username, hostname, 22);
            session.setPassword(password);

            // Avoid asking for key confirmation
            Properties prop = new Properties();
            prop.put("StrictHostKeyChecking", "no");
            session.setConfig(prop);

            session.connect();

            // SSH Channel
            ChannelExec channelssh = (ChannelExec)
                    session.openChannel("exec");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            channelssh.setOutputStream(baos);

            // Execute command
            channelssh.setCommand("touch testwwww");
            channelssh.connect();
            channelssh.disconnect();


        }
        catch (JSchException e){
            return 0;
        }
        //return baos.toString();

        return 1;
    }
    protected void onPostExecute(Integer result)
    {
        if(result==1){
            progres.setVisibility(View.GONE);
        }

    }


}
