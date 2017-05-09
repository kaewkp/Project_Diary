package com.example.kaew_pc.diary_project.Manager.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kaew_pc.diary_project.Database.PayType;

import java.util.ArrayList;

import static com.example.kaew_pc.diary_project.Database.PayType.Column.PayType_id;

/**
 * Created by Ekachart-PC on 12/4/2560.
 */

public class PaymentTypeRepository {
    private static final String TAG = PaymentTypeRepository.class.getSimpleName();

    public static String createTable(){
        String CREATE_PayType_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT)",
                PayType.TABLE,
                PayType.Column.PayType_id,
                PayType.Column.PayType_name);
        Log.i(TAG, CREATE_PayType_TABLE);
        return CREATE_PayType_TABLE;
    }

    public static String dropTable(){
        return "DROP TABLE IF EXISTS " + PayType.TABLE;
    }

    private void insertData(SQLiteDatabase db, String id, String name){
        ContentValues initialValues = new ContentValues();
//        initialValues.put(PayType_id, id);
        initialValues.put(PayType.Column.PayType_name, name);
        Log.d("Insert PayType Data", "title : " + name);
        db.insert(PayType.TABLE, null, initialValues);
    }

    public void createData(SQLiteDatabase db) {
        insertData(db, "pt1","ค่าน้ำ");
        insertData(db, "pt2","ค่าไฟ");
        insertData(db, "pt3","ค่าโทรศัพท์");
        insertData(db, "pt4","ค่าบัตรเครดิต");
        insertData(db, "pt5","ค่าผ่อนชำระ");
    }

    public ArrayList<PayType> getData(SQLiteDatabase db){
        Log.d("PayType", "select * from PayType");
        ArrayList<PayType> list = new ArrayList<PayType>();
        Cursor cursor = null;

        try {
            cursor = db.query(PayType.TABLE, null, null, null, null, null, null); //(table, column, where, where arg, groupby, having, orderby)

            if (cursor.getCount() < 1) {
                return list;
            }
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Log.d("PayType", "PayType id : " + cursor.getString(cursor.getColumnIndex(PayType_id)));

                PayType data = new PayType(cursor.getString(cursor.getColumnIndex(PayType_id))
                        , cursor.getString(cursor.getColumnIndex(PayType.Column.PayType_name)));
                list.add(data);
                cursor.moveToNext();
            }
        }
        catch (Exception ex){
            Log.e("PayType", ex.toString());
        }
        finally {
            if(cursor != null)
                cursor.close();
        }
        return list;
    }

    public PayType getDataById(SQLiteDatabase db, String id){
        Log.d(TAG + "Get Data By ID", "ID : " + id);

        PayType data = null;

        Cursor cursor = null;
        try {
            cursor = db.query(PayType.TABLE,                //table
                    null,                                   //column
                    PayType.Column.PayType_id + "=?",       //where
                    new String[]{id},                       //where arg
                    null, null, null);                      //groupby, having, orderby

            if (cursor.getCount() < 1) {
                return data;
            }
            cursor.moveToFirst();

            data = new PayType();
            data.setPayType_id(cursor.getString(cursor.getColumnIndex(PayType.Column.PayType_id)));
            data.setPayType_name(cursor.getString(cursor.getColumnIndex(PayType.Column.PayType_name)));
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
