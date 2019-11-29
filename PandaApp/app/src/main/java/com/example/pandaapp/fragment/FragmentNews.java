package com.example.pandaapp.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandaapp.Models.News;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.adapter.AdapterNews;
import com.example.pandaapp.view.ListProductSearchActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentNews extends Fragment {
    ListView listViewNews;
    AdapterNews adapterNews;
    ArrayList<News> listNews;
    SwipeRefreshLayout swipeRefreshLayout;
    public int firstVisibleItem, visibleItemCount,totalItemCount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        listViewNews = (ListView) view.findViewById(R.id.listviewNews_News);
        init(view);
        listViewNews.setAdapter(adapterNews);
        fetchData();
        onListener();


        return view;
    }



    private void init(View view) {
        listViewNews=(ListView) view.findViewById(R.id.listviewNews_News);
        listNews=new ArrayList<>();
        adapterNews=new AdapterNews(getActivity(),R.id.listviewNews_News,listNews);
        swipeRefreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.swiperefresh_News);

    }
    private void onListener() {
        swipeRefreshLayout.setColorSchemeResources(R.color.color_pink2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listNews.clear();
                fetchData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        listViewNews.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem==totalItemCount && scrollState==SCROLL_STATE_IDLE){

                    Toast.makeText(getActivity(), "Keo xong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisible, int visibleCount, int totalCount) {

                firstVisibleItem = firstVisibleItem;
                visibleItemCount = visibleCount;
                totalItemCount = totalCount;
            }
        });
    }

    private void fetchData() {

        DataClient dataClient= APIUltils.getData();
        Call<ArrayList<News>> arrayListCall=dataClient.getNews(0);
        arrayListCall.enqueue(new Callback<ArrayList<News>>() {
            @Override
            public void onResponse(Call<ArrayList<News>> call, Response<ArrayList<News>> response) {
//                Toast.makeText(getActivity(), "Tin tuc "+response.body().size(), Toast.LENGTH_SHORT).show();
                listNews=response.body();
                adapterNews=new AdapterNews(getActivity(),R.id.listviewNews_News,listNews);
                listViewNews.setAdapter(adapterNews);
            }

            @Override
            public void onFailure(Call<ArrayList<News>> call, Throwable t) {

            }
        });
    }


}
