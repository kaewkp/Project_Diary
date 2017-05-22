package com.example.kaew_pc.diary_project.Manager.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kaew_pc.diary_project.Manager.Database.Calendar_type;
import java.util.ArrayList;

/**
 * Created by Administrater on 22/5/2560.
 */

public class CalendarTypeRepository {
    private static final String TAG = CalendarTypeRepository.class.getSimpleName();

    public static String createTable(){
        String CREATE_CalendarType_TABLE = String.format("CREATE TABLE %s " +
                        "(%s VARCHAR(3) PRIMARY KEY AUTOINCREMENT NOT NULL, %s VARCHAR(30))",
                Calendar_type.TABLE,
                Calendar_type.Column.CalendarType_id,
                Calendar_type.Column.CalendarType_name);
        Log.i(TAG, CREATE_CalendarType_TABLE);
        return CREATE_CalendarType_TABLE;
    }

    public static String dropTable(){
        return "DROP TABLE IF EXISTS " + Calendar_type.TABLE;
    }

    private void insertData(SQLiteDatabase db, String id, String name){
        ContentValues initialValues = new ContentValues();

        initialValues.put(Calendar_type.Column.CalendarType_id, id);
        initialValues.put(Calendar_type.Column.CalendarType_name, name);
        Log.d("Insert CalendarType", "id : " + id);
        Log.d("Insert CalendarType", "title : " + name);
        db.insert(Calendar_type.TABLE, null, initialValues);
    }

    public void createData(SQLiteDatabase db) {
        insertData(db, "ct1","วันครบรอบ");
        insertData(db, "ct2","ประชุม");
        insertData(db, "ct3","ท่องเที่ยว");
        insertData(db, "ct4","วันเกิด");
    }

    public ArrayList<Calendar_type> getData(SQLiteDatabase db){
        Log.d("Calendar_type", "select * from PayType");
        ArrayList<Calendar_type> list = new ArrayList<Calendar_type>();
        Cursor cursor = null;

        try {
            cursor = db.query(Calendar_type.TABLE, null, null, null, null, null, null); //(table, column, where, where arg, groupby, having, orderby)

            if (cursor.getCount() < 1) {
                return list;
            }
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Log.d("Calendar_type", "Calendar_type id : " + cursor.getString(cursor.getColumnIndex(Calendar_type.Column.CalendarType_id)));

                Calendar_type data = new Calendar_type(cursor.getString(cursor.getColumnIndex(Calendar_type.Column.CalendarType_id))
                        , cursor.getString(cursor.getColumnIndex(Calendar_type.Column.CalendarType_name)));
                list.add(data);
                cursor.moveToNext();
            }
        }
        catch (Exception ex){
            Log.e("Calendar_type", ex.toString());
        }
        finally {
            if(cursor != null)
                cursor.close();
        }
        return list;
    }

    public Calendar_type getDataById(SQLiteDatabase db, String id){
        Log.d(TAG + "Get Data By ID", "ID : " + id);

        Calendar_type data = null;

        Cursor cursor = null;
        try {
            cursor = db.query(Calendar_type.TABLE,                //table
                    null,                                   //column
                    Calendar_type.Column.CalendarType_id + "=?",       //where
                    new String[]{id},                       //where arg
                    null, null, null);                      //groupby, having, orderby

            if (cursor.getCount() < 1) {
                return data;
            }
            cursor.moveToFirst();

            data = new Calendar_type();
            data.setCalendarType_id(cursor.getString(cursor.getColumnIndex(Calendar_type.Column.CalendarType_id)));
            data.setCalendarType_name(cursor.getString(cursor.getColumnIndex(Calendar_type.Column.CalendarType_name)));
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
}
