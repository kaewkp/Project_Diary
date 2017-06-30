package com.example.kaew_pc.diary_project.Activity.Payment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaew_pc.diary_project.Activity.MainActivity;
import com.example.kaew_pc.diary_project.Manager.Database.DBHelper;
import com.example.kaew_pc.diary_project.Manager.Database.Payment_data;
import com.example.kaew_pc.diary_project.Manager.Adapter.PaymentCustomAdapter;
import com.example.kaew_pc.diary_project.R;
import com.example.kaew_pc.diary_project.Manager.Repository.PaymentDataRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

/**
 * Created by chommchome on 27/3/2560.
 */

public class PaymentMainPageActivity extends AppCompatActivity {

    private FloatingActionButton fab, fab2;
    private AlertDialog.Builder builder, sortdialog;
    private String[] sortlist;
    private DBHelper db;
    private ListView listpayment;
    private Boolean isResume = false;
    public final Calendar cal = Calendar.getInstance();
    private HashSet<Integer> del = new HashSet<>();
    private PaymentCustomAdapter adapter;
    private ArrayList<Payment_data> data;

    private PaymentDataRepository paymentObj;

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
        paymentObj = new PaymentDataRepository();
        listpayment = (ListView) findViewById(R.id.paymentlist);

        fab = (FloatingActionButton) findViewById(R.id.fabpayment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isResume = true;
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                startActivity(intent);
            }
        });

        fab2 = (FloatingActionButton) findViewById(R.id.fabRemove);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Adapter", "Hash : "+ del);
                deleteDialog();
            }
        });

        sortlist = getResources().getStringArray(R.array.Sort);
    }

    private void deleteDialog(){
        final android.app.AlertDialog.Builder exitDialog = new android.app.AlertDialog.Builder(this);
        exitDialog.setTitle("ยืนยันการลบ");
        exitDialog.setPositiveButton("ไม่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        exitDialog.setNegativeButton("ใช่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                for ( int id : del ) {
                    paymentObj.deleteData(db.getWritableDatabase(), id);
                }
                fab2.setVisibility(View.GONE);
                loadPaymentList();
            }
        }).show();
    }


    private void initDialog(final String[] text, String head, final TextView tv) {
        builder = new AlertDialog.Builder(PaymentMainPageActivity.this);
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
        sortdialog = new AlertDialog.Builder(PaymentMainPageActivity.this);
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
        data = paymentObj != null ? paymentObj.getData(db.getReadableDatabase()) :
                        new PaymentDataRepository().getData(db.getReadableDatabase());

        adapter = new PaymentCustomAdapter(PaymentMainPageActivity.this, data);
        listpayment.setAdapter(adapter);
//        listpayment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                isResume = true;
//                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
//                intent.putExtra("id", data.get(position).getPayment_id());
//                Payment_data.setPaymentIdFromClicked(data, position);
//                startActivity(intent);
//            }
//        });

        listpayment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(fab2.getVisibility() == View.GONE) {
                    isResume = true;
                    Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                    intent.putExtra("id", data.get(position).getPayment_id());
                    startActivity(intent);
                }
                else {
                    CheckBox cb = (CheckBox) view.findViewById(R.id.checkbox);
                    if (cb.isChecked()) {
                        cb.setChecked(false);
                        del.remove(data.get(position).getPayment_id());
                    } else {
                        cb.setChecked(true);
                        del.add(data.get(position).getPayment_id());
                    }
                }
            }
        });

        listpayment.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                if(fab2.getVisibility() == View.GONE) {
                    fab2.setVisibility(View.VISIBLE);
                    adapter.toggleCheckbox(true);
                }
                else {
                    fab2.setVisibility(View.GONE);
                    adapter.toggleCheckbox(false);
                    del.clear();
                }
                return true;
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
//            if (item.getItemId() == R.id.action_history) {
//                intent = new Intent(getApplicationContext(), PaymentHistoryActivity.class);
//                startActivity(intent);
//            }
//        if(item.getItemId() == R.id.empty){
//            paymentObj.deleteData(db.getWritableDatabase());
//            loadPaymentList();
//            return true;
//        }

            if (item.getItemId() == android.R.id.home) {
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }

            return super.onOptionsItemSelected(item);
        }

    @Override
    public void onBackPressed() {
        if(fab2.getVisibility() == View.GONE) {
            finish();
        }
        else {
            fab2.setVisibility(View.GONE);
            adapter.toggleCheckbox(false);
            del.clear();
        }
    }

}


