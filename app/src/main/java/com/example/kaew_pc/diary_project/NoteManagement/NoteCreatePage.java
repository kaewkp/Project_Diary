package com.example.kaew_pc.diary_project.NoteManagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kaew_pc.diary_project.Database.DBHelper;
import com.example.kaew_pc.diary_project.Database.Note_data;
import com.example.kaew_pc.diary_project.R;
import com.example.kaew_pc.diary_project.UserPicture;
import com.example.kaew_pc.diary_project.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.provider.MediaStore.Images.Media;
import android.widget.Toast;

import static android.R.attr.bitmap;
import static com.example.kaew_pc.diary_project.R.id.imageView;
import static com.example.kaew_pc.diary_project.main.REQUEST_GALLERY;

/**
 * Created by Ekachart-PC on 23/3/2560.
 */

public class NoteCreatePage extends AppCompatActivity {

    private TextView date;
    private DBHelper db;
    private String formattedDate;
    private EditText title, desc;
    private Boolean isEdit = false;
    private Note_data data = new Note_data();
    private ImageView img;


    // this is the action code we use in our intent,
    // this way we know we're looking at the response from our own action
    private static final int SELECT_SINGLE_PICTURE = 101;

    private static final int SELECT_MULTIPLE_PICTURE = 201;

    public static final String IMAGE_TYPE = "image/*";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notecreatepage);
        init();
        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);
        action.setHomeButtonEnabled(true);

        int id = getIntent().getIntExtra("id", 0);

        if(id != 0){ //When click from listview
            data = db.getNoteById(String.valueOf(id));
            title.setText(data.getNote_title());
            desc.setText(data.getNote_desc());
            date.setText(data.getNote_date());
            isEdit = true;
        }

        ImageButton buttonIntent = (ImageButton)findViewById(R.id.picButton);
        buttonIntent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent
                        , "Select Picture"), SELECT_SINGLE_PICTURE);
            }
        });

        Button cancel = (Button)findViewById(R.id.cancelButton);
        Button save = (Button)findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveNote();

                Intent i = new Intent(getApplicationContext(), NoteMainPage.class);
                startActivity(i);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NoteMainPage.class);
                startActivity(i);
                finish();
            }
        });
    }

//    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//            switch (which){
//                case DialogInterface.BUTTON_POSITIVE:
//                    //Yes button clicked
//                    break;
//
//                case DialogInterface.BUTTON_NEGATIVE:
//                    //No button clicked
//                    break;
//            }
//        }
//    };
//
//    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//    builder.setMessage("คุณยังไม่ได้ทำการบันทึก คุณแน่ใจหรือว่าต้องการละทิ้งการเปลี่ยนแปลงนี้").setPositiveButton("Yes", dialogClickListener)
//    .setNegativeButton("No", dialogClickListener).show();


    private void saveNote() {
//        Note_data note = new Note_data();

        data.setNote_date(formattedDate);
        data.setNote_title(title.getText().toString());
        data.setNote_desc(desc.getText().toString());

        if(!isEdit)
            db.createNote(db.getWritableDatabase(), data);
        else
            db.updateNote(db.getWritableDatabase(), data);
    }

    private void init() {
        date = (TextView) findViewById(R.id.showdate);
        title = (EditText) findViewById(R.id.title);
        desc = (EditText) findViewById(R.id.desc);
        Date time = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        formattedDate = df.format(time);

        android.util.Log.i("Time Class ", " Time value in milliseconds "+time.getYear());
        date.setText(formattedDate);
        img = (ImageView)findViewById(R.id.picShow);

        db = DBHelper.getInstance(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_SINGLE_PICTURE) {

                Uri selectedImageUri = data.getData();
                try {
                    img.setImageBitmap(new UserPicture(selectedImageUri, getContentResolver()).getBitmap());
                } catch (IOException e) {
                    Log.e(NoteCreatePage.class.getSimpleName(), "Failed to load image", e);
                }
                // original code
//                String selectedImagePath = getPath(selectedImageUri);
//                selectedImagePreview.setImageURI(selectedImageUri);
            }
        } else {
            // report failure
            Toast.makeText(getApplicationContext(), "Fail to get intent data", Toast.LENGTH_LONG).show();
            Log.d(NoteCreatePage.class.getSimpleName(), "Failed to get intent data, result code is " + resultCode);
        }
    }

    public String getPath(Uri uri) {

        // just some safety built in
        if( uri == null ) {
            // perform some logging or show user feedback
            Toast.makeText(getApplicationContext(), "Fail to get picture", Toast.LENGTH_LONG).show();
            Log.d(NoteCreatePage.class.getSimpleName(), "Failed to parse image path from image URI " + uri);
            return null;
        }

        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here, thanks to the answer from @mad indicating this is needed for
        // working code based on images selected using other file managers
        return uri.getPath();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        Intent intent;
        switch (item.getItemId()) {

            case android.R.id.home:
//                Toast.makeText(getApplicationContext(), "Here",
//                        Toast.LENGTH_LONG).show();
                intent = new Intent(getApplicationContext(), NoteMainPage.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }


    }
}
