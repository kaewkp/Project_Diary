package com.example.kaew_pc.diary_project.Activity.Note;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaew_pc.diary_project.Manager.Database.DBHelper;
import com.example.kaew_pc.diary_project.Manager.Database.Note_data;
import com.example.kaew_pc.diary_project.Manager.Adapter.NoteCustomAdapter;
import com.example.kaew_pc.diary_project.R;
import com.example.kaew_pc.diary_project.Manager.Repository.NoteDataRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

/**
 * Created by Ekachart-PC on 23/3/2560.
 */

public class NoteMainPageActivity extends AppCompatActivity implements SearchView.OnSuggestionListener{

    private TextView date;
    private DBHelper db;
    private NoteDataRepository repo;
    private ListView list;
    private FloatingActionButton fab, fab2;
    private boolean isResume = false;
    private ArrayList<Note_data> data;
    private NoteCustomAdapter adapter;
    private HashSet<Integer> del = new HashSet<>();
    private SearchView searchview;
    private SwipeRefreshLayout sw;
    private Integer sortID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notemainpage);
        init();
        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);
        action.setHomeButtonEnabled(true);
        loadNoteList(1);
    }

    private void loadNoteList(final Integer s) {
        Toast.makeText(NoteMainPageActivity.this, ""+s, Toast.LENGTH_SHORT).show();
        data = repo.getData(db.getReadableDatabase());
        adapter = new NoteCustomAdapter(NoteMainPageActivity.this, data);

        try {
            if (data.size() > 0) {
                adapter.sort(new Comparator<Note_data>() {
                    @Override
                    public int compare(Note_data d1, Note_data d2) {
                        if(s == 0) {
                            return d2.getNote_savedate().compareTo(d1.getNote_savedate());
                        } else if(s == 2) {
                            return d2.getNote_alertdate().compareTo(d1.getNote_alertdate());
                        } else {
                            return d2.getNote_editdate().compareTo(d1.getNote_editdate());
                        }
                    }
                });
            }
        }
        catch (Exception ex){
            Toast.makeText(NoteMainPageActivity.this, "Error Naja", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }

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
                loadNoteList(sortID);
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

        sw = (SwipeRefreshLayout) findViewById(R.id.srl);
        sw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new refresh_data().execute();
            }
        });
    }

    private class refresh_data extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            loadNoteList(1);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (sw.isRefreshing()) {
                sw.setRefreshing(false);
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if(isResume)
            loadNoteList(1);
        isResume = false;
    }

    @Override
    public boolean onSuggestionSelect(int position) {
        return true;
    }

    @Override
    public boolean onSuggestionClick(int position) {
        Toast.makeText(NoteMainPageActivity.this, ""+position, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_note, menu);

        return true;
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        super.onPrepareOptionsMenu(menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
//
//        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(NoteMainPageActivity.this, query, Toast.LENGTH_SHORT).show();
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                Toast.makeText(NoteMainPageActivity.this, newText, Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//        return true;
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.sortNew:
                sortID = 0;
                loadNoteList(0);
                return true;
            case R.id.sortEdit:
                sortID = 1;
                loadNoteList(1);
                return true;
//            case R.id.sortAlert:
//                sortID = 2;
//                loadNoteList(2);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    private void sortDialog() {
//        final String[] items = this.getResources().getStringArray(R.array.sort);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Make your selection");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int item) {
//                sortID = item;
//                loadNoteList(item);
//            }
//        });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }

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
