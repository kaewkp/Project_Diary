package com.example.kaew_pc.diary_project.Manager.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kaew_pc.diary_project.Manager.Database.Note_data;
import com.example.kaew_pc.diary_project.Manager.Database.Payment_history;
import com.example.kaew_pc.diary_project.Manager.Database.Payment_data;
import com.example.kaew_pc.diary_project.Manager.Database.Payment_history;

import java.util.ArrayList;

import static com.example.kaew_pc.diary_project.Manager.Database.Payment_history.Column.History_id;

/**
 * Created by chommchome on 8/6/2560.
 */

@SuppressWarnings("TryFinallyCanBeTryWithResources")
public class PaymentHistoryRepository {
    private static final String TAG = PaymentHistoryRepository.class.getSimpleName();

    public static String createTable(){
        String CREATE_Payment_history_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT, %s TEXT, %s REAL, %s TEXT, %s TEXT, %s TEXT)",
                Payment_history.TABLE,
                Payment_history.Column.History_id,
                Payment_history.Column.History_title,
                Payment_history.Column.History_desc,
                Payment_history.Column.History_price,
                Payment_history.Column.History_endDate,
                Payment_history.Column.History_date,
                Payment_history.Column.History_datePay);
//                Payment_data.Column.PayType_id,
//                Payment_data.Column.BankName_id,
//                Payment_data.Column.DebtTime_id,
//                Payment_data.Column.PayStatus_id,
//                Payment_data.Column.Noti_id);

        Log.i(TAG, CREATE_Payment_history_TABLE);
        return CREATE_Payment_history_TABLE;
    }

    public static String dropTable(){
        return "DROP TABLE IF EXISTS " + Payment_history.TABLE;
    }


    public void insertData(SQLiteDatabase db, Payment_history paymenthistory){
        ContentValues initialValues = new ContentValues();
//        initialValues.put(Payment_history.Column.History_id, paymenthistory.getHistory_id());
        initialValues.put(Payment_history.Column.History_title, paymenthistory.getHistory_title());
        initialValues.put(Payment_history.Column.History_desc, paymenthistory.getHistory_desc());
        initialValues.put(Payment_history.Column.History_price, paymenthistory.getHistory_price());
        initialValues.put(Payment_history.Column.History_date, paymenthistory.getHistory_date());
        initialValues.put(Payment_history.Column.History_endDate, paymenthistory.getHistory_endDate());
        initialValues.put(Payment_history.Column.History_datePay, paymenthistory.getHistory_datePay());
//        initialValues.put(Payment_data.Column.PayType_id, paymentdata.getPayType_id());
//        initialValues.put(Payment_data.Column.BankName_id, paymentdata.getBankName_id());
//        initialValues.put(Payment_data.Column.DebtTime_id, paymentdata.getDebtTime_id());
//        initialValues.put(Payment_data.Column.PayStatus_id, paymentdata.getPayStatus_id());
//        initialValues.put(Payment_data.Column.Noti_id, paymentdata.getNoti_id());

        Log.d(TAG + " Insert Data", "title : " + paymenthistory.getHistory_title());

        db.insert(Payment_history.TABLE, null, initialValues);
    }

    public void updateData(SQLiteDatabase db, Payment_history paymenthistory){
        ContentValues initialValues = new ContentValues();
        initialValues.put(Payment_history.Column.History_id, paymenthistory.getHistory_id());
        initialValues.put(Payment_history.Column.History_title, paymenthistory.getHistory_title());
        initialValues.put(Payment_history.Column.History_desc, paymenthistory.getHistory_desc());
        initialValues.put(Payment_history.Column.History_price, paymenthistory.getHistory_price());
        initialValues.put(Payment_history.Column.History_date, paymenthistory.getHistory_date());
        initialValues.put(Payment_history.Column.History_endDate, paymenthistory.getHistory_endDate());
        initialValues.put(Payment_history.Column.History_datePay, paymenthistory.getHistory_datePay());
//        initialValues.put(Payment_data.Column.PayType_id, paymentdata.getPayType_id());
//        initialValues.put(Payment_data.Column.BankName_id, paymentdata.getBankName_id());
//        initialValues.put(Payment_data.Column.DebtTime_id, paymentdata.getDebtTime_id());
//        initialValues.put(Payment_data.Column.PayStatus_id, paymentdata.getPayStatus_id());
//        initialValues.put(Payment_data.Column.Noti_id, paymentdata.getNoti_id());

        Log.d(TAG + " Update Data", "title : " + paymenthistory.getHistory_title());

        db.update(Payment_history.TABLE, initialValues, Payment_history.Column.History_id+"="+paymenthistory.getHistory_id(), null);
    }

    public void deleteData(SQLiteDatabase db, Payment_history paymenthistory){
        //do something
    }

    public ArrayList<Payment_history> getData(SQLiteDatabase db){
        Log.d(TAG + "Get Data", "select * from History");
        ArrayList<Payment_history> list = new ArrayList<Payment_history>();
        Cursor cursor = null;

        try {
            cursor = db.query(Payment_history.TABLE, null, null, null, null, null, null); //(table, column, where, where arg, groupby, having, orderby)

            if (cursor.getCount() < 1) {
                return list;
            }
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Log.d("History", "History id : " + cursor.getString(cursor.getColumnIndex(Payment_history.Column.History_id)));

                Payment_history data = new Payment_history(cursor.getInt(cursor.getColumnIndex(Payment_history.Column.History_id))
                        , cursor.getString(cursor.getColumnIndex(Payment_history.Column.History_title))
                        , cursor.getString(cursor.getColumnIndex(Payment_history.Column.History_desc))
                        , cursor.getDouble(cursor.getColumnIndex(Payment_history.Column.History_price))
                        , cursor.getString(cursor.getColumnIndex(Payment_history.Column.History_date))
                        , cursor.getString(cursor.getColumnIndex(Payment_history.Column.History_endDate))
                        , cursor.getString(cursor.getColumnIndex(Payment_history.Column.History_datePay)));
//                        , cursor.getString(cursor.getColumnIndex(Payment_data.Column.PayType_id))
//                        , cursor.getString(cursor.getColumnIndex(Payment_data.Column.BankName_id))
//                        , cursor.getString(cursor.getColumnIndex(Payment_data.Column.DebtTime_id))
//                        , cursor.getString(cursor.getColumnIndex(Payment_data.Column.PayStatus_id))
//                        , cursor.getString(cursor.getColumnIndex(Note_data.Column.Noti_id)));
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

    public int getHistoryID(SQLiteDatabase db){
        int id = -1;
        Cursor cursor = null;
        try {
            cursor = db.query(Payment_history.TABLE, null, null, null, null, null, null); //(table, column, where, where arg, groupby, having, orderby)

            if (cursor.getCount() < 1) {
                return id;
            }
            cursor.moveToLast();
            id = cursor.getInt(cursor.getColumnIndex(Payment_history.Column.History_id));
            Log.d("History", "History id : " + cursor.getString(cursor.getColumnIndex(Payment_history.Column.History_id)));
        }
        catch (Exception ex){
            Log.e(TAG + "Get Data", "Exception : " + ex.toString());
        }
        finally {
            if(cursor != null)
                cursor.close();
        }
        Log.e("Last", ""+id);
        return id;
    }

    public Payment_history getDataById(SQLiteDatabase db, String id){
        Log.d(TAG + "Get Data By ID", "ID : " + id);

        Payment_history data = null;

        Cursor cursor = null;
        try {
            cursor = db.query(Payment_history.TABLE,   //table
                    null,                   //column
                    "History_id = ?",       //where
                    new String[]{id},       //where arg
                    null, null, null);      //groupby, having, orderby

            if (cursor.getCount() < 1) {
                return data;
            }
            cursor.moveToFirst();

            Log.e(TAG + "Get Data By ID", DatabaseUtils.dumpCursorToString(cursor));

            data = new Payment_history();
            data.setHistory_id(cursor.getInt(cursor.getColumnIndex(Payment_history.Column.History_id)));
            data.setHistory_title(cursor.getString(cursor.getColumnIndex(Payment_history.Column.History_title)));
            data.setHistory_desc(cursor.getString(cursor.getColumnIndex(Payment_history.Column.History_desc)));
            data.setHistory_price(cursor.getDouble(cursor.getColumnIndex(Payment_history.Column.History_price)));
            data.setHistory_date(cursor.getString(cursor.getColumnIndex(Payment_history.Column.History_date)));
            data.setHistory_endDate(cursor.getString(cursor.getColumnIndex(Payment_history.Column.History_endDate)));
            data.setHistory_datePay(cursor.getString(cursor.getColumnIndex(Payment_history.Column.History_datePay)));
//            data.setPayType_id(cursor.getString(cursor.getColumnIndex(Payment_data.Column.PayType_id)));
//            data.setBankName_id(cursor.getString(cursor.getColumnIndex(Payment_data.Column.BankName_id)));
//            data.setDebtTime_id(cursor.getString(cursor.getColumnIndex(Payment_data.Column.DebtTime_id)));
//            data.setPayStatus_id(cursor.getString(cursor.getColumnIndex(Payment_data.Column.PayStatus_id)));
//            data.setNoti_id(cursor.getString(cursor.getColumnIndex(Note_data.Column.Noti_id)));
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


//    public Payment_data getDataByIdOrderByNew(SQLiteDatabase db, String id){
//        Log.d(TAG + "Get Data By ID", "select * from Payment order by id where DESC" + id);
//
//        Payment_data data = null;
//
//        Cursor cursor = null;
//        try {
//            cursor = db.query(Payment_data.TABLE,   //table
//                    null,                   //column
//                    "Payment_id = ?",       //where
//                    new String[]{id},       //where arg
//                    null, null, null);      //groupby, having, orderby
//
//            if (cursor.getCount() < 1) {
//                return data;
//            }
//            cursor.moveToFirst();
//
//            data = new Payment_data();
//            data.setPayment_id(cursor.getInt(cursor.getColumnIndex(Payment_data.Column.Payment_id)));
//            data.setPayment_title(cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_title)));
//            data.setPayment_desc(cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_desc)));
//            data.setPayment_price(cursor.getDouble(cursor.getColumnIndex(Payment_data.Column.Payment_price)));
//            data.setPayment_date(cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_date)));
//            data.setPayment_endDate(cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_endDate)));
//            data.setPayment_datePay(cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_datePay)));
//            data.setPayType_id(cursor.getString(cursor.getColumnIndex(Payment_data.Column.PayType_id)));
//            data.setBankName_id(cursor.getString(cursor.getColumnIndex(Payment_data.Column.BankName_id)));
//            data.setDebtTime_id(cursor.getString(cursor.getColumnIndex(Payment_data.Column.DebtTime_id)));
//            data.setPayStatus_id(cursor.getString(cursor.getColumnIndex(Payment_data.Column.PayStatus_id)));
//            data.setNoti_id(cursor.getString(cursor.getColumnIndex(Note_data.Column.Noti_id)));
//        }
//        catch (Exception ex){
//            Log.e(TAG + "Get Data By ID", "Exception : " + ex.toString());
//        }
//        finally {
//            if(cursor != null)
//                cursor.close();
//        }
//        return data;
//    }
}
