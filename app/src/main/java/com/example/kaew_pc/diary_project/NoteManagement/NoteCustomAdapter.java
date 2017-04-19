package com.example.kaew_pc.diary_project.NoteManagement;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaew_pc.diary_project.Database.Note_data;
import com.example.kaew_pc.diary_project.R;

import java.util.ArrayList;

import static android.R.attr.checked;

/**
 * Created by Ekachart-PC on 23/3/2560.
 */

public class NoteCustomAdapter extends ArrayAdapter<Note_data> {

    private Activity mContext;
    private ArrayList<Note_data> data;
    private ArrayList<View> row2 = new ArrayList<>();
    private ArrayList<CheckBox> c = new ArrayList<>();
    private ArrayList<Boolean> isCheck = new ArrayList<>();
    private Boolean isVisible = false;

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


//        ViewHolder viewHolder = null;
//        if (view == null) {
//            LayoutInflater inflator = mContext.getLayoutInflater();
//            view = inflator.inflate(R.layout.listview_note, null);
//            viewHolder = new ViewHolder();
//            viewHolder.title = (TextView) view.findViewById(R.id.title);
//            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.checkbox);
//            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
//                    data.get(getPosition).setSelected(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
//                    viewHolder.checkbox.setChecked(true);
//                }
//            });
//
//            convertView.setTag(viewHolder);
//            convertView.setTag(R.id.label, viewHolder.text);
//            convertView.setTag(R.id.check, viewHolder.checkbox);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        viewHolder.checkbox.setTag(position); // This line is important.
//
//        viewHolder.title.setText(data.get(position).getNote_title());
//        viewHolder.checkbox.setChecked();
//
//        return convertView;

        LayoutInflater mInflater = mContext.getLayoutInflater();

        View row = mInflater.inflate(R.layout.listview_note,null,true);
        CheckBox checkbox = (CheckBox)row.findViewById(R.id.checkbox);

        TextView textView1 = (TextView)row.findViewById(R.id.title);
        textView1.setText(data.get(position).getNote_title());

        TextView textView2 = (TextView)row.findViewById(R.id.date);

        String date = data.get(position).getNote_date();
        date = date.substring(0,date.lastIndexOf(" "));
        textView2.setText(date);

        row2.add(row);
        isCheck.add(false);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isCheck.get(position)) {
                    isCheck.add(false);
                    Log.e("Messi" , "");
                }
                else {
                    isCheck.add(true);
                    Log.e("CR7","");
                }
            }
        });
        
        checkbox.setChecked(isCheck.get(position) == null ? false : isCheck.get(position));
        c.add( checkbox );

        return row;
    }

    public void toggleCheckbox(int pos){
        int i = 0;
        for ( CheckBox cc : c) {
            if(cc.getVisibility() == View.GONE){
                cc.setVisibility(View.VISIBLE);
//                ((CheckBox) row2.get(pos).findViewById(R.id.checkbox)).setChecked(true);
                isVisible = true;
            }
            else{
                cc.setVisibility(View.GONE);
                cc.setChecked(false);
                isVisible = false;
            }
            c.set(i, cc);
            i++;
        }
    }

    public void toggleCheckbox2(int pos){
        int i = 0;
        for ( CheckBox cc : c) {
            if(isVisible){
                cc.setVisibility(View.VISIBLE);
//                ((CheckBox) row2.get(pos).findViewById(R.id.checkbox)).setChecked(true);
                cc.setChecked(isCheck.get(i) ? isCheck.get(i) : false);
            }
            else{
                cc.setVisibility(View.GONE);
                cc.setChecked(false);
            }
            i++;
        }
    }

    public ArrayList<Integer> isChecked(){
        ArrayList<Integer> list = new ArrayList<>();
        int i = 0;
        for ( CheckBox cc : c ) {
            if(cc.isChecked()){
                list.add(data.get(i).getNote_id());
            }
            i++;
        }
        return list;
    }
}