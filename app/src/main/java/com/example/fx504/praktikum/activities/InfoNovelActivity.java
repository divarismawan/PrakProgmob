package com.example.fx504.praktikum.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fx504.praktikum.R;
import com.example.fx504.praktikum.admin.AddNovel;
import com.example.fx504.praktikum.api.APIClient;
import com.example.fx504.praktikum.api.APIService;
import com.example.fx504.praktikum.api.APIUrl;
import com.example.fx504.praktikum.model.ResGetById;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

public class InfoNovelActivity extends AppCompatActivity {

    Intent intent;

    APIService apiService;

    String url_stroy;

    ImageView iv_NovelCover;
    TextView tv_NovelTitle, tv_NovelGenre, tv_NovelRelease,tv_NovelDecs;
    TextView tv_readNow;
    Button btn_setFav;

    int id_novel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novelinfo);

        apiService = APIClient.getService();

        id_novel = getIntent().getIntExtra("id_novel",0);

        iv_NovelCover   = findViewById(R.id.iv_novelCover);
        tv_NovelTitle   = findViewById(R.id.tv_novelTitlle);
        tv_NovelGenre   = findViewById(R.id.tv_Novelgenre);
        tv_NovelRelease = findViewById(R.id.tv_Novelrelease);
        tv_NovelDecs    = findViewById(R.id.tv_NovelDesc);

        tv_readNow      = findViewById(R.id.tv_readNow);

        btn_setFav      = findViewById(R.id.btn_setFav);

        btn_setFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(InfoNovelActivity.this, AddNovel.class);
                startActivity(intent);
            }
        });

        tv_readNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(InfoNovelActivity.this, NovelReadActivity.class);
                intent.putExtra("id_novel",id_novel);
                intent.putExtra("story_novel",url_stroy);
                startActivity(intent);
            }
        });

        getAPINovel();

    }

    public void getAPINovel(){
        apiService.NovelGetById(id_novel)
                .enqueue(new Callback<ResGetById>() {

                    @Override
                    public void onResponse(Call<ResGetById> call, Response<ResGetById> response) {
                        if (response.isSuccessful()){

                            tv_NovelTitle.setText(response.body().getNovelTitle());
                            tv_NovelGenre.setText(response.body().getNovelGenre());
                            tv_NovelRelease.setText(response.body().getCreatedAt());
                            tv_NovelDecs.setText(response.body().getNovelSynopsis());

                            Glide.with(InfoNovelActivity.this)
                                    .load(APIUrl.BASE_DATA_URL +response.body().getNovelCover())
                                    .into(iv_NovelCover);
                            url_stroy = Glide.with(InfoNovelActivity.this)
                                    .load(APIUrl.BASE_DATA_URL +response.body().getNovelStory())
                                    .toString();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResGetById> call, Throwable t) {

                    }
                });
    }
}
