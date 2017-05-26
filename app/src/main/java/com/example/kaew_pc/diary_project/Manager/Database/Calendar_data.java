package com.example.kaew_pc.diary_project.Manager.Database;

import java.util.Date;

/**
 * Created by Administrater on 22/5/2560.
 */

public class Calendar_data {
    public static final String DATABASE_NAME = "Calendar_data.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "Calendar_data";

    private int Calendar_id;
    private String Calendar_title;
    private String Calendar_desc;
    private Date Calendar_time;
    private Date Calendar_createdTime;
    private String CalendarType_id; //FK
    private String Noti_id; //FK

    public class Column {
        public static final String Calendar_id = "Calendar_id";
        public static final String Calendar_title = "Calendar_title";
        public static final String Calendar_desc = "Calendar_desc";
        public static final String Calendar_createdTime = "Calendar_createdTime";
        public static final String CalendarType_id = "CalendarType_id"; //FK
        public static final String Noti_id ="Noti_id"; //FK
    }

    //Default Constructor
    public Calendar_data() {
    }

    //Constructor
    public Calendar_data(int Calendar_id, String Calendar_title
                         , String Calendar_desc
                         , Date Calendar_createdTime, String CalendarType_id
                         , String Noti_id) {
        this.Calendar_id = Calendar_id;
        this.Calendar_title = Calendar_title;
        this.Calendar_desc = Calendar_desc;
        this.Calendar_createdTime = Calendar_createdTime;
        this.CalendarType_id = CalendarType_id;
        this.Noti_id = Noti_id;
    }

    //Getter, Setter

    public int getCalendar_id(){
        return Calendar_id;
    }

    public String getCalendar_title() {
        return Calendar_title;
    }

    public String getCalendar_desc() {
        return Calendar_desc;
    }

    public Date getCalendar_createdTime() {
        return Calendar_createdTime;
    }

    public String getCalendarType_id() {
        return CalendarType_id;
    }

    public String getNoti_id() {
        return Noti_id;
    }

    public void setCalendar_id(int Calendar_id){
        this.Calendar_id = Calendar_id;
    }

    public void setCalendar_title(String Calendar_title) {
        this.Calendar_title = Calendar_title;
    }

    public void setCalendar_desc(String Calendar_desc) {
        this.Calendar_desc = Calendar_desc;
    }

    public void setCalendar_createdTime(Date Calendar_createdTime) {
        this.Calendar_createdTime = Calendar_createdTime;
    }

    public void setCalendarType_id(String CalendarType_id) {
        this.CalendarType_id = CalendarType_id;
    }

    public void setNoti_id(String Noti_id) {
        this.Noti_id = Noti_id;
    }

}
