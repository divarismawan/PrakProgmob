package com.example.fx504.praktikum.activities;

import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fx504.praktikum.R;
import com.example.fx504.praktikum.admin.AddNovel;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import retrofit2.http.Url;

public class NovelReadActivity extends AppCompatActivity {

    Intent intent;
    PDFView pdfView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novelread);

        pdfView = findViewById(R.id.pdf_View);
        textView = findViewById(R.id.textView);


        setPdfView();
    }


   public void setPdfView(){
        int id = getIntent().getIntExtra("id_novel",0);

   }



}
