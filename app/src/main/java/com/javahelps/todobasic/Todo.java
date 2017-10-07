package com.javahelps.todobasic;

import java.io.Serializable;

/**
 * Created by Sneha on 09-09-2017.
 */

public class Todo implements Serializable {

    private String task ;
    private String date ;
    private String id ;
    private String time ;


    public Todo(String task, String date ,String time) {
        this.task = task;
        this.date = date;
        this.time = time ;
    }
    public Todo(String task, String date , String time , String id)  {
        this.task = task;
        this.date = date;
        this.time = time;
        this.id = id ;
    }
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {return time; }

    public void setTime(String time) { this.time = time; }
}
