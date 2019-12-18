package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.pandaapp.Models.News;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.GlobalApplication;

public class NewsDetailActivity extends AppCompatActivity {
    WebView webView;
    GlobalApplication globalApplication;
    News news;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView imageViewback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        try {
            webView = (WebView) findViewById(R.id.webview_detail_News);
            swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.swiperefresh_News);
            globalApplication = (GlobalApplication) getApplicationContext();
            news = globalApplication.news;
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setDefaultTextEncodingName("utf-8");
            webView.loadData(news.getDetailNews(), "text/html; charset=utf-8", "utf-8");
        } catch (Exception e) {
            System.out.println("NewsDetailActivity: " + e.toString());
        }
        swipeRefreshLayout.setColorSchemeResources(R.color.color_pink2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        imageViewback=(ImageView) findViewById(R.id.img_back_News);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
