package com.example.kaew_pc.diary_project.Manager.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kaew_pc.diary_project.Manager.Database.BankName;

import java.util.ArrayList;

import static com.example.kaew_pc.diary_project.Manager.Database.BankName.Column.BankName_id;


/**
 * Created by chommchome on 29/4/2560.
 */

public class BankNameRepository {
    private static final String TAG = BankNameRepository.class.getSimpleName();

    public static String createTable(){
        String CREATE_BankName_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT)",
                BankName.TABLE,
                BankName_id,
                BankName.Column.BankName_name);
        Log.i(TAG, CREATE_BankName_TABLE);
        return CREATE_BankName_TABLE;
    }

    public static String dropTable(){
        return "DROP TABLE IF EXISTS " + BankName.TABLE;
    }

    private void insertData(SQLiteDatabase db, String id, String name){
        ContentValues initialValues = new ContentValues();
//        initialValues.put(BankName_id, id);
        initialValues.put(BankName.Column.BankName_name, name);
        Log.d("Insert BankName Data", "title : " + name);
        db.insert(BankName.TABLE, null, initialValues);
    }

    public void createData(SQLiteDatabase db) {
        insertData(db, "bn1","SCB (ไทยพาณิชย์)");
        insertData(db, "bn2","KBANK (กสิกรไทย)");
        insertData(db, "bn3","GSB (ออมสิน)");
        insertData(db, "bn4","KTC (กรุงไทย)");
        insertData(db, "bn5","UOB (ยูโอบี)");
        insertData(db, "bn6","KRUNGSRI (กรุงศรี)");
        insertData(db, "bn7","TMB (ทหารไทย)");
        insertData(db, "bn8","AEON (อิออน)");
        insertData(db, "bn9","BBL (บัวหลวง)");
        insertData(db, "bn10","Citybank  (ซิตี้แบงก์)");
    }

    public ArrayList<BankName> getData(SQLiteDatabase db){
        Log.d("BankName", "select * from Bankname");
        ArrayList<BankName> list = new ArrayList<BankName>();
        Cursor cursor = null;

        try {
            cursor = db.query(BankName.TABLE, null, null, null, null, null, null); //(table, column, where, where arg, groupby, having, orderby)

            if (cursor.getCount() < 1) {
                return list;
            }
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Log.d("BankName", "PayType id : " + cursor.getString(cursor.getColumnIndex(BankName_id)));

                BankName data = new BankName(cursor.getString(cursor.getColumnIndex(BankName_id))
                        , cursor.getString(cursor.getColumnIndex(BankName.Column.BankName_name)));
                list.add(data);
                cursor.moveToNext();
            }
        }
        catch (Exception ex){
            Log.e("BankName", ex.toString());
        }
        finally {
            if(cursor != null)
                cursor.close();
        }
        return list;
    }

    public BankName getDataById(SQLiteDatabase db, String id){
        Log.d(TAG + "Get Data By ID", "ID : " + id);

        BankName data = null;

        Cursor cursor = null;
        try {
            cursor = db.query(BankName.TABLE,                //table
                    null,                                   //column
                    BankName.Column.BankName_id + "=?",       //where
                    new String[]{id},                       //where arg
                    null, null, null);                      //groupby, having, orderby

            if (cursor.getCount() < 1) {
                return data;
            }
            cursor.moveToFirst();

            data = new BankName();
            data.setBankName_id(cursor.getString(cursor.getColumnIndex(BankName.Column.BankName_id)));
            data.setBankName_name(cursor.getString(cursor.getColumnIndex(BankName.Column.BankName_name)));
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
