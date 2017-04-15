package com.example.kaew_pc.diary_project.NoteManagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaew_pc.diary_project.Database.DBHelper;
import com.example.kaew_pc.diary_project.Database.Note_data;
import com.example.kaew_pc.diary_project.R;
import com.example.kaew_pc.diary_project.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.R.attr.data;

/**
 * Created by Ekachart-PC on 23/3/2560.
 */

public class NoteMainPage extends AppCompatActivity {

    private TextView date;
    private DBHelper db;
    private ListView list;
    private FloatingActionButton fab, fab2;
    private float historicX, historicY;
    static final int DELTA = 50;
    private boolean isResume = false;
    private ArrayList<Note_data> data;

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
                isResume = true;
                Intent intent = new Intent(getApplicationContext(), NoteCreatePage.class);
                intent.putExtra("id", data.get(position).getNote_id());
                startActivity(intent);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int pos, long id) {
//                Toast.makeText(NoteMainPage.this, "POS : " + pos, Toast.LENGTH_SHORT).show();
                adapter.toggleCheckbox(pos);
                fab2.setVisibility(View.VISIBLE);
                return true;
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDialog(adapter.isChecked());
            }
        });

//        registerForContextMenu(list);

//        list.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        historicX = event.getX();
//                        historicY = event.getY();
//                        break;
//
//                    case MotionEvent.ACTION_UP:
//                        if (event.getX() - historicX < -DELTA) {
//                            Toast.makeText(NoteMainPage.this, "X : ", Toast.LENGTH_SHORT).show();
//                            return true;
//                        }
//                        else if (event.getX() - historicX > DELTA) {
//                            Toast.makeText(NoteMainPage.this, "Y", Toast.LENGTH_SHORT).show();
//                            return true;
//                        }
//                        break;
//
//                    default:
//                        return false;
//                }
//                return false;
//            }
//        });
    }

    private void deleteDialog(final ArrayList<Integer> idList){
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
                for ( int id : idList ) {
                    db.deleteNote(db.getWritableDatabase(), id);
                }
                fab2.setVisibility(View.GONE);
                loadNoteList();
            }
        }).show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.listview) {
//            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
//            menu.setHeaderTitle(Countries[info.position]);
//            String[] menuItems = getResources().getStringArray(R.array.menu);
//            for (int i = 0; i<menuItems.length; i++) {
//                menu.add(Menu.NONE, i, i, menuItems[i]);
//            }
//            Toast.makeText(this, "Messi", Toast.LENGTH_SHORT).show();
        }
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
        getMenuInflater().inflate(R.menu.main, menu);
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
