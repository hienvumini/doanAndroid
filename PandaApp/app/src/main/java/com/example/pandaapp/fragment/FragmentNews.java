package com.example.pandaapp.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pandaapp.Models.News;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.Util.ChangeActivity;
import com.example.pandaapp.Util.OtherUltil;
import com.example.pandaapp.adapter.AdapterNews;
import com.example.pandaapp.view.MainActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentNews extends Fragment {
    ListView listViewNews;
    AdapterNews adapterNews;
    ArrayList<News> listNews;
    SwipeRefreshLayout swipeRefreshLayout;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    ProgressDialog progressDialog;
    ImageView imageViewback;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        try {
            init(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
        listViewNews.setAdapter(adapterNews);
        try {
            fetchData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            onListener();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }


    private void init(View view) throws Exception {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Đang tải tin tức");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgress(0);
        progressDialog.show();
        listViewNews = (ListView) view.findViewById(R.id.listviewNews_News);
        listNews = new ArrayList<>();
        adapterNews = new AdapterNews(getActivity(), R.id.listviewNews_News, listNews);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh_News);
        Animation animation_cycle = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_listview);
        listViewNews.setAnimation(animation_cycle);
        imageViewback=(ImageView) view.findViewById(R.id.img_back_NewsFrag);






    }

    private void onListener() throws Exception {
        swipeRefreshLayout.setColorSchemeResources(R.color.color_pink2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listNews.clear();
                try {
                    fetchData();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        listViewNews.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && scrollState == SCROLL_STATE_IDLE) {


                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisible, int visibleCount, int totalCount) {

                firstVisibleItem = firstVisibleItem;
                visibleItemCount = visibleCount;
                totalItemCount = totalCount;
            }
        });
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ChangeActivity.toActivity(getActivity(), MainActivity.class);
                ((MainActivity) getActivity()).changeNavigationBottomto(1);
            }
        });
    }

    private void fetchData() throws Exception {

        DataClient dataClient = APIUltils.getData();
        Call<ArrayList<News>> arrayListCall = dataClient.getNews(0);
        arrayListCall.enqueue(new Callback<ArrayList<News>>() {
            @Override
            public void onResponse(Call<ArrayList<News>> call, Response<ArrayList<News>> response) {
                listNews = response.body();
                adapterNews = new AdapterNews(getActivity(), R.id.listviewNews_News, listNews);
                listViewNews.setAdapter(adapterNews);
                progressDialog.hide();



            }

            @Override
            public void onFailure(Call<ArrayList<News>> call, Throwable t) {

            }
        });

    }


}
