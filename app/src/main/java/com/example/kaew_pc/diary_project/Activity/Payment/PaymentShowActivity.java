package com.example.kaew_pc.diary_project.Activity.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kaew_pc.diary_project.Manager.Database.DBHelper;
import com.example.kaew_pc.diary_project.R;

/**
 * Created by chommchome on 29/3/2560.
 */

public class PaymentShowActivity extends AppCompatActivity {
    private TextView desc;
    private Spinner paymentType;
    private EditText editprice;
    private DBHelper db;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_activity);

        init();

        paymentType.setSelected(true);
        desc.setText(intent.getStringExtra("desc"));
        editprice.setText(intent.getStringExtra("editprice"));

        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);
        action.setHomeButtonEnabled(true);
    }

    private void init() {
        desc = (TextView) findViewById(R.id.descpayment);
        paymentType = (Spinner) findViewById(R.id.typepayment);
        editprice = (EditText) findViewById(R.id.editprice);

        intent = getIntent();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), PaymentMainPageActivity.class);
        startActivity(intent);
        finish();
    }
}
