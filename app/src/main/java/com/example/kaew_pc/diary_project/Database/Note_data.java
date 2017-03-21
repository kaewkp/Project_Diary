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

public class Note_data {

    public static final String DATABASE_NAME = "Note_data.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "Note_data";

    public class Column{
        public static final String Note_id = "Note_id";
        public static final String Note_title = "Note_title";
        public static final String Note_desc = "Note_desc";
        public static final String Note_date = "Note_date";
        public static final String Noti_id = "Noti_id";
    }

    private String Note_id ;
    private String Note_title;
    private String Note_desc;
    private String Note_date;
    private String Noti_id;

    //Constructor
    public Note_data(String Note_id, String Note_title, String Note_desc, String Note_date, String Noti_id){
        this.Note_id = Note_id;
        this.Note_title = Note_title;
        this.Note_desc = Note_desc;
        this.Note_date = Note_date;
        this.Noti_id = Noti_id;
    }//Getter, Setter

    public String getNote_id(){
        return Note_id;
    }

    public String getNote_title() {
        return Note_title;
    }

    public String getNote_desc() {
        return Note_desc;
    }

    public String getNote_date(){ return Note_date; }

    public String getNoti_id(){
        return Noti_id;
    }
}