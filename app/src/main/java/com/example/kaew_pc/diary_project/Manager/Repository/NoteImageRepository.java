package com.example.kaew_pc.diary_project.Manager.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kaew_pc.diary_project.Manager.Database.Note_data;
import com.example.kaew_pc.diary_project.Manager.Database.Note_image;

import java.util.ArrayList;

/**
 * Created by Ekachart-PC on 24/5/2560.
 */

public class NoteImageRepository {
    private static final String TAG = Note_image.class.getSimpleName();

    public static String createTable(){
        String CREATE_Note_image_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s INTEGER, %s BLOB)",
                Note_image.TABLE,
                Note_image.Column.seq,
                Note_image.Column.Note_id,
                Note_image.Column.Note_image);
        Log.i(TAG, CREATE_Note_image_TABLE);
        return CREATE_Note_image_TABLE;
    }

    public static String dropTable(){
        return "DROP TABLE IF EXISTS " + Note_image.TABLE;
    }

    public void insertData(SQLiteDatabase db, Note_image data){
        ContentValues initialValues = new ContentValues();
//        initialValues.put(Note_data.Column.Note_id, notedata.getNote_id());
        initialValues.put(Note_image.Column.Note_id, data.getNote_id());
        initialValues.put(Note_image.Column.Note_image, data.getNote_image());

        Log.d("Insert Picture", "id : " + data.getNote_id());
        Log.d("Insert Picture", "BLOB : " + data.getNote_image());

        db.insert(Note_image.TABLE, null, initialValues);
    }

    public void deleteData(SQLiteDatabase db, int seq){
        ContentValues initialValues = new ContentValues();
        initialValues.put(Note_image.Column.seq, seq);

        Log.d("Delete Picture", "seq : " + seq);

        db.update(Note_image.TABLE, initialValues, Note_image.Column.seq+"="+seq, null);
    }

    public ArrayList<Note_image> getDataById(SQLiteDatabase db, int id){
        Log.d(TAG + "Get Image", "select image from Note_image id: "+id);
        ArrayList<Note_image> list = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = db.query(Note_image.TABLE,
                    null,
                    Note_image.Column.Note_id + " = ?",
                    new String[]{ String.valueOf(id) },
                    null, null, null); //(table, column, where, where arg, groupby, having, orderby)

            Log.d(TAG + "Get Image", "Size : "+cursor.getCount());
            if (cursor.getCount() < 1) {
                return list;
            }
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Log.d("Search!!!!!!", "Note id : " + cursor.getString(cursor.getColumnIndex(Note_image.Column.seq)));

                Note_image data = new Note_image(cursor.getInt(cursor.getColumnIndex(Note_image.Column.seq))
                        , cursor.getInt(cursor.getColumnIndex(Note_image.Column.Note_id))
                        , cursor.getBlob(cursor.getColumnIndex(Note_image.Column.Note_image)));
                list.add(data);
                cursor.moveToNext();
            }
        }
        catch (Exception ex){
            Log.e(TAG + "Get Image", "Exception : " + ex.toString());
        }
        finally {
            if(cursor != null)
                cursor.close();
        }
        return list;
    }
}
