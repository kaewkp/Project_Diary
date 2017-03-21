package com.example.kaew_pc.diary_project.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";
    private static final String DBName = "Database.db";

    private SQLiteDatabase db;
    private Context context;


    public DBHelper(Context context) {
        super(context, DBName, null, 1);
        this.context = context;
//        this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_Password_TABLE = String.format("CREATE TABLE %s " +
                        "(%s VARCHAR(1) PRIMARY KEY, %s TEXT)",
                Password.TABLE,
                Password.Column.id,
                Password.Column.Password);
        Log.i(TAG, CREATE_Password_TABLE);

        // Table Note_data
        String CREATE_Note_data_TABLE = String.format("CREATE TABLE %s " +
                        "(%s VARCHAR(5) PRIMARY KEY , %s TEXT, %s TEXT, %s TEXT, %s VARCHAR(3))",
                Note_data.TABLE,
                Note_data.Column.Note_id,
                Note_data.Column.Note_title,
                Note_data.Column.Note_desc,
                Note_data.Column.Note_date,
                Note_data.Column.Noti_id);
        Log.i(TAG, CREATE_Note_data_TABLE);

        db.execSQL(CREATE_Password_TABLE);
        db.execSQL(CREATE_Note_data_TABLE);
    }

    public void setPassword(SQLiteDatabase db, String pass){
        ContentValues initialValues = new ContentValues();
        initialValues.put(Password.Column.Password, pass);
        Log.d("Insert Note Dsta", "2 : ค้างชำระ");

        db.insert(Password.TABLE, null, initialValues);
    }

    public String getPassword(){
        Log.d("Search", "query in Password");
        SQLiteDatabase db = this.getReadableDatabase();
        String pass = "";

        Cursor cursor = db.query(Password.TABLE, null, null, null, null, null, null); //(table, column, where, where arg, groupby, having, orderby)
        if(cursor.getCount() < 1){

        }
        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {
            pass = cursor.getString(1);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("Search", "query in Password success : "+ pass);
        return pass;
    }

//    private void insertPaymentStatus(SQLiteDatabase payment, String id, String name) {
//        ContentValues initialValues = new ContentValues();
//        initialValues.put(Payments_status.Column.PayStatus_id, id);
//        initialValues.put(Payments_status.Column.PayStatus_name, name);
//        Log.d("Insert Payment Status", id + " : " + name);
//
//        payment.insert(Payments_status.TABLE, null, initialValues);
//    }

    private void insertNoteData(SQLiteDatabase note) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(Note_data.Column.Note_id, "N01");
        initialValues.put(Note_data.Column.Note_title, "สอบFinal13พ.ค.60");
        initialValues.put(Note_data.Column.Note_desc, "ถูกผิด เขียน เติมคำ");
        initialValues.put(Note_data.Column.Note_date, "10/02/2017");
        initialValues.put(Note_data.Column.Noti_id, "1");
        initialValues.put(Note_data.Column.Note_id, "์N02");
        initialValues.put(Note_data.Column.Note_title, "ค่าเทอม");
        initialValues.put(Note_data.Column.Note_desc, "19000");
        initialValues.put(Note_data.Column.Note_date, "15/03/2017");
        initialValues.put(Note_data.Column.Noti_id, "1");
        Log.d("Insert Note Data", "N01 : สอบSW Design Midterm : 27/03/60 : 22/03/60 : 25/03/60");
        Log.d("Insert Note Dsta", "2 : ค้างชำระ");

        note.insert(Note_data.TABLE, null, initialValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Table Note_data
        String DROP_Note_data_TABLE = "DROP TABLE IF EXISTS " + Note_data.TABLE;

        db.execSQL(DROP_Note_data_TABLE);

        Log.i(TAG, "Upgrade Database from " + oldVersion + " to " + newVersion);

        onCreate(db);

    }


    public List<Map.Entry<String,String>> getNoteData(){
        Log.d("Search", "query in Note Data");
        List<Map.Entry<String,String>> list = new ArrayList<>();

        db = this.getWritableDatabase();

        Cursor cursor = db.query(Note_data.TABLE, null, null, null, null, null, null); //(table, column, where, where arg, groupby, having, orderby)
        if(cursor.getCount() < 1){

        }
        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {
            list.add(new AbstractMap.SimpleEntry<>(cursor.getString(0), cursor.getString(1)));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }


//    public List<Map.Entry<String,String>> getPaymentData(){
//        Log.d("Search", "query in payment Data");
//        List<Map.Entry<String,String>> list = new ArrayList<>();
//
//        db = this.getWritableDatabase();
//
//        Cursor cursor = db.query(Payments_data.TABLE, null, null, null, null, null, null); //(table, column, where, where arg, groupby, having, orderby)
//        if(cursor.getCount() < 1){
//
//        }
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//
//        while(!cursor.isAfterLast()) {
//            list.add(new AbstractMap.SimpleEntry<>(cursor.getString(0), cursor.getString(1)));
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return list;
//    }
//
//
//    public List<Map.Entry<String,String>> getPaymentType(){
//        Log.d("Search", "query in payment Type");
//        List<Map.Entry<String,String>> list = new ArrayList<>();
//
//        db = this.getWritableDatabase();
//
//        Cursor cursor = db.query(Payments_type.TABLE, null, null, null, null, null, null); //(table, column, where, where arg, groupby, having, orderby)
//        if(cursor.getCount() < 1){
//
//        }
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//
//        while(!cursor.isAfterLast()) {
//            list.add(new AbstractMap.SimpleEntry<>(cursor.getString(0), cursor.getString(1)));
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return list;
//    }
//
//    public List<Map.Entry<String,String>> getPaymentStatus(){
//        Log.d("Search", "query in payment Status");
//        List<Map.Entry<String,String>> list = new ArrayList<>();
//
//        db = this.getWritableDatabase();
//
//        Cursor cursor = db.query(Payments_status.TABLE, null, null, null, null, null, null); //(table, column, where, where arg, groupby, having, orderby)
//        if(cursor.getCount() < 1){
//
//        }
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//
//        while(!cursor.isAfterLast()) {
//            list.add(new AbstractMap.SimpleEntry<>(cursor.getString(0), cursor.getString(1)));
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return list;
//    }
//    public Payments_data getNote_data(String Note_id) {
//
//        db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(Payments_data.TABLE,
//                null,
//                Payments_data.Column.Note_id + " = ? ",
//                new String[]{Note_id},
//                null,
//                null,
//                null,
//                null);
//
//        if (cursor != null && cursor.getCount() > 0) {
//            cursor.moveToFirst();
//        }else{
//            return null;
//        }
//    }

}

