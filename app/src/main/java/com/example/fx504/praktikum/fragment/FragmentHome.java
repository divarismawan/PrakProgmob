package com.example.fx504.praktikum.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.fx504.praktikum.activities.GenreActivity;
import com.example.fx504.praktikum.R;
import com.example.fx504.praktikum.adapter.FavNovelAdapter;
import com.example.fx504.praktikum.adapter.NewNovelAdapter;
import com.example.fx504.praktikum.api.APIClient;
import com.example.fx504.praktikum.api.APIService;
import com.example.fx504.praktikum.model.ResShowNovel;
import com.example.fx504.praktikum.model.RespFavMember;
import com.victor.loading.book.BookLoading;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHome extends Fragment {

    View view;

    ViewFlipper vf_novel;
    Intent intent;
    ImageView iv_allUpdate;
    ImageView iv_genre;

    APIService apiService;

    NewNovelAdapter newNovelAdapter;
    List<ResShowNovel> resShowNovels = new ArrayList<>();

    FavNovelAdapter favNovelAdapter;
    List<RespFavMember> respFavMembers = new ArrayList<>();

    RecyclerView rv_favNovel;
    RecyclerView rv_newNovel;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home,container,false);
        apiService=APIClient.getService();

//------------------ FIND ID ------------------//
        iv_allUpdate = view.findViewById(R.id.iv_allUpdate);
        iv_genre     = view.findViewById(R.id.iv_genre);


        rv_favNovel = view.findViewById(R.id.rc_fav);
        rv_newNovel = view.findViewById(R.id.rc_novelRilis);

        //flipper Image
        setflipperImage();

        // Favorite Novel
        setFav();

        // Update Novel
        newNovelView();

        // Go to GenreActivity
        goGenreActivity();

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

        apiService.getFavNovel()
                .enqueue(new Callback<List<RespFavMember>>() {
                    @Override
                    public void onResponse(Call<List<RespFavMember>> call, Response<List<RespFavMember>> response) {

                        if (response.isSuccessful()){
//                            Toast.makeText(getContext(), "Sukses", Toast.LENGTH_SHORT).show();
                            //get all data Novel from API SERVICE
                            assert response.body() != null;
                            respFavMembers.clear();
                            respFavMembers.addAll(response.body());
                            setAdapterFavNovel();
//                            loadingHomePage(false);
                        }else {
//                            Toast.makeText(getContext(), "Response Gagal", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<List<RespFavMember>> call, Throwable t) {
                    }

                });

    }

    public void setAdapterFavNovel(){
        favNovelAdapter = new FavNovelAdapter(getContext(), respFavMembers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        rv_favNovel.setLayoutManager(layoutManager);
        rv_favNovel.setAdapter(favNovelAdapter);
    }

    //--------------------BUTTON ACTION ICON--------------------//

    public void goGenreActivity(){
        iv_genre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(),GenreActivity.class);
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
//                            Toast.makeText(getContext(), "Sukses", Toast.LENGTH_SHORT).show();
                            //get all data Novel from API SERVICE
                            assert response.body() != null;
                            resShowNovels.clear();
                            resShowNovels.addAll(response.body());
                            setAdapterNewNovel();
                        }else {
//                            Toast.makeText(getContext(), "Response Gagal", Toast.LENGTH_SHORT).show();
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
