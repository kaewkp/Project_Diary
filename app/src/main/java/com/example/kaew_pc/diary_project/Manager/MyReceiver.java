package com.example.kaew_pc.diary_project.Manager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.example.kaew_pc.diary_project.Activity.Note.NoteCreatePageActivity;
import com.example.kaew_pc.diary_project.Activity.Payment.PaymentShowActivity;
import com.example.kaew_pc.diary_project.Activity.Payment.PaymentShowDummy;
import com.example.kaew_pc.diary_project.R;

import java.util.Random;

public class MyReceiver extends BroadcastReceiver {

    Random random = new Random();
    int m = random.nextInt(9999 - 1000) + 1000;

    @Override
    public void onReceive(Context context, Intent intent) {
//        Intent i = new Intent(context, NoteCreatePageActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);
        //createNoti(context, "Tamutami Diary", "ค่าโทรศัพท์", "รายการที่ต้องชำระ");

    }

    public void createNoti(Context context,String msg, String msgText, String msgAlert ){

        PendingIntent notificIntent = PendingIntent.getActivity(context,0, new Intent(context, PaymentShowDummy.class),0);

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


    }

//        Intent i = new Intent(context, NoteCreatePageActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);
}





