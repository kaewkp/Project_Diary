package com.example.kaew_pc.diary_project.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kaew_pc.diary_project.Activity.PasswordManagement.SettingPasswordActivity;
import com.example.kaew_pc.diary_project.Manager.Database.DBHelper;
import com.example.kaew_pc.diary_project.R;

import java.util.ArrayList;

public class SplashScreenActivity extends AppCompatActivity {

    private Runnable runnable;
    private Handler handler;
    private DBHelper dbHelper;
    private boolean havePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screen);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        dbHelper = DBHelper.getInstance(this);

        checkPassword();


        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                Intent intent;
                if(havePass) {
                    intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                }else{
                    intent = new Intent(SplashScreenActivity.this, SettingPasswordActivity.class);
                    intent.putExtra("Setting", "FirstRun");
                }
                startActivity(intent);
                finish();
            }
        };
    }

    private void checkPassword() {
//        dbHelper.getReadableDatabase();
        if(dbHelper.getPassword() == ""){
            havePass = false;
        }
        else{
            havePass = true;
        }
    }

    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }

    public void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }
}
