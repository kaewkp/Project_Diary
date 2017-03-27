package com.example.kaew_pc.diary_project.NoteManagement;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

    public NoteCustomAdapter(Activity mContext,  ArrayList<Note_data> data) {
        super(mContext, R.layout.listview_note, data);
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater = mContext.getLayoutInflater();

        row = mInflater.inflate(R.layout.listview_note,null,true);

        TextView textView1 = (TextView)row.findViewById(R.id.title);
        textView1.setText(data.get(position).getNote_title());

        TextView textView2 = (TextView)row.findViewById(R.id.desc);
        textView2.setText(data.get(position).getNote_date());

        return row;
    }
}