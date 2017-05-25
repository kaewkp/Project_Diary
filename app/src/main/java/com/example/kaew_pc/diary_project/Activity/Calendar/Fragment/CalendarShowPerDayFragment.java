package com.example.kaew_pc.diary_project.Activity.Calendar.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kaew_pc.diary_project.R;

public class CalendarShowPerDayFragment extends Fragment {

    private static CalendarShowPerDayFragment calendar_fragment_show_per_day;

    public static CalendarShowPerDayFragment Instance(){
        if(calendar_fragment_show_per_day == null){
            calendar_fragment_show_per_day = new CalendarShowPerDayFragment();
        }
        return calendar_fragment_show_per_day;
    }

    static class ViewHolder {
        protected ImageButton calendar_cancel;
        protected LinearLayout historyProgress;
        protected ProgressBar progress_bar;
        protected ListView listView;
    }

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,@Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.calendar_fragment_show_per_day, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view){
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.calendar_cancel = (ImageButton) view.findViewById(R.id.calendar_cancel);
        viewHolder.historyProgress = (LinearLayout) view.findViewById(R.id.historyProgress);
        viewHolder.progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);
        viewHolder.listView = (ListView) view.findViewById(R.id.listView);
    }
}
