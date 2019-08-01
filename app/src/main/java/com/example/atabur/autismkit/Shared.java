package com.example.atabur.autismkit;


import android.content.Context;
import android.content.SharedPreferences;

public class Shared {
    private String name;
    private String Password;

    Context context;
    SharedPreferences sharedPreferences;
    public Shared(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences("login_details",context.MODE_PRIVATE);
    }


    public String getName() {
        sharedPreferences.getString("name","");
        return name;
    }

    public void setName(String name) {
        this.name = name;
        sharedPreferences.edit().putString("name",name).commit();
    }

   public String getPassword() {
        Password=sharedPreferences.getString("password","");
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
        sharedPreferences.edit().putString("password",password).commit();
    }

    public void removeUser(){
        sharedPreferences.edit().clear().commit();
    }




}



