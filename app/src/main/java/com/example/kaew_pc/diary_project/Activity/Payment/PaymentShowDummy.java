package com.example.kaew_pc.diary_project.Activity.Payment;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaew_pc.diary_project.Activity.LoginActivity;
import com.example.kaew_pc.diary_project.Activity.MainActivity;
import com.example.kaew_pc.diary_project.Manager.Database.BankName;
import com.example.kaew_pc.diary_project.Manager.Database.DBHelper;
import com.example.kaew_pc.diary_project.Manager.Database.DebtTime;
import com.example.kaew_pc.diary_project.Manager.Database.Payment_data;
import com.example.kaew_pc.diary_project.Manager.MyReceiver;
import com.example.kaew_pc.diary_project.Manager.Repository.BankNameRepository;
import com.example.kaew_pc.diary_project.Manager.Repository.DebtTimeRepository;
import com.example.kaew_pc.diary_project.Manager.Repository.PaymentDataRepository;
import com.example.kaew_pc.diary_project.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PaymentShowDummy extends AppCompatActivity {

    private DBHelper db;
    private Intent intent;
    private Button already;
    private TextView title, des, money, dateEnd, date, Bbank, Ddebt;
    private PaymentDataRepository paymentObj;
    private Payment_data data;
    private BankName Bdata;
    private DebtTime Ddata;
    private PaymentDataRepository repo;
    private BankNameRepository Brepo;
    private DebtTimeRepository Drepo;
    private int y, d, m;
    static final int dialogCa = 0;
    private Boolean isEdit = false, isFinish = false;
    private String items, dateChoose, timeChoose;
    private String formattedDate;
    private int userChoose;
    private AlertDialog.Builder builder, timealertbuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_noti_detail);
        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);

        init();
        showCalendar();
        int id = getIntent().getIntExtra("ID", -1);
        Toast.makeText(this, "" + id, Toast.LENGTH_SHORT).show();
        if (id != -1) { //When click from NOTI
            data = repo.getDataById(db.getReadableDatabase(), String.valueOf(id));
            Bdata = Brepo.getDataById(db.getReadableDatabase(), String.valueOf(data.getBankName_id()));
            Ddata = Drepo.getDataById(db.getReadableDatabase(), String.valueOf(data.getDebtTime_id()));
            title.setText(data.getPayment_title());

//            Bbank.setText(Bdata.getBankName_name());
//
//            Ddebt.setText(Ddata.getDebtTime_id());
//            detailBD.setText(data2.getDebtTime_name());

            des.setText(data.getPayment_desc());
            money.setText(String.valueOf(data.getPayment_price()));
            dateEnd.setText(data.getPayment_endDate());

            date.setVisibility(View.VISIBLE);
//            Bbank.setVisibility(View.VISIBLE);
//            Ddebt.setVisibility(View.VISIBLE);

            String d = data.getPayment_datePay();
            if (d != null)
                date.setText("วันที่เลือก : " + d);
            isEdit = true;
        }
        else{
            Toast.makeText(this, "Can not get data", Toast.LENGTH_SHORT).show();
        }

        getPName();

    }

    public void init() {

        title = (TextView) findViewById(R.id.title);
        des = (TextView) findViewById(R.id.detail);
        Bbank = (TextView) findViewById(R.id.bank);
        Ddebt = (TextView) findViewById(R.id.debt);
        money = (TextView) findViewById(R.id.priceshow);
        dateEnd = (TextView) findViewById(R.id.endDate);
        date = (TextView) findViewById(R.id.showdatetime);
        db = DBHelper.getInstance(this);
        repo = new PaymentDataRepository();
        Brepo = new BankNameRepository(null);
        Drepo = new DebtTimeRepository(null);
        Bbank.setVisibility(View.INVISIBLE);
        Ddebt.setVisibility(View.INVISIBLE);

        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        formattedDate = df.format(time);

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
        


        if(dateChoose != null) {
            data.setPayment_datePay(dateChoose);
        } else {
            if(!isEdit) {
                Toast.makeText(this, "ยังไม่ได้เลือกวันที่", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        isFinish = true;
        data.setPayment_date(formattedDate);
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
    private void showCalendar() {
        final Calendar ca = Calendar.getInstance();
        y = ca.get(Calendar.YEAR);
        m = ca.get(Calendar.MONTH);
        d = ca.get(Calendar.DAY_OF_MONTH);

        Button calendarButton = (Button) findViewById(R.id.calendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialogCa);
            }
        });
        date.setVisibility(View.VISIBLE);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == dialogCa) {
            return new DatePickerDialog(this, /*android.R.style.Theme_Holo_Dialog*/dpicklistener, y, m, d);
        }

        return null;
    }

    private DatePickerDialog.OnDateSetListener dpicklistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            y = year;
            m = monthOfYear + 1; //Month start from 0
            d = dayOfMonth;
            dateChoose = String.valueOf(d) + " / " + String.valueOf(m) + " / " + String.valueOf(y);
            date.setVisibility(View.VISIBLE);
            date.setText("วันที่เลือก : " + dateChoose);
        }
    };
}
