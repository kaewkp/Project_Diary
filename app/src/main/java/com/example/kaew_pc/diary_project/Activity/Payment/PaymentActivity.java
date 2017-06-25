package com.example.kaew_pc.diary_project.Activity.Payment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.kaew_pc.diary_project.Manager.Database.BankName;
import com.example.kaew_pc.diary_project.Manager.Database.DBHelper;
import com.example.kaew_pc.diary_project.Manager.Database.DebtTime;
import com.example.kaew_pc.diary_project.Manager.Database.PayType;
import com.example.kaew_pc.diary_project.Manager.Database.Payment_data;
import com.example.kaew_pc.diary_project.Manager.MyReceiver;
import com.example.kaew_pc.diary_project.Manager.MyService;
import com.example.kaew_pc.diary_project.Manager.Repository.BankNameRepository;
import com.example.kaew_pc.diary_project.Manager.Repository.DebtTimeRepository;
import com.example.kaew_pc.diary_project.R;

import com.example.kaew_pc.diary_project.Manager.Repository.PaymentDataRepository;
import com.example.kaew_pc.diary_project.Manager.Repository.PaymentTypeRepository;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;

import static com.example.kaew_pc.diary_project.Activity.Payment.ListCount.listCountDown;

/**
 * Created by chommchome on 28/3/2560.
 */

public class PaymentActivity extends AppCompatActivity {

    NotificationManager notificationManager;
    boolean isNotificActive = false;
    int nofiId = 33;

    static final int dialogCa = 0;
    static final int dialogTm = 1;
    private static final int DIALOG_ATTIME = 1;
    private Spinner paymentTypeSpinner;
    private AlertDialog.Builder builder, timealertbuilder;
    private TextView bankname;
    private TextView showalertdate;
    private String[] banks, debtyear, timealert;

    private TextView debt, date, timesw, remind;
    private String formattedDate;

    private EditText priceEdit;
    private EditText descpayment;
    private Boolean isEdit = false, isFinish = false;

    private String items, dateChoose, timeChoose;//type of payment

    private int y, d, m;
    private int hh, mm;
    private Button save, distance;

    //countdown
    long diff;
    long milliseconds;
    long endTime;
    long startTime;

    //kaew
    Context context1 = this;
    CountDownTimer cdt;

    private TextView tvTimer;

    private ArrayList<String> paymentTypeID;
    private ArrayList<String> paymentTypeValue;
    private Payment_data data;
    private PayType paytypeData;
    private com.example.kaew_pc.diary_project.Manager.Database.DBHelper db;
    private PaymentDataRepository dataRepo;
    private PaymentTypeRepository spinnerRepo;
    private BankNameRepository bankRepo;
    private DebtTime deptRepo;
    private int userChoose;
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd kk:mm:ss";

    SharedPreferences mpref;
    SharedPreferences.Editor mEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.payment_activity);
        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);

        init();
        initAlertDialog();
        initSpinner();

        onSpinnerClick();
        showCalendar();
        showTime();
//        saveDate();
//        countDown();

        int id = getIntent().getIntExtra("id", 0);
        if (id != 0) { //When click from listview
            data = new PaymentDataRepository().getDataById(db.getReadableDatabase(), String.valueOf(id));
            paymentTypeSpinner.setSelected(true);

            descpayment.setText(data.getPayment_desc());

            priceEdit.setText(String.valueOf(data.getPayment_price()));

            //set spinner select position
            paymentTypeSpinner.setSelection(Integer.parseInt(data.getPayType_id()));
            date.setVisibility(View.VISIBLE);

            String d = data.getPayment_endDate();
            if (d != null)
                date.setText("วันที่เลือก : " + d);
            isEdit = true;
        }

        String x = Payment_data.getPaymentIdFromClicked();
        ArrayList<String> _paymentData = db.getPaymentData(x);
    }

    private void init() {
        bankname = (TextView) findViewById(R.id.bankname);
        debt = (TextView) findViewById(R.id.debt);
        date = (TextView) findViewById(R.id.showdatetime);
        priceEdit = (EditText) findViewById(R.id.editprice);
        descpayment = (EditText) findViewById(R.id.descpayment);
        paymentTypeSpinner = (Spinner) findViewById(R.id.typepayment);
        timesw = (TextView) findViewById(R.id.timedetail);

        timesw.setVisibility(View.INVISIBLE);
        tvTimer = (TextView) findViewById(R.id.remind);
        tvTimer.setVisibility(View.INVISIBLE);

        save = (Button) findViewById(R.id.button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePayment();
                countDown();

                final int id = new PaymentDataRepository().getPaymentID(db.getReadableDatabase());
                Intent intent_service = new Intent(PaymentActivity.this, MyReceiver.class);
                intent_service.putExtra("ID", id);
                PendingIntent pi = PendingIntent.getBroadcast(PaymentActivity.this, 0, intent_service, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+(1), pi);

                am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY*30,
                        AlarmManager.INTERVAL_DAY*30, pi);

                //Kaew
//                cdt = new CountDownTimer((15000), 1000) {   // 3 day = 259200000 , 1 day = 86400000
//                    public void onTick(long millisUntilFinished) {
//                        // Tick
//                    }
//
//                    public void onFinish() {
//
//                        MyReceiver set = new MyReceiver();
//
//                        set.createNoti(context1, "Tamutami Diary", "" + items, "รายการที่ต้องชำระ", id);
//                        // Finish
//                    }
//                }.start();


                //man
                listCountDown.add(new ListCountDown(30000, 1000, id, context1, items));  // 3 day = 259200000 , 1 day = 86400000
                if(isEdit){
                    ArrayList<Integer> indRemove = new ArrayList<Integer>();
                    for(int i=0;i<listCountDown.size();i++){
                        if(id == listCountDown.get(i).getId()){
                            listCountDown.get(i).cancel();
                            indRemove.add(i);
                        }
                    }
                    if(indRemove.size()!=0) {
                        for (int i = 0; i < indRemove.size(); i++) {
                            listCountDown.get(indRemove.get(i)).cancel();
                            listCountDown.remove(indRemove.get(i));
                        }
//                        items = "new msg";
                        listCountDown.add(new ListCountDown(30000, 1000, id, context1, items));
                    }
                }

                if(isFinish) {
                    Toast.makeText(PaymentActivity.this, "บันทึกแล้ว ", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        data = new Payment_data();
        paytypeData = new PayType();

        debtyear = getResources().getStringArray(R.array.DebtTime);
        banks = getResources().getStringArray(R.array.BankName);
        timealert = getResources().getStringArray(R.array.Alert);

        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        formattedDate = df.format(time);

        db = DBHelper.getInstance(this);

        mpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mEditor = mpref.edit();

    }

    private void initSpinner() {
        paymentTypeSpinner = (Spinner) findViewById(R.id.typepayment);
        paymentTypeID = new ArrayList<>();
        paymentTypeValue = new ArrayList<>();

        ArrayList<PayType> list = new PaymentTypeRepository().getData(db.getReadableDatabase());

        for (PayType p : list) {
            paymentTypeID.add(p.getPayType_id());
            paymentTypeValue.add(p.getPayType_name());
        }

        ArrayAdapter<String> adapterEnglish = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, paymentTypeValue);
        paymentTypeSpinner.setAdapter(adapterEnglish);
    }

//    private void saveDate(){
//        Alarm alarm = new Alarm();
//        alarm.setToDate(dateChoose);
//        alarm.setFromDate(formattedDate);
//    }

    private void savePayment() {
        long alarmId = 0;
        data.setPayment_title(items);

        data.setPayment_desc(descpayment.getText().toString());

        double price = 0;
        if (!priceEdit.getText().toString().equals("")) {
            price = Double.parseDouble(priceEdit.getText().toString());
        }

        data.setPayment_price(price);

        if(dateChoose != null) {
            data.setPayment_endDate(dateChoose);
        } else {
            if(!isEdit) {
                Toast.makeText(this, "ยังไม่ได้เลือกวันที่", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        isFinish = true;
        data.setPayment_date(formattedDate);

//        /*service*/
//        Intent service = new Intent(this, AlarmService.class);
//        service.putExtra(Notification_status.COL_ALARMID, String.valueOf(alarmId));
//        service.setAction(AlarmService.POPULATE);
//        startService(service);



//        TaskStackBuilder tStackBuilder = TaskStackBuilder.create(this);
//        tStackBuilder.addParentStack(PaymentShowActivity.class);
//        tStackBuilder.addNextIntent(payMent );


        //Save Bank
//        if( items.equals("ค่าบัตรเครดิต") ){
//            BankNameRepo bankNameRepo = new BankNameRepo();
//            BankName bankName = bankNameRepo.getDataByName(db.getReadableDatabase(), tv);
//        }
//        data.setBankName_id();

        if (!isEdit)
            new PaymentDataRepository().insertData(db.getWritableDatabase(), data);
        else
            new PaymentDataRepository().updateData(db.getWritableDatabase(), data);
    }

    private void initDialog(final String[] text, final String head, final TextView tv) {
        builder = new AlertDialog.Builder(PaymentActivity.this);
        builder.setTitle(head);
        int choiseNumber = 0;
        if (data.getBankName_id() != null) {
            choiseNumber = Integer.parseInt(data.getBankName_id()) - 1;
        }
        builder.setSingleChoiceItems(text, choiseNumber, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "คุณเลือก " +
                        text[which], Toast.LENGTH_SHORT).show();
                tv.setVisibility(View.VISIBLE);
                tv.setText(text[which]);
                userChoose = which;

            }


        });

        builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (head.equalsIgnoreCase("เลือกธนาคาร")) {
//                    Toast.makeText(PaymentActivity.this, ""+text[userChoose], Toast.LENGTH_SHORT).show();
                    //Set bank name to data
                    BankNameRepository bankNameRepo = new BankNameRepository(getApplicationContext());
                    BankName bankName = bankNameRepo.getDataByName(db.getReadableDatabase(), text[userChoose]);
                    data.setBankName_id(bankName.getBankName_id());
                } else if (head.equalsIgnoreCase("เลือกจำนวนงวด")) {
                    DebtTimeRepository dtRepo = new DebtTimeRepository(getApplicationContext());
                    DebtTime dt = dtRepo.getDataByName(db.getReadableDatabase(), text[userChoose]);
                    data.setDebtTime_id(dt.getDebtTime_id());
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("ยกเลิก", null);
        builder.create();
    }

    private void initAlertDialog() {
        timealertbuilder = new AlertDialog.Builder(PaymentActivity.this);
        timealertbuilder.setTitle("ตั้งค่าการแจ้งเตือน");
        timealertbuilder.setSingleChoiceItems(timealert, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "คุณเลือก " +
                        timealert[which], Toast.LENGTH_SHORT).show();

            }
        });
        timealertbuilder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        timealertbuilder.create();
    }


    private void onSpinnerClick() {
        paymentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {

                debt.setVisibility(View.INVISIBLE);
                bankname.setVisibility(View.INVISIBLE);

                data.setPayType_id(String.valueOf(position));

                items = paymentTypeSpinner.getSelectedItem().toString();
                Log.i("Selected item : ", items);

                if (items.equalsIgnoreCase("ค่าบัตรเครดิต")) {
                    initDialog(banks, "เลือกธนาคาร", bankname);
                    builder.show();
                    bankname.setVisibility(View.VISIBLE);
                } else if (items.equalsIgnoreCase("ค่าผ่อนชำระ")) {
                    initDialog(debtyear, "เลือกจำนวนงวด", debt);
                    builder.show();
                    debt.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manu_main_payment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
//        if (item.getItemId() == R.id.action_alert) {
////            timealertbuilder.show();
//            return true;
//        } else if (item.getItemId() == R.id.action_add) {
//            return true;
//        }

        if (item.getItemId() == android.R.id.home) {
            intent = new Intent(getApplicationContext(), PaymentMainPageActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    //    show calendar
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
        date.setVisibility(View.INVISIBLE);
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == dialogCa) {
            return new DatePickerDialog(this, /*android.R.style.Theme_Holo_Dialog*/dpicklistener, y, m, d);
        }
        else if (id == dialogTm) {
            return new TimePickerDialog(this, /*android.R.style.Theme_Holo_Dialog*/timelistener, hh, mm,true);

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


    //show time

    private void showTime() {
        final Calendar tm = Calendar.getInstance();
        hh = tm.get(Calendar.HOUR_OF_DAY);
        mm = tm.get(Calendar.MINUTE);

        Button timeButton = (Button) findViewById(R.id.timeshow);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialogTm);
            }
        });
        timesw.setVisibility(View.INVISIBLE);
        timeButton.setVisibility(View.INVISIBLE);

    }

    private TimePickerDialog.OnTimeSetListener timelistener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            hh = hour;
            mm = minute;
            timeChoose = String.valueOf(hh) + " : " + String.valueOf(mm);
            timesw.setVisibility(View.INVISIBLE);
            timesw.setText("เวลา : " + timeChoose);

        }
    };


    //countdown

    private void countDown(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        formatter.setLenient(false);

        // String newTime = "30.10.2013, 00:00";
        String newTime = d + "." + m + "." + y + ", 00:00";
        Date newDate;
        try {
            newDate = formatter.parse(newTime);
            milliseconds = newDate.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        startTime = System.currentTimeMillis();
        diff = milliseconds - startTime;

        MyCount counter = new MyCount(diff, 1000);
        counter.start();
    }



    public class MyCount extends CountDownTimer {

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            tvTimer.setText("Done!");
        }

        @Override
        public void onTick(long millisUntilFinished) {

            startTime = System.currentTimeMillis();
            diff = milliseconds - startTime;

            int days = (int)((diff / (1000*60*60*24)) % 365);
            long hours   = (long) ((diff / (1000*60*60)) % 24);
            long minutes = (long) ((diff / (1000*60)) % 60);
            long seconds = (long) (diff / 1000) % 60 ;

            tvTimer.setText(days + "  days\n" + hours + "  hours\n" + minutes
                    + "  min\n" + String.format("%02d", seconds) + " sec");

        }
    }

//
//    //Notification
//    public void shownotification(View view) {
//        String chom = tvTimer.getText().toString();
//
//        if (chom.equalsIgnoreCase("Done!")) {
//
//            NotificationCompat.Builder notificBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
//                    .setContentTitle("chommchome")
//                    .setContentText("Test")
//                    .setTicker("Alert New Message")
//                    .setSmallIcon(R.mipmap.ic_launcher);
//
//            Intent paymentShow = new Intent(this, PaymentShowActivity.class);
//
//            android.support.v4.app.TaskStackBuilder tStackBuilder = android.support.v4.app.TaskStackBuilder.create(this);
//
//            tStackBuilder.addParentStack(PaymentShowActivity.class);
//
//            tStackBuilder.addNextIntent(paymentShow);
//
//            PendingIntent pendingIntent = tStackBuilder.getPendingIntent(0,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//
//            notificBuilder.setContentIntent(pendingIntent);
//
//            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//            notificationManager.notify(nofiId, notificBuilder.build());
//
//            isNotificActive = true;
//
//        }
//    }


}

