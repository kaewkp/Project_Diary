package com.example.kaew_pc.diary_project.PaymentManagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaew_pc.diary_project.Database.DBHelper;
import com.example.kaew_pc.diary_project.Database.Payment_data;
import com.example.kaew_pc.diary_project.NoteManagement.NoteCreatePage;
import com.example.kaew_pc.diary_project.R;
import com.example.kaew_pc.diary_project.Repository.PaymentDataRepo;
import com.example.kaew_pc.diary_project.main;

import java.util.ArrayList;

import static com.example.kaew_pc.diary_project.Database.Password.Column.id;

/**
 * Created by chommchome on 27/3/2560.
 */

public class PaymentMainPage extends AppCompatActivity {

    private FloatingActionButton fab;
    private AlertDialog.Builder builder, sortdialog;
    private String[] sortlist;
    private DBHelper db;
    private ListView list;
    private Boolean isResume = false;

    private PaymentDataRepo paymentObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_main);
        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);
        init();
        loadPaymentList();
        initSortDialog();

    }


    private void init() {
        db = DBHelper.getInstance(this);
        paymentObj = new PaymentDataRepo();
        list = (ListView) findViewById(R.id.paymentlist);

        fab = (FloatingActionButton) findViewById(R.id.fabpayment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isResume = true;
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                startActivity(intent);
            }
        });

        sortlist = getResources().getStringArray(R.array.เรียงลำดับ);
    }


    private void initDialog(final String[] text, String head, final TextView tv) {
        builder = new AlertDialog.Builder(PaymentMainPage.this);
        builder.setTitle(head);
        builder.setSingleChoiceItems(text, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "คุณเลือก " +
                        text[which], Toast.LENGTH_SHORT).show();
                tv.setVisibility(View.VISIBLE);
                tv.setText(text[which]);
            }
        });
        builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("ยกเลิก", null);
        builder.create();
    }


    private void initSortDialog() {
        sortdialog = new AlertDialog.Builder(PaymentMainPage.this);
        sortdialog.setTitle("เรียงโดย");
        sortdialog.setSingleChoiceItems(sortlist, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "คุณเลือก " +
                        sortlist[which], Toast.LENGTH_SHORT).show();

            }
        });
        sortdialog.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        sortdialog.create();
    }



    private void loadPaymentList() {
        final ArrayList<Payment_data> data = paymentObj != null ?
                        paymentObj.getData(db.getReadableDatabase()) :
                        new PaymentDataRepo().getData(db.getReadableDatabase());

        PaymentCustomAdapter adapter = new PaymentCustomAdapter(PaymentMainPage.this, data);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isResume = true;
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                intent.putExtra("id", data.get(position).getPayment_id());
                startActivity(intent);
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        if(isResume)
            loadPaymentList();
        isResume = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manu_page_payment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            Intent intent;
            if (item.getItemId() == R.id.action_sortNew) {
                sortdialog.show();
//
                    paymentObj.getDataByIdOrderByNew(db.getReadableDatabase(), String.valueOf(id));
                    return true;

            } else if (item.getItemId() == R.id.action_add) {
                return true;
            }

            if (item.getItemId() == android.R.id.home) {
                intent = new Intent(getApplicationContext(), main.class);
                startActivity(intent);
                finish();
            }

            return super.onOptionsItemSelected(item);
        }

    @Override
    public void onBackPressed() {
        finish();
    }

}


