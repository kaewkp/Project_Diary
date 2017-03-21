package com.example.kaew_pc.diary_project.Database;

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
        public static final String Email = "Email";
    }

    private String id ;
    private String Password ;
    private String Email;

    //Constructor
    public Password(String Password){
        this.Password = Password;
    }//Getter, Setter

    public String getPassword(){
        return Password;
    }

}
