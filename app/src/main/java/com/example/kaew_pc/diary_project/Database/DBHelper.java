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
    private static DBHelper dbHelper = null;

    private static final String TAG = "DBHelper";
    private static final String DBName = "Database.db";
    private static final int DATABASE_VERSION = 1;

    private Context context;

    public static synchronized DBHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context.getApplicationContext());
        }
        return dbHelper;
    }


    public DBHelper(Context context) {
        super(context, DBName, null, DATABASE_VERSION);
        this.context = context;
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



        //Table Payment_data
        String CREATE_Payment_data_TABLE = String.format("CREATE TABLE %s " +
                        "(%s VARCHAR(5) PRIMARY KEY , %s TEXT, %s TEXT, DATE, DATE, %s VARCHAR(3))",
                Payment_data.TABLE,
                Payment_data.Column.Payment_id,
                Payment_data.Column.Payment_title,
                Payment_data.Column.Payment_price,
                Payment_data.Column.Payment_endDate,
                Payment_data.Column.Payment_date,
                Payment_data.Column.PayType_id,
                Payment_data.Column.PayStatus_id,
                Payment_data.Column.Noti_id);
        Log.i(TAG, CREATE_Payment_data_TABLE);


        db.execSQL(CREATE_Password_TABLE);
        db.execSQL(CREATE_Note_data_TABLE);
        db.execSQL(CREATE_Payment_data_TABLE);
    }

    public void setPassword(SQLiteDatabase db, String pass){
        ContentValues initialValues = new ContentValues();
        initialValues.put(Password.Column.Password, pass);
        Log.d("Set Password", "pass : " + pass);

        db.insert(Password.TABLE, null, initialValues);
    }

    public String getPassword(){
        Log.d("Search!!!!!", "query in Password");
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

    public void createNote(SQLiteDatabase db, Note_data notedata){
        ContentValues initialValues = new ContentValues();
        initialValues.put(Note_data.Column.Note_id, notedata.getNote_id());
        initialValues.put(Note_data.Column.Note_title, notedata.getNote_title());
        initialValues.put(Note_data.Column.Note_desc, notedata.getNote_desc());
        initialValues.put(Note_data.Column.Note_date, notedata.getNote_date());
        initialValues.put(Note_data.Column.Noti_id, notedata.getNoti_id());

        Log.d("Insert Note Data", "title : " + notedata.getNote_title());

        db.insert(Note_data.TABLE, null, initialValues);
    }


    public void createPayment(SQLiteDatabase db, Payment_data paymentdata){
        ContentValues initialValues = new ContentValues();
        initialValues.put(Payment_data.Column.Payment_id, paymentdata.getPayment_id());
        initialValues.put(Payment_data.Column.Payment_title, paymentdata.getPayment_title());
        initialValues.put(Payment_data.Column.Payment_price, paymentdata.getPayment_price());
        initialValues.put(Payment_data.Column.Payment_date, paymentdata.getPayment_date());
        initialValues.put(Payment_data.Column.Payment_endDate, paymentdata.getPayment_endDate());
        initialValues.put(Payment_data.Column.PayType_id, paymentdata.getPayType_id());
        initialValues.put(Payment_data.Column.PayStatus_id, paymentdata.getPayStatus_id());
        initialValues.put(Payment_data.Column.Noti_id, paymentdata.getNoti_id());

        Log.d("Insert Note Data", "title : " + paymentdata.getPayment_title());

        db.insert(Payment_data.TABLE, null, initialValues);
    }


    //Note
    public ArrayList<Note_data> getAllNote(){
        Log.d("Search!!!!!!", "query in Note");
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Note_data> list = new ArrayList<Note_data>();

        Cursor cursor = db.query(Note_data.TABLE, null, null, null, null, null, null); //(table, column, where, where arg, groupby, having, orderby)
        if(cursor.getCount() < 1){

        }
        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {
            Log.d("Search!!!!!!", "Note id : " + cursor.getString(cursor.getColumnIndex(Note_data.Column.Note_id)));

            Note_data data = new Note_data(cursor.getString(cursor.getColumnIndex(Note_data.Column.Note_id))
                    , cursor.getString(cursor.getColumnIndex(Note_data.Column.Note_title))
                    , cursor.getString(cursor.getColumnIndex(Note_data.Column.Note_desc))
                    , cursor.getString(cursor.getColumnIndex(Note_data.Column.Note_date))
                    , cursor.getString(cursor.getColumnIndex(Note_data.Column.Noti_id)));
            list.add(data);
            cursor.moveToNext();
        }
        cursor.close();

        return list;
    }


    //Payment
    public ArrayList<Payment_data> getAllPayment(){
        Log.d("Search!!!!!!", "query in Note");
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Payment_data> list = new ArrayList<Payment_data>();

        Cursor cursor = db.query(Payment_data.TABLE, null, null, null, null, null, null); //(table, column, where, where arg, groupby, having, orderby)
        if(cursor.getCount() < 1){

        }
        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {
            Log.d("Search!!!!!!", "Payment id : " + cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_id)));

            Payment_data data = new Payment_data(cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_id))
                    , cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_title))
                    , cursor.getDouble(cursor.getColumnIndex(Payment_data.Column.Payment_price))
                    , cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_date))
                    , cursor.getString(cursor.getColumnIndex(Payment_data.Column.Payment_endDate))
                    , cursor.getString(cursor.getColumnIndex(Payment_data.Column.PayType_id))
                    , cursor.getString(cursor.getColumnIndex(Payment_data.Column.PayStatus_id))
                    , cursor.getString(cursor.getColumnIndex(Note_data.Column.Noti_id)));
            list.add(data);
            cursor.moveToNext();
        }
        cursor.close();

        return list;
    }

//    private void insertPaymentStatus(SQLiteDatabase payment, String id, String name) {
//        ContentValues initialValues = new ContentValues();
//        initialValues.put(Payments_status.Column.PayStatus_id, id);
//        initialValues.put(Payments_status.Column.PayStatus_name, name);
//        Log.d("Insert Payment Status", id + " : " + name);
//
//        payment.insert(Payments_status.TABLE, null, initialValues);
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Table Note_data
        String DROP_Note_data_TABLE = "DROP TABLE IF EXISTS " + Note_data.TABLE;

        String DROP_Payment_data_TABLE = "DROP TABLE IF EXISTS " + Payment_data.TABLE;

        db.execSQL(DROP_Note_data_TABLE);
        db.execSQL(DROP_Payment_data_TABLE);

        Log.i(TAG, "Upgrade Database from " + oldVersion + " to " + newVersion);

        onCreate(db);

    }


    public List<Map.Entry<String,String>> getNoteData(){
        Log.d("Search", "query in Note Data");
        List<Map.Entry<String,String>> list = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

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



    public List<Map.Entry<String,String>> getPaymentData(){
        Log.d("Search", "query in Note Data");
        List<Map.Entry<String,String>> list = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(Payment_data.TABLE, null, null, null, null, null, null); //(table, column, where, where arg, groupby, having, orderby)
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

