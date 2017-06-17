package com.example.kaew_pc.diary_project.Manager.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kaew_pc.diary_project.Manager.Database.Payment_History;

import java.util.ArrayList;

import static com.example.kaew_pc.diary_project.Manager.Database.Payment_History.Column.History_id;

/**
 * Created by chommchome on 8/6/2560.
 */

public class PaymentHistoryRepository {
        private static final String TAG =PaymentHistoryRepository.class.getSimpleName();

        public static String createTable(){
            String CREATE_Payment_History_TABLE = String.format("CREATE TABLE %s " +
                            "(%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT, %s TEXT, %s REAL, %s TEXT, %s TEXT)",
                    Payment_History.TABLE,
                    Payment_History.Column.History_id,
                    Payment_History.Column.History_title,
                    Payment_History.Column.History_desc,
                    Payment_History.Column.History_price,
                    Payment_History.Column.History_date,
                    Payment_History.Column.History_endDate,
                    Payment_History.Column.History_finish);
            Log.i(TAG, CREATE_Payment_History_TABLE);
            return CREATE_Payment_History_TABLE;
        }

        public static String dropTable(){
            return "DROP TABLE IF EXISTS " + Payment_History.TABLE;
        }

        private void insertData(SQLiteDatabase db, String id, String name, String desc, String price, String date, String endDate, String finish){
            ContentValues initialValues = new ContentValues();
//        initialValues.put(History_id, id);
            initialValues.put(Payment_History.Column.History_title, name);
            initialValues.put(Payment_History.Column.History_desc, desc);
            initialValues.put(Payment_History.Column.History_price, price);
            initialValues.put(Payment_History.Column.History_date, date);
            initialValues.put(Payment_History.Column.History_endDate, endDate);
            initialValues.put(Payment_History.Column.History_finish, finish);

            Log.d("Insert History Data", "title : " + name);
            db.insert(Payment_History.TABLE, null, initialValues);
        }

        public void createData(SQLiteDatabase db) {
            insertData(db, "1","ค่าน้ำ","บ้านเลขที่ 18","1,250","2017-06-07","2017-06-12","2017-06-09");
//            insertData(db, "pt2","ค่าไฟ");
//            insertData(db, "pt3","ค่าโทรศัพท์");
//            insertData(db, "pt4","ค่าบัตรเครดิต");
//            insertData(db, "pt5","ค่าผ่อนชำระ");
//            insertData(db, "pt6","อื่นๆ");
        }

        public ArrayList<Payment_History> getData(SQLiteDatabase db){
            Log.d("History", "select * from History");
            ArrayList<Payment_History> list = new ArrayList<Payment_History>();
            Cursor cursor = null;

            try {
                cursor = db.query(Payment_History.TABLE, null, null, null, null, null, null); //(table, column, where, where arg, groupby, having, orderby)

                if (cursor.getCount() < 1) {
                    return list;
                }
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Log.d("Payment_History", "History id : " + cursor.getString(cursor.getColumnIndex(History_id)));

                    Payment_History data = new Payment_History(cursor.getInt(cursor.getColumnIndex(History_id))
                            , cursor.getString(cursor.getColumnIndex(Payment_History.Column.History_title))
                            , cursor.getString(cursor.getColumnIndex(Payment_History.Column.History_desc))
                            , cursor.getString(cursor.getColumnIndex(Payment_History.Column.History_price))
                            , cursor.getString(cursor.getColumnIndex(Payment_History.Column.History_date))
                            , cursor.getString(cursor.getColumnIndex(Payment_History.Column.History_endDate))
                            , cursor.getString(cursor.getColumnIndex(Payment_History.Column.History_finish)));
                    list.add(data);
                    cursor.moveToNext();
                }
            }
            catch (Exception ex){
                Log.e("Payment_History", ex.toString());
            }
            finally {
                if(cursor != null)
                    cursor.close();
            }
            return list;
        }

        public Payment_History getDataById(SQLiteDatabase db, String id){
            Log.d(TAG + "Get Data By ID", "ID : " + id);

            Payment_History data = null;

            Cursor cursor = null;
            try {
                cursor = db.query(Payment_History.TABLE,                //table
                        null,                                   //column
                        Payment_History.Column.History_id + "=?",       //where
                        new String[]{id},                       //where arg
                        null, null, null);                      //groupby, having, orderby

                if (cursor.getCount() < 1) {
                    return data;
                }
                cursor.moveToFirst();

                data = new Payment_History();
                data.setHistory_id(cursor.getInt(cursor.getColumnIndex(Payment_History.Column.History_id)));
                data.setHistory_title(cursor.getString(cursor.getColumnIndex(Payment_History.Column.History_title)));
                data.setHistory_desc(cursor.getString(cursor.getColumnIndex(Payment_History.Column.History_desc)));
                data.setHistory_price(cursor.getString(cursor.getColumnIndex(Payment_History.Column.History_price)));
                data.setHistory_date(cursor.getString(cursor.getColumnIndex(Payment_History.Column.History_date)));
                data.setHistory_endDate(cursor.getString(cursor.getColumnIndex(Payment_History.Column.History_endDate)));
                data.setHistory_finish(cursor.getString(cursor.getColumnIndex(Payment_History.Column.History_finish)));
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
