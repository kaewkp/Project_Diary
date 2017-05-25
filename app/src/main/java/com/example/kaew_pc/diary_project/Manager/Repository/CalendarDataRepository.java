package com.example.kaew_pc.diary_project.Manager.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kaew_pc.diary_project.Manager.Database.Calendar_data;
import com.example.kaew_pc.diary_project.Manager.Database.Note_data;
import com.example.kaew_pc.diary_project.Manager.Database.Payment_data;
import com.example.kaew_pc.diary_project.Manager.EventObjects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrater on 22/5/2560.
 */

public class CalendarDataRepository {
    private static final String TAG = PaymentDataRepository.class.getSimpleName();

    public static String createTable(){
        String CREATE_Calendar_data_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INT PRIMARY KEY AUTOINCREMENT NOT NULL, %s VARCHAR(60), %s TEXT, %s DATETIME, %s DATETIME, %s VARCHAR(3), %s VARCHAR(3))",
                Calendar_data.TABLE,
                Calendar_data.Column.Calendar_id,
                Calendar_data.Column.Calendar_title,
                Calendar_data.Column.Calendar_desc,
                Calendar_data.Column.Calendar_time,
                Calendar_data.Column.Calendar_createdTime,
                Calendar_data.Column.CalendarType_id,
                Calendar_data.Column.Noti_id);
        Log.i(TAG, CREATE_Calendar_data_TABLE);
        return CREATE_Calendar_data_TABLE;
    }

    public static String dropTable(){
        return "DROP TABLE IF EXISTS " + Calendar_data.TABLE;
    }

    public void insertData(SQLiteDatabase db, Calendar_data calendarData){
        ContentValues initialValues = new ContentValues();
        initialValues.put(Calendar_data.Column.Calendar_id, calendarData.getCalendar_id());
        initialValues.put(Calendar_data.Column.Calendar_title, calendarData.getCalendar_title());
        initialValues.put(Calendar_data.Column.Calendar_desc, calendarData.getCalendar_desc());
        initialValues.put(Calendar_data.Column.Calendar_time, String.valueOf(calendarData.getCalendar_time()));
        initialValues.put(Calendar_data.Column.Calendar_createdTime, String.valueOf(calendarData.getCalendar_createdTime()));
        initialValues.put(Calendar_data.Column.CalendarType_id, calendarData.getCalendarType_id());
        initialValues.put(Calendar_data.Column.Noti_id, calendarData.getNoti_id());

        Log.d(TAG + " Insert Data", "title : " + calendarData.getCalendar_title());

        db.insert(Calendar_data.TABLE, null, initialValues);
    }

    public void updateData(SQLiteDatabase db, Calendar_data calendarData){
        ContentValues initialValues = new ContentValues();
        initialValues.put(Calendar_data.Column.Calendar_id, calendarData.getCalendar_id());
        initialValues.put(Calendar_data.Column.Calendar_title, calendarData.getCalendar_title());
        initialValues.put(Calendar_data.Column.Calendar_desc, calendarData.getCalendar_desc());
        initialValues.put(Calendar_data.Column.Calendar_time, String.valueOf(calendarData.getCalendar_time()));
        initialValues.put(Calendar_data.Column.Calendar_createdTime, String.valueOf(calendarData.getCalendar_createdTime()));
        initialValues.put(Calendar_data.Column.CalendarType_id, calendarData.getCalendarType_id());
        initialValues.put(Calendar_data.Column.Noti_id, calendarData.getNoti_id());


        Log.d(TAG + " Update Data", "title : " + calendarData.getCalendar_title());

        db.update(Calendar_data.TABLE, initialValues, Calendar_data.Column.Calendar_id+"="+calendarData.getCalendar_id(), null);
    }

    public void deleteData(SQLiteDatabase db, Calendar_data paymentdata){
        //do something
    }

    public ArrayList<Calendar_data> getData(SQLiteDatabase db){
        Log.d(TAG + "Get Data", "select * from Calendar");
        ArrayList<Calendar_data> list = new ArrayList<Calendar_data>();
        Cursor cursor = null;

        try {
            cursor = db.query(Calendar_data.TABLE, null, null, null, null, null, null); //(table, column, where, where arg, groupby, having, orderby)

            if (cursor.getCount() < 1) {
                return list;
            }
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Log.d("Calendar_data", "Calendar_data id : " + cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_id)));


                Date time = new Date(), createdTime = new Date();
                try{
                    time = getDateFormat().parse(cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_time)));
                    createdTime = getDateFormat().parse(cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_createdTime)));
                }catch (ParseException e){
                    e.printStackTrace();
                }

                Calendar_data data = new Calendar_data(cursor.getInt(cursor.getColumnIndex(Calendar_data.Column.Calendar_id))
                        , cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_title))
                        , cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_desc))
                        , time
                        , createdTime
                        , cursor.getString(cursor.getColumnIndex(Calendar_data.Column.CalendarType_id))
                        , cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Noti_id)));
                list.add(data);
                cursor.moveToNext();
            }
        }
        catch (Exception ex){
            Log.e(TAG + "Get Data", "Exception : " + ex.toString());
        }
        finally {
            if(cursor != null)
                cursor.close();
        }
        return list;
    }

    public Calendar_data getDataById(SQLiteDatabase db, String id){
        Log.d(TAG + "Get Data By ID", "ID : " + id);

        Calendar_data data = null;

        Cursor cursor = null;
        try {
            cursor = db.query(Calendar_data.TABLE,   //table
                    null,                   //column
                    Calendar_data.Column.Calendar_id + " = ?",       //where
                    new String[]{id},       //where arg
                    null, null, null);      //groupby, having, orderby

            if (cursor.getCount() < 1) {
                return data;
            }
            cursor.moveToFirst();

            Date time = new Date(), createdTime = new Date();
            try{
                time = getDateFormat().parse(cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_time)));
                createdTime = getDateFormat().parse(cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_createdTime)));
            }catch (ParseException e){
                e.printStackTrace();
            }

            data = new Calendar_data();
            data.setCalendar_id(cursor.getInt(cursor.getColumnIndex(Payment_data.Column.Payment_id)));
            data.setCalendar_title(cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_title)));
            data.setCalendar_desc(cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_desc)));
            data.setCalendar_time(time);
            data.setCalendar_createdTime(createdTime);
            data.setCalendarType_id(cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_endDate)));
            data.setNoti_id(cursor.getString(cursor.getColumnIndex(Note_data.Column.Noti_id)));
        }
        catch (Exception ex){
            Log.e(TAG + "Get Data By ID", "Exception : " + ex.toString());
        }
        finally {
            if(cursor != null)
                cursor.close();
        }
        return data;
    }


    public Calendar_data getDataByIdOrderByNew(SQLiteDatabase db, String id){
        Log.d(TAG + "Get Data By ID", "select * from Calendar order by id where DESC" + id);

        Calendar_data data = null;

        Cursor cursor = null;
        try {
            cursor = db.query(Calendar_data.TABLE,   //table
                    null,                   //column
                    Calendar_data.Column.Calendar_id + " = ?",       //where
                    new String[]{id},       //where arg
                    null, null, null);      //groupby, having, orderby

            if (cursor.getCount() < 1) {
                return data;
            }
            cursor.moveToFirst();

            Date time = new Date(), createdTime = new Date();
            try{
                time = getDateFormat().parse(cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_time)));
                createdTime = getDateFormat().parse(cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_createdTime)));
            }catch (ParseException e){
                e.printStackTrace();
            }

            data = new Calendar_data();
            data.setCalendar_id(cursor.getInt(cursor.getColumnIndex(Payment_data.Column.Payment_id)));
            data.setCalendar_title(cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_title)));
            data.setCalendar_desc(cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_desc)));
            data.setCalendar_time(time);
            data.setCalendar_createdTime(createdTime);
            data.setCalendarType_id(cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_endDate)));
            data.setNoti_id(cursor.getString(cursor.getColumnIndex(Note_data.Column.Noti_id)));
        }
        catch (Exception ex){
            Log.e(TAG + "Get Data By ID", "Exception : " + ex.toString());
        }
        finally {
            if(cursor != null)
                cursor.close();
        }
        return data;
    }

    private SimpleDateFormat getDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.US);
        return dateFormat;
    }

    private Date convertStringToDate(String dateInString){
        Date date = null;
        try {
            date = getDateFormat().parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public List<EventObjects> getAllFutureEvents(SQLiteDatabase db){
        Date dateToday = new Date();
        List<EventObjects> events = new ArrayList<>();
        Log.d(TAG + "Get Data By ID", "select * from Calendar order by id where DESC");

        Cursor cursor = null;
        try {
            cursor = db.query(Calendar_data.TABLE,   //table
                    null,                   //column
                    null,       //where
                    null,       //where arg
                    null, null, null);      //groupby, having, orderby

            if (cursor.getCount() < 1) {
                return null;
            }

            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String message = cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_title));
                    String startDate = cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_createdTime));
                    //convert start date to date object
                    Date reminderDate = convertStringToDate(startDate);
                    if (reminderDate.after(dateToday) || reminderDate.equals(dateToday)) {
                        events.add(new EventObjects(id, message, reminderDate));
                    }
                } while (cursor.moveToNext());
            }
        }catch (Exception ex){
            Log.e(TAG + "Get Data By ID", "Exception : " + ex.toString());
        }
        finally {
            if(cursor != null)
                cursor.close();
        }
        return events;
    }

    private void insertDateTime(){

//        ContentValues values = new ContentValues();
//    values.put('username', 'ravitamada');
//    values.put('created_at', getDateTime());
//        // insert the row
//        long id = db.insert('users', null, values);
    }
}
