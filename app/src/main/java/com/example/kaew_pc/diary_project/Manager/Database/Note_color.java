package com.example.kaew_pc.diary_project.Manager.Database;

/**
 * Created by KAEW-PC on 21-May-17.
 */

public class Note_color {

    public static final String TABLE = "Note_color";

    public class Column{
        public static final String Color_id = "Color_id";
        public static final String Color_code = "Color_code";
        public static final String Color_name = "Color_name";
    }

    private int Color_id ;
    private String Color_code;
    private String Color_name;

    //Default Constructor
    public Note_color(){
    }

    //Constructor
    public Note_color(int Color_id, String Color_code, String Color_name){
        this.Color_id = Color_id;
        this.Color_code = Color_code;
        this.Color_name = Color_name;

    }//Getter, Setter

    public int getColor_id(){
        return Color_id;
    }

    public String getColor_code() {
        return Color_code;
    }

    public String getColor_name() {
        return Color_name;
    }

    public void setColor_id(int Color_id){
        this.Color_id = Color_id;
    }

    public void setColor_code( String Color_code) {
        this.Color_code = Color_code;
    }

    public void setColor_name(String Color_name) {
        this.Color_name = Color_name;
    }


}
