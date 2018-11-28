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

import com.example.fx504.praktikum.activities.GenreActivity;
import com.example.fx504.praktikum.R;
import com.example.fx504.praktikum.adapter.NewNovelAdapter;
import com.example.fx504.praktikum.adapter.NovelViewAdapter;
import com.example.fx504.praktikum.api.APIClient;
import com.example.fx504.praktikum.api.APIService;
import com.example.fx504.praktikum.model.Novel;
import com.example.fx504.praktikum.model.ResShowNovel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHome extends Fragment {

    ViewFlipper vf_novel;

    private List<Novel> myNovel;

    Intent intent;
    ImageView iv_allUpdate;
    ImageView iv_genre;

    View view;

    APIService apiService;

    NewNovelAdapter novelAdapter;
    List<ResShowNovel> resShowNovels = new ArrayList<>();
    RecyclerView rv_newNovel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);

       apiService=APIClient.getService();


        iv_allUpdate = view.findViewById(R.id.iv_allUpdate);
        iv_genre     = view.findViewById(R.id.iv_genre);

        //flipper Image
        setflipperImage();

        // Favorite Novel
        setFav();

        // Update Novel
//        setNovelUpdate();
        callAPI();

        // Go to GenreActivity
        goGenreActivity();

        return view;
    }

    //--------------------AD--------------------//
    public void setflipperImage(){
        vf_novel   = view.findViewById(R.id.vf_novel);
        int novel_cover[] = {R.drawable.cat_eye, R.drawable.dead_in_deep_water, R.drawable.strange_winds};
        for (int i =0; i<novel_cover.length;i++){
            animflipper(novel_cover[i]);
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
        RecyclerView recyclerView;
        NovelViewAdapter novelAdapter;

        myNovel = new ArrayList<>();

        for (int i =0; i<3; i++){
            myNovel.add(new Novel("Search Love", "Romance", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                    R.drawable.n_searchlove));
            myNovel.add(new Novel("Aullido", "Horror", "Description this Novel"
                    ,R.drawable.n_aullido));
        }
        recyclerView = view.findViewById(R.id.rc_fav);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        novelAdapter = new NovelViewAdapter(getContext(), myNovel);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(novelAdapter);
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

    public void setNovelUpdate(){
        RecyclerView recyclerView;
        NovelViewAdapter novelAdapter;

        myNovel = new ArrayList<>();

        for (int i =0; i<6; i++){
            myNovel.add(new Novel("Search Love", "Romance", "About someone who always find another to fix hem",R.drawable.n_searchlove));
            myNovel.add(new Novel("Aullido", "Horror", "Description this Novel",R.drawable.n_aullido));
        }
        recyclerView = view.findViewById(R.id.rc_novelRilis);

        novelAdapter = new NovelViewAdapter(getContext(), myNovel);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(novelAdapter);
    }

    public void callAPI(){
        apiService.getNovelList()
                .enqueue(new Callback<List<ResShowNovel>>() {
                    @Override
                    public void onResponse(Call<List<ResShowNovel>> call, Response<List<ResShowNovel>> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getContext(), "Sukses", Toast.LENGTH_SHORT).show();
                            resShowNovels.addAll(response.body());
                            setAdapter();
                        }else {
                            Toast.makeText(getContext(), "Response Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ResShowNovel>> call, Throwable t) {
                        Toast.makeText(getContext(), "API ERROR", Toast.LENGTH_SHORT).show();
                        Log.wtf("errorGetNovel",t.getMessage());
                    }
                });
    }

    public void setAdapter(){
        novelAdapter = new NewNovelAdapter(getContext(),resShowNovels);
        rv_newNovel = view.findViewById(R.id.rc_novelRilis);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        rv_newNovel.setLayoutManager(layoutManager);
        rv_newNovel.setAdapter(novelAdapter);
    }

}
