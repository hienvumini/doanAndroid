package com.example.pandaapp.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdapterTabOrder extends FragmentStatePagerAdapter {
    List<String> listTab;
    List<Fragment> listFragment;



    public AdapterTabOrder(@NonNull FragmentManager fm, List<String> listtabs,List<Fragment> lstFragment) {

        super(fm);
        this.listTab=listtabs;
        this.listFragment=lstFragment;



    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (listFragment != null) {
            return listFragment.get(position);
        }
        return new Fragment();
    }

    @Override
    public int getCount() {
        return listTab.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTab.get(position);
    }
}
