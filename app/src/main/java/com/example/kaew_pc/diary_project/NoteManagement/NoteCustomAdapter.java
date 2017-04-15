package com.example.kaew_pc.diary_project.NoteManagement;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.kaew_pc.diary_project.Database.Note_data;
import com.example.kaew_pc.diary_project.R;

import java.util.ArrayList;

/**
 * Created by Ekachart-PC on 23/3/2560.
 */

public class NoteCustomAdapter extends ArrayAdapter<Note_data> {

    private Activity mContext;
    private ArrayList<Note_data> data;
    private View row;
    private ArrayList<View> row2 = new ArrayList<>();
    private ArrayList<CheckBox> c = new ArrayList<>();

    public NoteCustomAdapter(Activity mContext,  ArrayList<Note_data> data) {
        super(mContext, R.layout.listview_note, data);
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater = mContext.getLayoutInflater();

        row = mInflater.inflate(R.layout.listview_note,null,true);
        c.add( (CheckBox)row.findViewById(R.id.checkbox) );

        TextView textView1 = (TextView)row.findViewById(R.id.title);
        textView1.setText(data.get(position).getNote_title());

        TextView textView2 = (TextView)row.findViewById(R.id.date);

        String date = data.get(position).getNote_date();
        date = date.substring(0,date.lastIndexOf(" "));
        textView2.setText(date);

        row2.add(row);

        return row;
    }

    public void toggleCheckbox(int pos){
        for ( CheckBox cc : c ) {
            if(cc.getVisibility() == View.GONE){
                cc.setVisibility(View.VISIBLE);
                ((CheckBox) row2.get(pos).findViewById(R.id.checkbox)).setChecked(true);
            }
            else{
                cc.setVisibility(View.GONE);
                cc.setChecked(false);
            }
        }
    }
}