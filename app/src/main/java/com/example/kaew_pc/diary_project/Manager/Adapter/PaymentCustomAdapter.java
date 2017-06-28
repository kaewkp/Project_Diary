package com.example.kaew_pc.diary_project.Manager.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    private View row;

    public PaymentCustomAdapter(Activity mContext, ArrayList<Payment_data> data) {
        super(mContext, R.layout.listview_payment, data);
        this.mContext = mContext;
        this.data = data;
    }

    static class ViewHolder {
        protected TextView title;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (view == null) {
            LayoutInflater inflator = mContext.getLayoutInflater();
            view = inflator.inflate(R.layout.listview_payment, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.title);
//            viewHolder.date = (TextView) view.findViewById(R.id.date);

            view.setTag(viewHolder);
            view.setTag(R.id.title, viewHolder.title);
//            view.setTag(R.id.date, viewHolder.date);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.title.setText(data.get(position).getPayment_title());
//        viewHolder.date.setText(data.get(position).getPayment_date());

        return view;
    }

//            LayoutInflater mInflater = mContext.getLayoutInflater();
//
//            row = mInflater.inflate(R.layout.listview_payment, null, true);

//            TextView textView1 = (TextView) row.findViewById(R.id.title);
//            textView1.setText(data.get(position).getPayment_title());
//
//            TextView textView2 = (TextView) row.findViewById(R.id.desc);
//            textView2.setText(data.get(position).getPayment_date());
//
//            return row;
//        }
    }

