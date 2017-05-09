package com.example.kaew_pc.diary_project.Manager.Database;

/**
 * Created by KAEW-PC on 21-Mar-17.
 */

public class Password {
    public static final String DATABASE_NAME = "Password.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "Password";

    public class Column{
        public static final String id = "id";
        public static final String Password = "Password";
        public static final String Personal_id = "Personal_id";
    }

    private String id ;
    private String Password ;
    private int Personal_id;

    //Constructor
    public Password(String Password){
        this.Password = Password;
    }//Getter, Setter

    public String getPassword(){
        return Password;
    }
    public int getPersonal_id(){
        return Personal_id;
    }

    public void setPersonal_id(int Personal_id){
        this.Personal_id = Personal_id;
    }


}
