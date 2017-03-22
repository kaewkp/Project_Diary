package com.example.kaew_pc.diary_project.NoteManagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kaew_pc.diary_project.Database.DBHelper;
import com.example.kaew_pc.diary_project.Database.Note_data;
import com.example.kaew_pc.diary_project.R;
import com.example.kaew_pc.diary_project.main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ekachart-PC on 23/3/2560.
 */

public class NoteCreatePage extends AppCompatActivity {

    private TextView date;
    private DBHelper db;
    private String formattedDate;
    private EditText title, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notecreatepage);
        init();
        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);
        action.setHomeButtonEnabled(true);

        Button cancel = (Button)findViewById(R.id.cancelButton);
        Button save = (Button)findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveNote();

                Intent i = new Intent(getApplicationContext(), NoteMainPage.class);
                startActivity(i);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NoteMainPage.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void saveNote() {
        Note_data note = new Note_data();
        note.setNote_date(formattedDate);
        note.setNote_title(title.getText().toString());
        note.setNote_desc(desc.getText().toString());

        db.createNote(db.getWritableDatabase(), note);
    }

    private void init() {
        date = (TextView) findViewById(R.id.showdate);
        title = (EditText) findViewById(R.id.title);
        desc = (EditText) findViewById(R.id.desc);

        Date time = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        formattedDate = df.format(time);

        android.util.Log.i("Time Class ", " Time value in milliseconds "+time.getYear());
        date.setText(formattedDate);

        db = DBHelper.getInstance(this);
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
