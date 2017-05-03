package com.example.kaew_pc.diary_project.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kaew_pc.diary_project.Database.DebtTime;

import java.util.ArrayList;

import static com.example.kaew_pc.diary_project.Database.DebtTime.Column.DebtTime_id;

/**
 * Created by chommchome on 30/4/2560.
 */

public class DebtTimeRepo {
    private static final String TAG = DebtTimeRepo.class.getSimpleName();

    public static String createTable(){
        String CREATE_DebtTime_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT)",
                DebtTime.TABLE,
                DebtTime_id,
                DebtTime.Column.DebtTime_name);
        Log.i(TAG, CREATE_DebtTime_TABLE);
        return CREATE_DebtTime_TABLE;
    }

    public static String dropTable(){
        return "DROP TABLE IF EXISTS " + DebtTime.TABLE;
    }

    private void insertData(SQLiteDatabase db, String id, String name){
        ContentValues initialValues = new ContentValues();
//        initialValues.put(DebtTime_id, id);
        initialValues.put(DebtTime.Column.DebtTime_name, name);
        Log.d("Insert DebtTime Data", "title : " + name);
        db.insert(DebtTime.TABLE, null, initialValues);
    }

    public void createData(SQLiteDatabase db) {
        insertData(db, "dt1","6 เดือน");
        insertData(db, "dt2","12 เดือน");
        insertData(db, "dt3","18 เดือน)");
        insertData(db, "dt4","24 เดือน");
        insertData(db, "dt5","30 เดือน");
        insertData(db, "dt6","36 เดือน");
        insertData(db, "dt7","40 เดือน");
        insertData(db, "dt8","44 เดือน");
        insertData(db, "dt9","50 เดือน");
        insertData(db, "dt10","62 เดือน");
    }

    public ArrayList<DebtTime> getData(SQLiteDatabase db){
        Log.d("DebtTime", "select * from DebtTime");
        ArrayList<DebtTime> list = new ArrayList<DebtTime>();
        Cursor cursor = null;

        try {
            cursor = db.query(DebtTime.TABLE, null, null, null, null, null, null); //(table, column, where, where arg, groupby, having, orderby)

            if (cursor.getCount() < 1) {
                return list;
            }
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Log.d("DebtTime", "DebtTime id : " + cursor.getString(cursor.getColumnIndex(DebtTime_id)));

                DebtTime data = new DebtTime(cursor.getString(cursor.getColumnIndex(DebtTime_id))
                        , cursor.getString(cursor.getColumnIndex(DebtTime.Column.DebtTime_name)));
                list.add(data);
                cursor.moveToNext();
            }
        }
        catch (Exception ex){
            Log.e("DebtTime", ex.toString());
        }
        finally {
            if(cursor != null)
                cursor.close();
        }
        return list;
    }

    public DebtTime getDataById(SQLiteDatabase db, String id){
        Log.d(TAG + "Get Data By ID", "ID : " + id);

        DebtTime data = null;

        Cursor cursor = null;
        try {
            cursor = db.query(DebtTime.TABLE,                //table
                    null,                                   //column
                    DebtTime.Column.DebtTime_id + "=?",       //where
                    new String[]{id},                       //where arg
                    null, null, null);                      //groupby, having, orderby

            if (cursor.getCount() < 1) {
                return data;
            }
            cursor.moveToFirst();

            data = new DebtTime();
            data.setDebtTime_id(cursor.getString(cursor.getColumnIndex(DebtTime.Column.DebtTime_id)));
            data.setDebtTime_name(cursor.getString(cursor.getColumnIndex(DebtTime.Column.DebtTime_name)));
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
