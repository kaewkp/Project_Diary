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

public class Login extends AppCompatActivity {

    private Button submit;
    private Button forgotPass;
    private EditText pass;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        init();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pass.getText().toString().equalsIgnoreCase(db.getPassword())) {
                    Intent intent = new Intent(Login.this, main.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(Login.this, "Incorect Password", Toast.LENGTH_LONG).show();
                }
            }
        });

//        forgotPass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Intent.ACTION_SEND);
//                i.setType("password (4 digits) : ");
//                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"com.example.kaew_pc.diary_project"});
//                i.putExtra(Intent.EXTRA_SUBJECT, "forgot password");
//                i.putExtra(Intent.EXTRA_TEXT, "password : ");
//                try {
//                    startActivity(Intent.createChooser(i, "Send mail..."));
//                } catch (android.content.ActivityNotFoundException ex) {
//                    Toast.makeText(Login.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    private void init() {
        db = new DBHelper(this);
        submit = (Button) findViewById(R.id.submit);
        pass = (EditText) findViewById(R.id.password);
    }
}
