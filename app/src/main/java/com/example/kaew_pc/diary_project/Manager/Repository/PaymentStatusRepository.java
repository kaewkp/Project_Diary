package com.example.kaew_pc.diary_project.Manager.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kaew_pc.diary_project.Manager.Database.Payment_status;

import java.util.ArrayList;

import static com.example.kaew_pc.diary_project.Manager.Database.Payment_status.Column.PayStatus_id;

/**
 * Created by Ekachart-PC on 12/4/2560.
 */

public class PaymentStatusRepository {
    private static final String TAG = PaymentStatusRepository.class.getSimpleName();

    public static String createTable(){
        String CREATE_Payment_status_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT)",
                Payment_status.TABLE,
                Payment_status.Column.PayStatus_id,
                Payment_status.Column.PayStatus_name);
        Log.i(TAG, CREATE_Payment_status_TABLE);
        return CREATE_Payment_status_TABLE;
    }

    public static String dropTable(){
        return "DROP TABLE IF EXISTS " + Payment_status.TABLE;
    }

    private void insertData(SQLiteDatabase db, String id, String name){
        ContentValues initialValues = new ContentValues();
//        initialValues.put(PayType_id, id);
        initialValues.put(Payment_status.Column.PayStatus_name, name);
        Log.d("Insert PayStatus Data", "title : " + name);
        db.insert(Payment_status.TABLE, null, initialValues);
    }

    public void createData(SQLiteDatabase db) {
        insertData(db, "pt1","ค้างชำระรายเดือน");
        insertData(db, "pt2","ค้างชำระรายงวด");
    }

    public ArrayList<Payment_status> getData(SQLiteDatabase db){
        Log.d("PayStatus", "select * from PayStatus");
        ArrayList<Payment_status> list = new ArrayList<Payment_status>();
        Cursor cursor = null;

        try {
            cursor = db.query(Payment_status.TABLE, null, null, null, null, null, null); //(table, column, where, where arg, groupby, having, orderby)

            if (cursor.getCount() < 1) {
                return list;
            }
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Log.d("PayStatus", "PayStatus id : " + cursor.getString(cursor.getColumnIndex(PayStatus_id)));

                Payment_status data = new Payment_status(cursor.getString(cursor.getColumnIndex(PayStatus_id))
                        , cursor.getString(cursor.getColumnIndex(Payment_status.Column.PayStatus_name)));
                list.add(data);
                cursor.moveToNext();
            }
        }
        catch (Exception ex){
            Log.e("PayStatus", ex.toString());
        }
        finally {
            if(cursor != null)
                cursor.close();
        }
        return list;
    }

    public Payment_status getDataById(SQLiteDatabase db, String id){
        Log.d(TAG + "Get Data By ID", "ID : " + id);

        Payment_status data = null;

        Cursor cursor = null;
        try {
            cursor = db.query(Payment_status.TABLE,                //table
                    null,                                   //column
                    Payment_status.Column.PayStatus_id + "=?",       //where
                    new String[]{id},                       //where arg
                    null, null, null);                      //groupby, having, orderby

            if (cursor.getCount() < 1) {
                return data;
            }
            cursor.moveToFirst();

            data = new Payment_status();
            data.setPayStatus_id(cursor.getString(cursor.getColumnIndex(Payment_status.Column.PayStatus_id)));
            data.setPayStatus_name(cursor.getString(cursor.getColumnIndex(Payment_status.Column.PayStatus_name)));
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

