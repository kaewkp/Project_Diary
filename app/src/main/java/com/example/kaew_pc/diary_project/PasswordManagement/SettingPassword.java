package com.example.kaew_pc.diary_project.PasswordManagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kaew_pc.diary_project.Database.DBHelper;
import com.example.kaew_pc.diary_project.R;
import com.example.kaew_pc.diary_project.Login;
import com.example.kaew_pc.diary_project.main;
import com.example.kaew_pc.diary_project.splash_screen;

public class SettingPassword extends AppCompatActivity {

    private Button submit;
    private EditText pass, pass2, pid;
    private DBHelper db;
    private Intent intent;
    private boolean isSetting = false;
    private int personalInfo_seq = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_manager);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        init();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateInput()){
                    if(isSetting) { //call setting from main
                        db.updatePassword(db.getWritableDatabase(), pass.getText().toString());
                    }
                    else { //call after install (first run)
                        db.setPassword(db.getWritableDatabase(), pass.getText().toString(), pid.getText().toString());
                        Intent go = new Intent(SettingPassword.this, Login.class);
                        startActivity(go);
                    }
                    finish();
                }
            }
        });
    }

    private boolean validateInput() {
        if(pid.length() != 13 && !isSetting){
            Toast.makeText(SettingPassword.this, "Personal ID must be 13 digit", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(pass.length() != 4){
            Toast.makeText(SettingPassword.this, "Password must be 4 digit", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!pass.getText().toString().equalsIgnoreCase(pass2.getText().toString())){
            Toast.makeText(SettingPassword.this, "Password not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void init() {
        db = new DBHelper(this);
        submit = (Button) findViewById(R.id.submit);
        pass = (EditText) findViewById(R.id.password);
        pass2 = (EditText) findViewById(R.id.password2);
        pid = (EditText) findViewById(R.id.pid);
        intent = getIntent();
        isSetting = intent.getExtras().getBoolean("Setting");

        if(isSetting){
            pid.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SettingPassword.this, main.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        Intent intent;
        switch (item.getItemId()) {

            case android.R.id.home:
//                Toast.makeText(getApplicationContext(), "Here",
//                        Toast.LENGTH_LONG).show();
                intent = new Intent(getApplicationContext(), main.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
