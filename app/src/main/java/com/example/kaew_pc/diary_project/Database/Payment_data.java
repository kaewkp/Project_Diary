package com.example.kaew_pc.diary_project.Database;

import android.widget.ListView;
import android.widget.TextView;
import com.example.kaew_pc.diary_project.R;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by KAEW-PC on 18-Feb-17.
 */

public class Payment_data {

    public static final String DATABASE_NAME = "Payment_data.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "Payment_data";

    public class Column{
        public static final String Payment_id = "Payment_id";
        public static final String Payment_title = "Payment_title";
        public static final String Payment_price = "Payment_price";
        public static final String Payment_endDate = "Payment_endDate";
        public static final String Payment_date = "Payment_date";
        public static final String PayType_id = "PayType_id";
        public static final String PayStatus_id = "PayStatus_id";
        public static final String Noti_id = "Noti_id";
    }

    private String Payment_id ;
    private String Payment_title;
    private String Payment_price;
    private String Payment_date;
    private String Payment_endDate;
    private String PayType_id;
    private String PayStatus_id;
    private String Noti_id;

    //Default Constructor
    public Payment_data(){
    }

    //Constructor
    public Payment_data(String Payment_id, String Payment_title, String Payment_price, String Payment_date, String Payment_endDate,
                        String PayType_id, String PayStatus_id, String Noti_id){
        this.Payment_id = Payment_id;
        this.Payment_title = Payment_title;
        this.Payment_price = Payment_price;
        this.Payment_date = Payment_date;
        this.Payment_endDate = Payment_endDate;
        this.PayType_id = PayType_id;
        this.PayStatus_id = PayStatus_id;
        this.Noti_id = Noti_id;
    }//Getter, Setter

    public String getPayment_id(){
        return Payment_id;
    }

    public String getPayment_title() {
        return Payment_title;
    }

    public String getPayment_price() {
        return Payment_price;
    }

    public String getPayment_date(){ return Payment_date; }

    public String getPayment_endDate(){ return Payment_endDate; }

    public String getPayType_id(){ return PayType_id; }

    public String getPayStatus_id(){ return PayStatus_id; }

    public String getNoti_id(){
        return Noti_id;
    }



    public void setPayment_id(String Payment_id){
        this.Payment_id = Payment_id;
    }

    public void setPayment_title( String Payment_title) {
        this.Payment_title = Payment_title;
    }

    public void setPayment_price(String Payment_price) {
        this.Payment_price = Payment_price;
    }

    public void setPayment_date(String Payment_date) {
        this.Payment_date = Payment_date;
    }

    public void setPayment_endDate(String Payment_endDate) {
        this.Payment_endDate = Payment_endDate;
    }

    public void setPayType_id(String PayType_id) {
        this.PayType_id = PayType_id;
    }

    public void setPayStatus_id(String PayStatus_id) {
        this.PayStatus_id = PayStatus_id;
    }

    public void setNoti_id(String Noti_id){
        this.Noti_id = Noti_id;
    }
}