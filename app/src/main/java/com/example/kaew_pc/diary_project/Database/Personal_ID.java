package com.example.kaew_pc.diary_project.Database;

/**
 * Created by KAEW-PC on 05-May-17.
 */

public class Personal_ID {

        public static final String DATABASE_NAME = "Personal_ID.db";
        public static final int DATABASE_VERSION = 1;
        public static final String TABLE = "Personal_ID";

        public class Column{
            public static final String id = "id";
            public static final String Personal_id = "Personal_id";
        }

        private String id ;
        private int Personal_id;

        //Constructor
        public Personal_ID(int Personal_id){
            this.Personal_id = Personal_id;
        }//Getter, Setter

        public int getPersonal_id(){
            return Personal_id;
        }

        public void setPersonal_id(int Personal_id){
            this.Personal_id = Personal_id;
        }

    }


