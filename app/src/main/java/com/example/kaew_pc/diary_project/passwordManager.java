package com.example.kaew_pc.diary_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kaew_pc.diary_project.Database.DBHelper;

public class passwordManager extends AppCompatActivity {

    private Button submit;
    private EditText pass, pass2;
    private DBHelper db;

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
                    db.setPassword(db.getWritableDatabase(), pass.getText().toString());
                    Intent intent = new Intent(passwordManager.this, confirmPassword.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(passwordManager.this, "Password not match", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void init() {
        db = new DBHelper(this);
        submit = (Button) findViewById(R.id.submit);
        pass = (EditText) findViewById(R.id.password);
        pass2 = (EditText) findViewById(R.id.password2);
    }

}
