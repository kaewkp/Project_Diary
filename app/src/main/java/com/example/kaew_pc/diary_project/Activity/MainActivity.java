package com.example.kaew_pc.diary_project.Activity;

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

import java.io.FileNotFoundException;
import java.io.IOException;

import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.kaew_pc.diary_project.Activity.Note.NoteMainPageActivity;
import com.example.kaew_pc.diary_project.Activity.PasswordManagement.SettingPasswordActivity;
import com.example.kaew_pc.diary_project.Activity.Payment.PaymentMainPageActivity;
import com.example.kaew_pc.diary_project.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int REQUEST_GALLERY = 1;
    Bitmap bitmap;
    ImageView imageView1;
    ImageView imageView2;

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

        imageView1 = (ImageView)findViewById(R.id.imageView);

//        imageView2 = (ImageView) findViewById(R.id.imageView2);
//
//        Button buttonIntent = (Button)findViewById(R.id.picButton);
//        buttonIntent.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(Intent.createChooser(intent
//                        , "Select Picture"), REQUEST_GALLERY);
//            }
//        });
    }

    public void onActivityResult(int requestCode, int resultCode
            , Intent data) {
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = Media.getBitmap(this.getContentResolver(), uri);
                imageView1.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            exitDialog();
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
            intent = new Intent(getApplicationContext(), NoteMainPageActivity.class);
            startActivity(intent);
//            finish();

        } else if (id == R.id.nav_payment) {
            intent = new Intent(getApplicationContext(), PaymentMainPageActivity.class);
            startActivity(intent);
//            finish();

        } else if (id == R.id.nav_calendar) {
                Toast.makeText(getApplicationContext(), "Not Available",
                    Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_bin) {
            intent = new Intent(getApplicationContext(), BinActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_pin) {
            intent = new Intent(getApplicationContext(), SettingPasswordActivity.class);
            intent.putExtra("Setting", "Main");
            startActivity(intent);
        } else if (id == R.id.nav_setup) {
            Toast.makeText(getApplicationContext(), "Not Available",
                    Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}