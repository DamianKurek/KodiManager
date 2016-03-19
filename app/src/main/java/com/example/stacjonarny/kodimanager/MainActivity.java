package com.example.stacjonarny.kodimanager;




import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stacjonarny.kodimanager.conections.AddTorrentSshFile;
import com.example.stacjonarny.kodimanager.conections.AddTorrentSshMagnetLink;
import com.example.stacjonarny.kodimanager.conections.SendSubtitlesSsh;
import com.example.stacjonarny.kodimanager.conections.SendTorrentSsh;
import com.example.stacjonarny.kodimanager.conections.SmbListDirectory;
import com.example.stacjonarny.kodimanager.conections.SmbListEpisode;
import com.example.stacjonarny.kodimanager.fragments.DodajOdcinekFragment;
import com.example.stacjonarny.kodimanager.fragments.MyDialogInfo;
import com.example.stacjonarny.kodimanager.fragments.SubFragment;
import com.example.stacjonarny.kodimanager.fragments.SettingFragment;
import com.jcraft.jsch.JSchException;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Ustawienia globalne
    //ssh
    public static String USSERNAME_SSH;
    public static String PASSWORD_SSH;
    public static String HOST_SSH;
    public static int PORT_SSH;
    public static String PATCH_TORRENT_FOLDER;
    public static String PATCH_DOWNLOAD_TORRENT_SERIAL;
    public static String PATCH_DOWNLOAD_TORRENT_FILM;
    //samba
    public static String USSERNAME_SMB;
    public static String PASSWORD_SMB;
    //patch = SAMBA_PATCH + ROOT_DIRECTORY
    public static String SAMBA_IP;
    public static String SAMBA_ROOT_DIRECTORY;
    //
    public String magnet_link_torrent;
    Toolbar toolbar = null;
    NavigationView navigationView = null;
    public TextView logi;
    private ProgressBar spinner;
    public String show_dir;
    public static String subtitles_target_dir;
    public static String temp_show_name;

    public AddTorrentSshFile connection;
    public ArrayList<String> list_folder = new ArrayList<String>();
    public static ArrayList<String> temp_list_episode = new ArrayList<String>();

    public void WczytajUstawienia() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        USSERNAME_SSH = getResources().getString(R.string.USSERNAME_SSH);
        PASSWORD_SSH = getResources().getString(R.string.PASSWORD_SSH);
        HOST_SSH = getResources().getString(R.string.HOST_SSH);
        PORT_SSH = Integer.parseInt(getResources().getString(R.string.PORT_SSH));
        PATCH_TORRENT_FOLDER = getResources().getString(R.string.PATCH_TORRENT_FOLDER);
        USSERNAME_SMB = getResources().getString(R.string.USSERNAME_SMB);
        PASSWORD_SMB = getResources().getString(R.string.PASSWORD_SMB);
        SAMBA_IP = getResources().getString(R.string.SAMBA_IP);
        SAMBA_ROOT_DIRECTORY = getResources().getString(R.string.SAMBA_ROOT_DIRECTORY);
        PATCH_DOWNLOAD_TORRENT_SERIAL = getResources().getString(R.string.PATCH_DOWNLOAD_TORRENT_SERIAL);
        PATCH_DOWNLOAD_TORRENT_FILM = getResources().getString(R.string.PATCH_DOWNLOAD_TORRENT_FILM);

    }

    void PrzywrocDomyslneUstawienia() {
        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.USSERNAME_SSH), "root");
        editor.putString(getString(R.string.PASSWORD_SSH), "openelec");
        editor.putString(getString(R.string.HOST_SSH), "192.168.1.10");
        editor.putInt(getString(R.string.PORT_SSH), 22);
        editor.putString(getString(R.string.PATCH_TORRENT_FOLDER), "/var/media/FreeAgent\\ Drive/torrenty/");
        editor.putString(getString(R.string.USSERNAME_SMB), "");
        editor.putString(getString(R.string.PASSWORD_SMB), "");
        editor.putString(getString(R.string.SAMBA_IP), "smb://192.168.1.10/");
        editor.putString(getString(R.string.SAMBA_ROOT_DIRECTORY), "FreeAgent Drive/SERIALE/berzace/");
        editor.putString(getString(R.string.PATCH_DOWNLOAD_TORRENT_SERIAL), "/var/media/FreeAgent\\ Drive/Seriale/berzace/");
        editor.putString(getString(R.string.PATCH_DOWNLOAD_TORRENT_FILM), "/var/media/FreeAgent\\ Drive/Filmy/");
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //progres
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        //

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //WCZYTANIE USTAWIEN
        WczytajUstawienia();
        ConnectivityManager CM = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = CM.getActiveNetworkInfo();
        if(info == null){
            Toast.makeText(MainActivity.this, "siec error", Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            ToastEror();
        }else if(info != null && !info.isConnected()){
            Toast.makeText(MainActivity.this, "siec error", Toast.LENGTH_LONG).show();
            startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
            ToastEror();
        }
            else if (info != null && info.isConnectedOrConnecting())
            Toast.makeText(MainActivity.this, "siec ok", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            ToastOK();
            ToastEror();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.ustawienia) {

            Fragment fragment = new SettingFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_conteiner, fragment)
                    .commit();
        } else if (id == R.id.menu_glowne) {

            Fragment fragment = new SubFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_conteiner, fragment)
                    .commit();

        } else if (id == R.id.dodaj_odcinek) {
            Fragment fragment = new DodajOdcinekFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_conteiner, fragment)
                    .commit();
        }else if (id == R.id.dodaj_film) {
            ChooseTorrentFilm();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void PolaczSSH(View view) throws JSchException {
        SettingFragment fragment = (SettingFragment) getFragmentManager().findFragmentById(R.id.fragment_conteiner);

        //spinner = (ProgressBar)fragment.getView().findViewById(R.id.progressBar1);
        spinner = (ProgressBar) findViewById(R.id.progressBar1);

        spinner.setVisibility(View.VISIBLE);

        // c//onnection = new AddTorrentSshFile(this,spinner);
        //c/onnection.execute();


    }

    public void ListaFolder(View view) {

        SubFragment fragment = (SubFragment) getFragmentManager().findFragmentById(R.id.fragment_conteiner);

        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        new SmbListDirectory(list_folder, spinner).execute();

    }

    //lista do napisów
    public void PobierzListe2(View viev) {
        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);
        SubFragment fragment = (SubFragment) getFragmentManager().findFragmentById(R.id.fragment_conteiner);
        ListView listviev = (ListView) fragment.getView().findViewById(R.id.thelistviev2);
        new SmbListDirectory(this, listviev, list_folder, spinner).execute();
        listviev.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final String selected = String.valueOf(parent.getItemAtPosition(position));
                        //Toast.makeText(MainActivity.this, selected, Toast.LENGTH_SHORT).show();
                        final String[] items = {"Dodaj napisy", "Popraw napisy"};

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Akcja");
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                if (items[item].equals("Dodaj napisy")) {
                                    show_dir = selected;
                                    spinner = (ProgressBar) findViewById(R.id.progressBar1);
                                    spinner.setVisibility(View.VISIBLE);
                                    new SmbListEpisode(MainActivity.this, show_dir, spinner).execute();
                                    //
                                    //tes();

                                }

                                //url
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
        );

    }

    public void ShowDialogEpisode() {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
        builder2.setTitle("Akcja2");
        final CharSequence[] items2 = temp_list_episode.toArray(new CharSequence[temp_list_episode.size()]);
        builder2.setItems(items2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                subtitles_target_dir = PATCH_DOWNLOAD_TORRENT_SERIAL + temp_show_name + String.valueOf(items2[which]);
                Toast.makeText(MainActivity.this, subtitles_target_dir, Toast.LENGTH_LONG).show();
                //wybór plików z napisami
                ChooseSub();
                //
            }
        });
        AlertDialog alert = builder2.create();
        alert.show();
    }

    //lista do seriali
    public void PobierzListe(View viev) {
        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);
        DodajOdcinekFragment fragment = (DodajOdcinekFragment) getFragmentManager().findFragmentById(R.id.fragment_conteiner);
        ListView listviev = (ListView) fragment.getView().findViewById(R.id.thelistviev);
        new SmbListDirectory(this, listviev, list_folder, spinner).execute();
        listviev.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final String selected = String.valueOf(parent.getItemAtPosition(position));
                        //Toast.makeText(MainActivity.this, selected, Toast.LENGTH_SHORT).show();
                        final String[] items = {"Dodaj odcinek (plik)", "Dodaj odcinek (url)", "Popraw napisy"};

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Akcja");
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                if (items[item].equals("Dodaj odcinek (plik)")) {
                                    Toast.makeText(getApplicationContext(), selected + ":" + items[item], Toast.LENGTH_SHORT).show();
                                    show_dir = selected;
                                    ChooseTorrentSerial();
                                }
                                if (items[item].equals("Dodaj odcinek (url)")) {
                                    final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                    final EditText input = new EditText(MainActivity.this);
                                    alert.setView(input);
                                    ClipboardManager myClipboard;
                                    myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                                    ClipData abc = myClipboard.getPrimaryClip();
                                    ClipData.Item schowek = abc.getItemAt(0);
                                    String text = schowek.getText().toString();
                                    input.setText(text);
                                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            magnet_link_torrent = input.getText().toString().trim();
                                            Toast.makeText(getApplicationContext(), magnet_link_torrent,
                                                    Toast.LENGTH_SHORT).show();
                                            //dodawanie
                                            spinner = (ProgressBar) findViewById(R.id.progressBar1);
                                            spinner.setVisibility(View.VISIBLE);
                                            String dowload_patch;
                                            show_dir = selected;
                                            dowload_patch = PATCH_DOWNLOAD_TORRENT_SERIAL + show_dir;

                                            new AddTorrentSshMagnetLink(MainActivity.this, spinner, dowload_patch, magnet_link_torrent).execute();
                                        }
                                    });

                                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            dialog.cancel();
                                        }
                                    });
                                    alert.show();
                                }
                                //url
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
        );

    }

    void ChooseTorrentSerial() {
        Intent intent = new Intent(MainActivity.this, FilePickerActivity.class);
        intent.putExtra(FilePickerActivity.ARG_FILE_FILTER, Pattern.compile(".*\\.torrent"));
        //intent.putExtra(FilePickerActivity.ARG_FILE_FILTER, Pattern.compile(".*"));
        intent.putExtra(FilePickerActivity.ARG_START_PATH, "/storage/emulated/0/Download");
        intent.putExtra(FilePickerActivity.ARG_DIRECTORIES_FILTER, false);
        //intent.putExtra(FilePickerActivity.ARG_SHOW_HIDDEN, true);
        startActivityForResult(intent, 1);
    }
    void ChooseTorrentFilm() {
        Intent intent = new Intent(MainActivity.this, FilePickerActivity.class);
        intent.putExtra(FilePickerActivity.ARG_FILE_FILTER, Pattern.compile(".*\\.torrent"));
        //intent.putExtra(FilePickerActivity.ARG_FILE_FILTER, Pattern.compile(".*"));
        intent.putExtra(FilePickerActivity.ARG_START_PATH, "/storage/emulated/0/Download");
        intent.putExtra(FilePickerActivity.ARG_DIRECTORIES_FILTER, false);
        //intent.putExtra(FilePickerActivity.ARG_SHOW_HIDDEN, true);
        startActivityForResult(intent, 3);
    }

    void ChooseSub() {
        Intent intent = new Intent(MainActivity.this, FilePickerActivity.class);
        intent.putExtra(FilePickerActivity.ARG_FILE_FILTER, Pattern.compile(".*\\.*"));
        //intent.putExtra(FilePickerActivity.ARG_FILE_FILTER, Pattern.compile(".*"));
        intent.putExtra(FilePickerActivity.ARG_START_PATH, "/storage/emulated/0/Download");
        intent.putExtra(FilePickerActivity.ARG_DIRECTORIES_FILTER, false);
        //intent.putExtra(FilePickerActivity.ARG_SHOW_HIDDEN, true);
        startActivityForResult(intent, 2);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //torrent serial
        if (requestCode == 1 && data != null) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            ///if(!filePath.isEmpty()) {
            Toast.makeText(MainActivity.this, filePath, Toast.LENGTH_SHORT).show();
            File torrent = new File(filePath);
            spinner = (ProgressBar) findViewById(R.id.progressBar1);
            spinner.setVisibility(View.VISIBLE);
            String dowload_patch;
            dowload_patch = PATCH_DOWNLOAD_TORRENT_SERIAL + show_dir;
            new SendTorrentSsh(MainActivity.this, spinner, torrent, dowload_patch).execute();
            //}
        }
        //napisy
        if (requestCode == 2 && data != null) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            // Toast.makeText(MainActivity.this, subtitles_target_dir, Toast.LENGTH_SHORT).show();
            //Toast.makeText(MainActivity.this, subtitles_target_dir, Toast.LENGTH_LONG).show();
            //gdzie - //send_dir = 192.168.1.1.0../berzace+subtitles_target_dir
            //co - filepath
            spinner = (ProgressBar) findViewById(R.id.progressBar1);
            spinner.setVisibility(View.VISIBLE);
            File napisy = new File(filePath);
            new SendSubtitlesSsh(subtitles_target_dir,
                    spinner, napisy, this).execute();
            //subtitles_target_dir="";

        }
        //film
        if (requestCode == 3 && data != null) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            ///if(!filePath.isEmpty()) {
            Toast.makeText(MainActivity.this, filePath, Toast.LENGTH_SHORT).show();
            File torrent = new File(filePath);
            spinner = (ProgressBar) findViewById(R.id.progressBar1);
            spinner.setVisibility(View.VISIBLE);
            new SendTorrentSsh(MainActivity.this, spinner, torrent, PATCH_DOWNLOAD_TORRENT_FILM).execute();
            //}

        }
    }

    public void Toasto(String s) {
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
    }

    public void Dialogo(final String s) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        final EditText input = new EditText(MainActivity.this);
        alert.setView(input);
        ClipboardManager myClipboard;
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData abc = myClipboard.getPrimaryClip();
        ClipData.Item schowek = abc.getItemAt(0);
        String text = schowek.getText().toString();
        input.setText(s);
   /* alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            magnet_link_torrent = input.getText().toString().trim();
            Toast.makeText(getApplicationContext(), s,
                    Toast.LENGTH_SHORT).show();

        }
    });*/

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alert.show();

    }


    public void ToastOK(){
        Context context = getApplicationContext();
        Toast toast = new Toast(context);
        ImageView view = new ImageView(context);
        view.setImageResource(R.drawable.ok);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, Gravity.CENTER, Gravity.CENTER);
        toast.show();
    }
    public void ToastEror(){
        Context context = getApplicationContext();
        Toast toast = new Toast(context);
        ImageView view = new ImageView(context);
        view.setImageResource(R.drawable.error);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER,Gravity.CENTER,Gravity.CENTER);
        toast.show();
    }
    public void DialogOKTEST(View viev) {
        final MyDialogInfo dialog = new MyDialogInfo();
        dialog.show(getFragmentManager(), "dialog");
    }

    public void CustomList(View view) {
        ArrayList<String> lista_tutyłow = new ArrayList<String>();
        ArrayList<String> lista_opisow = new ArrayList<String>();
        for(int loop=0;loop<30;loop++){
            lista_tutyłow.add("tutyl 1");
            lista_opisow.add("opis 1 ");
            lista_tutyłow.add("tutyl 2");
            lista_opisow.add("opis 2 ");
        }
        //listvievi
        SubFragment fragment = (SubFragment) getFragmentManager().findFragmentById(R.id.fragment_conteiner);
        ListView listviev = (ListView) fragment.getView().findViewById(R.id.thelistviev2);
        MyListAdapter adapter = new MyListAdapter(
                this,
                lista_tutyłow,
                lista_opisow
        );
        listviev.setAdapter(adapter);

    }
}
