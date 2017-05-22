package com.example.kaew_pc.diary_project.Manager.Database;

/**
 * Created by KAEW-PC on 18-Feb-17.
 */

public class Note_data {

    public static final String TABLE = "Note_data";

    public class Column{
        public static final String Note_id = "Note_id";
        public static final String Note_title = "Note_title";
        public static final String Note_desc = "Note_desc";
        public static final String Note_dateCreate = "Note_dateCreate";
        public static final String Note_dateAlert = "Note_dateAlert";
        public static final String Noti_id = "Noti_id";
        public static final String isDelete = "Active";
    }

    private int Note_id ;
    private String Note_title;
    private String Note_desc;
    private String Note_dateCreate;
    private String Note_dateAlert;
    private String Noti_id;
    private int isDelete;

    //Default Constructor
    public Note_data(){
    }

    //Constructor
    public Note_data(int Note_id, String Note_title, String Note_desc, String Note_dateCreate, String Note_dateAlert, String Noti_id, int isDelete){
        this.Note_id = Note_id;
        this.Note_title = Note_title;
        this.Note_desc = Note_desc;
        this.Note_dateCreate = Note_dateCreate;
        this.Note_dateAlert = Note_dateAlert;
        this.Noti_id = Noti_id;
        this.isDelete = isDelete;
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

    public String getNote_dateCreate(){ return Note_dateCreate; }

    public String getNote_dateAlert(){ return Note_dateAlert; }

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

    public void setNote_dateCreate(String Note_dateCreate) {
        this.Note_dateCreate = Note_dateCreate;
    }

    public void setNote_dateAlert(String Note_dateAlert) {
        this.Note_dateAlert = Note_dateAlert;
    }

    public void setNoti_id(String Noti_id){
        this.Noti_id = Noti_id;
    }


    public int getisDelete() {
        return isDelete;
    }

    public void setisDelete(int isDelete){
        this.isDelete = isDelete;
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