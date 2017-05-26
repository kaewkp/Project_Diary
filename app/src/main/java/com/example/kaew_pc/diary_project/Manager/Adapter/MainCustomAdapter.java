package com.example.kaew_pc.diary_project.Manager.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kaew_pc.diary_project.Manager.Database.Note_data;
import com.example.kaew_pc.diary_project.Manager.Database.Payment_data;
import com.example.kaew_pc.diary_project.R;

import java.util.ArrayList;
/**
 * Created by chommchome on 17/5/2560.
 */

public class MainCustomAdapter extends ArrayAdapter<Payment_data> {

    private Activity mContext;
    private ArrayList<Payment_data> data;
    private ArrayList<Note_data> datanote;
    private View row;

    public MainCustomAdapter(Activity mContext, ArrayList<Payment_data> data) {
        super(mContext, R.layout.listview_main, data);
        this.mContext = mContext;
        this.data = data;
    }

    static class ViewHolder {
        protected TextView title, date;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        PaymentCustomAdapter.ViewHolder viewHolder = null;

        if (view == null) {
            LayoutInflater inflator = mContext.getLayoutInflater();
            view = inflator.inflate(R.layout.listview_main, null);
            viewHolder = new PaymentCustomAdapter.ViewHolder();

            viewHolder.title = (TextView) view.findViewById(R.id.title);

            viewHolder.date = (TextView) view.findViewById(R.id.date);


            view.setTag(viewHolder);
            view.setTag(R.id.title, viewHolder.title);
            view.setTag(R.id.date, viewHolder.date);



        } else {
            viewHolder = (PaymentCustomAdapter.ViewHolder) view.getTag();

        }

        viewHolder.title.setText(data.get(position).getPayment_title());
        viewHolder.date.setText(data.get(position).getPayment_date());
//        viewHolder1.title.setText(data.get(position).getNote_title());
//        viewHolder1.date.setText(data.get(position).getNote_date());

        return view;
    }

}
