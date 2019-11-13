package com.example.pandaapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.pandaapp.Models.Order;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.adapter.AdapterOrderShop;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentOrderShop extends Fragment {
    ListView listView;
    AdapterOrderShop adapterOrderShop;
    ArrayList<Order> lstOrder;
    GlobalApplication globalApplication;
    callbackActivity callbackActivity;
    int statusOrrder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragmentordershop, container, false);
        init(view);


        callbackActivity = (callbackActivity) getActivity();

        switch (callbackActivity.gettab()) {
            case 0:
                statusOrrder = 1;
                break;
            case 1:
                statusOrrder = 2;
                break;
            case 2:
                statusOrrder = 3;
                break;
            case 3:
                statusOrrder = 4;
                break;


        }
        lstOrder.clear();
        adapterOrderShop = new AdapterOrderShop(getActivity(), R.id.listviewOrderDetail, lstOrder);
        adapterOrderShop.notifyDataSetChanged();
        getOrder(view, statusOrrder);
        return view;

    }


    private void init(View view) {
        listView = (ListView) view.findViewById(R.id.listviewOrder);
        lstOrder = new ArrayList<>();
        adapterOrderShop = new AdapterOrderShop(getActivity(), R.id.listviewOrder, lstOrder);

        globalApplication = (GlobalApplication) getActivity().getApplicationContext();
        adapterOrderShop.clear();

    }

    private void getOrder(View view, int stt) {
        DataClient dataClient = APIUltils.getData();
        Call<ArrayList<Order>> listCall = dataClient.getOrderShop(globalApplication.account.getIdShop(), stt);
        listCall.enqueue(new Callback<ArrayList<Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                Log.d("A1A1", "onResponse: " + response.body());

                lstOrder = response.body();
                adapterOrderShop = new AdapterOrderShop(getActivity(), R.id.listviewOrder, lstOrder);
                listView.setAdapter(adapterOrderShop);


            }

            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                Log.d("A2A3", "onResponse: " + t.toString());

            }
        });
    }



    public interface callbackActivity {
        public int gettab();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commitAllowingStateLoss();
        }
    }


}
