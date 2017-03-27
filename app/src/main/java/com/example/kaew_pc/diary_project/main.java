package com.example.kaew_pc.diary_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kaew_pc.diary_project.NoteManagement.NoteMainPage;
import com.example.kaew_pc.diary_project.PasswordManagement.SettingPassword;

public class main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            exitDialog();
//            super.onBackPressed();
        }
    }

    private void exitDialog(){
        final AlertDialog.Builder exitDialog = new AlertDialog.Builder(this);
        exitDialog.setTitle("Exit Application");
        exitDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        exitDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).show();
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
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent;// Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_note) {
            intent = new Intent(getApplicationContext(), NoteMainPage.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_payment) {
            Toast.makeText(getApplicationContext(), "Not Available",
                    Toast.LENGTH_LONG).show();
//            intent = new Intent(getApplicationContext(), PaymentShow.class);
//            startActivity(intent);
//            finish();

        } else if (id == R.id.nav_calendar) {
                Toast.makeText(getApplicationContext(), "Not Available",
                    Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_bin) {
            Toast.makeText(getApplicationContext(), "Not Available",
                    Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_pin) {
            intent = new Intent(getApplicationContext(), SettingPassword.class);
            intent.putExtra("Setting", true);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_setup) {
            Toast.makeText(getApplicationContext(), "Not Available",
                    Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
