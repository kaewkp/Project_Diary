package com.example.kaew_pc.diary_project.NoteManagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kaew_pc.diary_project.Database.DBHelper;
import com.example.kaew_pc.diary_project.R;

/**
 * Created by KAEW-PC on 29-Mar-17.
 */

public class NoteShowData extends AppCompatActivity {
    private TextView title,desc;
    private DBHelper db;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notecreatepage);

        init();

        title.setText(intent.getStringExtra("title"));
        desc.setText(intent.getStringExtra("desc"));

        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);
        action.setHomeButtonEnabled(true);
    }

    private void init() {
        desc = (TextView) findViewById(R.id.desc);
        title = (TextView) findViewById(R.id.title);

        intent = getIntent();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), NoteMainPage.class);
        startActivity(intent);
        finish();
    }
}
