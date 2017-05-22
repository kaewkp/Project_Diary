package com.example.kaew_pc.diary_project.Activity.Note;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kaew_pc.diary_project.Activity.ChangeColor;
import com.example.kaew_pc.diary_project.Manager.UserPicture;
import com.example.kaew_pc.diary_project.Manager.Database.DBHelper;
import com.example.kaew_pc.diary_project.Manager.Database.Note_data;
import com.example.kaew_pc.diary_project.R;
import com.example.kaew_pc.diary_project.Manager.Repository.NoteDataRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.widget.Toast;

/**
 * Created by Ekachart-PC on 23/3/2560.
 */

public class NoteCreatePageActivity extends AppCompatActivity {

    static final int dialogID = 0;
    private TextView dateCreate, dateAlert;

    private DBHelper db;
    private NoteDataRepository repo;
    private String formattedDate;
    private EditText title, desc;
    private Boolean isEdit = false;
    private Note_data data = new Note_data();
    private ImageView img;
//    private ImageButton white, yellow, green, pink, violet, blue;
    private String dateChoose;
    private AlertDialog.Builder builder, timealertbuilder;
    private String[] timealert;

    private int y, d, m;

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
//        initAlertDialog();
        showCalendar();

        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);
        action.setHomeButtonEnabled(true);

        int id = getIntent().getIntExtra("id", 0);

        if (id != 0) { //When click from listview
            data = repo.getDataById(String.valueOf(id), db.getReadableDatabase());
            title.setText(data.getNote_title());
            desc.setText(data.getNote_desc());
            dateCreate.setText(data.getNote_dateCreate());
            dateAlert.setText(data.getNote_dateAlert());
            isEdit = true;
        }

        ImageButton buttonIntent = (ImageButton) findViewById(R.id.picButton);
        buttonIntent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent
                        , "Select Picture"), SELECT_SINGLE_PICTURE);
            }
        });


        final String titleStr = title.getText().toString();
        final String descStr = desc.getText().toString();

        Button cancel = (Button) findViewById(R.id.cancelButton);
        Button save = (Button) findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((titleStr != null && !titleStr.isEmpty()) || (descStr != null && !descStr.isEmpty())){
                    saveNote();
                    Toast.makeText(NoteCreatePageActivity.this, "บันทึกแล้ว ",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(NoteCreatePageActivity.this, " ใส่ชื่อบันทึกและรายละเอียด ", Toast.LENGTH_SHORT).show();

                }
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmCancel();
            }
        });

    }

    private void confirmCancel(){
        final AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("บันทึก");
        adb.setMessage("คุณต้องการบันทึกหรือไม่ ?");
        adb.setNegativeButton("ไม่", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                finish();
            }

        });
        adb.setPositiveButton("บันทึก", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                saveNote();
                Toast.makeText(NoteCreatePageActivity.this, "บันทึกแล้ว ",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });
        adb.setNeutralButton("ยกเลิก", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });
        adb.show();
    }

// private void initAlertDialog() {
//        timealertbuilder = new AlertDialog.Builder(์NoteCreatePageActivity.this);
//        timealertbuilder.setTitle("ตั้งค่าการแจ้งเตือน");
//        timealertbuilder.setSingleChoiceItems(timealert, 0, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(), "คุณเลือก " +
//                        timealert[which], Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        timealertbuilder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        timealertbuilder.create();
//    }


    private void saveNote() {
        data.setNote_dateCreate(formattedDate);
        data.setNote_dateAlert(formattedDate);
        data.setNote_title(title.getText().toString());
        data.setNote_desc(desc.getText().toString());

        if(!isEdit)
            repo.insertData(db.getWritableDatabase(), data);
        else
            repo.updateData(db.getWritableDatabase(), data);
    }

    private void init() {
        dateCreate = (TextView) findViewById(R.id.showdate);
        dateAlert = (TextView) findViewById(R.id.dateAlert);

        title = (EditText) findViewById(R.id.title);
        desc = (EditText) findViewById(R.id.desc);
        Date time = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        formattedDate = df.format(time);

        android.util.Log.i("Time Class ", " Time value in milliseconds "+time.getYear());
        dateCreate.setText(formattedDate);


        img = (ImageView)findViewById(R.id.picShow);

        db = DBHelper.getInstance(this);
        repo = new NoteDataRepository();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_SINGLE_PICTURE) {

                Uri selectedImageUri = data.getData();
                try {
                    img.setImageBitmap(new UserPicture(selectedImageUri, getContentResolver()).getBitmap());
                } catch (Exception e) {
                    Log.e(NoteCreatePageActivity.class.getSimpleName(), "Failed to load image", e);
                }
                // original code
//                String selectedImagePath = getPath(selectedImageUri);
//                selectedImagePreview.setImageURI(selectedImageUri);
            }
        } else {
            // report failure
            Toast.makeText(getApplicationContext(), "Fail to get intent data", Toast.LENGTH_LONG).show();
            Log.d(NoteCreatePageActivity.class.getSimpleName(), "Failed to get intent data, result code is " + resultCode);
        }
    }

    public String getPath(Uri uri) {

        // just some safety built in
        if( uri == null ) {
            // perform some logging or show user feedback
            Toast.makeText(getApplicationContext(), "Fail to get picture", Toast.LENGTH_LONG).show();
            Log.d(NoteCreatePageActivity.class.getSimpleName(), "Failed to parse image path from image URI " + uri);
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
            case R.id.action_color:
                intent = new Intent(getApplicationContext(), ChangeColor.class);
                startActivity(intent);
                finish();

            case R.id.action_alert:

            case android.R.id.home:
                confirmCancel();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_notecreatepage, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        confirmCancel();
    }

    //    show calendar
    private void showCalendar() {
        final Calendar ca = Calendar.getInstance();
        y = ca.get(Calendar.YEAR);
        m = ca.get(Calendar.MONTH);
        d = ca.get(Calendar.DAY_OF_MONTH);

        showDialog(dialogID);
        Button calendarButton = (Button) findViewById(R.id.dateButton);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialogID);
            }
        });
        dateAlert.setVisibility(View.INVISIBLE);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == dialogID) {
            return new DatePickerDialog(this, /*android.R.style.Theme_Holo_Dialog*/dpicklistener, y, m, d);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpicklistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            y = year;
            m = monthOfYear + 1; //Month start from 0
            d = dayOfMonth;
            dateChoose = String.valueOf(d) + " / " + String.valueOf(m) + " / " + String.valueOf(y);
            dateAlert.setVisibility(View.VISIBLE);
            dateAlert.setText("วันที่เลือก : " + dateChoose);
        }
    };

}
