package com.example.fx504.praktikum.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fx504.praktikum.R;
import com.example.fx504.praktikum.admin.AddNovel;
import com.example.fx504.praktikum.api.APIClient;
import com.example.fx504.praktikum.api.APIService;
import com.example.fx504.praktikum.api.APIUrl;
import com.example.fx504.praktikum.model.ResGetById;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

public class NovelReadActivity extends AppCompatActivity {

    APIService apiService;

    PDFView pdfView;
    TextView textView;

    Url url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novelread);

        apiService = APIClient.getService();

        pdfView = findViewById(R.id.pdf_View);
        textView = findViewById(R.id.textView);

        String uri = getIntent().getStringExtra("story_novel");
        Toast.makeText(this, uri, Toast.LENGTH_SHORT).show();
        Log.wtf("grideee",uri);

        pdfView.fromAsset("ijis03b.pdf").load();
    }


   public void setPdfView(){
        int id = getIntent().getIntExtra("id_novel",0);
        apiService.NovelGetById(id)
                .enqueue(new Callback<ResGetById>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void onResponse(Call<ResGetById> call, Response<ResGetById> response) {
                        if (response.isSuccessful()){
//                            url = APIUrl.BASE_DATA_URL + response.body().getNovelStory();
//                            pdfView.fromBytes(url).load();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResGetById> call, Throwable t) {

                    }
                });

   }



}
