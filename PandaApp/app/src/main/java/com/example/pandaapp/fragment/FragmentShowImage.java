package com.example.pandaapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pandaapp.R;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.adapter.AdapterViewPagger;

import java.util.ArrayList;


public class FragmentShowImage extends Fragment {
    ViewPager viewPager;
    AdapterViewPagger adapterViewPagger;
    ArrayList<String> arrImages;
    GlobalApplication globalApplication;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_image, container, false);
        try {
            viewPager = (ViewPager) view.findViewById(R.id.viewpager_ShowImage);
            globalApplication = (GlobalApplication) getActivity().getApplicationContext();
            arrImages = globalApplication.product.getImages();
            adapterViewPagger = new AdapterViewPagger(getActivity(), arrImages);
            viewPager.setAdapter(adapterViewPagger);
        } catch (Exception e){
            System.out.println(e.toString());

        }


        return view;
    }

}
