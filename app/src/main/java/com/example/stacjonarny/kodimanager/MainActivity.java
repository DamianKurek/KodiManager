package com.example.stacjonarny.kodimanager;




import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.stacjonarny.kodimanager.conections.SmbListDirectory;
import com.example.stacjonarny.kodimanager.conections.SshConnect;
import com.example.stacjonarny.kodimanager.fragments.DodajOdcinekFragment;
import com.example.stacjonarny.kodimanager.fragments.MainFragment;
import com.example.stacjonarny.kodimanager.fragments.SettingFragment;
import com.jcraft.jsch.JSchException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar = null;
    NavigationView navigationView = null;
    public TextView logi;
    private ProgressBar spinner;

    public SshConnect connection;
    ArrayList<String> list_folder = new ArrayList<String>();

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

      /*  String[] tvshow={"breaking bad","dexter","TVD"};
        ListAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,tvshow);
        SettingFragment fragment = (SettingFragment) getFragmentManager().findFragmentById(R.id.fragment_conteiner);
        ListView listviev = (ListView)fragment.getView().findViewById(R.id.thelistviev);
       // ListView listviev = (ListView) findViewById(R.id.thelistviev);
        listviev.setAdapter(adapter);*/



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
            /*Bundle args = new Bundle();

            fragment.setArguments(args);*/

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_conteiner, fragment)
                    .commit();
        } else if (id == R.id.menu_glowne) {

            Fragment fragment = new MainFragment();
            /*Bundle args = new Bundle();

            fragment.setArguments(args);*/

            // Insert the fragment by replacing any existing fragment
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
        }  else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public  void PolaczSSH(View view) throws JSchException {
        SettingFragment fragment = (SettingFragment) getFragmentManager().findFragmentById(R.id.fragment_conteiner);
        logi = (TextView)fragment.getView().findViewById(R.id.logi);
        //spinner = (ProgressBar)fragment.getView().findViewById(R.id.progressBar1);
        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        logi.setText("łączenie");
        spinner.setVisibility(View.VISIBLE);

        connection = new SshConnect(this,spinner);
        connection.execute();




    }

    public void TestujPolaczenie(View view) {
        AsyncTask.Status status =connection.getStatus();
        if(status == AsyncTask.Status.FINISHED){
            logi.setText("finished");
            spinner.setVisibility(View.GONE);
        }

        if(status == AsyncTask.Status.PENDING){
            logi.setText("pending");
        }
        if(status == AsyncTask.Status.RUNNING){
            logi.setText("runing");
        }
    }

    public void Refresh(View view) {
        String[] tvshow={"breaking bad","dexter","TVD"};
        final ListAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,tvshow);
        DodajOdcinekFragment fragment = (DodajOdcinekFragment) getFragmentManager().findFragmentById(R.id.fragment_conteiner);
        ListView listviev = (ListView)fragment.getView().findViewById(R.id.thelistviev);
        listviev.setAdapter(adapter);

    }

    public void ListaFolder(View view) {

        MainFragment fragment = (MainFragment) getFragmentManager().findFragmentById(R.id.fragment_conteiner);
        TextView maintext = (TextView)fragment.getView().findViewById(R.id.main_text);
        new SmbListDirectory(maintext,list_folder).execute();

    }
}
