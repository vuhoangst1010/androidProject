package com.example.covid_19.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.covid_19.R;

public class NewsDetail extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }
        String url = bundle.get("news_url").toString().trim();
        Toast.makeText(this,url,Toast.LENGTH_SHORT).show();
        webView=findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}