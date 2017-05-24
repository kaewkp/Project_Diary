package com.example.kaew_pc.diary_project.Activity.Payment;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kaew_pc.diary_project.Manager.Adapter.PaymentCustomAdapter;
import com.example.kaew_pc.diary_project.Manager.Database.DBHelper;
import com.example.kaew_pc.diary_project.Manager.Database.Payment_data;
import com.example.kaew_pc.diary_project.Manager.Repository.PaymentDataRepository;
import com.example.kaew_pc.diary_project.R;

import java.util.ArrayList;

/**
 * Created by chommchome on 29/3/2560.
 */

public class PaymentShowActivity extends AppCompatActivity {
    private DBHelper db;
    private Intent intent;
    private Button already;
    private TextView title, des, money, dateEnd;
    private PaymentDataRepository paymentObj;
    private Payment_data data;


    //Notification
    boolean isNotificActive = false;
    int nofiId = 33;
    NotificationManager notificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_show);

        init();

        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);
        action.setHomeButtonEnabled(true);

        data = new Payment_data();
        ArrayList<String> _paymentData = db.getPaymentData(data.getPaymentIdFromClicked());
        title.setText(_paymentData.get(0));

        LocalBroadcastManager.getInstance(PaymentShowActivity.this)
                .registerReceiver(broadcastReceiver, new IntentFilter("Already"));
    }

    private void init() {
        already = (Button)findViewById(R.id.alreadypay);
        title = (TextView) findViewById(R.id.title);
        des = (TextView)findViewById(R.id.detail);
        money = (TextView)findViewById(R.id.priceshow) ;
        dateEnd = (TextView)findViewById(R.id.endDate);

        intent = getIntent();


    }

    protected BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


        }
    };

    @Override
    public void onDestroy(){
        super.onDestroy();

        LocalBroadcastManager.getInstance(PaymentShowActivity.this)
                .unregisterReceiver(broadcastReceiver);
    }

    public void stopNotification(View view) {
            if(isNotificActive){
                notificationManager.cancel(nofiId) ;
            }

        }



    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), PaymentMainPageActivity.class);
        startActivity(intent);
        finish();
    }
}
