package com.example.kaew_pc.diary_project.Activity.Payment;

import android.os.CountDownTimer;
import android.content.Context;
import com.example.kaew_pc.diary_project.Manager.MyReceiver;

/**
 * Created by yotsathon on 22/6/2560.
 */

public class ListCountDown extends CountDownTimer {
    int id;
    Context context ;
    String item;

    public int getId() {
        return id;
    }

    public ListCountDown(long millisInFuture, long countDownInterval, int id, Context context, String item) {
        super(millisInFuture, countDownInterval);
        this.id = id;
        this.context = context;
        this.item = item;
        this.start();
    }

    @Override
    public void onTick(long millisUntilFinished) {

    }

    @Override
    public void onFinish() {
        MyReceiver set = new MyReceiver();
//        set.createNoti(context, "Tamutami Diary", "" + item, "รายการที่ต้องชำระ", id);

    }

}
