package com.moviehub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Account {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("role")
    @Expose
    private int role;

    public Account(String username, String password, int role) {
        super();
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Account() {
        super();
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public int getRole() {
        return role;
    }


    public void setRole(int role) {
        this.role = role;
    }



}
