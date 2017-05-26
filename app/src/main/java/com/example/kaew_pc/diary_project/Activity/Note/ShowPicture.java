package com.example.kaew_pc.diary_project.Activity.Note;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.kaew_pc.diary_project.R;
import uk.co.senab.photoview.PhotoView;

import java.util.ArrayList;

/**
 * Created by Ekachart-PC on 23/5/2560.
 */

public class ShowPicture extends AppCompatActivity {
    private PhotoView i;
    private ArrayList<Uri> uri;
    private int position, total;
    private FloatingActionButton l,r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testimage);
        init();

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position > 0){
                    position--;
                    onChangeImage();
                }
            }
        });

        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position < total){
                    position++;
                    onChangeImage();
                }
            }
        });
    }

    private void onChangeImage() {
        i.setImageBitmap(getBitmap());
    }

    private void init() {
        l = (FloatingActionButton) findViewById(R.id.left);
        r = (FloatingActionButton) findViewById(R.id.right);
        uri = getIntent().getParcelableArrayListExtra("URI");
        position = getIntent().getIntExtra("Touch", 0);
        total = uri.size() - 1;

        i = (PhotoView) findViewById(R.id.i);
        i.setImageBitmap(getBitmap());
    }

    private Bitmap getBitmap(){
        Bitmap bm = null;
        try {
            bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri.get(position));
        }catch (Exception e){
            e.printStackTrace();
        }
        return bm;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
