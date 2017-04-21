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
import com.example.kaew_pc.diary_project.Database.PayType;
import com.example.kaew_pc.diary_project.Database.Payment_data;
import com.example.kaew_pc.diary_project.R;

import com.example.kaew_pc.diary_project.Repository.PaymentDataRepo;
import com.example.kaew_pc.diary_project.Repository.PaymentTypeRepo;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;

/**
 * Created by chommchome on 28/3/2560.
 */

public class PaymentActivity extends AppCompatActivity {

    static final int dialogID = 0;
    private Spinner paymentTypeSpinner;
    private AlertDialog.Builder builder, timealertbuilder;
    private TextView bankname;
    private TextView showalertdate;
    private String[] banks, debtyear, timealert;

    private TextView debt, date;
    private String formattedDate;

    private EditText priceEdit;
    private EditText descpayment;
    private Boolean isEdit = false;

    private String items, dateChoose;//type of payment

    private int y, d, m;
    private Button save;

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
        initAlertDialog();
        initSpinner();

        onSpinnerClick();
        showCalendar();

        int id = getIntent().getIntExtra("id", 0);
        if (id != 0) { //When click from listview
            data = new PaymentDataRepo().getDataById(db.getReadableDatabase(), String.valueOf(id));
            paymentTypeSpinner.setSelected(true);


            descpayment.setText(data.getPayment_desc());

            priceEdit.setText(String.valueOf(data.getPayment_price()));

            //set spinner select position
            paymentTypeSpinner.setSelection(Integer.parseInt(data.getPayType_id()));
            date.setVisibility(View.VISIBLE);

            String d = data.getPayment_endDate();
            if(d != null)
                date.setText("วันที่เลือก : " + d);
            isEdit = true;
        }
    }

    private void init() {
        bankname = (TextView) findViewById(R.id.bankname);
        debt = (TextView) findViewById(R.id.debt);
        date = (TextView) findViewById(R.id.showdatetime);
//        start = (Button) findViewById(R.id.start);
        priceEdit = (EditText) findViewById(R.id.editprice);
        descpayment = (EditText) findViewById(R.id.descpayment);
//        showalertdate = (TextView) findViewById(R.id.showalert);
        paymentTypeSpinner = (Spinner) findViewById(R.id.typepayment);

        save = (Button) findViewById(R.id.button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePayment();
                finish();
            }
        });

        data = new Payment_data();
        paytypeData = new PayType();

        debtyear = getResources().getStringArray(R.array.การผ่อนชำระ);
        banks = getResources().getStringArray(R.array.ประเภทคธนาคาร);
        timealert = getResources().getStringArray(R.array.การแจ้งเตือน);

        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        formattedDate = df.format(time);

        db = DBHelper.getInstance(this);
    }


    private void initSpinner() {
        paymentTypeSpinner = (Spinner) findViewById(R.id.typepayment);
        paymentTypeID = new ArrayList<>();
        paymentTypeValue = new ArrayList<>();

        ArrayList<PayType> list = new PaymentTypeRepo().getData(db.getReadableDatabase());

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

        double price = 0;
        if(!priceEdit.getText().toString().equals("")) {
            price = Double.parseDouble(priceEdit.getText().toString());
        }

        data.setPayment_price(price);
        data.setPayment_endDate(dateChoose);
        data.setPayment_date(formattedDate);

        if (!isEdit)
            new PaymentDataRepo().insertData(db.getWritableDatabase(), data);
        else
            new PaymentDataRepo().updateData(db.getWritableDatabase(), data);
    }

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
                paymentTypeSpinner.setPrompt("เลือกประเภทค่าใช้จ่าย");
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

        Button calendarButton = (Button) findViewById(R.id.calendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
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
            dateChoose = String.valueOf(d) + " / " + String.valueOf(m) + " / " + String.valueOf(y);
            date.setVisibility(View.VISIBLE);
            date.setText("วันที่เลือก : " + dateChoose);
        }
    };



}
