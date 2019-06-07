package com.example.sccm.javabean;

import java.io.Serializable;

public class User implements Serializable {

    private int user_Id;
    private String user_Name;
    private String user_Password;


    public User( String user_Name, String user_Password) {
        this.user_Name = user_Name;
        this.user_Password = user_Password;
    }
    public User() {
    }

    public int getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public String getUser_Password() {
        return user_Password;
    }

    public void setUser_Password(String user_Password) {
        this.user_Password = user_Password;
    }

}
