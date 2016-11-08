package com.lx.chat.bean;

/**
 * Created by lx on 16/11/8.
 */
public class User {
    private int id;
    private String name ;
    private String avatar ;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
