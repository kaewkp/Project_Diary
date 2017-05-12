package com.example.kaew_pc.diary_project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kaew_pc.diary_project.Activity.PasswordManagement.PersonalCheck;
import com.example.kaew_pc.diary_project.Manager.Database.DBHelper;
import com.example.kaew_pc.diary_project.R;


/**
 * Created by KAEW-PC on 21-Mar-17.
 */

public class LoginActivity extends AppCompatActivity {

    private DBHelper db;
    private ImageView[] imageSet;
    private String inputPass = "", realPass;
    private int count = 0;
    private Button forgot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pass);
        init();

    }

    private void init() {
        db = DBHelper.getInstance(this);
        realPass = db.getPassword();
        db.close();
        forgot = (Button)findViewById(R.id.forgotPass);

        imageSet = new ImageView[]{ (ImageView)findViewById(R.id.i1), (ImageView) findViewById(R.id.i2), (ImageView) findViewById(R.id.i3), (ImageView) findViewById(R.id.i4) };

        findViewById(R.id.n0).setOnClickListener(click);
        findViewById(R.id.n1).setOnClickListener(click);
        findViewById(R.id.n2).setOnClickListener(click);
        findViewById(R.id.n3).setOnClickListener(click);
        findViewById(R.id.n4).setOnClickListener(click);
        findViewById(R.id.n5).setOnClickListener(click);
        findViewById(R.id.n6).setOnClickListener(click);
        findViewById(R.id.n7).setOnClickListener(click);
        findViewById(R.id.n8).setOnClickListener(click);
        findViewById(R.id.n9).setOnClickListener(click);

        findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count > 0) {
                    inputPass = inputPass.substring(0, inputPass.length() - 1);
                    count--;
                }
                changeImg();
            }
        });

        forgot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PersonalCheck.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void checkPass(){
        if(inputPass.equalsIgnoreCase(realPass)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(LoginActivity.this, "Incorect Password" , Toast.LENGTH_SHORT).show();
            setImage( new int[]{ R.drawable.aaa, R.drawable.aaa, R.drawable.aaa, R.drawable.aaa}, 0);
            count = 0;
            inputPass = "";
        }
    }

    private void changeImg(){
        switch (count) {
            case 0:
                setImage( new int[]{ R.drawable.aaa, R.drawable.aaa, R.drawable.aaa, R.drawable.aaa}, 0);
                break;
            case 1:
                setImage( new int[]{ R.drawable.starlogin, R.drawable.aaa, R.drawable.aaa, R.drawable.aaa}, 0);
                break;
            case 2:
                setImage( new int[]{ R.drawable.starlogin, R.drawable.aaa, R.drawable.aaa}, 1);
                break;
            case 3:
                setImage( new int[]{ R.drawable.starlogin, R.drawable.aaa }, 2);
                break;
            case 4:
                setImage( new int[]{ R.drawable.starlogin }, 3);
                break;
        }
    }

    private void setImage(int[] rsc, int start){
        for ( int i = start; i < imageSet.length; i++) {
            imageSet[i].setImageResource(rsc[i - start]);
        }
    }

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(count < 4) {
                inputPass = inputPass + ((Button) v).getText().toString();
                count++;
                changeImg();
            }

            if(count == 4) {
                checkPass();
            }
        }
    };
}
