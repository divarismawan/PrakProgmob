package com.example.fx504.praktikum.novels;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fx504.praktikum.R;
import com.example.fx504.praktikum.adapter.NewNovelAdapter;
import com.example.fx504.praktikum.api.APIClient;
import com.example.fx504.praktikum.api.APIService;
import com.example.fx504.praktikum.model.ResShowNovel;
import com.victor.loading.book.BookLoading;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NovelAllUpdate extends AppCompatActivity {

    BookLoading book_loading;
    RecyclerView rv_allUpdate;

    APIService apiService;

    NewNovelAdapter newNovelAdapter;
    List<ResShowNovel> resShowNovels = new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_update);

        book_loading  = findViewById(R.id.book_loading);
        rv_allUpdate  = findViewById(R.id.rv_allUpdate);

        apiService = APIClient.getService();

        showAllUpdate();



    }

    public void showAllUpdate(){
        apiService.showAllUpdate()
                .enqueue(new Callback<List<ResShowNovel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ResShowNovel>> call,
                                           @NonNull Response<List<ResShowNovel>> response) {
                        if (response.isSuccessful()){
//                            Toast.makeText(getContext(), "Sukses", Toast.LENGTH_SHORT).show();
                            //get all data Novel from API SERVICE
                            assert response.body() != null;
                            resShowNovels.clear();
                            resShowNovels.addAll(response.body());
                            setAdapterNewNovel();
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ResShowNovel>> call, @NonNull Throwable t) {
                    }
                });
    }

    public void setAdapterNewNovel(){
        newNovelAdapter = new NewNovelAdapter(this,resShowNovels);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
        rv_allUpdate.setLayoutManager(layoutManager);
        rv_allUpdate.setAdapter(newNovelAdapter);
    }

}
