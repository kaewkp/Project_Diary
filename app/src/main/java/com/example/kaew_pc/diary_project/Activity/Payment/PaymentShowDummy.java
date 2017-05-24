package com.example.kaew_pc.diary_project.Activity.Payment;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kaew_pc.diary_project.R;

public class PaymentShowDummy extends AppCompatActivity {

    private Button already;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_show_dummy);
        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);

        init();
    }

    public void init() {

        already = (Button) findViewById(R.id.alreadypay);
        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentShowDummy.this, PaymentMainPageActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }


    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), PaymentMainPageActivity.class);
        startActivity(intent);
        finish();
    }

}
