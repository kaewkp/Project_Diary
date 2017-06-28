package com.example.kaew_pc.diary_project.Activity.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.kaew_pc.diary_project.Activity.MainActivity;
import com.example.kaew_pc.diary_project.Manager.Adapter.PaymentCustomAdapter;
import com.example.kaew_pc.diary_project.Manager.Adapter.PaymentHistoryCustomAdapter;
import com.example.kaew_pc.diary_project.Manager.Database.DBHelper;
import com.example.kaew_pc.diary_project.Manager.Database.PayType;
import com.example.kaew_pc.diary_project.Manager.Database.Payment_data;
import com.example.kaew_pc.diary_project.Manager.Database.Payment_history;
import com.example.kaew_pc.diary_project.Manager.Repository.PaymentDataRepository;
import com.example.kaew_pc.diary_project.Manager.Repository.PaymentHistoryRepository;
import com.example.kaew_pc.diary_project.Manager.Repository.PaymentTypeRepository;
import com.example.kaew_pc.diary_project.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by chommchome on 27/5/2560.
 */

public class PaymentHistoryActivity extends AppCompatActivity {

    private DBHelper db;
    private Intent intent, intent2;
//    private ListView listpayment;
    private Boolean isResume = false;
    public final Calendar cal = Calendar.getInstance();
    private ArrayList<String> paymentTypeID;
    private ArrayList<String> paymentTypeValue;
    private Spinner paymentTypeSpinner;
    private PayType paytypeData;
    private ListView historylist;
    private Payment_history data;

    private PaymentHistoryRepository paymentObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_history);
        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);

        init();
        initSpinner();
        loadHistoryList();
    }

    private void init() {
        db = DBHelper.getInstance(this);
        paymentObj = new PaymentHistoryRepository();
        paymentTypeSpinner = (Spinner) findViewById(R.id.typepayment);
        historylist = (ListView) findViewById(R.id.historylist);

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

    private void loadHistoryList() {
        final ArrayList<Payment_history> data = paymentObj != null ?
                paymentObj.getData(db.getReadableDatabase()) :
                new PaymentHistoryRepository().getData(db.getReadableDatabase());

        PaymentHistoryCustomAdapter adapter = new PaymentHistoryCustomAdapter(PaymentHistoryActivity.this, data);
        historylist.setAdapter(adapter);
        historylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                isResume = true;
                Intent intent = new Intent(getApplicationContext(), PaymentShowHistoryActivity.class);
                intent.putExtra("id", data.get(position).getHistory_id());
                Payment_history.setPaymentIdFromClicked(data, position);
                startActivity(intent);

//                isResume = true;
//                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
//                intent.putExtra("id", data.get(position).getPayment_id());
//                Payment_data.setPaymentIdFromClicked(data, position);
//                startActivity(intent);

                if(data.get(position).getHistory_id() == id){

                    Intent intent2 = new Intent(getApplicationContext(), PaymentHistoryActivity.class);
                    startActivity(intent);
                }
//                else if (data.get(position).getPayment_id() == 2){
//                    Intent intent = new Intent(getApplicationContext(), PaymentHistoryDummy2.class);
//                    startActivity(intent);
//                }

            }
        });
    }


        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            Intent intent;
            if (item.getItemId() == android.R.id.home) {
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }

            return super.onOptionsItemSelected(item);
        }


        @Override
        public void onBackPressed () {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }


    }

