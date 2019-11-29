package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.pandaapp.Models.News;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.GlobalApplication;

public class NewsDetailActivity extends AppCompatActivity {
    WebView webView;
    GlobalApplication globalApplication;
    News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        webView=(WebView) findViewById(R.id.webview_detail_News);
        globalApplication=(GlobalApplication) getApplicationContext();
        news=globalApplication.news;

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");

        webView.loadData(news.getDetailNews(), "text/html; charset=utf-8", "utf-8");
       // webView.loadData(news.getDetailNews(), "text/html", "utf-8");

    }
}
