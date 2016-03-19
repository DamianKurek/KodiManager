package com.example.stacjonarny.kodimanager.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.stacjonarny.kodimanager.MainActivity;
import com.example.stacjonarny.kodimanager.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myInflatedView = inflater.inflate(R.layout.fragment_setting, container, false);
        EditText x = (EditText)myInflatedView.findViewById(R.id.setting_username_samba);
        x.setText(MainActivity.USSERNAME_SMB);

        x = (EditText)myInflatedView.findViewById(R.id.setting_password_samba);
        x.setText(MainActivity.PASSWORD_SMB);

        x = (EditText)myInflatedView.findViewById(R.id.setting_host_samba);
        x.setText(MainActivity.SAMBA_IP);

        x = (EditText)myInflatedView.findViewById(R.id.setting_srart_path_samba);
        x.setText(MainActivity.SAMBA_ROOT_DIRECTORY);

        x = (EditText)myInflatedView.findViewById(R.id.setting_username_ssh);
        x.setText(MainActivity.USSERNAME_SSH);

        x = (EditText)myInflatedView.findViewById(R.id.setting_password_ssh);
        x.setText(MainActivity.PASSWORD_SSH);

        x = (EditText)myInflatedView.findViewById(R.id.setting_ip_ssh);
        x.setText(MainActivity.HOST_SSH);

        x = (EditText)myInflatedView.findViewById(R.id.setting_port_ssh);
        x.setText(String.valueOf(MainActivity.PORT_SSH));

        x = (EditText)myInflatedView.findViewById(R.id.setting_directory_torrent_ssh);
        x.setText(MainActivity.PATCH_TORRENT_FOLDER);

        x = (EditText)myInflatedView.findViewById(R.id.setting_root_directory_torrent_ssh);
        x.setText(MainActivity.PATCH_DOWNLOAD_TORRENT_SERIAL);

        x = (EditText)myInflatedView.findViewById(R.id.setting_root_directory_film_torrent_ssh);
        x.setText(MainActivity.PATCH_DOWNLOAD_TORRENT_FILM);



        return myInflatedView;
    }


}
