package com.example.kaew_pc.diary_project.Manager.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.benzneststudios.eventCalendarView.fragment.CalendarFragment;
import com.example.kaew_pc.diary_project.Activity.Calendar.CalendarMainActivity;
import com.example.kaew_pc.diary_project.Manager.EventObjects;
import com.example.kaew_pc.diary_project.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrater on 25/5/2560.
 */

public class CalendarAdapter extends ArrayAdapter {
    private static final String TAG = CalendarAdapter.class.getSimpleName();
    private LayoutInflater inflater;
    private List<Date> monthlyDates;
    private Calendar currentDate;
    private List<EventObjects> allEvents;
    private Context mContext;
    private ViewHolder viewHolder;

    static class ViewHolder {
        protected ImageView highlight, small_highlight;
        protected TextView tv_day;
    }

    public CalendarAdapter(Context mContext, List<Date> monthlyDates, Calendar currentDate, List<EventObjects> allEvents){
        super(mContext, R.layout.calendar_layout_day);
        this.mContext = mContext;
        this.monthlyDates = monthlyDates;
        this.currentDate = currentDate;
        this.allEvents = allEvents;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Date mDate = monthlyDates.get(position);
        Calendar dateCal = Calendar.getInstance();
        viewHolder = new ViewHolder();
        dateCal.setTime(mDate);

        int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCal.get(Calendar.MONTH) + 1;
        int displayYear = dateCal.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);

        if (view == null) {
            view = inflater.inflate(R.layout.calendar_layout_day, parent, false);
        }

        viewHolder.highlight = (ImageView) view.findViewById(R.id.img_highlight);

        viewHolder.small_highlight = (ImageView) view.findViewById(R.id.view_small_highlight);

        if(!(displayMonth == currentMonth && displayYear == currentYear)){
            view.setBackgroundColor(Color.parseColor("#ffcdd5"));
            viewHolder.highlight.setColorFilter(Color.parseColor("#ffcdd5"));
            viewHolder.small_highlight.setColorFilter(Color.parseColor("#ffcdd5"));
        }

        viewHolder.tv_day = (TextView) view.findViewById(R.id.tv_day);
        viewHolder.tv_day.setText(String.valueOf(dayValue));

        Calendar eventCalendar = Calendar.getInstance();
        if(dayValue == eventCalendar.get(Calendar.DAY_OF_MONTH)
                && displayMonth == eventCalendar.get(Calendar.MONTH) + 1
                && displayYear == eventCalendar.get(Calendar.YEAR)){

            viewHolder.highlight.setColorFilter(ContextCompat.getColor(mContext,R.color.colorAccent_second));

        }
//        Log.d("222","===========================");
//        Log.d("111", dayValue + " + " + displayMonth + " + " +displayYear);
        Log.d("Even Size", allEvents.size()+"");
        for(int i=0; i< allEvents.size();i++){
            eventCalendar.setTime(allEvents.get(i).getDate());
            Log.d("Event",eventCalendar.get(Calendar.DAY_OF_MONTH) + " + " + (eventCalendar.get(Calendar.MONTH) + 1) + " + " +eventCalendar.get(Calendar.YEAR));
            if(dayValue == eventCalendar.get(Calendar.DAY_OF_MONTH)
                && displayMonth == eventCalendar.get(Calendar.MONTH)+1
                && displayYear == eventCalendar.get(Calendar.YEAR)){
//                Log.d("555",eventCalendar.get(Calendar.DAY_OF_MONTH) + " + " + (eventCalendar.get(Calendar.MONTH) + 1) + " + " +eventCalendar.get(Calendar.YEAR));
//                Log.d("111", dayValue + " + " + displayMonth + " + " +displayYear);

                viewHolder.small_highlight.setColorFilter(ContextCompat.getColor(mContext,R.color.colorFriday));
            }
        }
        return view;
    }

    @Override
    public int getCount() {
        return monthlyDates.size();
    }
    @Nullable
    @Override
    public Object getItem(int position) {
        return monthlyDates.get(position);
    }
    @Override
    public int getPosition(Object item) {
        return monthlyDates.indexOf(item);
    }
}
