package com.example.kaew_pc.diary_project.PasswordManagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kaew_pc.diary_project.Database.DBHelper;
import com.example.kaew_pc.diary_project.R;
import com.example.kaew_pc.diary_project.Login;
import com.example.kaew_pc.diary_project.main;

public class SettingPassword extends AppCompatActivity {

    private Button submit;
    private EditText pass, pass2;
    private DBHelper db;
    private Intent intent;

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
                if(pass.getText().toString().equalsIgnoreCase(pass2.getText().toString())){

                    Intent go;
                    db.setPassword(db.getWritableDatabase(), pass.getText().toString());

                    if(intent.getExtras().getBoolean("Setting")) { //call setting from main
                        go = new Intent(SettingPassword.this, main.class);
                    }
                    else { //call after install (first run)
                        go = new Intent(SettingPassword.this, Login.class);
                    }

                    startActivity(go);
                    finish();
                }
                else{
                    Toast.makeText(SettingPassword.this, "Password not match", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void init() {
        db = new DBHelper(this);
        submit = (Button) findViewById(R.id.submit);
        pass = (EditText) findViewById(R.id.password);
        pass2 = (EditText) findViewById(R.id.password2);

        intent = getIntent();
    }

}
