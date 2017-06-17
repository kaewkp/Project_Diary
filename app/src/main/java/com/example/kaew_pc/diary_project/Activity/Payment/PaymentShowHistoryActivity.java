package com.example.kaew_pc.diary_project.Activity.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kaew_pc.diary_project.R;

/**
 * Created by KAEW-PC on 17-Jun-17.
 */

public class PaymentShowHistoryActivity extends AppCompatActivity{

    private TextView title, des, money, dateEnd, bankname, debt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_noti_detail);
        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);

        init();

    }

    private void init() {
        title = (TextView) findViewById(R.id.title);
        des = (TextView) findViewById(R.id.detail);
        money = (TextView) findViewById(R.id.priceshow);
        dateEnd = (TextView) findViewById(R.id.endDate);

        bankname = (TextView) findViewById(R.id.bankname);
        debt = (TextView) findViewById(R.id.debt);

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
