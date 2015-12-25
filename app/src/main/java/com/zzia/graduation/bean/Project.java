package com.zzia.graduation.bean;

import java.util.ArrayList;

/**
 * Author: yunyujing
 * Date: 2015/12/9
 */
public class Project {
    private String id;
    private String company;
    private String icon;
    private String name;
    private ArrayList<User> users;

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
