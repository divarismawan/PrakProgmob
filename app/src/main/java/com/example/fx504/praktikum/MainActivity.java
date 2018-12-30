package com.example.fx504.praktikum;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fx504.praktikum.api.APIClient;
import com.example.fx504.praktikum.api.APIService;
import com.example.fx504.praktikum.fragment.FragmentFavorites;
import com.example.fx504.praktikum.fragment.FragmentHome;
import com.example.fx504.praktikum.fragment.FragmentProfile;
import com.example.fx504.praktikum.model.ResponseApi;
import com.victor.loading.book.BookLoading;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    APIService apiService;

    BottomNavigationView btn_navView;
    Fragment fragment;

    Handler handler;
    TextView tv_offline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = APIClient.getService();

        handler = new Handler();

//        cekKoneksi();
        loadingHomePage(false);



        btn_navView = findViewById(R.id.btn_navView);
        btn_navView.setOnNavigationItemSelectedListener(navListener);
        tv_offline = findViewById(R.id.tv_offline);

        fragment = new FragmentHome();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_layout, fragment).commit();





    }

    public void cekKoneksi(){
        apiService.CheckConncetion()
                .enqueue(new Callback<ResponseApi>() {
                    @Override
                    public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                        if (response.isSuccessful()){
                            loadingHomePage(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseApi> call, Throwable t) {
                        loadingHomePage(true);
                        final TextView tv_loading = findViewById(R.id.tv_loading);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tv_loading.setText("Connection fail, please check your connection");
                                tv_offline.setText("Offline Mode");
                                setOfflineMode();
                            }
                        },2000);

                    }
                });
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                     fragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            fragment = new FragmentHome();
                            break;
                        case R.id.nav_fav:
                            fragment = new FragmentFavorites();
                            break;
                        case R.id.nav_profile:
                            fragment = new FragmentProfile();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_layout, fragment).commit();
                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                        finishAffinity();
                        System.exit(0);
                    }
                }).create().show();
    }


    public void loadingHomePage(Boolean status){
        LinearLayout loadingHomepage = findViewById(R.id.layout_loadingHome);
        BookLoading bookLoading      = findViewById(R.id.book_loading);
        RelativeLayout layout_home   = findViewById(R.id.layout_main);

        if (status){
            layout_home.setVisibility(View.INVISIBLE);
            loadingHomepage.setVisibility(View.VISIBLE);
            bookLoading.start();
        }else {
            layout_home.setVisibility(View.VISIBLE);
            loadingHomepage.setVisibility(View.INVISIBLE);
            bookLoading.stop();
        }
    }

    public void setOfflineMode(){
        tv_offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OfflineActivity.class);
                startActivity(intent);

            }
        });
    }

}
