package com.kamilkorzeniewski.stockcontrolclient.security;

import com.google.gson.annotations.SerializedName;

public class AuthModel {
    @SerializedName("username")
    String userName;
    @SerializedName("password")
    String password;

    public AuthModel(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthModel{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
