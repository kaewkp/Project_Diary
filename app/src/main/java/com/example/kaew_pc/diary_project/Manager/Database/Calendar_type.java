package com.example.kaew_pc.diary_project.Manager.Database;

/**
 * Created by Administrater on 22/5/2560.
 */

public class Calendar_type {

    public static final String DATABASE_NAME = "Calendar_type.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "Calendar_type";

    private String CalendarType_id;
    private String CalendarType_name;

    public class Column {
        public static final String CalendarType_id = "CalendarType_id";
        public static final String CalendarType_name = "CalendarType_name";

    }

    //Default Constructor
    public Calendar_type() {
    }

    //Constructor
    public Calendar_type(String CalendarType_id, String CalendarType_name) {
        this.CalendarType_id = CalendarType_id;
        this.CalendarType_name = CalendarType_name;

    }//Getter, Setter

    public void setCalendarType_id(String CalendarType_id){
        this.CalendarType_id = CalendarType_id;
    }

    public void setCalendarType_name(String CalendarType_name){
        CalendarType_id = CalendarType_name;
    }

    public String getCalendarType_id(){
        return CalendarType_id;
    }

    public String getCalendarType_name() {
        return CalendarType_name;
    }
}
