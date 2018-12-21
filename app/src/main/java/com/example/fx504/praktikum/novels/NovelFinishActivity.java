package com.example.fx504.praktikum.novels;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.fx504.praktikum.R;
import com.example.fx504.praktikum.adapter.FinishNovelAdapter;
import com.example.fx504.praktikum.api.APIClient;
import com.example.fx504.praktikum.api.APIService;
import com.example.fx504.praktikum.model.RespFavMember;
import com.victor.loading.book.BookLoading;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NovelFinishActivity extends AppCompatActivity {

    LinearLayout layout_update;
    BookLoading book_loading;
    RecyclerView rv_finish;

    APIService apiService;

    FinishNovelAdapter finishNovelAdapter;
    List<RespFavMember> resFinishNovel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_novel);

        layout_update = findViewById(R.id.layout_update);
        book_loading  = findViewById(R.id.book_loading);
        rv_finish  = findViewById(R.id.rv_finish);

        apiService = APIClient.getService();

        setFinishNovel();


    }

    public void setFinishNovel(){
        apiService.finishNovel()
                .enqueue(new Callback<List<RespFavMember>>() {
                    @Override
                    public void onResponse(Call<List<RespFavMember>> call, Response<List<RespFavMember>> response) {
                        resFinishNovel.clear();
                        resFinishNovel.addAll(response.body());
                        setAdapterFavNovel();
                    }

                    @Override
                    public void onFailure(Call<List<RespFavMember>> call, Throwable t) {

                    }
                });

    }

    public void setAdapterFavNovel(){
        finishNovelAdapter = new FinishNovelAdapter(this, resFinishNovel);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        rv_finish.setLayoutManager(gridLayoutManager);
        rv_finish.setAdapter(finishNovelAdapter);
    }

}
