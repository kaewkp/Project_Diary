package com.example.kaew_pc.diary_project.Manager.Repository;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.example.kaew_pc.diary_project.Activity.Note.NoteCreatePageActivity;
import com.example.kaew_pc.diary_project.Activity.Payment.PaymentShowDummy;
import com.example.kaew_pc.diary_project.R;

/**
 * Created by chommchome on 25/5/2560.
 */

public class NoteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        Intent i = new Intent(context, NoteCreatePageActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);
        createNoteNotify(context, "Tamutami Diary", "ค่าน้ำ", "ครบปีแล้ว เย้!!!");

    }

    public void createNoteNotify(Context context,String msg, String msgText, String msgAlert ){

        PendingIntent notificIntent = PendingIntent.getActivity(context,0, new Intent(context, NoteCreatePageActivity.class),0);

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

        mNotificationManager.notify(1, mBuilder.build());


    }
}
