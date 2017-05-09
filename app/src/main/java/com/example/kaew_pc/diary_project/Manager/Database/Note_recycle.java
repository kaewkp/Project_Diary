package com.example.kaew_pc.diary_project.Manager.Database;

/**
 * Created by KAEW-PC on 03-May-17.
 */

public class Note_recycle {
    public static final String TABLE = "Note_recycle";

    public class Column{
        public static final String Recycle_id = "Recycle_id";
        public static final String Note_id = "Note_id";
        public static final String Time_del = "Time_del";
    }

    private int Recycle_id ;
    private int Note_id;
    private String Time_del;


    //Default Constructor
    public Note_recycle(){
    }

    //Constructor
    public Note_recycle(int Recycle_id, int Note_id, String Time_del){

        this.Recycle_id = Recycle_id;
        this.Note_id = Note_id;
        this.Time_del = Time_del;
    }//Getter, Setter

    public int getRecycle_id(){
        return Recycle_id;
    }

    public int getNote_id() {
        return Note_id;
    }

    public String getTime_del() {
        return Time_del;
    }


    public void setRecycle_id(int Recycle_id){
        this.Recycle_id = Recycle_id;
    }

    public void setNote_id(int Note_id) {
        this.Note_id = Note_id;
    }

    public void setTime_del(String Time_del) {
        this.Time_del = Time_del;
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
