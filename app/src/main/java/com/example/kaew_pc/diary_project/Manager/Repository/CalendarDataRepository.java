package com.example.kaew_pc.diary_project.Manager.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kaew_pc.diary_project.Manager.Database.Calendar_data;
import com.example.kaew_pc.diary_project.Manager.Database.DebtTime;
import com.example.kaew_pc.diary_project.Manager.Database.Note_data;
import com.example.kaew_pc.diary_project.Manager.Database.Payment_data;
import com.example.kaew_pc.diary_project.Manager.EventObjects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.kaew_pc.diary_project.Manager.Database.DebtTime.Column.DebtTime_id;
import static com.example.kaew_pc.diary_project.R.drawable.list;

/**
 * Created by Administrater on 22/5/2560.
 */

public class CalendarDataRepository {
    private static final String TAG = PaymentDataRepository.class.getSimpleName();

    public static String createTable(){
        String CREATE_Calendar_data_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s VARCHAR(60), %s TEXT, %s DATETIME, %s VARCHAR(20), %s VARCHAR(20))",
                Calendar_data.TABLE,
                Calendar_data.Column.Calendar_id,
                Calendar_data.Column.Calendar_title,
                Calendar_data.Column.Calendar_desc,
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
//        initialValues.put(Calendar_data.Column.Calendar_id, calendarData.getCalendar_id());
        initialValues.put(Calendar_data.Column.Calendar_title, calendarData.getCalendar_title());
        initialValues.put(Calendar_data.Column.Calendar_desc, calendarData.getCalendar_desc());
        initialValues.put(Calendar_data.Column.Calendar_createdTime, DateToStringConverter(calendarData.getCalendar_createdTime()));
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
        initialValues.put(Calendar_data.Column.Calendar_createdTime, DateToStringConverter(calendarData.getCalendar_createdTime()));
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
                createdTime = StringToDateConverter(cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_createdTime)));

                Calendar_data data = new Calendar_data(cursor.getInt(cursor.getColumnIndex(Calendar_data.Column.Calendar_id))
                        , cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_title))
                        , cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_desc))
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
            createdTime = StringToDateConverter(cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_createdTime)));

            data = new Calendar_data();
            data.setCalendar_id(cursor.getInt(cursor.getColumnIndex(Calendar_data.Column.Calendar_id)));
            data.setCalendar_title(cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_title)));
            data.setCalendar_desc(cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_desc)));
            data.setCalendar_createdTime(createdTime);
            data.setCalendarType_id(cursor.getString(cursor.getColumnIndex(Calendar_data.Column.CalendarType_id)));
            data.setNoti_id(cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Noti_id)));
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

            Date createdTime = new Date();
            createdTime = StringToDateConverter(cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_createdTime)));

            data = new Calendar_data();
            data.setCalendar_id(cursor.getInt(cursor.getColumnIndex(Calendar_data.Column.Calendar_id)));
            data.setCalendar_title(cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_title)));
            data.setCalendar_desc(cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_desc)));
            data.setCalendar_createdTime(createdTime);
            data.setCalendarType_id(cursor.getString(cursor.getColumnIndex(Calendar_data.Column.CalendarType_id)));
            data.setNoti_id(cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Noti_id)));
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

    public SimpleDateFormat getDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm", Locale.US);
        return dateFormat;
    }

    public Date StringToDateConverter(String value){
        Date date = new Date();
        try {
            date = getDateFormat().parse(value);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    public String DateToStringConverter(Date date){
        String datetime = "";
        try {
            datetime = getDateFormat().format(date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return datetime;
    }

    public ArrayList<Calendar_data> getDataByDate(SQLiteDatabase db, String date){
        Log.d(TAG + "Get Data By Date", "Date : " + date);
        ArrayList<Calendar_data> list = new ArrayList<>();
        Date createdTime = new Date();
        Calendar_data data = null;
        Cursor cursor = null;
        try {
            cursor = db.query(Calendar_data.TABLE,   //table
                    null,                   //column
                    Calendar_data.Column.Calendar_createdTime + " BETWEEN ? AND ? = ?",       //where
                    new String[]{date + " 00:00",date + " 23:59"},       //where arg
                    null, null, null);      //groupby, having, orderby

            if (cursor.getCount() < 1) {
                return list;
            }
            if (cursor.moveToFirst()) {
                do {
                    createdTime = StringToDateConverter(cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_createdTime)));

                    data = new Calendar_data();
                    data.setCalendar_id(cursor.getInt(cursor.getColumnIndex(Calendar_data.Column.Calendar_id)));
                    data.setCalendar_title(cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_title)));
                    data.setCalendar_desc(cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_desc)));
                    data.setCalendar_createdTime(createdTime);
                    data.setCalendarType_id(cursor.getString(cursor.getColumnIndex(Calendar_data.Column.CalendarType_id)));
                    data.setNoti_id(cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Noti_id)));

                    list.add(data);
                } while (cursor.moveToNext());
            }
        }
        catch (Exception ex){
            Log.e(TAG + "Get Data By Date", "Exception : " + ex.toString());
        }
        finally {
            if(cursor != null)
                cursor.close();
        }
        return list;
    }

    public ArrayList<EventObjects> getAllFutureEvents(SQLiteDatabase db){
        Date dateToday = new Date();
        ArrayList<EventObjects> events = new ArrayList<EventObjects>();
        Log.d(TAG + "Get Data By ID", "select * from Calendar order by id where DESC");

        Cursor cursor = null;
        try {
            cursor = db.query(Calendar_data.TABLE,   //table
                    null,       //column
                    null,       //where
                    null,       //where arg
                    null, null, null);      //groupby, having, orderby

            if (cursor.getCount() < 1) {
                return null;
            }

            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String title = cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_title));
                    String message = cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_desc));
                    String startDate = cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Calendar_createdTime));
                    String type = cursor.getString(cursor.getColumnIndex(Calendar_data.Column.CalendarType_id));
                    String notic = cursor.getString(cursor.getColumnIndex(Calendar_data.Column.Noti_id));
                    //convert start date to date object
                    Date reminderDate = StringToDateConverter(startDate);
                    if (reminderDate.after(dateToday) || reminderDate.equals(dateToday)) {

                        events.add(new EventObjects(id, message, reminderDate, title, type, notic));

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
}
