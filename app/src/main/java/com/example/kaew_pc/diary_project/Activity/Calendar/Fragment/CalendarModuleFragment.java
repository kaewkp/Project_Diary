package com.example.kaew_pc.diary_project.Activity.Calendar.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kaew_pc.diary_project.Activity.Calendar.CalendarDetailActivity;
import com.example.kaew_pc.diary_project.Manager.Adapter.CalendarAdapter;
import com.example.kaew_pc.diary_project.Manager.Adapter.Calendar_showRowEventAdapter;
import com.example.kaew_pc.diary_project.Manager.Database.DBHelper;
import com.example.kaew_pc.diary_project.Manager.EventObjects;
import com.example.kaew_pc.diary_project.Manager.Repository.CalendarDataRepository;
import com.example.kaew_pc.diary_project.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class CalendarModuleFragment extends Fragment implements View.OnClickListener{

    private static final String TAG_CALENDAR_FRAGMENT = "tag_calendar_fragment";
    private static CalendarModuleFragment calendar_module_fragment;
    private static final int MAX_CALENDAR_COLUMN = 42;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.US);
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private CalendarAdapter mAdapter;
    private CalendarDataRepository repo;
    private DBHelper db;
    private Context context;
    private ViewHolder viewHolder;
    private View view;
    private Calendar_showRowEventAdapter rowAdapter;
    private ImageButton cancelEvent;
    private ListView listView;
    private ArrayList<Date> dayValueInCells2 = new ArrayList<Date>();

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
        this.view = view;
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
        ArrayList<Date> dayValueInCells = new ArrayList<Date>();
        dayValueInCells2.clear();
        repo = new CalendarDataRepository();
        final ArrayList<EventObjects> mEvents = repo.getAllFutureEvents(db.getReadableDatabase());
        Calendar mCal = (Calendar)cal.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
        while(dayValueInCells.size() < MAX_CALENDAR_COLUMN){
            dayValueInCells.add(mCal.getTime());
            dayValueInCells2.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        Log.d(TAG,"OutListEvent : "+dayValueInCells.get(13)+"");
        Log.d(TAG, "Number of date " + dayValueInCells.size());
        final String sDate = formatter.format(cal.getTime());
        viewHolder.tv_title_month_year.setText(sDate);
        mAdapter = new CalendarAdapter(context, dayValueInCells, cal, mEvents == null ? new ArrayList<EventObjects>() : mEvents);
        viewHolder.calendar_grid.setAdapter(mAdapter);
        viewHolder.calendar_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.calendar_show_event);
                dialog.setCancelable(true);

                repo = new CalendarDataRepository();
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "MM-yyyy", Locale.US);
                SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm", Locale.US);
                ArrayList<EventObjects> mEvents = repo.getAllFutureEvents(db.getReadableDatabase());

                String a = dateFormat.format(cal.getTime());
                String x = repo.getDateFormat().format(cal.getTime());
//                String dateTest = repo.getDateFormat().format(cal.getT);
                Calendar dCal = Calendar.getInstance();
                Log.d(TAG,"inListEvent : "+dayValueInCells2.get(arg2)+"");
                dCal.setTime(dayValueInCells2.get(arg2));
                int dayForEvent = dCal.get(Calendar.DAY_OF_MONTH);

                Log.d(TAG,arg2+" OR "+dayForEvent);
                Log.d(TAG,a+" is ...");
                final String startTime;
                if (dayForEvent<10){ startTime = "0"+dayForEvent + "-" + a;}
                else{ startTime = dayForEvent + "-" + a;}
                String m = "";
                String time = "";
                final List<String> timeList = new ArrayList<String>();
                final List<String> title = new ArrayList<String>();
                final List<String> desc = new ArrayList<String>();
                final List<String> notic = new ArrayList<String>();
                final List<String> type = new ArrayList<String>();
                Log.d(TAG,mEvents.get(0).getDate()+"");
                for (int i = 0; i < mEvents.size(); i++) {
                    m = repo.DateToStringConverter(mEvents.get(i).getDate());
                    if (m.contains(startTime)) {
                        time = localDateFormat.format(mEvents.get(i).getDate());
                        timeList.add(time);
                        title.add(mEvents.get(i).getTitle());
                        desc.add(mEvents.get(i).getMessage());
                        notic.add(mEvents.get(i).getNotic());
                        type.add(mEvents.get(i).getType());
                    }
                }

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View view = inflater.inflate(R.layout.calendar_show_event, null);
                cancelEvent = (ImageButton) dialog.findViewById(R.id.calendar_cancel);
                ListView listView_event = (ListView) dialog.findViewById(R.id.listView_event);
                Log.d(TAG,startTime+"ModuleFragment_CreateEvent");
                listView_event.setAdapter(new Calendar_showRowEventAdapter(
                        getActivity().getApplicationContext(), startTime, timeList, title, desc));
                listView_event.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), CalendarDetailActivity.class);
                        intent.putExtra("1", title.get(i));
                        intent.putExtra("2", type.get(i));
                        intent.putExtra("Date", startTime+"");
                        intent.putExtra("4", timeList.get(i));
                        intent.putExtra("5", notic.get(i));
                        intent.putExtra("6", desc.get(i));
                        startActivity(intent);
                    }
                });

                cancelEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });
    }

    // **************** start interesting part ************************

    private OnFragmentInteractionListener mListener;


    @Override
    public void onClick(View v) {
        mListener.messageFromParentFragmentToActivity("I am the parent fragment.");
    }

    public interface OnFragmentInteractionListener {
        void messageFromParentFragmentToActivity(String myString);
    }

    // **************** end interesting part ************************

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
