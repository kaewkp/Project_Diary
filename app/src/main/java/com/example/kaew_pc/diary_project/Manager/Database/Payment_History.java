package com.example.kaew_pc.diary_project.Manager.Database;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by chommchome on 8/6/2560.
 */

public class Payment_History {
    public static final String DATABASE_NAME = "Payment_data.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "Payment_History";

    public Payment_History() {

    }

    public class Column{
        public static final String History_id = "History_id";
        public static final String History_title = "History_title";
        public static final String History_desc = "History_desc";
        public static final String History_price = "History_price";
        public static final String History_endDate = "History_endDate";
        public static final String History_date = "History_date";
        public static final String History_finish = "History_finish";
//        public static final String PayType_id = "PayType_id";
//        public static final String BankName_id = "BankName_id";
//        public static final String DebtTime_id = "DebtTime_id";
//        public static final String PayStatus_id = "PayStatus_id";
//        public static final String Noti_id = "Noti_id";
    }

    private int History_id ;
    private String History_title;
    private String History_desc;
    private String History_price;
    private String History_date;
    private String History_endDate;
    private String History_finish;
//    private String PayType_id;
//    private String BankName_id;
//    private String DebtTime_id;
//    private String PayStatus_id;
//    private String Noti_id;

    private static String paymentIdFromClicked;

//    //Default Constructor
//    public Payment_History(String string, String cursorString, String history_desc, String s, String history_date, String history_endDate, String history_finish){
//    }

    //Constructor
    public Payment_History(int History_id, String History_title, String History_desc, String History_price, String History_date, String History_endDate,
                           String History_finish){
        this.History_id = History_id;
        this.History_title = History_title;
        this.History_desc = History_desc;
        this.History_price = History_price;
        this.History_date = History_date;
        this.History_endDate = History_endDate;
        this.History_finish = History_finish;
//        this.PayType_id = PayType_id;
//        this.BankName_id = BankName_id;
//        this.DebtTime_id = DebtTime_id;
//        this.PayStatus_id = PayStatus_id;
//        this.Noti_id = Noti_id;
    }//Getter, Setter

    public int getHistory_id(){
        return History_id;
    }

    public static void setPaymentIdFromClicked(ArrayList<Payment_data> data, int position){
        paymentIdFromClicked = String.valueOf(data.get(position).getPayment_id());
        Log.d("55555","5555");
    }

    public static String getPaymentIdFromClicked() {
        return paymentIdFromClicked;
    }

    public String getHistory_title() {
        return History_title;
    }

    public String getHistory_desc() {
        return History_desc;
    }

    public String getHistory_price() {
        return History_price;
    }

    public String getHistory_date(){ return History_date; }

    public String getHistory_endDate(){ return History_endDate; }

    public String getHistory_finish(){ return History_finish; }

//    public String getPayType_id(){ return PayType_id; }
//
//    public String getBankName_id(){ return BankName_id; }
//
//    public String getDebtTime_id(){ return DebtTime_id; }
//
//    public String getPayStatus_id(){ return PayStatus_id; }
//
//    public String getNoti_id(){
//        return Noti_id;
//    }



    public void setHistory_id(int History_id){
        this.History_id = History_id;
    }

    public void setHistory_title( String History_title) {
        this.History_title = History_title;
    }

    public void setHistory_desc( String History_desc) {
        this.History_desc = History_desc;
    }

    public void setHistory_price(String History_price) {
        this.History_price = History_price;
    }

    public void setHistory_date(String History_date) {
        this.History_date = History_date;
    }

    public void setHistory_endDate(String History_endDate) {
        this.History_endDate = History_endDate;
    }

    public void setHistory_finish(String History_finish) {
        this.History_finish = History_finish;
    }

//    public void setPayType_id(String PayType_id) {
//        this.PayType_id = PayType_id;
//    }
//
//    public void setBankName_id(String BankName_id) {
//        this.BankName_id = BankName_id;
//    }
//
//    public void setDebtTime_id(String DebtTime_id) {
//        this.DebtTime_id = DebtTime_id;
//    }
//
//    public void setPayStatus_id(String PayStatus_id) {
//        this.PayStatus_id = PayStatus_id;
//    }
//
//    public void setNoti_id(String Noti_id){
//        this.Noti_id = Noti_id;
//    }
}
