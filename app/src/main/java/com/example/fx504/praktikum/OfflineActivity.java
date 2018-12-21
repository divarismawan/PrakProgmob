package com.example.fx504.praktikum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.fx504.praktikum.adapter.FavoriteAdapter;
import com.example.fx504.praktikum.model.RespFavorite;

import java.util.ArrayList;
import java.util.List;

public class OfflineActivity extends AppCompatActivity {
    RecyclerView rv_favorite;

    FavoriteAdapter favoriteAdapter;
    DB_Lite sql_novel;

    List<RespFavorite> respFavorites = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);

        sql_novel =  new DB_Lite(this);

        rv_favorite = findViewById(R.id.rv_favorite);
        setDataFavorite();

    }

    public void setDataFavorite(){
        respFavorites = sql_novel.setNovelFavorite();
        for (int i=0;i<respFavorites.size();i++){
            Toast.makeText(this, "Offline Mode", Toast.LENGTH_SHORT).show();
        }
        setFavoriteAdapter();
    }

    public void setFavoriteAdapter(){
        favoriteAdapter = new FavoriteAdapter(this,respFavorites);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        rv_favorite.setLayoutManager(gridLayoutManager);
        rv_favorite.setAdapter(favoriteAdapter);
    }
}
