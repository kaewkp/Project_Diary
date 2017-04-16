package com.example.kaew_pc.diary_project.PaymentManagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kaew_pc.diary_project.Database.DBHelper;
import com.example.kaew_pc.diary_project.Database.Payment_data;
import com.example.kaew_pc.diary_project.R;
import com.example.kaew_pc.diary_project.Repository.PaymentDataRepo;
import com.example.kaew_pc.diary_project.main;

import java.util.ArrayList;

/**
 * Created by chommchome on 27/3/2560.
 */

public class PaymentMainPage extends AppCompatActivity {

    private FloatingActionButton fab;
    private DBHelper db;
    private ListView list;
    private Boolean isResume = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_main);
        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);
        init();
        loadPaymentList();
    }

    private void init() {
        db = DBHelper.getInstance(this);
        list = (ListView) findViewById(R.id.paymentlist);

        fab = (FloatingActionButton) findViewById(R.id.fabpayment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent;
                intent = new Intent(getApplicationContext(), PaymentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadPaymentList() {
        ArrayList<Payment_data> data = new PaymentDataRepo().getData(db.getReadableDatabase());
        PaymentCustomAdapter adapter = new PaymentCustomAdapter(PaymentMainPage.this, data);
        list.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isResume)
            loadPaymentList();
        isResume = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manu_page_payment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
//        if (item.getItemId() == R.id.action_addshow) {
//            intent = new Intent(getApplicationContext(), PaymentActivity.class);
//            startActivity(intent);
//        }
//        else if (item.getItemId() == R.id.action_add) {
//            return true;
//        }

        if(item.getItemId() == android.R.id.home){
//            intent = new Intent(getApplicationContext(), main.class);
//            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
