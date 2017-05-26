package com.example.kaew_pc.diary_project.Activity.Note;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kaew_pc.diary_project.Activity.Payment.PaymentActivity;
import com.example.kaew_pc.diary_project.Manager.MyReceiver;
import com.example.kaew_pc.diary_project.Manager.NoteReceiver;
import com.example.kaew_pc.diary_project.Manager.UserPicture;
import com.andremion.louvre.Louvre;
import com.andremion.louvre.home.GalleryActivity;
import com.andremion.louvre.util.ItemOffsetDecoration;
import com.example.kaew_pc.diary_project.Manager.Adapter.PickerImageAdapter;
import com.example.kaew_pc.diary_project.Manager.Database.DBHelper;
import com.example.kaew_pc.diary_project.Manager.Database.Note_data;
import com.example.kaew_pc.diary_project.Manager.Database.Note_image;
import com.example.kaew_pc.diary_project.Manager.Repository.NoteImageRepository;
import com.example.kaew_pc.diary_project.R;
import com.example.kaew_pc.diary_project.Manager.Repository.NoteDataRepository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.widget.Toast;

/**
 * Created by Ekachart-PC on 23/3/2560.
 */

public class NoteCreatePageActivity extends AppCompatActivity {

    static final int dialogID = 0;
    private TextView dateCreate, dateAlert;

    private DBHelper db;
    private NoteDataRepository repo;
    private NoteImageRepository imageRepo;
    private String formattedDate;
    private EditText title, desc;
    private Boolean isEdit = false;
    private Note_data data = new Note_data();
    private ImageView img;
//    private ImageButton white, yellow, green, pink, violet, blue;
    private String dateChoose;
    private AlertDialog.Builder builder, timealertbuilder;
    private String[] timealert;
    private List<Uri> uri;
    private ArrayList<Uri> imagelist = null;

    private static final int LOUVRE_REQUEST_CODE = 0;
    private PickerImageAdapter mAdapter;

    private static final String[] EXTENSIONS = new String[]{
            "png", "bmp", "jpg" // and other formats you need
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notecreatepage);
        init();
//        initAlertDialog();
//        showCalendar();

        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);
        action.setHomeButtonEnabled(true);


        //RecycleView
        final int spacing = getResources().getDimensionPixelSize(R.dimen.gallery_item_offset);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(spacing));
        recyclerView.setHasFixedSize(true);

        int id = getIntent().getIntExtra("id", 0);
        if (id != 0) { //When click from listview
            data = repo.getDataById(String.valueOf(id), db.getReadableDatabase());
            title.setText(data.getNote_title());
            desc.setText(data.getNote_desc());
            isEdit = true;
            getUriInFolder(id);
        }

        mAdapter = new PickerImageAdapter(NoteCreatePageActivity.this, recyclerView, imagelist);
        recyclerView.setAdapter(mAdapter);

        recyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                int size = getResources().getDimensionPixelSize(R.dimen.gallery_item_size);
                int width = recyclerView.getMeasuredWidth();
                int columnCount = width / (size + spacing);
                recyclerView.setLayoutManager(new GridLayoutManager(NoteCreatePageActivity.this, columnCount));

                uri = mAdapter.getAllURI();
                return false;
            }
        });

        ImageButton pic = (ImageButton)findViewById(R.id.picButton);
        pic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Louvre l = Louvre.init(NoteCreatePageActivity.this)
                        .setRequestCode(LOUVRE_REQUEST_CODE)
                        .setMaxSelection(100) //insert only 100 pic for 1 note
                        .setMediaTypeFilter(Louvre.IMAGE_TYPE_BMP, Louvre.IMAGE_TYPE_JPEG, Louvre.IMAGE_TYPE_PNG);
                l.open();
            }
        });

        Button cancel = (Button) findViewById(R.id.cancelButton);
        Button save = (Button) findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((title != null && !title.getText().toString().equals("")) && (desc != null && !desc.getText().toString().equals(""))){
                    saveNote();
                    Toast.makeText(NoteCreatePageActivity.this, "บันทึกแล้ว ", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(NoteCreatePageActivity.this, " ใส่ชื่อบันทึกและรายละเอียด ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmCancel();
            }
        });
    }

    static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {
        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };

    private void getUriInFolder(int id) {
        File f = new File(android.os.Environment.getExternalStorageDirectory(), File.separator+"TAMUTAMI/Note/"+id);
        if(f.exists()) {
            File[] filelist = f.listFiles(IMAGE_FILTER);
            imagelist = new ArrayList<>();
            for(File file : filelist){
                imagelist.add(Uri.fromFile(file));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOUVRE_REQUEST_CODE && resultCode == RESULT_OK) {
            mAdapter.swapData(GalleryActivity.getSelection(data));
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    private void saveNote() {
        data.setNote_title(title.getText().toString());
        data.setNote_desc(desc.getText().toString());
        int id = repo.getLatestId(db.getReadableDatabase()) + 1;

        if(!isEdit) { //Create
            data.setNote_savedate(dateCreate.getText().toString());
            data.setNote_editdate(dateCreate.getText().toString());
            repo.insertData(db.getWritableDatabase(), data);
        }
        else { //Edit
            id = data.getNote_id();
            data.setNote_editdate(dateCreate.getText().toString());
            repo.updateData(db.getWritableDatabase(), data);
        }

        if(uri.size() > 0)
            Log.e("ID" , ""+id);
            saveImage(id);
    }

    private void saveImage(int id) {
        File f = new File(android.os.Environment.getExternalStorageDirectory(), File.separator+"TAMUTAMI/Note/"+id);
        if(!f.exists()) {
            f.mkdirs();
        }

        try {
            for (int i = 0; i < uri.size(); i++) {
                f = new File(android.os.Environment.getExternalStorageDirectory(), File.separator+"TAMUTAMI/Note/" + id +"/"+ i + ".jpg");
                copyFile(new File(getPath(uri.get(i))), f);
            }
        }
        catch (Exception ex){ ex.printStackTrace(); }

    }

    private void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }

        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (source != null) {
            destination.transferFrom(source, 0, source.size());
            source.close();
        }
        destination.close();
    }

    public String getPath(Uri uri) {
        Cursor cursor = null;
        try {
            String[] projection = { MediaStore.Images.Media.DATA };
//            cursor = this.getContentResolver().query(uri, projection, null, null, null);
            uri = convertFileToContentUri(this, new File(uri.getPath()));
            CursorLoader loader = new CursorLoader(NoteCreatePageActivity.this, uri, projection, null, null, null);
            cursor = loader.loadInBackground();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /**
     * Converts a file to a content uri, by inserting it into the media store.
     * Requires this permission: <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
     */
    protected static Uri convertFileToContentUri(Context context, File file) {

        //Uri localImageUri = Uri.fromFile(localImageFile); // Not suitable as it's not a content Uri

        ContentResolver cr = context.getContentResolver();
        String imagePath = file.getAbsolutePath();
        String imageName = null;
        String imageDescription = null;
        String uriString = null;
        try {
            uriString = MediaStore.Images.Media.insertImage(cr, imagePath, imageName, imageDescription);
        }catch (Exception ex){}
        return Uri.parse(uriString);
    }

    private Bitmap getBitmapFromUri(Uri uri){
        Bitmap bitmap = null;
        try {
            InputStream is = getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(is);
            if(is != null)
                is.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return bitmap;
    }

    // convert from bitmap to byte array
    public byte[] getBytes(Uri uri) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    private void init() {
        dateCreate = (TextView) findViewById(R.id.showdate);
//        dateAlert = (TextView) findViewById(R.id.dateAlert);

        title = (EditText) findViewById(R.id.title);
        desc = (EditText) findViewById(R.id.desc);
        Date time = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        formattedDate = df.format(time);

        android.util.Log.i("Time Class ", " Time value in milliseconds "+time.getYear());
        dateCreate.setText(formattedDate);


//        img = (ImageView)findViewById(R.id.picShow);

        db = DBHelper.getInstance(this);
        repo = new NoteDataRepository();
        imageRepo = new NoteImageRepository();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        Intent intent;
        switch (item.getItemId()) {
//            case R.id.action_color:
//                intent = new Intent(getApplicationContext(), ChangeColor.class);
//                startActivity(intent);
//                finish();

            case R.id.action_alert:

            case android.R.id.home:
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

    public  void printLog(String tag, String detail){
        Log.w(tag, detail);
    }

    public  void printLToast(String detail, int length){
        Toast.makeText(this, detail, length).show();
    }

    //    show calendar
//    private void showCalendar() {
//        final Calendar ca = Calendar.getInstance();
////        y = ca.get(Calendar.YEAR);
////        m = ca.get(Calendar.MONTH);
////        d = ca.get(Calendar.DAY_OF_MONTH);
//
//        showDialog(dialogID);
//        Button calendarButton = (Button) findViewById(R.id.dateButton);
//        calendarButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDialog(dialogID);
//            }
//        });
//        dateAlert.setVisibility(View.INVISIBLE);
//    }

//    @Override
//    protected Dialog onCreateDialog(int id) {
//        if (id == dialogID) {
//            return new DatePickerDialog(this, /*android.R.style.Theme_Holo_Dialog*/dpicklistener, y, m, d);
//        }
//        return null;
//    }

//    private DatePickerDialog.OnDateSetListener dpicklistener = new DatePickerDialog.OnDateSetListener() {
//        @Override
//        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//            y = year;
//            m = monthOfYear + 1; //Month start from 0
//            d = dayOfMonth;
//            dateChoose = String.valueOf(d) + " / " + String.valueOf(m) + " / " + String.valueOf(y);
//            dateAlert.setVisibility(View.VISIBLE);
//            dateAlert.setText("วันที่เลือก : " + dateChoose);
//        }
//    };
}
