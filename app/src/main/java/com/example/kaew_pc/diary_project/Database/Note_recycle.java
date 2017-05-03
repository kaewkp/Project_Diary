package com.example.kaew_pc.diary_project.Database;

/**
 * Created by KAEW-PC on 03-May-17.
 */

public class Note_recycle {
    public static final String DATABASE_NAME = "์Note_recycle.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "Note_recycle";

    public class Column{
        public static final String Note_id = "Note_id";
        public static final String Note_title = "Note_title";
        public static final String Note_desc = "Note_desc";
        public static final String Note_date = "Note_date";
        public static final String Noti_id = "Noti_id";
    }

    private int Note_id ;
    private String Note_title;
    private String Note_desc;
    private String Note_date;
    private String Noti_id;

    //Default Constructor
    public Note_recycle(){
    }

    //Constructor
    public Note_recycle(int Note_id, String Note_title, String Note_desc, String Note_date, String Noti_id){
        this.Note_id = Note_id;
        this.Note_title = Note_title;
        this.Note_desc = Note_desc;
        this.Note_date = Note_date;
        this.Noti_id = Noti_id;
    }//Getter, Setter

    public int getNote_id(){
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

    public void setNote_id(int Note_id){
        this.Note_id = Note_id;
    }

    public void setNote_title( String Note_title) {
        this.Note_title = Note_title;
    }

    public void setNote_desc(String Note_desc) {
        this.Note_desc = Note_desc;
    }

    public void setNote_date(String Note_date) {
        this.Note_date = Note_date;
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
