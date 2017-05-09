package com.example.kaew_pc.diary_project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.kaew_pc.diary_project.Database.DBHelper;
import com.example.kaew_pc.diary_project.R;

/**
 * Created by KAEW-PC on 29-Mar-17.
 */

public class NoteShowDataActivity extends AppCompatActivity {
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
        desc = (TextView) findViewById(R.id.date);
        title = (TextView) findViewById(R.id.title);

        intent = getIntent();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), NoteMainPageActivity.class);
        startActivity(intent);
        finish();
    }
}
