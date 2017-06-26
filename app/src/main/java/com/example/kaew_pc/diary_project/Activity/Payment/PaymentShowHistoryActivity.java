package com.example.kaew_pc.diary_project.Activity.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaew_pc.diary_project.Manager.Database.DBHelper;
import com.example.kaew_pc.diary_project.Manager.Database.Payment_data;
import com.example.kaew_pc.diary_project.Manager.Database.Payment_history;
import com.example.kaew_pc.diary_project.Manager.Repository.PaymentDataRepository;
import com.example.kaew_pc.diary_project.Manager.Repository.PaymentHistoryRepository;
import com.example.kaew_pc.diary_project.R;

/**
 * Created by KAEW-PC on 17-Jun-17.
 */

public class PaymentShowHistoryActivity extends AppCompatActivity{

    private DBHelper db;
    private Intent intent;
    private TextView title, des, money, dateEnd, datePay, detailBD;
    private Payment_history data;
    private PaymentHistoryRepository repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history_detail);
        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);

        init();
        int id = getIntent().getIntExtra("ID", -1);
        Toast.makeText(this, "" + id, Toast.LENGTH_SHORT).show();
        if (id != -1) { //When click from NOTI
            data = repo.getDataById(db.getReadableDatabase(), String.valueOf(id));
            title.setText(data.getHistory_id());

//            detailBD.setText(data.getBankName_id());
//            detailBD.setText(data.getDebtTime_id());

            des.setText(data.getHistory_desc());
            money.setText(String.valueOf(data.getHistory_price()));
            dateEnd.setText(data.getHistory_endDate());
            datePay.setText(data.getHistory_datePay());

        }
        else{
            Toast.makeText(this, "Can not get data", Toast.LENGTH_SHORT).show();
        }

    }

    private void init() {
        title = (TextView) findViewById(R.id.title);
        des = (TextView) findViewById(R.id.detail);
        money = (TextView) findViewById(R.id.priceshow);
        dateEnd = (TextView) findViewById(R.id.endDate);
        datePay = (TextView) findViewById(R.id.showdatetime);
        db = DBHelper.getInstance(this);
        repo = new PaymentHistoryRepository();

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
