package com.example.fx504.praktikum.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.fx504.praktikum.R;
import com.example.fx504.praktikum.adapter.GenreAdapter;
import com.example.fx504.praktikum.adapter.NovelViewAdapter;
import com.example.fx504.praktikum.model.Genre;
import com.example.fx504.praktikum.model.Novel;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {

    ViewFlipper vf_novel;
    List<Novel> myNovel;
    List<Genre> myGenre;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);


        vf_novel   = view.findViewById(R.id.vf_novel);
        int novel_cover[] = {R.drawable.cat_eye, R.drawable.dead_in_deep_water, R.drawable.strange_winds};
        for (int i =0; i<novel_cover.length;i++){
            flipperImage(novel_cover[i]);
        }

        // Favorite Novel
        setFav(view);

        // Genre Novel
        setMyGenre(view);

        // Update Novel
        setNovelUpdate(view);


        return view;
    }

    public void flipperImage(int img){
        ImageView imageView= new ImageView(getContext());
        imageView.setBackgroundResource(img);

        vf_novel.addView(imageView);
        vf_novel.setFlipInterval(2500); //1000 = 1 detik
        vf_novel.setAutoStart(true);

        //animation
        vf_novel.setInAnimation(getContext(), android.R.anim.slide_in_left);
        vf_novel.setOutAnimation(getContext(), android.R.anim.slide_out_right);
    }

    public void setFav(View v){
        RecyclerView recyclerView;
        NovelViewAdapter novelAdapter;

        myNovel = new ArrayList<>();

        for (int i =0; i<3; i++){
            myNovel.add(new Novel("Search Love", "Romance", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                    R.drawable.n_searchlove));
            myNovel.add(new Novel("Aullido", "Horror", "Description this Novel"
                    ,R.drawable.n_aullido));
        }
        recyclerView = v.findViewById(R.id.rc_fav);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        novelAdapter = new NovelViewAdapter(getContext(), myNovel);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(novelAdapter);
    }

    public void setMyGenre(View v){
        RecyclerView recyclerView;
        GenreAdapter genreAdapter;

        myGenre = new ArrayList<>();

        myGenre.add(new Genre("Action", R.drawable.gr_action));
        myGenre.add(new Genre("Comedy", R.drawable.gr_comedy));
        myGenre.add(new Genre("Fantasy", R.drawable.gr_fantas));
        myGenre.add(new Genre("History", R.drawable.gr_history));
        myGenre.add(new Genre("Horror", R.drawable.gr_horror));
        myGenre.add(new Genre("Romance", R.drawable.gr_romace));
        myGenre.add(new Genre("Sci fi", R.drawable.gr_scifi));
        myGenre.add(new Genre("Sport", R.drawable.gr_sport));

        recyclerView = v.findViewById(R.id.rc_genre);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        genreAdapter = new GenreAdapter(getContext(),myGenre);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(genreAdapter);

    }

    public void setNovelUpdate(View v){
        RecyclerView recyclerView;
        NovelViewAdapter novelAdapter;

        myNovel = new ArrayList<>();

        for (int i =0; i<6; i++){
            myNovel.add(new Novel("Search Love", "Romance", "About someone who always find another to fix hem",R.drawable.n_searchlove));
            myNovel.add(new Novel("Aullido", "Horror", "Description this Novel",R.drawable.n_aullido));
        }
        recyclerView = v.findViewById(R.id.rc_novelRilis);


        novelAdapter = new NovelViewAdapter(getContext(), myNovel);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(novelAdapter);
    }

}
