package com.example.kaew_pc.diary_project.Manager.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.kaew_pc.diary_project.Manager.Database.Payment_data;
import com.example.kaew_pc.diary_project.R;

import java.util.ArrayList;

/**
 * Created by chommchome on 1/4/2560.
 */

public class PaymentCustomAdapter extends ArrayAdapter<Payment_data> {

    private Activity mContext;
    private ArrayList<Payment_data> data;
    private ArrayList<CheckBox> c = new ArrayList<>();

    public PaymentCustomAdapter(Activity mContext, ArrayList<Payment_data> data) {
        super(mContext, R.layout.listview_payment, data);
        this.mContext = mContext;
        this.data = data;
    }

    static class ViewHolder {
        protected TextView title, date;
        protected CheckBox checkbox;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        NoteCustomAdapter.ViewHolder viewHolder = null;
        if (view == null) {
            LayoutInflater inflator = mContext.getLayoutInflater();
            view = inflator.inflate(R.layout.listview_payment, null);
            viewHolder = new NoteCustomAdapter.ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.title);
            viewHolder.date = (TextView) view.findViewById(R.id.desc);
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
            view.setTag(R.id.desc, viewHolder.date);
            view.setTag(R.id.checkbox, viewHolder.checkbox);
        } else {
            viewHolder = (NoteCustomAdapter.ViewHolder) view.getTag();
        }
        viewHolder.checkbox.setTag(position); // This line is important.

        viewHolder.title.setText(data.get(position).getPayment_title());
        viewHolder.date.setText(data.get(position).getPayment_datePay());
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

