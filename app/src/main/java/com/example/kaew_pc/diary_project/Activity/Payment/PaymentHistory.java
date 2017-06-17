package com.example.kaew_pc.diary_project.Activity.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kaew_pc.diary_project.Activity.MainActivity;
import com.example.kaew_pc.diary_project.Manager.Adapter.PaymentCustomAdapter;
import com.example.kaew_pc.diary_project.Manager.Database.DBHelper;
import com.example.kaew_pc.diary_project.Manager.Database.Payment_data;
import com.example.kaew_pc.diary_project.Manager.Repository.PaymentDataRepository;
import com.example.kaew_pc.diary_project.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by chommchome on 27/5/2560.
 */

public class PaymentHistory extends AppCompatActivity {

    private DBHelper db;
    private ListView listpayment;
    private Boolean isResume = false;
    public final Calendar cal = Calendar.getInstance();

    private PaymentDataRepository paymentObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_history);
        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);

        init();
        loadPaymentList();
    }

    private void init() {
        db = DBHelper.getInstance(this);
        paymentObj = new PaymentDataRepository();
        listpayment = (ListView) findViewById(R.id.historylist);
    }


    private void loadPaymentList() {
        final ArrayList<Payment_data> data = paymentObj != null ?
                paymentObj.getData(db.getReadableDatabase()) :
                new PaymentDataRepository().getData(db.getReadableDatabase());

        PaymentCustomAdapter adapter = new PaymentCustomAdapter(PaymentHistory.this, data);
        listpayment.setAdapter(adapter);
        listpayment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                isResume = true;
//                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
//                intent.putExtra("id", data.get(position).getPayment_id());
//                Payment_data.setPaymentIdFromClicked(data, position);
//                startActivity(intent);



//                if(data.get(position).getPayment_id() == 1){
//                    Intent intent = new Intent(getApplicationContext(), PaymentHistoryDummy.class);
//                    startActivity(intent);
//                }else if (data.get(position).getPayment_id() == 2){
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

