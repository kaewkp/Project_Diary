package com.example.kaew_pc.diary_project.Manager.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.kaew_pc.diary_project.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrater on 25/5/2560.
 */

public class Calendar_showRowEventAdapter extends ArrayAdapter{
    private static final String TAG = Calendar_showRowEventAdapter.class.getSimpleName();
    private LayoutInflater inflater;
    private Context mContext;
    private String day;
    private List<String> timeList;
    private List<String> title;
    private List<String> desc;
    private ViewHolder viewHolder;

    public class ViewHolder {
        protected TextView title, desc, time, day;
        public ViewHolder(View v){
            time = (TextView) v.findViewById(R.id.tv_day_of_month);
            day = (TextView) v.findViewById(R.id.tv_day_of_week);
            title = (TextView) v.findViewById(R.id.tv_title);
            desc = (TextView) v.findViewById(R.id.tv_description);
        }
    }

    public Calendar_showRowEventAdapter(Context mContext
            , String day, List<String> timeList
            , List<String> title, List<String> desc){
        super(mContext, R.layout.calendar_row_event);
        Log.d(TAG,"ShowEvent");
        Log.d(TAG,day+"ShowEvent");
        this.day = day;
        this.mContext = mContext;
        this.timeList = timeList;
        this.title = title;
        this.desc = desc;
    }

    @Override
    public int getCount() {
        return title.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.calendar_row_event, parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SimpleDateFormat dayFormat = new SimpleDateFormat("dd-MMM", Locale.US);
        Date date = new Date();
        try {
            date = dayFormat.parse(day);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        holder.time.setText(timeList.get(position));
        holder.day.setText(dayFormat.format(date));
        holder.title.setText(title.get(position));
        holder.desc.setText(desc.get(position));
        return convertView;
    }
}
