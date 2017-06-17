package com.example.kaew_pc.diary_project.Manager.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kaew_pc.diary_project.Manager.Repository.BankNameRepository;
import com.example.kaew_pc.diary_project.Manager.Repository.DebtTimeRepository;
import com.example.kaew_pc.diary_project.Manager.Repository.NoteImageRepository;

import com.example.kaew_pc.diary_project.Manager.Repository.CalendarDataRepository;
import com.example.kaew_pc.diary_project.Manager.Repository.CalendarTypeRepository;

import com.example.kaew_pc.diary_project.Manager.Repository.PaymentDataRepository;
import com.example.kaew_pc.diary_project.Manager.Repository.PaymentStatusRepository;
import com.example.kaew_pc.diary_project.Manager.Repository.PaymentTypeRepository;

import com.example.kaew_pc.diary_project.Manager.Repository.BinRepository;
import com.example.kaew_pc.diary_project.Manager.Repository.NoteDataRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper dbHelper = null;

    private static final String TAG = "DBHelper";
    private static final String DBName = "Database.db";
    private static final int DATABASE_VERSION = 1;

    public static final SimpleDateFormat sdf = new SimpleDateFormat(Notification.DEFAULT_DATE_FORMAT);

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
        db.execSQL(CREATE_Password_TABLE);

        // Table Note_data
        db.execSQL(NoteDataRepository.createTable());
        // Table Recycle
        db.execSQL(BinRepository.createTable());
        // Table Payment
        db.execSQL(PaymentDataRepository.createTable());
        // Table Payment Type
        db.execSQL(PaymentTypeRepository.createTable());

        db.execSQL(PaymentStatusRepository.createTable());

        new PaymentTypeRepository().createData(db);
        new PaymentStatusRepository().createData(db);
        new BankNameRepository(context).createData(db);
        new DebtTimeRepository(context).createData(db);

        // Table Calendar Type
        db.execSQL(CalendarTypeRepository.createTable());
        // Table Calendar
        db.execSQL(CalendarDataRepository.createTable());
    }

    public String getPersonalID(){
        String personalID = "";

        Cursor cursor = this.getReadableDatabase().query(
                Password.TABLE,
                new String[] {Password.Column.Personal_id},
                null,
                null,
                null,
                null,
                null); //(table, column, where, where arg, groupby, having, orderby)
        if(cursor.getCount() < 1){
            Log.d("5555555555555", cursor.getString(cursor.getPosition()) + "");
        }
        if (cursor != null) {
            cursor.moveToFirst();
        }

        int i=0;
        while(!cursor.isAfterLast()) {
            personalID = cursor.getString(0);
            Log.d("Personal ID"+ (i++), personalID);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("Search", "query in Password success : "+ personalID);
        return personalID;
    }


    public ArrayList<String> getPaymentData(String paymentId){

        Log.d("Search!!!!!", "query in Password");
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> payment = new ArrayList<String>();

//        Cursor cursor = db.query(Payment_data.TABLE, null, null, null, null, null, null); //(table, column, where, where arg, groupby, having, orderby)
        Cursor cursor = db.query(Payment_data.TABLE, null, Payment_data.Column.Payment_id+" = "+paymentId, null, null, null, null);
        if(cursor.getCount() < 1){

        }
        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {
            //Inside
            payment.add(cursor.getString(0));
            payment.add(cursor.getString(1));
            payment.add(cursor.getString(2));
            payment.add(cursor.getString(3));
            payment.add(cursor.getString(4));
            payment.add(cursor.getString(5));
            payment.add(cursor.getString(6));
            payment.add(cursor.getString(7));
            payment.add(cursor.getString(8));
            payment.add(cursor.getString(9));
            payment.add(cursor.getString(10));

            cursor.moveToNext();
        }
        cursor.close();
        Log.d("Search", "query in Password success : "+ payment);
        return payment;
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

        //Table Note_data
        db.execSQL(NoteDataRepository.dropTable());

        //Table PaymentData
        db.execSQL(PaymentDataRepository.dropTable());

        //Table PaymentType
        db.execSQL(PaymentTypeRepository.dropTable());

        db.execSQL(PaymentStatusRepository.dropTable());

        //Table BinRepository
        db.execSQL(BinRepository.dropTable());

        db.execSQL(BankNameRepository.dropTable());
        db.execSQL(DebtTimeRepository.dropTable());

        //Alarm
//        db.execSQL("DROP TABLE IF EXISTS " + Alarm.TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + AlarmTime.TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + Notification_status.TABLE_NAME);

        db.execSQL(CalendarTypeRepository.dropTable());
        db.execSQL(CalendarDataRepository.dropTable());


        Log.i(TAG, "Upgrade Database from " + oldVersion + " to " + newVersion);

        onCreate(db);
    }

//    public void shred(SQLiteDatabase db) {
//        db.delete(Notification_status.TABLE_NAME, Notification_status.COL_STATUS+" = ?", new String[]{Notification_status.CANCELLED});
//    }

//    private final String populateSQL = Util.concat("SELECT ",
//            "a."+Alarm.COL_FROMDATE+", ",
//            "a."+Alarm.COL_TODATE+", ",
//            "a."+Alarm.COL_RULE+", ",
//            "a."+Alarm.COL_INTERVAL+", ",
//            "at."+AlarmTime.COL_AT,
//            " FROM "+Alarm.TABLE_NAME+" AS a",
//            " JOIN "+AlarmTime.TABLE_NAME+" AS at",
//            " ON a."+Alarm.COL_ID+" = at."+AlarmTime.COL_ALARMID,
//            " WHERE a."+Alarm.COL_ID+" = ?");

//    public void populate(long alarmId, SQLiteDatabase db) {
//
//        String[] selectionArgs = {String.valueOf(alarmId)};
//        Cursor c = db.rawQuery(populateSQL, selectionArgs);
//
//        if (c.moveToFirst()) {
//            Calendar cal = Calendar.getInstance();
//            Notification_status alarmMsg = new Notification_status();
//            long now = System.currentTimeMillis();
//            db.beginTransaction();
//            try {
//                do {
//                    Date fromDate = sdf.parse(c.getString(0)); //yyyy-M-d
//                    cal.setTime(fromDate);
//
//                    //at
//                    String[] tokens = c.getString(4).split(":"); //hh:mm
//                    cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(tokens[0]));
//                    cal.set(Calendar.MINUTE, Integer.parseInt(tokens[1]));
//                    cal.set(Calendar.SECOND, 0);
//                    cal.set(Calendar.MILLISECOND, 0);
//
//                    String rule = c.getString(2); //every day month
//                    String interval = c.getString(3); //mm hh dd MM yy
//
//                    if (rule == null && interval == null) {//one time
//                        alarmMsg.reset();
//                        alarmMsg.setAlarmId(alarmId);
//                        alarmMsg.setDateTime(cal.getTimeInMillis());
//                        if (alarmMsg.getDateTime() < now-Util.MIN)
//                            alarmMsg.setStatus(Notification_status.EXPIRED);
//                        alarmMsg.save(db);
//
//                    } else {//repeating
//                        if (rule != null) {
//                            tokens = rule.split(" ");
//                            interval = "0 0 1 0 0"; //date++;
//
//                            //Day
//                            if (!"0".equals(tokens[1])) {
//                                cal.set(Calendar.DAY_OF_WEEK, Integer.parseInt(tokens[1]));
//                                interval = "0 0 7 0 0"; //week++;
//                            }
//                            //Every
//                            if (!"0".equals(tokens[0]) && "0".equals(tokens[1])) {
//                                cal.set(Calendar.DATE, Integer.parseInt(tokens[0]));
//                                interval = "0 0 0 1 0"; //month++;
//                            }
//                            //Month
//                            if (!"0".equals(tokens[2])) {
//                                cal.set(Calendar.MONTH, Integer.parseInt(tokens[2])-1);
//                                interval = "0 0 0 0 1"; //year++;
//                            }
//
//                            while(cal.getTime().before(fromDate)) {
//                                nextDate(cal, interval);
//                            }
//                        }
//
//                        Date toDate = sdf.parse(c.getString(1));
//                        toDate.setHours(0);
//                        toDate.setMinutes(0);
//                        toDate.setSeconds(0);
//                        toDate.setDate(toDate.getDate()+1);
//                        while(cal.getTime().before(toDate)) {
//                            alarmMsg.reset();
//                            alarmMsg.setAlarmId(alarmId);
//                            alarmMsg.setDateTime(cal.getTimeInMillis());
//                            if (alarmMsg.getDateTime() < now-Util.MIN)
//                                alarmMsg.setStatus(Notification_status.EXPIRED);
//                            alarmMsg.save(db);
//                            nextDate(cal, interval);
//                        }
//                    }
//
//                } while(c.moveToNext());
//
//                db.setTransactionSuccessful();
//            } catch (Exception e) {
////		    	Log.e(TAG, e.getMessage(), e);
//            } finally {
//                db.endTransaction();
//            }
//        }
//        c.close();
//    }
//
//    private void nextDate(Calendar cal, String interval) {
//        String[] tokens = interval.split(" ");
//        cal.add(Calendar.MINUTE, Integer.parseInt(tokens[0]));
//        cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(tokens[1]));
//        cal.add(Calendar.DATE, Integer.parseInt(tokens[2]));
//        cal.add(Calendar.MONTH, Integer.parseInt(tokens[3]));
//        cal.add(Calendar.YEAR, Integer.parseInt(tokens[4]));
//    }
//
//    private final String listSQL = Util.concat("SELECT ",
//            "a."+Alarm.COL_NAME+", ",
//            "am."+ Notification_status.COL_ID+", ",
//            "am."+ Notification_status.COL_DATETIME+", ",
//            "am."+ Notification_status.COL_STATUS,
//            " FROM "+Alarm.TABLE_NAME+" AS a",
//            " JOIN "+ Notification_status.TABLE_NAME+" AS am",
//            " ON a."+Alarm.COL_ID+" = am."+ Notification_status.COL_ALARMID);
//
//    /**
//     * @param db
//     * @param args {startTime, endTime}
//     * @return cursor
//     */
//    public Cursor listNotifications(SQLiteDatabase db, String... args) {
//        String selection = "am."+ Notification_status.COL_STATUS+" != '"+ Notification_status.CANCELLED+"'";
//        selection += (args!=null && args.length>0 && args[0]!=null) ? " AND am."+ Notification_status.COL_DATETIME+" >= "+args[0] : "";
//        selection += (args!=null && args.length>1 && args[1]!=null) ? " AND am."+ Notification_status.COL_DATETIME+" <= "+args[1] : "";
//
//        String sql = Util.concat(listSQL,
//                " WHERE "+selection,
//                " ORDER BY am."+ Notification_status.COL_DATETIME+" ASC");
//
//        return db.rawQuery(sql, null);
//    }
//
//    public int cancelNotification(SQLiteDatabase db, long id, boolean isAlarmId) {
//        ContentValues cv = new ContentValues();
//        cv.put(Notification_status.COL_STATUS, Notification_status.CANCELLED);
//        return db.update(Notification_status.TABLE_NAME,
//                cv,
//                (isAlarmId ? Notification_status.COL_ALARMID : Notification_status.COL_ID)+" = ?",
//                new String[]{String.valueOf(id)});
//    }
//
//    public int cancelNotification(SQLiteDatabase db, String startTime, String endTime) {
//        ContentValues cv = new ContentValues();
//        cv.put(Notification_status.COL_STATUS, Notification_status.CANCELLED);
//        return db.update(Notification_status.TABLE_NAME,
//                cv,
//                Notification_status.COL_DATETIME+" >= ? AND "+ Notification_status.COL_DATETIME+" <= ?",
//                new String[]{startTime, endTime});
//    }
//
//    public static final String getDateStr(int year, int month, int date) {
//        return Util.concat(year, "-", month, "-", date);
//    }
//
//    public static final String getTimeStr(int hour, int minute) {
//        return Util.concat(hour, ":", minute>9 ? "":"0", minute);
//    }

}

