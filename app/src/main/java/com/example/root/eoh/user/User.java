package com.example.root.eoh.user;

/**
 * Created by root on 16/08/18.
 */

public class User {

    String id;
    String name;
    String img;
    String comment;
    String date;
    String type;

    public User(){


    }


    public User(String id, String name, String img , String date , String comment , String type) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.date = date;
        this.comment = comment;
        this.type = type;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
