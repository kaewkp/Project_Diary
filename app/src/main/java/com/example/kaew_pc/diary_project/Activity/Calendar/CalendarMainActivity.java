package com.example.kaew_pc.diary_project.Activity.Calendar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.benzneststudios.eventCalendarView.model.Event;
import com.example.kaew_pc.diary_project.Activity.Calendar.Fragment.CalendarModuleFragment;
import com.example.kaew_pc.diary_project.Manager.Database.Calendar_data;
import com.example.kaew_pc.diary_project.Manager.Database.DBHelper;
import com.example.kaew_pc.diary_project.Manager.Repository.CalendarDataRepository;
import com.example.kaew_pc.diary_project.R;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Rachanon on 5/20/2017.
 */

public class CalendarMainActivity extends AppCompatActivity implements CalendarModuleFragment.OnFragmentInteractionListener {
    //Aim
    private static final String TAG_CALENDAR_FRAGMENT = "tag_calendar_fragment";
    //Rachanon
    public FloatingActionButton addButton;
    private Spinner spinTypeEvent, spinNotic;
    private ImageButton doneEvent , cancelEvent ;
    private Button startDatePicker, startTimePicker;
    protected int mYear, mMonth, mDay, mHour, mMinute;
    private String txtDate, txtTime;
    private String startDateTime, endDateTime;
    private DateFormatSymbols formatStartDate, dateFormatSymbols;
    private EditText titleT, descT;
    private SimpleDateFormat fdt;
    private Date dateS, dateD;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_main);

        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);
        action.setHomeButtonEnabled(true);

        init();
//        toggleCalendar();
        if(savedInstanceState == null){
//            First created
//            Place fragment -> notify to activity
            getSupportFragmentManager()
                    .beginTransaction()
                    //Place fragment at ที่ที่เรากันพื้นที่ไว้ให้
                    .replace(R.id.contentContainer, CalendarModuleFragment.Instance(), TAG_CALENDAR_FRAGMENT)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void init(){
        CalendarModuleFragment.Instance().setDbHelper(this);
        db = DBHelper.getInstance(this);
        final CalendarDataRepository cdr = new CalendarDataRepository();
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_calendar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("xx", "addButton");
                final Dialog dialog = new Dialog(CalendarMainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.calendar_addevent);
                dialog.setCancelable(true);

                startDatePicker = (Button) dialog.findViewById(R.id.start_date);
                startTimePicker = (Button) dialog.findViewById(R.id.start_time);
                spinTypeEvent = (Spinner) dialog.findViewById(R.id.type_event);
                spinNotic = (Spinner) dialog.findViewById(R.id.time_notic);
                doneEvent = (ImageButton) dialog.findViewById(R.id.calendar_done);
                cancelEvent = (ImageButton) dialog.findViewById(R.id.calendar_cancel);
                titleT = (EditText) dialog.findViewById(R.id.txtNameofEvent);
                descT = (EditText) dialog.findViewById(R.id.descEvent);

                setDateTimeNow();

                ArrayAdapter<CharSequence> adapterType;
                adapterType = ArrayAdapter.createFromResource(CalendarMainActivity.this,
                        R.array.type_event, android.R.layout.simple_spinner_item );
                adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinTypeEvent.setAdapter(adapterType);

                ArrayAdapter<CharSequence> adapterTime;
                adapterTime = ArrayAdapter.createFromResource(CalendarMainActivity.this,
                        R.array.time_notic, android.R.layout.simple_spinner_item );
                adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinNotic.setAdapter(adapterTime);

                doneEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.w("startDatePicker",startDatePicker.getText()+"");
                        Log.w("startTimePicker",startTimePicker.getText()+"");
                        Log.w("spinTypeEvent",spinTypeEvent.getTextAlignment()+"");
                        Log.w("spinNotic",spinNotic.getTextAlignment()+"");
                        String createDateTime = startDatePicker.getText()
                                +" "+startTimePicker.getText();

                        Log.w("createDateTime",createDateTime);

                        Calendar_data sCalendar = new Calendar_data();

                        sCalendar.setCalendar_title(titleT.getText()+"");
                        sCalendar.setCalendar_desc(descT.getText()+"");
                        sCalendar.setNoti_id(spinNotic.getTextAlignment()+"");
                        sCalendar.setCalendarType_id(spinTypeEvent.getTextAlignment()+"");
                        sCalendar.setCalendar_createdTime(cdr.StringToDateConverter(createDateTime));

                        cdr.insertData(db.getReadableDatabase(),sCalendar);
                        dialog.cancel();
//                        Intent intent = new Intent(getApplicationContext(),CalendarMainActivity.class);
//                        startActivity(intent);

                        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_CALENDAR_FRAGMENT);
                        if(fragment != null) {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .detach(fragment)
                                    .attach(fragment)
                                    .commit();
                        }
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

    private View.OnClickListener onCloseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_CALENDAR_FRAGMENT);
            if(fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(fragment)
                        .commit();
            }
        }
    };

    private void setEvent(int color){
        Event event = new Event();
        event.setPointColor(color);
        event.setCirCleColor(color);
    }

    public void clickDate(View view){
        if (view.getId() == R.id.start_date){
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            String dayS = "";
                            String monthS = "";
                            dayS = ((dayOfMonth>10)? ""+dayOfMonth : "0"+dayOfMonth);
                            monthS = ((monthOfYear+1>10)? ""+(monthOfYear+1) : "0"+(monthOfYear+1));
                            txtDate = dayS + "-" + monthS + "-" + year;
                            startDatePicker.setText(txtDate);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

    }

    public void clickTime(View view){
        if (view.getId() == R.id.start_time){
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            String hourS = "";
                            String minS = "";
                            hourS = ((hourOfDay>10)? ""+hourOfDay : "0"+hourOfDay);
                            minS = ((minute>10)? ""+(minute) : "0"+(minute));
                            txtTime = hourS + ":" + minS;
                            startTimePicker.setText(txtTime);
                        }
                    }, mHour, mMinute, false);

            timePickerDialog.show();
        }
//        else if(view.getId() == R.id.end_time){
//            final Calendar c = Calendar.getInstance();
//            mHour = c.get(Calendar.HOUR_OF_DAY);
//            mMinute = c.get(Calendar.MINUTE);
//
//            // Launch Time Picker Dialog
//            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
//                    new TimePickerDialog.OnTimeSetListener() {
//
//                        @Override
//                        public void onTimeSet(TimePicker view, int hourOfDay,
//                                              int minute) {
//                            String hourS = "";
//                            String minS = "";
//                            hourS = ((hourOfDay>10)? ""+hourOfDay : "0"+hourOfDay);
//                            minS = ((minute>10)? ""+(minute) : "0"+(minute));
//                            txtTime = hourS + ":" + minS;
//                            endTimePicker.setText(txtTime);
//                        }
//                    }, mHour, mMinute, false);
//
//            timePickerDialog.show();
//        }
    }

    public void setDateTimeNow(){
        Calendar c = Calendar.getInstance();
        int dayN = c.get(Calendar.DAY_OF_MONTH);
        int monthN = c.get(Calendar.MONTH) + 1;
        int yearN = c.get(Calendar.YEAR);
        int hourN = c.get(Calendar.HOUR_OF_DAY);
        int minuteN = c.get(Calendar.MINUTE);
        String hourS,minS,dayS,monthS;
        hourS = ((hourN>10)? ""+hourN : "0"+hourN);
        minS = ((minuteN>10)? ""+(minuteN) : "0"+(minuteN));
        dayS = ((dayN>10)? ""+dayN : "0"+dayN);
        monthS = ((monthN+1>10)? ""+(monthN+1) : "0"+(monthN));
        txtDate = dayS + "-" + monthS + "-" + yearN;
        txtTime = hourS + ":" + minS;
        startDatePicker.setText(txtDate);
        startTimePicker.setText(txtTime);
    }

    @Override

    public void messageFromParentFragmentToActivity(String myString) {

    }

    @Override
    public void onBackPressed(){
        finish();
    }
}
