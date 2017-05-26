package com.example.kaew_pc.diary_project.Manager;

/**
 * Created by Administrater on 25/5/2560.
 */

import java.util.Date;
public class EventObjects {
    private int id;
    private String message;
    private Date date;
    private String title;
    private String type;
    private String notic;

    public EventObjects(String message, Date date) {
        this.message = message;
        this.date = date;
    }
    public EventObjects(int id, String message, Date date,
                        String title, String type, String notic) {
        this.date = date;
        this.message = message;
        this.id = id;
        this.title = title;
        this.type = type;
        this.notic = notic;
    }
    public int getId() {
        return id;
    }
    public String getMessage() {
        return message;
    }
    public Date getDate() {
        return date;
    }
    public String getTitle() {return title;}
    public String getNotic() {return notic;}
    public String getType() {return type;}
}