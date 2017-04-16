package com.example.kaew_pc.diary_project.PaymentManagement;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Toast;

import com.example.kaew_pc.diary_project.Database.DBHelper;
import com.example.kaew_pc.diary_project.Database.PayType;
import com.example.kaew_pc.diary_project.Database.Payment_data;
import com.example.kaew_pc.diary_project.R;

import com.example.kaew_pc.diary_project.Repository.PaymentDataRepo;
import com.example.kaew_pc.diary_project.Repository.PaymentTypeRepo;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;

import java.util.List;


/**
 * Created by chommchome on 28/3/2560.
 */

public class PaymentActivity extends AppCompatActivity {

    static final int dialogID = 0;
    private Spinner paymentTypeSpinner;
    private AlertDialog.Builder builder, timealertbuilder;
    private TextView bankname;
    private TextView showalertdate;
    private String[] banks = new String[]{ "SCB (ไทยพาณิชย์)", "KBANK (กสิกรไทย)","GSB (ออมสิน)","KTC (กรุงไทย)","UOB (ยูโอบี)","KRUNGSRI (กรุงศรี)",
            "TMB (ทหารไทย)","AEON (อิออน)","BBL (บัวหลวง)","Citybank  (ซิตี้แบงก์)"};
    private TextView debt;
    private String[] debtyear = new String[]{"6 เดือน", "12 งวด"};
    private String[] timealert = new String[]{"ล่วงหน้า 1 วัน", "ล่วงหน้า 3 วัน", "ล่วงหน้า 5 วัน", "ล่วงหน้า 1 สัปดาห์", "ล่วงหน้า 2 สัปดาห์"};

    private TextView date;

    private EditText priceEdit;
    private EditText descpayment;
    private Boolean isEdit = false;

    private String items;//type of payment
    private String formattedDate;

    private int y, d, m;
    private Button click;
    private Button start;

    private ArrayList<String> paymentTypeID;
    private ArrayList<String> paymentTypeValue;
    private Payment_data data;
    private PayType paytypeData;
    private DBHelper db;
    private PaymentDataRepo dataRepo;
    private PaymentTypeRepo spinnerRepo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_activity);
        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);

        init();



        String[] paymentT = getResources().getStringArray(R.array.ประเภทค่าใช้จ่าย);
        ArrayAdapter<String> adapterPayment = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, paymentT);
        paymentTypeSpinner.setAdapter(adapterPayment);



        initAlertDialog();
        initSpinner();

        Button save = (Button) findViewById(R.id.button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePayment();
                finish();
            }
        });


        onSpinnerClick();
        showCalendar();

        int id = getIntent().getIntExtra("id", 0);

        if (id != 0) { //When click from listview

            data = new PaymentDataRepo().getDataById(db.getReadableDatabase(), String.valueOf(id));
            paymentTypeSpinner.setSelected(true);
            descpayment.setText(data.getPayment_desc());
//            priceEdit.setDouble.parseDouble(data.getPayment_price());

            data = dataRepo.getDataById(db.getReadableDatabase(), String.valueOf(id));
//            paymentTypeSpinner.setText(data.getPayment_title());
//            priceEdit.setText(data.getPayment_price());

            date.setText(data.getPayment_endDate());

            isEdit = true;

        }
    }


    private void initSpinner() {
        paymentTypeSpinner = (Spinner) findViewById(R.id.typepayment);
        paymentTypeID = new ArrayList<>();
        paymentTypeValue = new ArrayList<>();

        ArrayList<PayType> list = spinnerRepo.getData(db.getReadableDatabase());

        for ( PayType p : list ) {
            paymentTypeID.add(p.getPayType_id());
            paymentTypeValue.add(p.getPayType_name());
        }

        ArrayAdapter<String> adapterEnglish = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, paymentTypeValue);
        paymentTypeSpinner.setAdapter(adapterEnglish);
    }


    private void savePayment() {
        data.setPayment_title(items);
        data.setPayment_desc(descpayment.getText().toString());
        data.setPayment_price(Double.parseDouble(priceEdit.getText().toString()));
        data.setPayment_endDate(date.getText().toString());
        data.setPayment_date(formattedDate);


        if (!isEdit)
            new PaymentDataRepo().insertData(db.getWritableDatabase(), data);
        else
            new PaymentDataRepo().updateData(db.getWritableDatabase(), data);


    }

    private void init() {
        bankname = (TextView) findViewById(R.id.bankname);
        debt = (TextView) findViewById(R.id.debt);
        date = (TextView) findViewById(R.id.showdatetime);
        start = (Button) findViewById(R.id.start);
        priceEdit = (EditText) findViewById(R.id.editprice);
        descpayment = (EditText) findViewById(R.id.descpayment);
//        showalertdate = (TextView) findViewById(R.id.showalert);
        paymentTypeSpinner = (Spinner) findViewById(R.id.typepayment);

        data = new Payment_data();

        paytypeData = new PayType();


        Date time = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        formattedDate = df.format(time);

        Log.i("Time Class ", " Time value in milliseconds " + time.getYear());
        date.setText(formattedDate);

        db = DBHelper.getInstance(this);

        initAlertDialog();


        if(!isEdit)
            dataRepo.insertData(db.getWritableDatabase(), data);
        else
            dataRepo.updateData(db.getWritableDatabase(), data);
    }

//    private void init() {
//            bankname = (TextView) findViewById(R.id.bankname);
//            debt = (TextView) findViewById(R.id.debt);
//            date = (TextView) findViewById(R.id.showdatetime);
//            start = (Button)findViewById(R.id.start);
//            priceEdit = (EditText)findViewById(R.id.editprice);
////            descpayment = (EditText)findViewById(R.id.descpayment);
//
//            data = new Payment_data();
//            dataRepo = new PaymentDataRepo();
//            spinnerRepo = new PaymentTypeRepo();
//            db = DBHelper.getInstance(this);
//
//    }

    private void initDialog(final String[] text, String head, final TextView tv) {
        builder = new AlertDialog.Builder(PaymentActivity.this);
        builder.setTitle(head);
        builder.setSingleChoiceItems(text, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "คุณเลือก " +
                        text[which], Toast.LENGTH_SHORT).show();
                tv.setVisibility(View.VISIBLE);
                tv.setText(text[which]);
            }
        });
        builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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

//        timealertbuilder.setNegativeButton("ยกเลิก", null);
        timealertbuilder.create();
    }

    private void onSpinnerClick() {

        paymentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                debt.setVisibility(View.INVISIBLE);
                bankname.setVisibility(View.INVISIBLE);
//                db.getAllPayType();

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
                loadSpinnerData();

            }

            private void loadSpinnerData() {
            }


            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });
    }


    private void loadSpinnerData() {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manu_main_payment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        if (item.getItemId() == R.id.action_alert) {
            timealertbuilder.show();
            return true;
        } else if (item.getItemId() == R.id.action_add) {
            return true;
        }

        if (item.getItemId() == android.R.id.home) {
            intent = new Intent(getApplicationContext(), PaymentMainPage.class);
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

        click = (Button) findViewById(R.id.calendar);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialogID);
            }
        });
        date.setVisibility(View.INVISIBLE);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == dialogID) {
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
            date.setVisibility(View.VISIBLE);
            date.setText("วันที่เลือก : " + String.valueOf(d) + " / " + String.valueOf(m) + " / " + String.valueOf(y));
        }
    };



}
