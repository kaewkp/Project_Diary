package com.example.kaew_pc.diary_project.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kaew_pc.diary_project.Repository.PaymentDataRepo;
import com.example.kaew_pc.diary_project.Repository.PaymentTypeRepo;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.runner.Version.id;

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper dbHelper = null;

    private static final String TAG = "DBHelper";
    private static final String DBName = "Database.db";
    private static final int DATABASE_VERSION = 2;

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
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT, %s TEXT, %s TEXT, %s VARCHAR(3))",
                Note_data.TABLE,
                Note_data.Column.Note_id,
                Note_data.Column.Note_title,
                Note_data.Column.Note_desc,
                Note_data.Column.Note_date,
                Note_data.Column.Noti_id);
        Log.i(TAG, CREATE_Note_data_TABLE);

        db.execSQL(CREATE_Password_TABLE);
        db.execSQL(CREATE_Note_data_TABLE);
        db.execSQL(PaymentDataRepo.createTable());
        db.execSQL(PaymentTypeRepo.createTable());

        new PaymentTypeRepo().createData(db);
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
//        initialValues.put(Note_data.Column.Note_id, notedata.getNote_id());
        initialValues.put(Note_data.Column.Note_title, notedata.getNote_title());
        initialValues.put(Note_data.Column.Note_desc, notedata.getNote_desc());
        initialValues.put(Note_data.Column.Note_date, notedata.getNote_date());
        initialValues.put(Note_data.Column.Noti_id, notedata.getNoti_id());

        Log.d("Insert Note Data", "title : " + notedata.getNote_title());

        db.insert(Note_data.TABLE, null, initialValues);
    }

    public void updateNote(SQLiteDatabase db, Note_data notedata){
        ContentValues initialValues = new ContentValues();
//        initialValues.put(Note_data.Column.Note_id, notedata.getNote_id());
        initialValues.put(Note_data.Column.Note_title, notedata.getNote_title());
        initialValues.put(Note_data.Column.Note_desc, notedata.getNote_desc());
        initialValues.put(Note_data.Column.Note_date, notedata.getNote_date());
        initialValues.put(Note_data.Column.Noti_id, notedata.getNoti_id());

        Log.d("Update Note Dsta", "title : " + notedata.getNote_title());

        db.update(Note_data.TABLE, initialValues, "Note_id="+notedata.getNote_id(), null);
    }

    public ArrayList<Note_data> getAllNote(){
        Log.d("Search!!!!!!", "query in Note");
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Note_data> list = new ArrayList<Note_data>();
        Cursor cursor = null;
        try {
            cursor = db.query("yy", null, null, null, null, null, null); //(table, column, where, where arg, groupby, having, orderby)
            if (cursor.getCount() < 1) {
                return list;
            }
            Log.d("Search!!!!!!", "Here 1");
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Log.d("Search!!!!!!", "Note id : " + cursor.getString(cursor.getColumnIndex(Note_data.Column.Note_id)));

                Note_data data = new Note_data(cursor.getInt(cursor.getColumnIndex(Note_data.Column.Note_id))
                        , cursor.getString(cursor.getColumnIndex(Note_data.Column.Note_title))
                        , cursor.getString(cursor.getColumnIndex(Note_data.Column.Note_desc))
                        , cursor.getString(cursor.getColumnIndex(Note_data.Column.Note_date))
                        , cursor.getString(cursor.getColumnIndex(Note_data.Column.Noti_id)));
                list.add(data);
                cursor.moveToNext();
            }
        }
        catch (Exception ex){
            Log.e(TAG + "Get Data", "Exception : " + ex.toString());
        }
        finally {
            Log.d("Search!!!!!!", "Here 2");
            cursor.close();
        }
        Log.d("Search!!!!!!", "Here 3");
        return list;
    }

    public Note_data getNoteById(String id){
        Log.d("Search!!!!!!", "query in Note by id");

        Note_data data = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Note_data.TABLE,
                null,
                "Note_id = ?",
                new String[] { id },
                null, null, null); //(table, column, where, where arg, groupby, having, orderby)

        if(cursor.getCount() < 1){

        }

        Log.d("Search!!!!!!", "size : " + cursor.getCount());

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {
            data = new Note_data();
            data.setNote_id(cursor.getInt(cursor.getColumnIndex(Note_data.Column.Note_id)));
            data.setNote_title(cursor.getString(cursor.getColumnIndex(Note_data.Column.Note_title)));
            data.setNote_desc(cursor.getString(cursor.getColumnIndex(Note_data.Column.Note_desc)));
            data.setNote_date(cursor.getString(cursor.getColumnIndex(Note_data.Column.Note_date)));
            data.setNoti_id(cursor.getString(cursor.getColumnIndex(Note_data.Column.Noti_id)));
            cursor.moveToNext();
        }
        cursor.close();

        return data;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Table Password
        String DROP_Password_TABLE = "DROP TABLE IF EXISTS " + Password.TABLE;

        //Table Note_data
        String DROP_Note_data_TABLE = "DROP TABLE IF EXISTS " + Note_data.TABLE;

        db.execSQL(DROP_Note_data_TABLE);
        db.execSQL(PaymentDataRepo.dropTable());
        db.execSQL(DROP_Password_TABLE);
        db.execSQL(PaymentTypeRepo.dropTable());

        Log.i(TAG, "Upgrade Database from " + oldVersion + " to " + newVersion);

        onCreate(db);

    }
}

