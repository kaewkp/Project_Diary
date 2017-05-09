package com.example.kaew_pc.diary_project.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kaew_pc.diary_project.Database.DBHelper;
import com.example.kaew_pc.diary_project.Database.Note_data;
import com.example.kaew_pc.diary_project.Manager.Adapter.NoteCustomAdapter;
import com.example.kaew_pc.diary_project.R;
import com.example.kaew_pc.diary_project.Manager.Repository.BinRepository;

import java.util.ArrayList;

/**
 * Created by KAEW-PC on 23-Apr-17.
 */

public class BinActivity extends AppCompatActivity {
    private DBHelper db;
    private BinRepository repo;
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

        final NoteCustomAdapter adapter = new NoteCustomAdapter(BinActivity.this, data);

        list.setAdapter(adapter);
        registerForContextMenu(list);
    }

    private void init() {
        db = DBHelper.getInstance(this);
        repo = new BinRepository();
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.listview) {
            menu.setHeaderTitle("Choose");
            String[] menuItems = new String[]{ "Restore", "Delete"};
//                    getResources().getStringArray(R.array.menu);
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = new String[]{ "Restore", "Delete"};
        String menuItemName = menuItems[menuItemIndex];
        Toast.makeText(getApplicationContext(), menuItemName + " : " + data.get(info.position).getNote_title(),
                Toast.LENGTH_SHORT).show();
        onSelectChoice(menuItemName, data.get(info.position).getNote_id());
        loadBinList();
        return true;
    }

    private void onSelectChoice(String selected, int id) {
        if(selected.equals("Delete")){
            repo.deleteData(db.getWritableDatabase(), id);
        }
        else{
            repo.restoreData(db.getWritableDatabase(), id);
        }
    }

}
