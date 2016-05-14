package com.zzia.graduation.bean;

/**
 * Author: yunyujing
 * Date: 2015/12/9
 */
public class User {
    private int id;
    private String icon;
    private String name;
    private String sex;
    private int age;
    private String department;
    private String isManager;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }



    public  String getIcon() {
        return icon;
    }

    public  int getId() {
        return id;
    }
}
