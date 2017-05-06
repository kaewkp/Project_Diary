package com.example.kaew_pc.diary_project.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kaew_pc.diary_project.Repository.BinRepo;
import com.example.kaew_pc.diary_project.Repository.Note_dataRepo;

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
                        "(%s VARCHAR(1) PRIMARY KEY, %s TEXT, %s TEXT)",
                Password.TABLE,
                Password.Column.id,
                Password.Column.Password,
                Password.Column.Personal_id);
        Log.i(TAG, CREATE_Password_TABLE);

        // Table Note_data
        db.execSQL(Note_dataRepo.createTable());

        // Table Recycle
        db.execSQL(BinRepo.createTable());

        db.execSQL(CREATE_Password_TABLE);
    }


    public void setPassword(SQLiteDatabase db, String pass, String pid){
        ContentValues initialValues = new ContentValues();
        initialValues.put(Password.Column.id, "0");
        initialValues.put(Password.Column.Password, pass);
        initialValues.put(Password.Column.Personal_id, pid);
        Log.d("Set Password", "pass : " + pass);

        db.insert(Password.TABLE, null, initialValues);
    }

    public void updatePassword(SQLiteDatabase db, String pass){
        ContentValues initialValues = new ContentValues();
        initialValues.put(Password.Column.Password, pass);
        Log.d("Update Password", "pass : " + pass);

        db.update(Password.TABLE, initialValues, Password.Column.id+"=0", null);
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Table Password
        String DROP_Password_TABLE = "DROP TABLE IF EXISTS " + Password.TABLE;

        db.execSQL(DROP_Password_TABLE);
        db.execSQL(Note_dataRepo.dropTable());
        db.execSQL(BinRepo.dropTable());

        Log.i(TAG, "Upgrade Database from " + oldVersion + " to " + newVersion);

        onCreate(db);

    }

}

