package com.example.kaew_pc.diary_project.Manager.Database;

/**
 * Created by chommchome on 31/3/2560.
 */

public class Payment_status {
    public static final String TABLE = "Payment_status";

    public class Column {
        public static final String PayStatus_id = "PayStatus_id";
        public static final String PayStatus_name = "PayStatus_name";

    }

    private String PayStatus_id;
    private String PayStatus_name;


    //Default Constructor
    public Payment_status() {
    }

    //Constructor
    public Payment_status(String PayStatus_id, String PayStatus_name) {
        this.PayStatus_id = PayStatus_id;
        this.PayStatus_name = PayStatus_name;

    }//Getter, Setter

    public String getPayStatus_id(){
        return PayStatus_id;
    }

    public String getPayStatus_name() {
        return PayStatus_name;
    }

    public void setPayStatus_id(String id){
        this.PayStatus_id = id;
    }

    public void setPayStatus_name(String name){
        this.PayStatus_name = name;
    }
}
