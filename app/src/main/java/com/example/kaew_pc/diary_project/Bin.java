package com.example.kaew_pc.diary_project;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.kaew_pc.diary_project.Database.DBHelper;
import com.example.kaew_pc.diary_project.Database.Note_data;
import com.example.kaew_pc.diary_project.NoteManagement.NoteCustomAdapter;
import com.example.kaew_pc.diary_project.NoteManagement.NoteMainPage;
import com.example.kaew_pc.diary_project.Repository.BinRepo;

import java.util.ArrayList;

/**
 * Created by KAEW-PC on 23-Apr-17.
 */

public class Bin extends AppCompatActivity {
    private DBHelper db;
    private BinRepo repo;
    private ListView list;
    private boolean isResume = false;
    private ArrayList<Note_data> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bin);
        init();
        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);
        action.setHomeButtonEnabled(true);
        loadBinList();
    }

    private void loadBinList() {
        data = repo.getData(db.getReadableDatabase());

        final NoteCustomAdapter adapter = new NoteCustomAdapter(Bin.this, data);

        list.setAdapter(adapter);
    }

    private void init() {
        db = DBHelper.getInstance(this);
        repo = new BinRepo();
        list = (ListView) findViewById(R.id.listview);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
