package com.example.fx504.praktikum.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fx504.praktikum.R;
import com.example.fx504.praktikum.adapter.FavoriteAdapter;
import com.example.fx504.praktikum.api.APIClient;
import com.example.fx504.praktikum.api.APIService;
import com.example.fx504.praktikum.model.RespFavorite;
import com.example.fx504.praktikum.model.SharePref;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentFavorites extends Fragment {

    SharePref sharePref;

    int id_user;

    View view;
    RecyclerView rv_favorite;

    APIService apiService;

    FavoriteAdapter favoriteAdapter;
    List<RespFavorite> favorites = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorites,container,false);

        sharePref = new SharePref(Objects.requireNonNull(getContext()));

        id_user = sharePref.getDataInt(SharePref.KEY_ID);

        apiService = APIClient.getService();

        rv_favorite = view.findViewById(R.id.rv_favorite);

//        setMemberFav();

        setFavorite();

        return view;
    }
    public void setFavorite(){
        apiService.NovelFavorite(id_user)
                .enqueue(new Callback<List<RespFavorite>>() {
                    @Override
                    public void onResponse(Call<List<RespFavorite>> call, Response<List<RespFavorite>> response) {
                        if (response.isSuccessful()){
                            favorites.clear();
                            assert response.body() != null;
                            favorites.addAll(response.body());
                            setAdapterFavNovel();
                        }else {
                        }
                    }

                    @Override
                    public void onFailure(Call<List<RespFavorite>> call, Throwable t) {

                    }
                });
    }

    public void setAdapterFavNovel(){
        favoriteAdapter = new FavoriteAdapter(getContext(), favorites);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rv_favorite.setLayoutManager(gridLayoutManager);
        rv_favorite.setAdapter(favoriteAdapter);
    }

}
