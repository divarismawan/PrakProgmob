package com.example.fx504.praktikum;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.fx504.praktikum.model.RespFavorite;

import java.util.ArrayList;
import java.util.List;

public class DB_Lite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "db_novel";

    private static final String TABLE_NAME = "tb_favorite";

    private static final String ID_NOVEL          = "id";
    private static final String STATUS            = "status";
    private static final String TITLE             = "novel_title";
    private static final String GENRE             = "novel_genre";
    private static final String SYNOPSIS          = "novel_synopsis";
    private static final String STORY             = "novel_story";
    private static final String COVER             = "novel_cover";
    private static final String CREATE            = "created_at";
    private static final String UPDATE            = "updated_at";

    private DB_Lite sql_novel;
    SQLiteDatabase database;

    public DB_Lite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    private static final String CREATE_FAVORITE = "CREATE TABLE "+TABLE_NAME +" (" +
            ID_NOVEL + " INTEGER PRIMARY KEY," +
            STATUS   + " INTEGER," +
            TITLE    + " TEXT," +
            GENRE    + " TEXT," +
            SYNOPSIS + " TEXT," +
            STORY    + " TEXT," +
            COVER    + " TEXT," +
            CREATE   + " TEXT," +
            UPDATE   + " TEXT" +
            ")";

    public void saveFavorite(int id, int status, String title, String genre, String synopsis, String story,
                             String cover, String create, String update){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_NOVEL,id);
        contentValues.put(STATUS,status);
        contentValues.put(TITLE,title);
        contentValues.put(GENRE,genre);
        contentValues.put(SYNOPSIS,synopsis);
        contentValues.put(STORY,story);
        contentValues.put(COVER,cover);
        contentValues.put(CREATE,create);
        contentValues.put(UPDATE,update);
        database.insert(TABLE_NAME,null,contentValues);
    }

    public void deleteData(){
        database.delete(TABLE_NAME,null,null);
        Log.d("delete", "success");
    }


    public List<RespFavorite> setNovelFavorite(){
        List<RespFavorite> respFavorites = new ArrayList<>();
         SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " +TABLE_NAME, null);
        int count = cursor.getCount();

        if (count>0){
            while (cursor.moveToNext()){
                int    id       = cursor.getInt(cursor.getColumnIndex(ID_NOVEL));
                int status      = cursor.getInt(cursor.getColumnIndex(STATUS));
                String title    = cursor.getString(cursor.getColumnIndex(TITLE));
                String genre    = cursor.getString(cursor.getColumnIndex(GENRE));
                String synopsis = cursor.getString(cursor.getColumnIndex(SYNOPSIS));
                String story    = cursor.getString(cursor.getColumnIndex(STORY));
                String cover    = cursor.getString(cursor.getColumnIndex(COVER));
                String create   = cursor.getString(cursor.getColumnIndex(CREATE));
                String update   = cursor.getString(cursor.getColumnIndex(UPDATE));

                RespFavorite rf = new RespFavorite();
                rf.setId(id);
                rf.setStatus(status);
                rf.setNovelTitle(title);
                rf.setNovelGenre(genre);
                rf.setNovelSynopsis(synopsis);
                rf.setNovelStory(story);
                rf.setNovelCover(cover);
                rf.setCreatedAt(create);
                rf.setUpdatedAt(update);
                respFavorites.add(rf);
            }
        }
        cursor.close();
        database.close();
        return respFavorites;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FAVORITE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
