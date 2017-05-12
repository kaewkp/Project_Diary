package com.example.kaew_pc.diary_project.Manager.Database;

/**
 * Created by KAEW-PC on 18-Feb-17.
 */

public class PayType {

    public static final String DATABASE_NAME = "Paymant_data.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "PayType";

    public class Column {
        public static final String PayType_id = "PayType_id";
        public static final String PayType_name = "PayType_name";

    }

    private String PayType_id;
    private String PayType_name;


    //Default Constructor
    public PayType() {
    }

    //Constructor
    public PayType(String PayType_id, String PayType_name) {
        this.PayType_id = PayType_id;
        this.PayType_name = PayType_name;

    }//Getter, Setter

    public String getPayType_id(){
        return PayType_id;
    }

    public void setPayType_id(String id){
        this.PayType_id = id;
    }

    public String getPayType_name() {
        return PayType_name;
    }

    public void setPayType_name(String name){
        this.PayType_name = name;
    }
}