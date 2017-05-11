package com.example.kaew_pc.diary_project.Activity.PasswordManagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.kaew_pc.diary_project.Activity.LoginActivity;
import com.example.kaew_pc.diary_project.Manager.Database.DBHelper;
import com.example.kaew_pc.diary_project.R;

public class SettingPasswordActivity extends AppCompatActivity {

    private Button submit;
    private EditText pass, pass2, pid;
    private DBHelper db;
    private Intent intent;
    private String comeFrom;
    private int personalInfo_seq = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_password);
        init();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput()){
                    if(!comeFrom.equals("FirstRun")) { //call setting from main or forgot password from login so let user to change their password
                        db.updatePassword(db.getWritableDatabase(), pass.getText().toString());
                    }
                    else { //call after install (first run) so "must" set personalID and password for the app
                        db.setPassword(db.getWritableDatabase(), pass.getText().toString(), pid.getText().toString());
                    }

                    // If come from forgot password or first run we will lead user to login page
                    if(!comeFrom.equals("Main")){
                        Intent go = new Intent(SettingPasswordActivity.this, LoginActivity.class);
                        startActivity(go);
                    }
                    finish();
                }
            }
        });
    }

    private boolean validateInput() {
        if(pid != null && pid.length() != 13){
            Toast.makeText(this, "Personal ID must be 13 digit", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(pass.length() != 4){
            Toast.makeText(this, "Password must be 4 digit", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!pass.getText().toString().equalsIgnoreCase(pass2.getText().toString())){
            Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void init() {
        db = DBHelper.getInstance(this);
        comeFrom = getIntent().getExtras().getString("Setting");

        //Mean come from first run so need to set personal ID
        if(comeFrom.equals("FirstRun")){
            setContentView(R.layout.activity_password_manager);
            pid = (EditText) findViewById(R.id.pid);
        }

//        Toast.makeText(this, "PID is init : " + (pid != null), Toast.LENGTH_SHORT).show();

        // 2 layout have the same ID
        submit = (Button) findViewById(R.id.submit);
        pass = (EditText) findViewById(R.id.password);
        pass2 = (EditText) findViewById(R.id.password2);
    }

    @Override
    public void onBackPressed() {
        if(comeFrom.equals("ForgotPassword")) {
            Intent intent = new Intent(this, PersonalCheck.class);
            startActivity(intent);
        }
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
