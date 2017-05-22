package com.example.kaew_pc.diary_project.Activity.Note;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kaew_pc.diary_project.Manager.Database.DBHelper;
import com.example.kaew_pc.diary_project.Manager.Database.Note_data;
import com.example.kaew_pc.diary_project.Manager.Adapter.NoteCustomAdapter;
import com.example.kaew_pc.diary_project.R;
import com.example.kaew_pc.diary_project.Manager.Repository.NoteDataRepository;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Ekachart-PC on 23/3/2560.
 */

public class NoteMainPageActivity extends AppCompatActivity {

    private TextView date;
    private DBHelper db;
    private NoteDataRepository repo;
    private ListView list;
    private FloatingActionButton fab, fab2;
    private boolean isResume = false;
    private ArrayList<Note_data> data;
    private NoteCustomAdapter adapter;
    private HashSet<Integer> del = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notemainpage);
        init();
        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);
        action.setHomeButtonEnabled(true);

        loadNoteList();

    }



    private void loadNoteList() {
        adapter = new NoteCustomAdapter(NoteMainPageActivity.this, data);
        data = repo.getData(db.getReadableDatabase());

        final NoteCustomAdapter adapter = new NoteCustomAdapter(NoteMainPageActivity.this, data);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(fab2.getVisibility() == View.GONE) {
                    isResume = true;
                    Intent intent = new Intent(getApplicationContext(), NoteCreatePageActivity.class);
                    intent.putExtra("id", data.get(position).getNote_id());
                    startActivity(intent);
                }
                else {
                    CheckBox cb = (CheckBox) view.findViewById(R.id.checkbox);
                    if (cb.isChecked()) {
                        cb.setChecked(false);
                        del.remove(data.get(position).getNote_id());
                    } else {
                        cb.setChecked(true);
                        del.add(data.get(position).getNote_id());
                    }
                }
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                if(fab2.getVisibility() == View.GONE) {
                    fab2.setVisibility(View.VISIBLE);
                    adapter.toggleCheckbox(true);
                }
                else {
                    fab2.setVisibility(View.GONE);
                    adapter.toggleCheckbox(false);
                    del.clear();
                }

                return true;
            }
        });
    }

    private void deleteDialog(){
        final AlertDialog.Builder exitDialog = new AlertDialog.Builder(this);
        exitDialog.setTitle("Confirm Delete");
        exitDialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        exitDialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                for ( int id : del ) {
                    repo.deleteData(db.getWritableDatabase(), id);
                }
                fab2.setVisibility(View.GONE);
                loadNoteList();
            }
        }).show();
    }

    private void init() {
        db = DBHelper.getInstance(this);
        repo = new NoteDataRepository();
        list = (ListView) findViewById(R.id.listview);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isResume = true;
                Intent intent = new Intent(getApplicationContext(), NoteCreatePageActivity.class);
                startActivity(intent);

//                For quick test only
//                Note_data d = new Note_data();
//                for(int i=0;i<15;i++){
//                    d.setNote_title(""+i);
//                    repo.insertData(db.getWritableDatabase(), d);
//                }
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Adapter", "Hash : "+ del);
                deleteDialog();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        if(isResume)
            loadNoteList();
        isResume = false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_note, menu);

        return true;
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

    @Override
    public void onBackPressed() {
        if(fab2.getVisibility() == View.GONE) {
            finish();
        }
        else {
            fab2.setVisibility(View.GONE);
            adapter.toggleCheckbox(false);
            del.clear();
        }
    }
}
