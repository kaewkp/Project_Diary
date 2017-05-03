package com.example.kaew_pc.diary_project.NoteManagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kaew_pc.diary_project.Database.DBHelper;
import com.example.kaew_pc.diary_project.Database.Note_data;
import com.example.kaew_pc.diary_project.R;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Ekachart-PC on 23/3/2560.
 */

public class NoteMainPage extends AppCompatActivity {

    private TextView date;
    private DBHelper db;
    private ListView list;
    private FloatingActionButton fab, fab2;
    private boolean isResume = false;
    private ArrayList<Note_data> data;
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
        data = db.getAllNote();

        final NoteCustomAdapter adapter = new NoteCustomAdapter(NoteMainPage.this, data);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(fab2.getVisibility() == View.GONE) {
                    isResume = true;
                    Intent intent = new Intent(getApplicationContext(), NoteCreatePage.class);
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

        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int currentVisibleItemCount;
            private int currentScrollState;
            private int currentFirstVisibleItem;
            private int totalItem;

            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
//                Toast.makeText(NoteMainPage.this, "Messi", Toast.LENGTH_SHORT).show();
//                if(isScrollCompleted())
//                    adapter.toggleCheckbox(0);
//                adapter.toggleCheckbox2();
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
//                Toast.makeText(NoteMainPage.this, "CR7", Toast.LENGTH_LONG).show();
//                adapter.toggleCheckbox();
            }

            private boolean isScrollCompleted() {
                if (totalItem - currentFirstVisibleItem == currentVisibleItemCount
                        && this.currentScrollState == SCROLL_STATE_IDLE)
                    return true;
                else
                    return false;
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
                    db.deleteNote(db.getWritableDatabase(), id);
                }
                fab2.setVisibility(View.GONE);
                loadNoteList();
            }
        }).show();
    }

    private void init() {
        db = DBHelper.getInstance(this);
        list = (ListView) findViewById(R.id.listview);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isResume = true;
                Intent intent = new Intent(getApplicationContext(), NoteCreatePage.class);
                startActivity(intent);

            }
        });

        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
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
}
