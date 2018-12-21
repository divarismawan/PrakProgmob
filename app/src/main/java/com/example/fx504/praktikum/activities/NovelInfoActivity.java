package com.example.fx504.praktikum.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fx504.praktikum.R;
import com.example.fx504.praktikum.api.APIClient;
import com.example.fx504.praktikum.api.APIService;
import com.example.fx504.praktikum.api.APIUrl;
import com.example.fx504.praktikum.model.ResGetById;
import com.example.fx504.praktikum.model.RespAddFavorite;
import com.example.fx504.praktikum.model.RespDeleteFav;
import com.example.fx504.praktikum.model.SharePref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NovelInfoActivity extends AppCompatActivity {

    Intent intent;
    SharePref sharePref;
    APIService apiService;


    ImageView iv_NovelCover;
    TextView tv_NovelTitle, tv_NovelGenre, tv_NovelRelease,tv_NovelDecs;
    TextView tv_readNow;
    Button btn_setFav;

    int id_novel;
    int id_member;
    int status_fav=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novelinfo);

        sharePref = new SharePref(this);

        apiService = APIClient.getService();

        id_novel  = getIntent().getIntExtra("id_novel",0);
        id_member = sharePref.getDataInt(SharePref.KEY_ID);

        iv_NovelCover   = findViewById(R.id.iv_novelCover);
        tv_NovelTitle   = findViewById(R.id.tv_novelTitlle);
        tv_NovelGenre   = findViewById(R.id.tv_Novelgenre);
        tv_NovelRelease = findViewById(R.id.tv_Novelrelease);
        tv_NovelDecs    = findViewById(R.id.tv_NovelDesc);

        tv_readNow      = findViewById(R.id.tv_readNow);

        btn_setFav      = findViewById(R.id.btn_setFav);


        setButtonFav();

        tv_readNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(NovelInfoActivity.this, NovelReadActivity.class);
                intent.putExtra("id_novel",id_novel);
                startActivity(intent);
            }
        });

        getAPINovel();

    }

    public void setButtonFav(){
        btn_setFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status_fav *=-1;
                if (status_fav == 1){
                    setFavorite();

                }else if (status_fav == -1){
                    deleteFav();

                }
            }
        });
    }

    public void getAPINovel(){
        apiService.NovelGetById(id_novel)
                .enqueue(new Callback<ResGetById>() {

                    @Override
                    public void onResponse(@NonNull Call<ResGetById> call, @NonNull Response<ResGetById> response) {
                        if (response.isSuccessful()){

                            tv_NovelTitle.setText(response.body().getNovelTitle());
                            tv_NovelRelease.setText(response.body().getCreatedAt());
                            tv_NovelGenre.setText(response.body().getNovelGenre());
                            tv_NovelDecs.setText(response.body().getNovelSynopsis());

                            Glide.with(NovelInfoActivity.this)
                                    .load(APIUrl.BASE_DATA_URL +response.body().getNovelCover())
                                    .into(iv_NovelCover);
                        }

                    }

                    @Override
                    public void onFailure(Call<ResGetById> call, Throwable t) {

                    }
                });
    }

    public void setFavorite(){
        apiService.AddFavorite(id_novel,id_member)
                .enqueue(new Callback<RespAddFavorite>() {
                    @Override
                    public void onResponse(Call<RespAddFavorite> call, Response<RespAddFavorite> response) {
                        if (response.isSuccessful()) {
                            btn_setFav.setBackgroundResource(R.drawable.bg_btn);
                            btn_setFav.setText("Favorited");
                            btn_setFav.setTextColor(Color.WHITE);
                        }else {
                            Toast.makeText(NovelInfoActivity.this, "Data Kosong",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RespAddFavorite> call, Throwable t) {
                        Toast.makeText(NovelInfoActivity.this, "Gagal menyimpan data",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        }

    public void deleteFav(){
        apiService.DeleteFav(id_novel,id_member)
                .enqueue(new Callback<RespDeleteFav>() {
                    @Override
                    public void onResponse(Call<RespDeleteFav> call, Response<RespDeleteFav> response) {
                        if (response.isSuccessful()) {
                            btn_setFav.setBackgroundResource(R.drawable.bg_btn2);
                            btn_setFav.setText("Add to Favorite");
                            btn_setFav.setTextColor(Color.BLACK);
                        }else {
                            Toast.makeText(NovelInfoActivity.this, "Data Kosong",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RespDeleteFav> call, Throwable t) {
                        Toast.makeText(NovelInfoActivity.this, "Gagal menyimpan data",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }






}
