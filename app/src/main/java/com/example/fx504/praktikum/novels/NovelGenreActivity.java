package com.example.fx504.praktikum.novels;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.fx504.praktikum.NovelGenreAdapter;
import com.example.fx504.praktikum.R;

public class NovelGenreActivity extends AppCompatActivity {

    static Button btn_action;
    static Button btn_comedy;
    static Button btn_romance;
    static Button btn_horror;
    static Button btn_sol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novelgenre);

        btn_action  = findViewById(R.id.btn_action);
        btn_comedy  = findViewById(R.id.btn_comedy);
        btn_romance = findViewById(R.id.btn_romance);
        btn_horror  = findViewById(R.id.btn_horror);
        btn_sol     = findViewById(R.id.btn_sol);

        button_clicked(btn_action,1);
        button_clicked(btn_comedy,2);
        button_clicked(btn_romance,3);
        button_clicked(btn_horror,4);
        button_clicked(btn_sol,5);

        setSwipe();

    }

//    public void testing(int val ){
//        switch (val){
//            case 1:
//                btn_action.setBackgroundColor(Color.parseColor("#222431"));
//                viewer.setBackgroundColor(Color.parseColor("#222431"));
//                break;
//
//
//        }
//    }

    public void button_clicked(final Button btn, final int val){
        final Intent intent = new Intent();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_action.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_comedy.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_romance.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_horror.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_sol.setBackgroundColor(Color.parseColor("#ffffff"));
                btn.setBackgroundColor(Color.parseColor("#222431"));
                intent.putExtra("ValButton",val);
            }
        });
    }

    public void colorPositionButton(int val){
        btn_action.setBackgroundColor(Color.parseColor("#ffffff"));
        btn_comedy.setBackgroundColor(Color.parseColor("#ffffff"));
        btn_romance.setBackgroundColor(Color.parseColor("#ffffff"));
        btn_horror.setBackgroundColor(Color.parseColor("#ffffff"));
        btn_sol.setBackgroundColor(Color.parseColor("#ffffff"));
        switch (val){
            case 1:
                btn_action.setBackgroundColor(Color.parseColor("#222431"));
                break;
            case 2:
                btn_comedy.setBackgroundColor(Color.parseColor("#222431"));
                break;
            case 3:
                btn_romance.setBackgroundColor(Color.parseColor("#222431"));
                break;
            case 4:
                btn_horror.setBackgroundColor(Color.parseColor("#222431"));
                break;
            case 5:
                btn_sol.setBackgroundColor(Color.parseColor("#222431"));
                break;

        }

    }

    private void setSwipe(){
        ViewPager view_pager = findViewById(R.id.view_pager);
        view_pager.setOffscreenPageLimit(1);
        NovelGenreAdapter novelGenreAdapter = new NovelGenreAdapter(getSupportFragmentManager());
        view_pager.setAdapter(novelGenreAdapter);
        view_pager.setCurrentItem(0);
    }

}
