package com.example.kaew_pc.diary_project.Manager.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.kaew_pc.diary_project.Manager.Database.Note_data;
import com.example.kaew_pc.diary_project.R;

import java.util.ArrayList;

/**
 * Created by Ekachart-PC on 23/3/2560.
 */

public class NoteCustomAdapter extends ArrayAdapter<Note_data> {

    private Activity mContext;
    private ArrayList<Note_data> data;
    private ArrayList<CheckBox> c = new ArrayList<>();

    public NoteCustomAdapter(Activity mContext,  ArrayList<Note_data> data) {
        super(mContext, R.layout.listview_note, data);
        this.mContext = mContext;
        this.data = data;
    }

    static class ViewHolder {
        protected TextView title, date;
        protected CheckBox checkbox;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {


        ViewHolder viewHolder = null;
        if (view == null) {
            LayoutInflater inflator = mContext.getLayoutInflater();
            view = inflator.inflate(R.layout.listview_note, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.title);
            viewHolder.date = (TextView) view.findViewById(R.id.date);
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.checkbox);
            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                    data.get(getPosition).setSelected(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
                }
            });
            view.setTag(viewHolder);
            view.setTag(R.id.title, viewHolder.title);
            view.setTag(R.id.date, viewHolder.date);
            view.setTag(R.id.checkbox, viewHolder.checkbox);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.checkbox.setTag(position); // This line is important.

        viewHolder.title.setText(data.get(position).getNote_title());
        viewHolder.date.setText(data.get(position).getNote_date());
        viewHolder.checkbox.setChecked(data.get(position).isSelected());

        c.add( viewHolder.checkbox );

        return view;
    }

    public void toggleCheckbox(boolean isChecked){
        if(isChecked){
            for(CheckBox cc : c){
                cc.setVisibility(View.VISIBLE);
            }
        }
        else{
            for(CheckBox cc : c){
                cc.setVisibility(View.GONE);
                cc.setChecked(false);
            }
        }
    }
}