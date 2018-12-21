package com.example.fx504.praktikum.novels;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fx504.praktikum.R;
import com.example.fx504.praktikum.api.APIClient;
import com.example.fx504.praktikum.api.APIService;
import com.github.barteksc.pdfviewer.PDFView;

public class NovelReadActivity extends AppCompatActivity {

    APIService apiService;

    PDFView pdfView;
    TextView textView;


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

//        webViewer();
    }

    public void webViewer(){
        WebView webview = findViewById(R.id.wv_doc);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webview.loadUrl("https://www.youtube.com");
    }
}
