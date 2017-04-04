package com.example.kaew_pc.diary_project.PaymentManagement;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.kaew_pc.diary_project.Database.Payment_data;
import com.example.kaew_pc.diary_project.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by chommchome on 28/3/2560.
 */

public class PaymentActivity extends AppCompatActivity {

    static final int dialogID = 0;
    private Spinner paymentTypeSpinner;
    private AlertDialog.Builder builder, timealertbuilder;
    private TextView bankname;
    private String[] banks = new String[]{ "SCB (ไทยพาณิชย์)", "KBANK (กสิกรไทย)","GSB (ออมสิน)","KTC (กรุงไทย)","UOB (ยูโอบี)","KRUNGSRI (กรุงศรี)",
            "TMB (ทหารไทย)","AEON (อิออน)","BBL (บัวหลวง)","Citybank  (ซิตี้แบงก์)"};
    private TextView debt;
    private String[] debtyear = new String[]{ "6 เดือน", "12 งวด"};
    private String[] timealert = new String[]{ "ล่วงหน้า 1 วัน", "ล่วงหน้า 3 วัน", "ล่วงหน้า 5 วัน", "ล่วงหน้า 1 สัปดาห์", "ล่วงหน้า 2 สัปดาห์"};

    private TextView date;

    private EditText priceEdit;
    private Boolean isEdit = false;

    private String items;//type of payment

    private int y,d,m ;
    private Button click;
    private Button start;

    private ArrayList<String> paymentType = new ArrayList<String>();
    private Payment_data data;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_activity);
        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);

        init();

        paymentTypeSpinner = (Spinner) findViewById(R.id.typepayment);

        String[] paymentT = getResources().getStringArray(R.array.ประเภทค่าใช้จ่าย);
        ArrayAdapter<String> adapterEnglish = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, paymentT);
        paymentTypeSpinner.setAdapter(adapterEnglish);


        Button save = (Button) findViewById(R.id.button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                savePayment();

                Intent i = new Intent(getApplicationContext(), PaymentMainPage.class);
                startActivity(i);
                finish();
            }
        });

        onSpinnerClick();
        showCalendar();

        int id = getIntent().getIntExtra("id", 0);

        if (id != 0) { //When click from listview
            data = db.getPaymentById(String.valueOf(id));
//            paymentTypeSpinner.setText(data.getPayment_title());
//            priceEdit.setText(data.getPayment_price());
            date.setText(data.getPayment_endDate());

            isEdit = true;

        }
    }

    private void savePayment() {
        data.setPayment_title(items);
//        data.setPayment_price(priceEdit.Double().parseDouble());
        data.setPayment_endDate(date.getText().toString());


        if(!isEdit)
            db.createPayment(db.getWritableDatabase(), data);
        else
            db.updatePayment(db.getWritableDatabase(), data);
    }

    private void init() {
            bankname = (TextView) findViewById(R.id.bankname);
            debt = (TextView) findViewById(R.id.debt);
            date = (TextView) findViewById(R.id.showdatetime);
            start = (Button)findViewById(R.id.start);
            priceEdit = (EditText)findViewById(R.id.editprice);

            data = new Payment_data();
            db = DBHelper.getInstance(this);

            initAlertDialog();
        }

    private void initDialog(final String[] text, String head, final TextView tv) {
        builder = new AlertDialog.Builder(PaymentActivity.this);
        builder.setTitle(head);
        builder.setSingleChoiceItems(text , 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "คุณเลือก " +
                        text[which] , Toast.LENGTH_SHORT).show();
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
        timealertbuilder.setSingleChoiceItems(timealert , 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "คุณเลือก " +
                        timealert[which] , Toast.LENGTH_SHORT).show();
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

                items = paymentTypeSpinner.getSelectedItem().toString();
                Log.i("Selected item : ", items);

                if(items.equalsIgnoreCase("ค่าบัตรเครดิต")){
                    initDialog(banks, "เลือกธนาคาร", bankname);
                    builder.show();
                    bankname.setVisibility(View.VISIBLE);
                }
                else if(items.equalsIgnoreCase("ค่าผ่อนชำระ")){
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
        if (item.getItemId() == R.id.action_alert) {
            timealertbuilder.show();
            return true;
        } else if (item.getItemId() == R.id.action_add) {
            return true;
        }

        if(item.getItemId() == android.R.id.home){
            intent = new Intent(getApplicationContext(), PaymentMainPage.class);
            startActivity(intent);
            finish();
        }


        return super.onOptionsItemSelected(item);
    }


    //    show calendar
    private void showCalendar(){
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
    protected Dialog onCreateDialog(int id){
        if(id == dialogID){
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
            date.setText("วันที่เลือก : "+String.valueOf(d)+" / "+String.valueOf(m)+" / "+String.valueOf(y));
        }
    };



}
