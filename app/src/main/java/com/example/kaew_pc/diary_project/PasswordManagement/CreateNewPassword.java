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
import com.example.kaew_pc.diary_project.main;

/**
 * Created by KAEW-PC on 05-May-17.
 */

public class CreateNewPassword extends AppCompatActivity {

    private DBHelper db;
    private Button confirm;
    private EditText newpass1,newpass2,pid;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_password);

        init();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newpass1.getText().toString().equalsIgnoreCase(newpass2.getText().toString())){

                    Intent go;
                    db.setPassword(db.getWritableDatabase(), newpass1.getText().toString(), pid.getText().toString());

                    if(intent.getExtras().getBoolean("Setting")) { //call setting from main
                        go = new Intent(CreateNewPassword.this, main.class);
                    }
                    else { //call after install (first run)
                        go = new Intent(CreateNewPassword.this, main.class);
                    }

                    startActivity(go);
                    finish();
                }
                else{
                    Toast.makeText(CreateNewPassword.this, "Password not match", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void init(){
         confirm = (Button)findViewById(R.id.submit);
         newpass1 = (EditText)findViewById(R.id.newpassword);
         newpass2 = (EditText)findViewById(R.id.newpassword2);
    }



}
