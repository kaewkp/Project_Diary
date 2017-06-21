package com.example.kaew_pc.diary_project.Manager.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kaew_pc.diary_project.Manager.Database.BankName;
import com.example.kaew_pc.diary_project.Manager.Database.Note_recycle;
import com.example.kaew_pc.diary_project.R;

import java.util.ArrayList;

import static com.example.kaew_pc.diary_project.Manager.Database.BankName.Column.BankName_id;


/**
 * Created by chommchome on 29/4/2560.
 */

public class BankNameRepository {
    private static final String TAG = BankNameRepository.class.getSimpleName();

    Context mContext;
    public BankNameRepository(Context context) // constructor
    {
        mContext = context;
    }

    public static String createTable(){
        String CREATE_BankName_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT)",
                BankName.TABLE,
                BankName.Column.BankName_id,
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
        String[] BankName = mContext.getResources().getStringArray(R.array.BankName);
        for (int i = 0; i < BankName.length; i++) {
            insertData(db, "bn"+(i+1), BankName[i]);
        }
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
                Log.d("BankName", "BankName id : " + cursor.getString(cursor.getColumnIndex(BankName_id)));

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

    public BankName getDataByName(SQLiteDatabase db, String name){
        Log.d(TAG + "Get Data By Name", "Name : " + name);

        BankName data = null;

        Cursor cursor = null;
        try {
            cursor = db.query(BankName.TABLE,                //table
                    null,                                   //column
                    BankName.Column.BankName_name + "=?",       //where
                    new String[]{name},                       //where arg
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
