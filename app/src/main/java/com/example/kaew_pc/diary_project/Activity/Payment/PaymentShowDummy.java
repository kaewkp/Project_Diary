package com.example.kaew_pc.diary_project.Activity.Payment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kaew_pc.diary_project.Manager.Database.DBHelper;
import com.example.kaew_pc.diary_project.Manager.Database.Payment_data;
import com.example.kaew_pc.diary_project.Manager.MyReceiver;
import com.example.kaew_pc.diary_project.Manager.Repository.PaymentDataRepository;
import com.example.kaew_pc.diary_project.R;

import java.util.Calendar;

public class PaymentShowDummy extends AppCompatActivity {

    private DBHelper db;
    private Intent intent;
    private Button already;
    private TextView title, des, money, dateEnd;
    private PaymentDataRepository paymentObj;
    private Payment_data data;
    private PaymentDataRepository repo;

    double coreDouble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_noti_detail);
        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);

        init();

//        int id = getIntent().getIntExtra("id", 0);
//        if (id != 0) { //When click from listview
//            data = new PaymentDataRepository().getDataById(db.getReadableDatabase(), String.valueOf(id));
//            paymentTypeSpinner.setSelected(true);
//
//            title.setText(data.getPayment_title());
//
//            money.setText(String.valueOf(data.getPayment_price()));
//
//            //set spinner select position
//            paymentTypeSpinner.setSelection(Integer.parseInt(data.getPayType_id()));
//            date.setVisibility(View.VISIBLE);
//
//            String d = data.getPayment_endDate();
//            if (d != null)
//                date.setText("วันที่เลือก : " + d);
//            isEdit = true;
//
//            //set spinner select position
//            paymentTypeSpinner.setSelection(Integer.parseInt(data.getPayType_id()));
//            date.setVisibility(View.VISIBLE);
//
//        }
    }

    public void init() {

        title = (TextView) findViewById(R.id.title);
        des = (TextView) findViewById(R.id.detail);
        money = (TextView) findViewById(R.id.priceshow);
        dateEnd = (TextView) findViewById(R.id.endDate);
//        paymentTypeSpinner = (Spinner) findViewById(R.id.typepayment);
        already = (Button) findViewById(R.id.alreadypay);
        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentShowDummy.this, PaymentMainPageActivity.class);
                startActivity(intent);

                Calendar calendar = Calendar.getInstance();
                Intent intent_service = new Intent(PaymentShowDummy.this, MyReceiver.class);
                PendingIntent pi = PendingIntent.getBroadcast(PaymentShowDummy.this, 0, intent_service, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                am.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis() + AlarmManager.INTERVAL_DAY*30,
                        AlarmManager.INTERVAL_DAY*30, pi);
                finish();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent;
        if (item.getItemId() == android.R.id.home) {
            intent = new Intent(getApplicationContext(), PaymentMainPageActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), PaymentMainPageActivity.class);
        startActivity(intent);
        finish();
    }

}
