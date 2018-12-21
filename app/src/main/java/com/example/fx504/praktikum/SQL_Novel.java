package com.example.fx504.praktikum;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQL_Novel {
    private static final String DATABASE     = "db_progmob";
    private static final int    DATA_VERSION = 1;
    private static final String TABLE        = "tb_novel";
    private static final String ID_NOVEL     = "id";
    private static final String STATUS_NOVEL = "status";
    private static final String TITLE        = "novel_title";
    private static final String GENRE        = "novel_genre";
    private static final String SYNOPSIS     = "novel_synopsis";
    private static final String STORY        = "novel_story";
    private static final String COVER        = "novel_cover";
    private static final String CREAT        = "created_at";
    private static final String UPDATE       = "updated_at";

    Helpers helpers;
    private SQLiteDatabase sqLiteOpenHelper;

    public SQL_Novel(Context context){
        helpers = new Helpers(context);
        sqLiteOpenHelper = helpers.getWritableDatabase();
    }

    private class Helpers extends SQLiteOpenHelper{

        public Helpers(Context context){
            super(context,DATABASE,null,DATA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
