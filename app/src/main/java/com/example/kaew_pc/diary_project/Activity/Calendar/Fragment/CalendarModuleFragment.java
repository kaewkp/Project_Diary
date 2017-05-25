package com.example.kaew_pc.diary_project.Activity.Calendar.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kaew_pc.diary_project.Activity.Calendar.CalendarMainActivity;
import com.example.kaew_pc.diary_project.Manager.Adapter.CalendarAdapter;
import com.example.kaew_pc.diary_project.Manager.Database.DBHelper;
import com.example.kaew_pc.diary_project.Manager.EventObjects;
import com.example.kaew_pc.diary_project.Manager.Repository.CalendarDataRepository;
import com.example.kaew_pc.diary_project.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class CalendarModuleFragment extends Fragment {

    private static CalendarModuleFragment calendar_module_fragment;
    private static final int MAX_CALENDAR_COLUMN = 42;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private CalendarAdapter mAdapter;
    private CalendarDataRepository repo;
    private DBHelper db;
    private Context context;
    private ViewHolder viewHolder;

    static class ViewHolder {
        protected ImageView img_arrow_left, img_arrow_right;
        protected TextView tv_month, tv_title_month_year, tv_day_1,tv_day_2,tv_day_3,tv_day_4,tv_day_5,tv_day_6,tv_day_7;
        protected GridView calendar_grid;
        protected View view_header;
    }

    public static CalendarModuleFragment Instance(){
        if(calendar_module_fragment == null){
            calendar_module_fragment = new CalendarModuleFragment();
        }
        return calendar_module_fragment;
    }

    public void setDbHelper(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.calendar_fragment_module, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setUpCalendarAdapter();
    }

    private void init(View view){
        db = DBHelper.getInstance(context);
        viewHolder = new ViewHolder();
        viewHolder.calendar_grid = (GridView) view.findViewById(R.id.calendar_grid);
        viewHolder.img_arrow_left = (ImageView) view.findViewById(R.id.img_arrow_left);
        viewHolder.tv_title_month_year = (TextView) view.findViewById(R.id.tv_title_month_year);
        viewHolder.img_arrow_right = (ImageView) view.findViewById(R.id.img_arrow_right);
        viewHolder.tv_day_1 = (TextView) view.findViewById(R.id.tv_day_1);
        viewHolder.tv_day_2 = (TextView) view.findViewById(R.id.tv_day_2);
        viewHolder.tv_day_3 = (TextView) view.findViewById(R.id.tv_day_3);
        viewHolder.tv_day_4 = (TextView) view.findViewById(R.id.tv_day_4);
        viewHolder.tv_day_5 = (TextView) view.findViewById(R.id.tv_day_5);
        viewHolder.tv_day_6 = (TextView) view.findViewById(R.id.tv_day_6);
        viewHolder.tv_day_7 = (TextView) view.findViewById(R.id.tv_day_7);
        viewHolder.view_header = (View) view.findViewById(R.id.view_header);
        viewHolder.view_header.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();
    }
    private void setPreviousButtonClickEvent(){
        viewHolder.img_arrow_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, -1);
                setUpCalendarAdapter();
            }
        });
    }
    private void setNextButtonClickEvent(){
        viewHolder.img_arrow_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, 1);
                setUpCalendarAdapter();
            }
        });
    }
    private void setUpCalendarAdapter(){
        List<Date> dayValueInCells = new ArrayList<Date>();
        repo = new CalendarDataRepository();
        List<EventObjects> mEvents = repo.getAllFutureEvents(db.getReadableDatabase());
        Calendar mCal = (Calendar)cal.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
        while(dayValueInCells.size() < MAX_CALENDAR_COLUMN){
            dayValueInCells.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        Log.d(TAG, "Number of date " + dayValueInCells.size());
        String sDate = formatter.format(cal.getTime());
        viewHolder.tv_title_month_year.setText(sDate);
        mAdapter = new CalendarAdapter(context, dayValueInCells, cal, mEvents);
        viewHolder.calendar_grid.setAdapter(mAdapter);
        viewHolder.calendar_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }
        });
    }
}
