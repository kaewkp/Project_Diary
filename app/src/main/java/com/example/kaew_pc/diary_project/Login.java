package com.example.kaew_pc.diary_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kaew_pc.diary_project.Database.DBHelper;

/**
 * Created by KAEW-PC on 21-Mar-17.
 */

public class Login extends AppCompatActivity {

    private Button submit;
//    private EditText pass;
    private DBHelper db;

    private ImageButton b1,b2,b3,b4;
    private Button n1,n2,n3,n4,n5,n6,n7,n8,n9,n0,del;
    private String pass = "";
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pass);

        init();

//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                if(pass.getText().toString().equalsIgnoreCase(db.getPassword())) {
////                    Intent intent = new Intent(Login.this, main.class);
////                    startActivity(intent);
////                    finish();
////                }
////                else{
////                    Toast.makeText(Login.this, "Incorect Password", Toast.LENGTH_LONG).show();
////                }
//            }
//        });
    }

    private void init() {
        db = new DBHelper(this);
//        submit = (Button) findViewById(R.id.submit);
//        pass = (EditText) findViewById(R.id.password);

        b1 = (ImageButton) findViewById(R.id.b1);
        b2 = (ImageButton) findViewById(R.id.b2);
        b3 = (ImageButton) findViewById(R.id.b3);
        b4 = (ImageButton) findViewById(R.id.b4);

        n0 = (Button) findViewById(R.id.n0);
        n1 = (Button) findViewById(R.id.n1);
        n2 = (Button) findViewById(R.id.n2);
        n3 = (Button) findViewById(R.id.n3);
        n4 = (Button) findViewById(R.id.n4);
        n5 = (Button) findViewById(R.id.n5);
        n6 = (Button) findViewById(R.id.n6);
        n7 = (Button) findViewById(R.id.n7);
        n8 = (Button) findViewById(R.id.n8);
        n9 = (Button) findViewById(R.id.n9);
        del = (Button) findViewById(R.id.del);

        changeImg();
        buttonClick();
    }

    private void buttonClick() {
        n0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count<4) {
                    pass = pass + "0";
                    count++;
                    changeImg();
                }
                checkPass();
            }
        });

        n1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count<4) {
                    pass = pass + "1";
                    count++;
                    changeImg();
                }checkPass();
            }
        });

        n2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count<4) {
                    pass = pass + "2";
                    count++;
                    changeImg();
                }checkPass();
            }
        });

        n3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count<4) {
                    pass = pass + "3";
                    count++;
                    changeImg();
                }checkPass();
            }
        });

        n4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count<4) {
                    pass = pass + "4";
                    count++;
                    changeImg();
                }checkPass();
            }
        });

        n5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count<4) {
                    pass = pass + "5";
                    count++;
                    changeImg();
                }checkPass();
            }
        });

        n6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count<4) {
                    pass = pass + "6";
                    count++;
                    changeImg();
                }checkPass();
            }
        });

        n7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count<4) {
                    pass = pass + "7";
                    count++;
                    changeImg();
                }checkPass();
            }
        });

        n8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count<4) {
                    pass = pass + "8";
                    count++;
                    changeImg();
                }checkPass();
            }
        });

        n9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count<4) {
                    pass = pass + "9";
                    count++;
                    changeImg();
                }checkPass();
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count > 0) {
                    pass = pass.substring(0, pass.length() - 1);
                    count--;
                }
                changeImg();
            }
        });
    }

    private void checkPass(){
        if(count == 4){
            if(pass.equalsIgnoreCase(db.getPassword())) {
                Intent intent = new Intent(Login.this, main.class);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(Login.this, "Incorect Password : "+ pass , Toast.LENGTH_LONG).show();
            }
        }
    }

    private void changeImg(){
        Log.e("Count : ", String.valueOf(count));
        switch (count) {
            case 0:
                b1.setImageResource(R.drawable.red);
                b2.setImageResource(R.drawable.red);
                b3.setImageResource(R.drawable.red);
                b4.setImageResource(R.drawable.red);
                break;
            case 1:
                b1.setImageResource(R.drawable.yellow);
                b2.setImageResource(R.drawable.red);
                b3.setImageResource(R.drawable.red);
                b4.setImageResource(R.drawable.red);
                break;
            case 2:
                b2.setImageResource(R.drawable.yellow);
                b3.setImageResource(R.drawable.red);
                b4.setImageResource(R.drawable.red);
                break;
            case 3:
                b3.setImageResource(R.drawable.yellow);
                b4.setImageResource(R.drawable.red);
                break;
            case 4:
                b4.setImageResource(R.drawable.yellow);
                break;
        }
    }
}
