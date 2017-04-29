package com.example.kaew_pc.diary_project.Database;

import android.widget.ListView;
import android.widget.TextView;
import com.example.kaew_pc.diary_project.R;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by chommchome on 29/4/2560.
 */

public class BankName {

        public static final String DATABASE_NAME = "Paymant_data.db";
        public static final int DATABASE_VERSION = 1;
        public static final String TABLE = "BankName";

        public class Column {
            public static final String BankName_id = "BankName_id";
            public static final String BankName_name = "BankName_name";

        }

        private String BankName_id;
        private String BankName_name;


        //Default Constructor
        public BankName() {
        }

        //Constructor
        public BankName(String BankName_id, String BankName_name) {
            this.BankName_id = BankName_id;
            this.BankName_name = BankName_name;

        }//Getter, Setter

        public String getBankName_id(){
            return BankName_id;
        }

        public void setBankName_id(String id){
            this.BankName_id = id;
        }

        public String getBankName_name() {
            return BankName_name;
        }

        public void setBankName_name(String name){
            this.BankName_name = name;
        }


}

