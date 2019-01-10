package com.example.fx504.praktikum.novels;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fx504.praktikum.R;
import com.example.fx504.praktikum.adapter.FavoriteAdapter;
import com.example.fx504.praktikum.adapter.GenreAdapter;
import com.example.fx504.praktikum.adapter.NewNovelAdapter;
import com.example.fx504.praktikum.api.APIClient;
import com.example.fx504.praktikum.api.APIService;
import com.example.fx504.praktikum.model.ResShowNovel;
import com.example.fx504.praktikum.model.RespAddNovel;
import com.example.fx504.praktikum.model.RespFavorite;
import com.example.fx504.praktikum.model.RespGenre;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NovelGenreActivity extends AppCompatActivity {

    APIService apiService;

    GenreAdapter genreAdapter;
    List<RespGenre> respGenres = new ArrayList<>();

    RecyclerView rv_genre;

    static Button btn_action;
    static Button btn_comedy;
    static Button btn_romance;
    static Button btn_horror;
    static Button btn_sol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novelgenre);

        apiService = APIClient.getService();

        btn_action  = findViewById(R.id.btn_action);
        btn_comedy  = findViewById(R.id.btn_comedy);
        btn_romance = findViewById(R.id.btn_romance);
        btn_horror  = findViewById(R.id.btn_horror);
        btn_sol     = findViewById(R.id.btn_sol);

        button_genre(btn_action,"Action");
        button_genre(btn_comedy,"Comedy");
        button_genre(btn_romance,"Romance");
        button_genre(btn_horror,"Horror");
        button_genre(btn_sol,"Sci Fi");

        rv_genre = findViewById(R.id.rv_genre);

        novelbyGenre("Action");

    }

    public void novelbyGenre(final String genre){
       apiService.NovelByGenre(genre)
               .enqueue(new Callback<List<RespGenre>>() {
                   @Override
                   public void onResponse(Call<List<RespGenre>> call, Response<List<RespGenre>> response) {
                       respGenres.clear();
                       assert response.body() != null;
                       respGenres.addAll(response.body());
                       setAdapter();
                   }

                   @Override
                   public void onFailure(Call<List<RespGenre>> call, Throwable t) {
                       Log.d("kosong","pesannya :"+ t.getMessage());
                       Toast.makeText(NovelGenreActivity.this, "Nyerah", Toast.LENGTH_SHORT).show();
                   }
               });
    }

    public void setAdapter(){
        genreAdapter = new GenreAdapter(this,respGenres);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
        rv_genre.setLayoutManager(layoutManager);
        rv_genre.setAdapter(genreAdapter);
    }

    public void button_genre(final Button btn, final String genre){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            btn_action.setBackgroundColor(Color.parseColor("#ffffff"));
            btn_comedy.setBackgroundColor(Color.parseColor("#ffffff"));
            btn_romance.setBackgroundColor(Color.parseColor("#ffffff"));
            btn_horror.setBackgroundColor(Color.parseColor("#ffffff"));
            btn_sol.setBackgroundColor(Color.parseColor("#ffffff"));
            btn.setBackgroundColor(Color.parseColor("#222431"));
            novelbyGenre(genre);
                Toast.makeText(NovelGenreActivity.this, genre, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
