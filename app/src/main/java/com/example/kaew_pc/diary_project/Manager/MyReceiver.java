package com.example.kaew_pc.diary_project.Manager;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.kaew_pc.diary_project.Activity.Note.NoteCreatePageActivity;
import com.example.kaew_pc.diary_project.Activity.Payment.PaymentActivity;
import com.example.kaew_pc.diary_project.Activity.Payment.PaymentShowActivity;
import com.example.kaew_pc.diary_project.Activity.Payment.PaymentShowDummy;
import com.example.kaew_pc.diary_project.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static android.content.Context.ALARM_SERVICE;

public class MyReceiver extends BroadcastReceiver {

    Random random = new Random();
    int m = random.nextInt(9999 - 1000) + 1000;

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("ID", -1);
        createNoti(context, "Tamutami Diary", intent.getStringExtra("Head"), "รายการที่ต้องชำระ", id, intent);
    }

    public void createNoti(Context context,String msg, String msgText, String msgAlert, int id, Intent i){

        Intent intent = new Intent(context, PaymentShowDummy.class);
        intent.putExtra("ID", id);
        Log.e("ID NOTI", ""+id);

        PendingIntent notificIntent = PendingIntent.getActivity(context,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(msg)
                .setTicker(msgAlert)
                .setContentText(msgText);

        mBuilder.setContentIntent(notificIntent);

        mBuilder.setDefaults((NotificationCompat.DEFAULT_SOUND));

        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(m, mBuilder.build());
        alarm(context, msgText, id, i);
    }

    public Date alarm(Context context, String msgText, int id, Intent i){
        Intent myIntent = new Intent(context , MyReceiver.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(i.getIntExtra("year", 0), i.getIntExtra("month", 0)+1, i.getIntExtra("day", 2), i.getIntExtra("hour", 0),
                i.getIntExtra("minute", 0), 0);

        myIntent.putExtra("ID", id);
        myIntent.putExtra("Head", msgText);
        myIntent.putExtra("Time", ""+calendar.getTime());
        myIntent.putExtra("year", i.getIntExtra("year", 0));
        myIntent.putExtra("month", i.getIntExtra("month", 0)+1);
        myIntent.putExtra("day", i.getIntExtra("day", 0));
        myIntent.putExtra("hour", i.getIntExtra("hour", 0));
        myIntent.putExtra("minute", i.getIntExtra("minute", 0));

        myIntent.putExtra("Time", ""+calendar.getTime());
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MINUTE, minute);
//        calendar.set(Calendar.HOUR, hour);
//        calendar.set(Calendar.AM_PM, Calendar.AM);    //set accordingly
//        calendar.add(Calendar.DAY_OF_YEAR, 0);

        Log.e("Alarm", "Alert in : "+ calendar.getTime());

//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), getDuration(), pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        return calendar.getTime();
    }
}





