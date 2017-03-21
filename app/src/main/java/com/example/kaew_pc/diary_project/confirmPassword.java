package com.example.kaew_pc.diary_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kaew_pc.diary_project.Database.DBHelper;

/**
 * Created by KAEW-PC on 21-Mar-17.
 */

public class confirmPassword extends AppCompatActivity {

    private Button submit;
    private EditText pass;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_manager);

        init();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pass.getText().toString().equalsIgnoreCase(db.getPassword())) {
                    Intent intent = new Intent(confirmPassword.this, main.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(confirmPassword.this, "Incorect Password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void init() {
        db = new DBHelper(this);
        submit = (Button) findViewById(R.id.submit);
        pass = (EditText) findViewById(R.id.password);
    }
}
