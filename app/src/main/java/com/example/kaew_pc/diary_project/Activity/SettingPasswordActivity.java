package com.example.kaew_pc.diary_project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kaew_pc.diary_project.Manager.Database.DBHelper;
import com.example.kaew_pc.diary_project.R;

public class SettingPasswordActivity extends AppCompatActivity {

    private Button submit;
    private EditText pass, pass2, pid;
    private DBHelper db;
    private Intent intent;
    private boolean isSetting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_manager);

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
                        Intent go = new Intent(SettingPasswordActivity.this, LoginActivity.class);
                        startActivity(go);
                    }
                    finish();
                }
                else{
                    Toast.makeText(SettingPasswordActivity.this, "Password must not empty and must be the same", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validateInput() {
        if(pid.length() != 13 && !isSetting){
            Toast.makeText(SettingPasswordActivity.this, "Personal ID must be 13 digit", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(pass.length() != 4){
            Toast.makeText(SettingPasswordActivity.this, "Password must be 4 digit", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!pass.getText().toString().equalsIgnoreCase(pass2.getText().toString())){
            Toast.makeText(SettingPasswordActivity.this, "Password not match", Toast.LENGTH_SHORT).show();
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
        if(isSetting) {
            finish();
        }
    }

}
