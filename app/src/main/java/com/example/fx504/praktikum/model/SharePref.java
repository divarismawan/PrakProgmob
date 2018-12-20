package com.example.fx504.praktikum.model;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePref{

    public final static String SP_NAME    = "sharePref";
    public final static String KEY_NAME   = "user_name";
    public final static String KEY_EMAIL  = "user_email";
    public final static String KEY_PHONE  = "user_phone";
    public final static String KEY_TOKEN  = "user_token";
    public final static String KEY_STATUS = "user_status";
    public final static String KEY_ID     = "user_id";

    private SharedPreferences sharedPreferences;

    public SharePref(Context context) {
        sharedPreferences=context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
    }

    public void setDataString(String key, String val){
        sharedPreferences.edit().putString(key,val).apply();
    }

    public int setDataInt(String key, int val){
        sharedPreferences.edit().putInt(key,val).apply();
        return val;
    }

    public String getDataString(String key){
        return sharedPreferences.getString(key,"");
    }

    public int getDataInt(String key){
        return sharedPreferences.getInt(key,0);
    }

    public void clearData(){
        sharedPreferences
                .edit()
                .clear()
                .apply();
    }
}