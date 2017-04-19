package com.example.kaew_pc.diary_project.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kaew_pc.diary_project.Database.Note_data;
import com.example.kaew_pc.diary_project.Database.PayType;
import com.example.kaew_pc.diary_project.Database.Payment_data;

import java.util.ArrayList;
import java.util.EmptyStackException;

/**
 * Created by Ekachart-PC on 12/4/2560.
 */

@SuppressWarnings("TryFinallyCanBeTryWithResources")
public class PaymentDataRepo {
    private static final String TAG = PaymentDataRepo.class.getSimpleName();

    public static String createTable(){
        String CREATE_Payment_data_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT, %s TEXT, %s REAL, %s TEXT, %s TEXT, %s VARCHAR(3), %s VARCHAR(3), %s VARCHAR(3))",
                Payment_data.TABLE,
                Payment_data.Column.Payment_id,
                Payment_data.Column.Payment_title,
                Payment_data.Column.Payment_desc,
                Payment_data.Column.Payment_price,
                Payment_data.Column.Payment_endDate,
                Payment_data.Column.Payment_date,
                Payment_data.Column.PayType_id,
                Payment_data.Column.PayStatus_id,
                Payment_data.Column.Noti_id);
        Log.i(TAG, CREATE_Payment_data_TABLE);
        return CREATE_Payment_data_TABLE;
    }

    public static String dropTable(){
        return "DROP TABLE IF EXISTS " + Payment_data.TABLE;
    }


    public void insertData(SQLiteDatabase db, Payment_data paymentdata){
        ContentValues initialValues = new ContentValues();
//        initialValues.put(Payment_data.Column.Payment_id, paymentdata.getPayment_id());
        initialValues.put(Payment_data.Column.Payment_title, paymentdata.getPayment_title());
        initialValues.put(Payment_data.Column.Payment_desc, paymentdata.getPayment_desc());
        initialValues.put(Payment_data.Column.Payment_price, paymentdata.getPayment_price());
        initialValues.put(Payment_data.Column.Payment_date, paymentdata.getPayment_date());
        initialValues.put(Payment_data.Column.Payment_endDate, paymentdata.getPayment_endDate());
        initialValues.put(Payment_data.Column.PayType_id, paymentdata.getPayType_id());
        initialValues.put(Payment_data.Column.PayStatus_id, paymentdata.getPayStatus_id());
        initialValues.put(Payment_data.Column.Noti_id, paymentdata.getNoti_id());

        Log.d(TAG + " Insert Data", "title : " + paymentdata.getPayment_title());

        db.insert(Payment_data.TABLE, null, initialValues);
    }

    public void updateData(SQLiteDatabase db, Payment_data paymentdata){
        ContentValues initialValues = new ContentValues();
        initialValues.put(Payment_data.Column.Payment_id, paymentdata.getPayment_id());
        initialValues.put(Payment_data.Column.Payment_title, paymentdata.getPayment_title());
        initialValues.put(Payment_data.Column.Payment_desc, paymentdata.getPayment_desc());
        initialValues.put(Payment_data.Column.Payment_price, paymentdata.getPayment_price());
        initialValues.put(Payment_data.Column.Payment_date, paymentdata.getPayment_date());
        initialValues.put(Payment_data.Column.Payment_endDate, paymentdata.getPayment_endDate());
        initialValues.put(Payment_data.Column.PayType_id, paymentdata.getPayType_id());
        initialValues.put(Payment_data.Column.PayStatus_id, paymentdata.getPayStatus_id());
        initialValues.put(Payment_data.Column.Noti_id, paymentdata.getNoti_id());

        Log.d(TAG + " Update Data", "title : " + paymentdata.getPayment_title());

        db.update(Payment_data.TABLE, initialValues, Payment_data.Column.Payment_id+"="+paymentdata.getPayment_id(), null);
    }

    public void deleteData(SQLiteDatabase db, Payment_data paymentdata){
        //do something
    }

    public ArrayList<Payment_data> getData(SQLiteDatabase db){
        Log.d(TAG + "Get Data", "select * from Payment");
        ArrayList<Payment_data> list = new ArrayList<Payment_data>();
        Cursor cursor = null;

        try {
            cursor = db.query(Payment_data.TABLE, null, null, null, null, null, null); //(table, column, where, where arg, groupby, having, orderby)

            if (cursor.getCount() < 1) {
                return list;
            }
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Log.d("Payment", "Payment id : " + cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_id)));

                Payment_data data = new Payment_data(cursor.getInt(cursor.getColumnIndex(Payment_data.Column.Payment_id))
                        , cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_title))
                        , cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_desc))
                        , cursor.getDouble(cursor.getColumnIndex(Payment_data.Column.Payment_price))
                        , cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_date))
                        , cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_endDate))
                        , cursor.getString(cursor.getColumnIndex(Payment_data.Column.PayType_id))
                        , cursor.getString(cursor.getColumnIndex(Payment_data.Column.PayStatus_id))
                        , cursor.getString(cursor.getColumnIndex(Note_data.Column.Noti_id)));
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

    public Payment_data getDataById(SQLiteDatabase db, String id){
        Log.d(TAG + "Get Data By ID", "ID : " + id);

        Payment_data data = null;

        Cursor cursor = null;
        try {
            cursor = db.query(Payment_data.TABLE,   //table
                            null,                   //column
                            "Payment_id = ?",       //where
                            new String[]{id},       //where arg
                            null, null, null);      //groupby, having, orderby

            if (cursor.getCount() < 1) {
                return data;
            }
            cursor.moveToFirst();

            data = new Payment_data();
            data.setPayment_id(cursor.getInt(cursor.getColumnIndex(Payment_data.Column.Payment_id)));
            data.setPayment_title(cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_title)));
            data.setPayment_title(cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_desc)));
            data.setPayment_price(cursor.getDouble(cursor.getColumnIndex(Payment_data.Column.Payment_price)));
            data.setPayment_date(cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_date)));
            data.setPayment_endDate(cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_endDate)));
            data.setPayType_id(cursor.getString(cursor.getColumnIndex(Payment_data.Column.PayType_id)));
            data.setPayStatus_id(cursor.getString(cursor.getColumnIndex(Payment_data.Column.PayStatus_id)));
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
}
