package com.example.kaew_pc.diary_project.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kaew_pc.diary_project.Database.Note_data;
import com.example.kaew_pc.diary_project.Database.Note_recycle;

import java.util.ArrayList;

/**
 * Created by Ekachart-PC on 5/5/2560.
 */

@SuppressWarnings("TryFinallyCanBeTryWithResources")
public class BinRepo {

    private static final String TAG = Note_recycle.class.getSimpleName();

    public static String createTable(){
        String CREATE_Note_recycle_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s INTEGER, %s TEXT )",
                Note_recycle.TABLE,
                Note_recycle.Column.Recycle_id,
                Note_recycle.Column.Note_id,
                Note_recycle.Column.Time_del);
        Log.i(TAG, CREATE_Note_recycle_TABLE);
        return CREATE_Note_recycle_TABLE;
    }

    public static String dropTable(){
        return "DROP TABLE IF EXISTS " + Note_recycle.TABLE;
    }


    public void insertData(SQLiteDatabase db, Note_recycle data){
        ContentValues initialValues = new ContentValues();
//            initialValues.put(Note_recycle.Column.Recycle_id, data.getRecycle_id());
        initialValues.put(Note_recycle.Column.Note_id, data.getNote_id());
        initialValues.put(Note_recycle.Column.Time_del, data.getTime_del());

        Log.d(TAG + " Insert Data", "title : " + data.getRecycle_id());
        db.insert(Note_recycle.TABLE, null, initialValues);
    }

    public void updateData(SQLiteDatabase db, Note_recycle data){
        ContentValues initialValues = new ContentValues();
//            initialValues.put(Note_recycle.Column.Recycle_id, data.getRecycle_id());
        initialValues.put(Note_recycle.Column.Note_id, data.getNote_id());
        initialValues.put(Note_recycle.Column.Time_del, data.getTime_del());

        Log.d(TAG + " Update Data", "title : " + data.getRecycle_id());
        db.update(Note_recycle.TABLE, initialValues, Note_recycle.Column.Recycle_id+"="+data.getRecycle_id(), null);
    }

    public ArrayList<Note_data> getData(SQLiteDatabase db){
        Log.d(TAG + "Get Data", "select * from Note_data");
        ArrayList<Note_data> list = new ArrayList<Note_data>();

        Cursor cursor = null;
        try {
            cursor = db.query(Note_data.TABLE,
                    null,
                    Note_data.Column.isDelete + "=?",
                    new String[]{ "0" },
                    null, null, null); //(table, column, where, where arg, groupby, having, orderby)


            if (cursor.getCount() < 1) {
                return list;
            }
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Log.d("Search!!!!!!", "Note id : " + cursor.getString(cursor.getColumnIndex(Note_data.Column.Note_id)));

                Note_data data = new Note_data(cursor.getInt(cursor.getColumnIndex(Note_data.Column.Note_id))
                        , cursor.getString(cursor.getColumnIndex(Note_data.Column.Note_title))
                        , cursor.getString(cursor.getColumnIndex(Note_data.Column.Note_desc))
                        , cursor.getString(cursor.getColumnIndex(Note_data.Column.Note_date))
                        , cursor.getString(cursor.getColumnIndex(Note_data.Column.Noti_id))
                        , cursor.getInt(cursor.getColumnIndex(Note_data.Column.isDelete)));
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

    public Note_data getDataById(String id, SQLiteDatabase db){
        Log.d(TAG + "Get Data By ID", "ID : " + id);

        Note_data data = null;

        Cursor cursor = null;
        try {
            cursor = db.query(Note_data.TABLE,
                    null,
                    "Note_id = ?",
                    new String[]{ id },
                    null, null, null); //(table, column, where, where arg, groupby, having, orderby)

            if (cursor.getCount() < 1) {
                return data;
            }
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                data = new Note_data();
                data.setNote_id(cursor.getInt(cursor.getColumnIndex(Note_data.Column.Note_id)));
                data.setNote_title(cursor.getString(cursor.getColumnIndex(Note_data.Column.Note_title)));
                data.setNote_desc(cursor.getString(cursor.getColumnIndex(Note_data.Column.Note_desc)));
                data.setNote_date(cursor.getString(cursor.getColumnIndex(Note_data.Column.Note_date)));
                data.setNoti_id(cursor.getString(cursor.getColumnIndex(Note_data.Column.Noti_id)));
                cursor.moveToNext();
            }
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
