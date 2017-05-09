package com.example.kaew_pc.diary_project.Manager.Database;

/**
 * Created by chommchome on 30/4/2560.
 */

public class DebtTime {

    public static final String DATABASE_NAME = "Paymant_data.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "DebtTime";

    public class Column {
        public static final String DebtTime_id = "DebtTime_id";
        public static final String DebtTime_name = "DebtTime_name";

    }

    private String DebtTime_id;
    private String DebtTime_name;


    //Default Constructor
    public DebtTime() {
    }

    //Constructor
    public DebtTime(String DebtTime_id, String DebtTime_name) {
        this.DebtTime_id = DebtTime_id;
        this.DebtTime_name = DebtTime_name;

    }//Getter, Setter

    public String getDebtTime_id(){
        return DebtTime_id;
    }

    public void setDebtTime_id(String id){
        this.DebtTime_id = id;
    }

    public String getDebtTime_name() {
        return DebtTime_name;
    }

    public void setDebtTime_name(String name){
        this.DebtTime_name = name;
    }


}
