package com.example.kaew_pc.diary_project.NoteManagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kaew_pc.diary_project.Database.DBHelper;
import com.example.kaew_pc.diary_project.Database.Note_data;
import com.example.kaew_pc.diary_project.R;
import com.example.kaew_pc.diary_project.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ekachart-PC on 23/3/2560.
 */

public class NoteMainPage extends AppCompatActivity {

    private TextView date;
    private DBHelper db;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notemainpage);
        init();
        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);
        action.setHomeButtonEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent;
                intent = new Intent(getApplicationContext(), NoteCreatePage.class);
                startActivity(intent);
                finish();
            }
        });

        loadNoteList();

//        Button cancel = (Button)findViewById(R.id.cancelButton);
//        Button save = (Button)findViewById(R.id.saveButton);
//        save.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), MainMenu.class);
//                startActivity(i);
//                finish();
//            }
//        });
//        cancel.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), MainMenu.class);
//                startActivity(i);
//                finish();
//            }
//        });
    }

    private void loadNoteList() {
        ArrayList<Note_data> data = db.getAllNote();

        NoteCustomAdapter adapter = new NoteCustomAdapter(NoteMainPage.this, data);
        list = (ListView) findViewById(R.id.listview);

        list.setAdapter(adapter);

//        Toast.makeText(getApplicationContext(), String.valueOf(data.size()),
//                        Toast.LENGTH_LONG).show();
    }

    private void init() {
//        date = (TextView) findViewById(R.id.showdate);

        Date time = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        String formattedDate = df.format(time);

        android.util.Log.i("Time Class ", " Time value in milliseconds "+time.getYear());
//        date.setText(formattedDate);

        db = DBHelper.getInstance(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        Intent intent;
        switch (item.getItemId()) {

            case android.R.id.home:
//                Toast.makeText(getApplicationContext(), "Here",
//                        Toast.LENGTH_LONG).show();
                intent = new Intent(getApplicationContext(), main.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }


    }
}
