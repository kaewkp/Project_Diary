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

public class PersonalCheck extends AppCompatActivity {

    private Button submit;
    private EditText personalID;
    private DBHelper db;
    private String inputID = "", realID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_check);

        init();
//        checkPass();

    }

    private void init()
    {
        submit = (Button)findViewById(R.id.submit);
        personalID = (EditText)findViewById(R.id.pid);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPass();
            }
    });
}

    private void checkPass(){
        db.getPassword(db.getWritableDatabase(), personalID.getText().toString());

        if(inputID.equalsIgnoreCase(realID)) {
            Intent intent = new Intent(PersonalCheck.this, CreateNewPassword.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(PersonalCheck.this, "Incorect Personal ID" , Toast.LENGTH_SHORT).show();
//            db.getPersonalData(db.getWritableDatabase(), pid.getText().toString(), (++personalInfo_seq)+"");

//            db.setPassword(db.getWritableDatabase(), pass.getText().toString(), pid.getText().toString());
            //            setImage( new int[]{ R.drawable.aaa, R.drawable.aaa, R.drawable.aaa, R.drawable.aaa}, 0);
//            count = 0;
//            inputPass = "";
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PersonalCheck.this, main.class);
        startActivity(intent);
        finish();
    }

}
