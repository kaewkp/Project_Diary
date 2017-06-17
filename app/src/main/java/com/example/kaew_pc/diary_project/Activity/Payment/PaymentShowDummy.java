package com.example.kaew_pc.diary_project.Activity.Payment;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaew_pc.diary_project.Activity.LoginActivity;
import com.example.kaew_pc.diary_project.Activity.MainActivity;
import com.example.kaew_pc.diary_project.Manager.Database.DBHelper;
import com.example.kaew_pc.diary_project.Manager.Database.Payment_data;
import com.example.kaew_pc.diary_project.Manager.MyReceiver;
import com.example.kaew_pc.diary_project.Manager.Repository.PaymentDataRepository;
import com.example.kaew_pc.diary_project.R;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

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

        int id = getIntent().getIntExtra("ID", -1);
        Toast.makeText(this, "" + id, Toast.LENGTH_SHORT).show();
        if (id != -1) { //When click from NOTI
            data = repo.getDataById(db.getReadableDatabase(), String.valueOf(id));
            title.setText(data.getPayment_title());
            des.setText(data.getPayment_desc());
            money.setText(String.valueOf(data.getPayment_price()));
            dateEnd.setText(data.getPayment_endDate());

        }

        getPName();

    }

    public void init() {

        title = (TextView) findViewById(R.id.title);
        des = (TextView) findViewById(R.id.detail);
        money = (TextView) findViewById(R.id.priceshow);
        dateEnd = (TextView) findViewById(R.id.endDate);
        already = (Button) findViewById(R.id.alreadypay);
        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(PaymentShowDummy.this, PaymentMainPageActivity.class);
//                startActivity(intent);
//
//                Calendar calendar = Calendar.getInstance();
//                Intent intent_service = new Intent(PaymentShowDummy.this, MyReceiver.class);
//                PendingIntent pi = PendingIntent.getBroadcast(PaymentShowDummy.this, 0, intent_service, PendingIntent.FLAG_UPDATE_CURRENT);
//                AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                am.setInexactRepeating(AlarmManager.RTC_WAKEUP,
//                        calendar.getTimeInMillis() + AlarmManager.INTERVAL_DAY*30,
//                        AlarmManager.INTERVAL_DAY*30, pi);
                OnOpenApp();
            }
        });
        
        db = DBHelper.getInstance(this);
        repo = new PaymentDataRepository();
    }

    private void OnOpenApp() {
        if(MainActivity.isRunning){
            finish();
        }else {
            intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    public  boolean isAppRunning(final Context context, final String packageName) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        if (procInfos != null)
        {
            for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                if (processInfo.processName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void getPName(){
        String currentProcName = "";
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses())
        {
            if (processInfo.pid == pid)
            {
                currentProcName = processInfo.processName;
                break;
            }
        }
        Log.e("Process Name : ", currentProcName);
    }
}
