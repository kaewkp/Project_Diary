package com.example.kaew_pc.diary_project.Manager.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kaew_pc.diary_project.Manager.Database.Note_data;

import java.util.ArrayList;

/**
 * Created by Ekachart-PC on 5/5/2560.
 */

public class NoteDataRepository {
    private static final String TAG = Note_data.class.getSimpleName();

    public static String createTable(){
        String CREATE_Note_data_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT, %s TEXT, %s TEXT, %s VARCHAR(3), %s INTEGER)",
                Note_data.TABLE,
                Note_data.Column.Note_id,
                Note_data.Column.Note_title,
                Note_data.Column.Note_desc,
                Note_data.Column.Note_date,
                Note_data.Column.Noti_id,
                Note_data.Column.isDelete);
        Log.i(TAG, CREATE_Note_data_TABLE);
        return CREATE_Note_data_TABLE;
    }

    public static String dropTable(){
        return "DROP TABLE IF EXISTS " + Note_data.TABLE;
    }

    public void insertData(SQLiteDatabase db, Note_data notedata){
        ContentValues initialValues = new ContentValues();
//        initialValues.put(Note_data.Column.Note_id, notedata.getNote_id());
        initialValues.put(Note_data.Column.Note_title, notedata.getNote_title());
        initialValues.put(Note_data.Column.Note_desc, notedata.getNote_desc());
        initialValues.put(Note_data.Column.Note_date, notedata.getNote_date());
        initialValues.put(Note_data.Column.Noti_id, notedata.getNoti_id());
        initialValues.put(Note_data.Column.isDelete, 1);

        Log.d("Insert Data", "title : " + notedata.getNote_title());

        db.insert(Note_data.TABLE, null, initialValues);
    }

    public void updateData(SQLiteDatabase db, Note_data notedata){
        ContentValues initialValues = new ContentValues();
//        initialValues.put(Note_data.Column.Note_id, notedata.getNote_id());
        initialValues.put(Note_data.Column.Note_title, notedata.getNote_title());
        initialValues.put(Note_data.Column.Note_desc, notedata.getNote_desc());
        initialValues.put(Note_data.Column.Note_date, notedata.getNote_date());
        initialValues.put(Note_data.Column.Noti_id, notedata.getNoti_id());
        initialValues.put(Note_data.Column.isDelete, 1);

        Log.d("Update Dsta", "title : " + notedata.getNote_title());

        db.update(Note_data.TABLE, initialValues, "Note_id="+notedata.getNote_id(), null);
    }

    public void deleteData(SQLiteDatabase db, int id){
        ContentValues initialValues = new ContentValues();
        initialValues.put(Note_data.Column.isDelete, 0);

        Log.d("Delete Dsta to Recycle", "title : " + id);

        db.update(Note_data.TABLE, initialValues, "Note_id="+id, null);
    }

    public ArrayList<Note_data> getData(SQLiteDatabase db){
        Log.d(TAG + "Get Data", "select * from Note_data");
        ArrayList<Note_data> list = new ArrayList<Note_data>();

        Cursor cursor = null;
        try {
            cursor = db.query(Note_data.TABLE,
                    null,
                    Note_data.Column.isDelete + "=?",
                    new String[]{ "1" },
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

    public void deleteNote(SQLiteDatabase db, int id){
        String whereClause = Note_data.Column.Note_id + "=?";
        String[] whereArgs = new String[] { String.valueOf(id) };
        db.delete(Note_data.TABLE, whereClause, whereArgs);
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
