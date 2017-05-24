package com.example.kaew_pc.diary_project.Activity.Note;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

    private TextView date;
    private DBHelper db;
    private NoteDataRepository repo;
    private NoteImageRepository imageRepo;
    private String formattedDate;
    private EditText title, desc;
    private Boolean isEdit = false;
    private Note_data data = new Note_data();
    private ImageView img;

    private List<Uri> uri;
    private ArrayList<Note_image> imagelist;


    // this is the action code we use in our intent,
    // this way we know we're looking at the response from our own action
    private static final int SELECT_SINGLE_PICTURE = 101;

    private static final int SELECT_MULTIPLE_PICTURE = 201;

    public static final String IMAGE_TYPE = "image/*";


////////////////////////////////////////////////////////////////////////

    private static final int LOUVRE_REQUEST_CODE = 0;

    private PickerImageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notecreatepage);
        init();
        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);
        action.setHomeButtonEnabled(true);

        int id = getIntent().getIntExtra("id", 0);

        if (id != 0) { //When click from listview
            data = repo.getDataById(String.valueOf(id), db.getReadableDatabase());
            title.setText(data.getNote_title());
            desc.setText(data.getNote_desc());
            date.setText(data.getNote_editdate());
            imagelist = imageRepo.getDataById(db.getReadableDatabase(), id);

            Toast.makeText(NoteCreatePageActivity.this, ""+imagelist.size(), Toast.LENGTH_SHORT).show();
            isEdit = true;
        }

        //RecycleView
        final int spacing = getResources().getDimensionPixelSize(R.dimen.gallery_item_offset);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(spacing));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter = new PickerImageAdapter(NoteCreatePageActivity.this, recyclerView));

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
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                // this line is different here !!
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_MULTIPLE_PICTURE);

                Louvre l = Louvre.init(NoteCreatePageActivity.this)
                        .setRequestCode(LOUVRE_REQUEST_CODE)
                        .setMaxSelection(100)
                        .setMediaTypeFilter(Louvre.IMAGE_TYPE_BMP, Louvre.IMAGE_TYPE_JPEG, Louvre.IMAGE_TYPE_PNG);
                l.open();
            }
        });

        Button cancel = (Button) findViewById(R.id.cancelButton);
        Button save = (Button) findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
                Toast.makeText(NoteCreatePageActivity.this, "บันทึกแล้ว ",
                        Toast.LENGTH_LONG).show();
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
                        Toast.LENGTH_SHORT).show();
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
        int id = repo.getLatestId(db.getReadableDatabase());

        if(!isEdit) { //Create
            data.setNote_savedate(formattedDate);
            data.setNote_editdate(formattedDate);
            repo.insertData(db.getWritableDatabase(), data);
            for(int i=0;i<uri.size();i++) {
                imageRepo.insertData(db.getWritableDatabase(), new Note_image(id, getBytes(uri.get(i))));
            }
        }
        else { //Edit
            id = data.getNote_id();
            data.setNote_editdate(formattedDate);
            repo.updateData(db.getWritableDatabase(), data);
            for(int i=0;i<uri.size();i++) {
                imageRepo.insertData(db.getWritableDatabase(), new Note_image(id, getBytes(uri.get(i))));
            }
        }
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
        repo = new NoteDataRepository();
        imageRepo = new NoteImageRepository();
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            if (requestCode == SELECT_SINGLE_PICTURE) {
//
//                Uri selectedImageUri = data.getData();
//                try {
//                    img.setImageBitmap(new UserPicture(selectedImageUri, getContentResolver()).getBitmap());
//                } catch (Exception e) {
//                    Log.e(NoteCreatePageActivity.class.getSimpleName(), "Failed to load image", e);
//                }
//                // original code
////                String selectedImagePath = getPath(selectedImageUri);
////                selectedImagePreview.setImageURI(selectedImageUri);
//            }
//        } else {
//            // report failure
//            Toast.makeText(getApplicationContext(), "Fail to get intent data", Toast.LENGTH_LONG).show();
//            Log.d(NoteCreatePageActivity.class.getSimpleName(), "Failed to get intent data, result code is " + resultCode);
//        }
//    }

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
}
