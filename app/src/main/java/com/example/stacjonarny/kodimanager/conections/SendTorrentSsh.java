package com.example.stacjonarny.kodimanager.conections;

import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.example.stacjonarny.kodimanager.MainActivity;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * Created by stacjonarny on 14.03.2016.
 */
public class SendTorrentSsh extends AsyncTask<Void,Integer,Integer>{

    public String dowloadn_dir;
    public ProgressBar progres;
    public File torrent;
    public MainActivity mainactivity;

    public SendTorrentSsh(MainActivity activity,ProgressBar progres,File f,String patch) {
        this.progres = progres;
        this.torrent = f;
        this.dowloadn_dir=patch;
        this.mainactivity = activity;
    }
    @Override
    protected Integer doInBackground(Void... params) {
        try{
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
            ChannelSftp channelSftp = (ChannelSftp)channel;
            channelSftp.cd(MainActivity.PATCH_TORRENT_FOLDER);
            channelSftp.put(new FileInputStream(torrent), torrent.getName());


            return 1;
        } catch (JSchException e){
            return 0;
        } catch (SftpException e) {
            e.printStackTrace();
            return 0;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }
    @Override
    protected void onPostExecute(Integer result)
    {
        if(result==1){
            new AddTorrentSshFile(mainactivity,progres,dowloadn_dir,torrent.getName()).execute();
        }
    }
}
