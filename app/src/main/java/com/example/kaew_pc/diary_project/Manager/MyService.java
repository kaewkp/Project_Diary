package com.example.kaew_pc.diary_project.Manager;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import
        android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.ParseException;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaew_pc.diary_project.Activity.Payment.PaymentShowActivity;
import com.example.kaew_pc.diary_project.R;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static android.R.attr.y;

public class MyService extends IntentService {

    public MyService(){
        super("");

    }

    NotificationManager notificationManager;
    boolean isNotificActive = false;
    int nofiId = 33;

    //countdown
    long diff;
    long milliseconds;
    long endTime;
    long startTime;
    private TextView tvTimer;

    private int y, d, m;

    public MyService(String name){
        super(name);

    }

    protected void onHandleIntent(Intent intent){
        //Run in Background Thread
        Intent broadcastIntent = new Intent("Already");
//        broadcastIntent.putExtra()
        LocalBroadcastManager.getInstance(MyService.this)
                .sendBroadcast(broadcastIntent);

        String day = intent.getStringExtra("Date");
        String month = intent.getStringExtra("Month");
        String year = intent.getStringExtra("Year");
    }

        MyCounter timer;
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        timer = new MyCounter(100000,1000);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        timer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    private class MyCounter extends CountDownTimer{

        public MyCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            Intent intent = new Intent(MyService.this, MyReceiver.class);
            PendingIntent pi = PendingIntent.getBroadcast(MyService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, milliseconds, pi);
            Toast.makeText(getApplicationContext(), "death", Toast.LENGTH_LONG).show();
            stopSelf();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Intent bi = new Intent(MyService.this, MyReceiver.class);

            startTime = System.currentTimeMillis();
            diff = milliseconds - startTime;

            int days = (int)((diff / (1000*60*60*24)) % 365);
            long hours   = (long) ((diff / (1000*60*60)) % 24);
            long minutes = (long) ((diff / (1000*60)) % 60);
            long seconds = (long) (diff / 1000) % 60 ;
            bi.putExtra("countdown", millisUntilFinished);
            Toast.makeText(getApplicationContext(), (millisUntilFinished/1000)+"", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        timer.cancel();
        //stopSelf();
        super.onDestroy();

    }



//    private void countDown(){
//        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd.MM.yyyy, HH:mm");
//        formatter.setLenient(false);
//
//        // String newTime = "30.10.2013, 00:00";
//        String newTime = d + "." + m + "." + y + ", 00:00";
//        Date newDate;
//        try {
//            newDate = formatter.parse(newTime);
//            milliseconds = newDate.getTime();
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } catch (java.text.ParseException e) {
//            e.printStackTrace();
//        }
//
//        startTime = System.currentTimeMillis();
//        diff = milliseconds - startTime;
//
//        MyCount counter = new MyCount(diff, 1000);
//        counter.start();
//    }
//
//
//
//    public class MyCount extends CountDownTimer {
//
//        public MyCount(long millisInFuture, long countDownInterval) {
//            super(millisInFuture, countDownInterval);
//        }
//
//        @Override
//        public void onFinish() {
//            tvTimer.setText("Done!");
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {
//
//            startTime = System.currentTimeMillis();
//            diff = milliseconds - startTime;
//
//            int days = (int)((diff / (1000*60*60*24)) % 365);
//            long hours   = (long) ((diff / (1000*60*60)) % 24);
//            long minutes = (long) ((diff / (1000*60)) % 60);
//            long seconds = (long) (diff / 1000) % 60 ;
//
//            tvTimer.setText(days + "  days\n" + hours + "  hours\n" + minutes
//                    + "  min\n" + String.format("%02d", seconds) + " sec");
//        }
//    }







    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



//    public MyService() {
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
}
