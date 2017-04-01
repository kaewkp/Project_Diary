package com.example.kaew_pc.diary_project.Database;

/**
 * Created by KAEW-PC on 19-Feb-17.
 */

/**
 * Created by KAEW-PC on 18-Feb-17.
 */

public class Payments_data {
    public static final String DATABASE_NAME = "Payments_data.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "Payments_data";

    public class Column{
        public static final String Payments_id = "Payments_id";
        public static final String Payments_title = "Payments_title";
        public static final String Payments_price = "Payments_price";
        public static final String Payments_endDate = "Payments_endDate";
        public static final String Payments_date = "Payments_date";
        public static final String Noti_id = "Noti_id";
        public static final String PayType_id = "PayType_id";
        public static final String PayStatus_id = "PayStatus_id";

}

    private String Payments_id ;
    private String Payments_title;
    private int Payments_price;
    private String Payments_endDate;
    private String Payments_date;
    private String Noti_id;
    private String PayType_id;
    private String PayStatus_id;

    //Default Constructor
    public Payments_data() {

    }

    //Constructor
    public Payments_data(String Payments_id, String Payments_title, int Payments_price, String Payments_endDate,
                         String Payments_date, String Noti_id, String PayType_id, String PayStatus_id){
        this.Payments_id = Payments_id;
        this.Payments_title = Payments_title;
        this.Payments_price = Payments_price;
        this.Payments_endDate = Payments_endDate;
        this.Payments_date = Payments_date;
        this.Noti_id = Noti_id;
        this.PayType_id = PayType_id;
        this.PayStatus_id = PayStatus_id;

    }//Getter, Setter

    public String getPayments_id(){
        return Payments_id;
    }

    public String getPayments_title(){
        return Payments_title;
    }

    public int getPayments_price(){
        return Payments_price;
    }

    public String getPayments_endDate(){
        return Payments_endDate;
    }


//    private ListView date;
    public String getPayments_date(){

//        date = (ListView) findViewById(R.id.listView);
//
//        java.util.Date time = Calendar.getInstance().getTime();
//
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        String formattedDate = df.format(time);
//
//        android.util.Log.i("Time Class ", " Time value in milliseconds "+time.getYear());
//        date.setText(formattedDate);

        return Payments_date;
    }

    public String getNoti_id(){
        return Noti_id;
    }

    public String getPayType_id(){
        return PayType_id;
    }

    public String getPayStatus_id(){
        return PayStatus_id;
    }

}