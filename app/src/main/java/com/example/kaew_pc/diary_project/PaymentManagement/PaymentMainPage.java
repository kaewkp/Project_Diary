package com.example.kaew_pc.diary_project.PaymentManagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.kaew_pc.diary_project.Login;
import com.example.kaew_pc.diary_project.R;
import com.example.kaew_pc.diary_project.main;

/**
 * Created by chommchome on 27/3/2560.
 */

public class PaymentMainPage extends AppCompatActivity {

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_main);
        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);


        init();

    }

    private void init() {

        fab = (FloatingActionButton) findViewById(R.id.fabpayment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(PaymentMainPage.this, "Click", Toast.LENGTH_LONG).show();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent;
                intent = new Intent(getApplicationContext(), PaymentActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
            intent = new Intent(getApplicationContext(), main.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
