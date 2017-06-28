package com.example.kaew_pc.diary_project.Manager.Database;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by KAEW-PC on 18-Feb-17.
 */

public class Payment_data {
    public static final String TABLE = "Payment_data";

    public class Column{
        public static final String Payment_id = "Payment_id";
        public static final String Payment_title = "Payment_title";
        public static final String Payment_desc = "Payment_desc";
        public static final String Payment_price = "Payment_price";
        public static final String Payment_endDate = "Payment_endDate";
        public static final String Payment_datePay = "Payment_datePay";
        public static final String Payment_date = "Payment_date";
        public static final String PayType_id = "PayType_id";
        public static final String BankName_id = "BankName_id";
        public static final String DebtTime_id = "DebtTime_id";
        public static final String PayStatus_id = "PayStatus_id";
        public static final String Noti_id = "Noti_id";
    }

    private int Payment_id ;
    private String Payment_title;
    private String Payment_desc;
    private double Payment_price;
    private String Payment_date;
    private String Payment_endDate;
    private String Payment_datePay;
    private String PayType_id;
    private String BankName_id;
    private String DebtTime_id;
    private String PayStatus_id;
    private String Noti_id;

    private static String paymentIdFromClicked;

    //Default Constructor
    public Payment_data(){
    }

    //Constructor
    public Payment_data(int Payment_id, String Payment_title, String Payment_desc, double Payment_price, String Payment_date, String Payment_endDate,
                        String Payment_datePay, String PayType_id, String BankName_id, String DebtTime_id, String PayStatus_id, String Noti_id){
        this.Payment_id = Payment_id;
        this.Payment_title = Payment_title;
        this.Payment_desc = Payment_desc;
        this.Payment_price = Payment_price;
        this.Payment_date = Payment_date;
        this.Payment_endDate = Payment_endDate;
        this.Payment_datePay = Payment_datePay;
        this.PayType_id = PayType_id;
        this.BankName_id = BankName_id;
        this.DebtTime_id = DebtTime_id;
        this.PayStatus_id = PayStatus_id;
        this.Noti_id = Noti_id;
    }//Getter, Setter

    public int getPayment_id() {
        return Payment_id;
    }

    public static void setPaymentIdFromClicked(ArrayList<Payment_data> data, int position){
        paymentIdFromClicked = String.valueOf(data.get(position).getPayment_id());
        Log.d("55555","5555");
    }

    public static String getPaymentIdFromClicked() {
        return paymentIdFromClicked;
    }

    public String getPayment_title() {
        return Payment_title;
    }

    public String getPayment_desc() {
        return Payment_desc;
    }

    public double getPayment_price() {
        return Payment_price;
    }

    public String getPayment_date(){ return Payment_date; }

    public String getPayment_endDate(){ return Payment_endDate; }

    public String getPayment_datePay(){ return  Payment_datePay; }

    public String getPayType_id(){ return PayType_id; }

    public String getBankName_id(){ return BankName_id; }

    public String getDebtTime_id(){ return DebtTime_id; }

    public String getPayStatus_id(){ return PayStatus_id; }

    public String getNoti_id(){
        return Noti_id;
    }



    public void setPayment_id(int Payment_id){
        this.Payment_id = Payment_id;
    }

    public void setPayment_title( String Payment_title) {
        this.Payment_title = Payment_title;
    }

    public void setPayment_desc( String Payment_desc) {
        this.Payment_desc = Payment_desc;
    }

    public void setPayment_price(double Payment_price) {
        this.Payment_price = Payment_price;
    }

    public void setPayment_date(String Payment_date) {
        this.Payment_date = Payment_date;
    }

    public void setPayment_endDate(String Payment_endDate) {
        this.Payment_endDate = Payment_endDate;
    }

    public void setPayment_datePay(String Payment_datePay){
        this.Payment_datePay = Payment_datePay;
    }

    public void setPayType_id(String PayType_id) {
        this.PayType_id = PayType_id;
    }

    public void setBankName_id(String BankName_id) {
        this.BankName_id = BankName_id;
    }

    public void setDebtTime_id(String DebtTime_id) {
        this.DebtTime_id = DebtTime_id;
    }

    public void setPayStatus_id(String PayStatus_id) {
        this.PayStatus_id = PayStatus_id;
    }

    public void setNoti_id(String Noti_id){
        this.Noti_id = Noti_id;
    }

    // For Manage checkbox
    private Boolean selected = false;
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}