package com.example.kaew_pc.diary_project.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kaew_pc.diary_project.Activity.Note.NoteCreatePageActivity;
import com.example.kaew_pc.diary_project.R;

public class ChangeColor extends AppCompatActivity {

    private ImageButton w, p, y, g, v, b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_color);

        init();
        ImageButton white = (ImageButton) findViewById(R.id.whiteButton);

        white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NoteCreatePageActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    private void init() {
        w = (ImageButton) findViewById(R.id.whiteButton);
        p = (ImageButton) findViewById(R.id.pinkButton);
        g = (ImageButton) findViewById(R.id.greenButton);
        b = (ImageButton) findViewById(R.id.blueButton);
        y = (ImageButton) findViewById(R.id.yellowButton);
        v = (ImageButton) findViewById(R.id.violetButton);
    }
}
