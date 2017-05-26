package com.example.kaew_pc.diary_project.Manager.Database;

import android.graphics.Bitmap;

import java.sql.Blob;

/**
 * Created by Ekachart-PC on 24/5/2560.
 */

public class Note_image {
    public static final String TABLE = "Note_image";

    public class Column{
        public static final String seq = "seq";
        public static final String Note_id = "Note_id";
        public static final String Note_image = "Note_image";
    }

    private int seq ;
    private int Note_id;
    private byte[] Note_image;

    public Note_image(int seq, int id, byte[] image){
        this.seq = seq;
        this.Note_id = id;
        this.Note_image = image;
    }

    public Note_image(int id, byte[] image){
        this.Note_id = id;
        this.Note_image = image;
    }

    public int getNote_id(){
        return Note_id;
    }

    public byte[] getNote_image() {
        return Note_image;
    }

    public void setNote_id(int id){
        this.Note_id = id;
    }

    public void setNote_image(byte[] image) {
        this.Note_image = image;
    }
}
