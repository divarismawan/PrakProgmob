package com.example.fx504.praktikum.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.fx504.praktikum.DB_Lite;
import com.example.fx504.praktikum.novels.NovelGenreActivity;
import com.example.fx504.praktikum.R;
import com.example.fx504.praktikum.adapter.FavoriteAdapter;
import com.example.fx504.praktikum.adapter.NewNovelAdapter;
import com.example.fx504.praktikum.api.APIClient;
import com.example.fx504.praktikum.api.APIService;
import com.example.fx504.praktikum.model.ResShowNovel;
import com.example.fx504.praktikum.model.RespFavorite;
import com.example.fx504.praktikum.model.SharePref;
import com.example.fx504.praktikum.novels.NovelAllUpdate;
import com.example.fx504.praktikum.novels.NovelFinishActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHome extends Fragment {

    View view;

    ViewFlipper vf_novel;
    Intent intent;



    APIService apiService;

    NewNovelAdapter newNovelAdapter;
    List<ResShowNovel> resShowNovels = new ArrayList<>();


    RecyclerView rv_favNovel;
    RecyclerView rv_newNovel;

    FavoriteAdapter favoriteAdapter;
    List<RespFavorite> favorites = new ArrayList<>();

    DB_Lite db_lite;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        db_lite = new DB_Lite(getContext());

        view = inflater.inflate(R.layout.fragment_home,container,false);

        apiService=APIClient.getService();


//------------------ FIND ID ------------------//


        rv_favNovel = view.findViewById(R.id.rc_fav);
        rv_newNovel = view.findViewById(R.id.rc_novelRilis);

        //flipper Image
        setflipperImage();

        // Favorite Novel
        setFav();

        // Update Novel
        newNovelView();

        // 3 Icon Button Intent
        goAllUpdateActivity();
        goGenreActivity();
        goFinishActivity();

        return view;
    }

    //--------------------AD--------------------//
    public void setflipperImage(){
        vf_novel   = view.findViewById(R.id.vf_novel);
        int novel_cover[] = {R.drawable.cat_eye, R.drawable.dead_in_deep_water, R.drawable.strange_winds};
        for (int aNovel_cover : novel_cover) {
            animflipper(aNovel_cover);
        }
    }

    @SuppressLint("NewApi")
    public void animflipper(int img){
        ImageView imageView= new ImageView(getContext());
        imageView.setBackgroundResource(img);

        vf_novel.addView(imageView);
        vf_novel.setFlipInterval(2500); //1000 = 1 detik
        vf_novel.setAutoStart(true);

        //animation
        vf_novel.setInAnimation(getContext(), android.R.anim.slide_in_left);
        vf_novel.setOutAnimation(getContext(), android.R.anim.slide_out_right);
    }


    //--------------------SET FAV NOVEL--------------------//
    public void setFav(){
        SharePref sharePref = new SharePref(Objects.requireNonNull(getContext()));
        int id_user = sharePref.getDataInt(SharePref.KEY_ID);
        apiService.NovelFavorite(id_user)
                .enqueue(new Callback<List<RespFavorite>>() {
                    @Override
                    public void onResponse(Call<List<RespFavorite>> call, @NonNull Response<List<RespFavorite>> response) {
                        if (response.isSuccessful()){
                            favorites.clear();
                            favorites.addAll(response.body());
                            setAdapterFavNovel();

//                            Input Data to SQL Lite
//                            db_lite.deleteData();
//                            for(int i=0;i<favorites.size();i++){
//                                RespFavorite rs = favorites.get(i);
//                                int id          = rs.getId();
//                                int status      = rs.getStatus();
//                                String title    = rs.getNovelTitle();
//                                String genre    = rs.getNovelGenre();
//                                String synopsis = rs.getNovelSynopsis();
//                                String story    = rs.getNovelStory();
//                                String cover    = rs.getNovelCover();
//                                String create   = rs.getCreatedAt();
//                                String update   = rs.getUpdatedAt();
//
//                                Log.wtf("data", id + status+title+genre+
//                                        synopsis+story+cover+create+update );
//
//                                db_lite.saveFavorite(id,status,title,genre,
//                                        synopsis,story,cover,create,update);
//                            }
                        }else {
                        }
                    }

                    @Override
                    public void onFailure(Call<List<RespFavorite>> call, Throwable t) {

                    }
                });

    }

    public void setAdapterFavNovel(){
        favoriteAdapter = new FavoriteAdapter(getContext(),favorites);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        rv_favNovel.setLayoutManager(layoutManager);
        rv_favNovel.setAdapter(favoriteAdapter);
    }

    //--------------------BUTTON ACTION ICON--------------------//

    public void goAllUpdateActivity(){
        ImageView iv_allUpdate = view.findViewById(R.id.iv_allUpdate);

        iv_allUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(),NovelAllUpdate.class);
                startActivity(intent);
            }
        });
    }

    public void goGenreActivity(){
        ImageView iv_genre     = view.findViewById(R.id.iv_genre);
        iv_genre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(),NovelGenreActivity.class);
                startActivity(intent);
            }
        });
    }

    public void goFinishActivity(){
        ImageView iv_finish     = view.findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(),NovelFinishActivity.class);
                startActivity(intent);
            }
        });
    }


    //--------------------NEW UPDATE NOVEL--------------------//

    public void newNovelView(){
        apiService.getNovelList()
                .enqueue(new Callback<List<ResShowNovel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ResShowNovel>> call,
                                           @NonNull Response<List<ResShowNovel>> response) {
                        if (response.isSuccessful()){
                            //get all data Novel from API SERVICE
                            assert response.body() != null;
                            resShowNovels.clear();
                            resShowNovels.addAll(response.body());
                            setAdapterNewNovel();
                        }else {
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ResShowNovel>> call, @NonNull Throwable t) {
                        }
                });
    }

    public void setAdapterNewNovel(){
        newNovelAdapter = new NewNovelAdapter(getContext(),resShowNovels);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        rv_newNovel.setLayoutManager(layoutManager);
        rv_newNovel.setAdapter(newNovelAdapter);
    }

}
