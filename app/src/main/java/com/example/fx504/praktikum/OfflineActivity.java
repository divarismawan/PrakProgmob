package com.example.fx504.praktikum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.victor.loading.newton.NewtonCradleLoading;

public class OfflineActivity extends AppCompatActivity {

    NewtonCradleLoading newtonCradleLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);

        newtonCradleLoading = findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.start();


    }
}
