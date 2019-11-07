package com.example.pandaapp.Util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pandaapp.R;

public class FragmentUtils {
    public static void openFragment(final Fragment fragment,FragmentManager fragmentManager,int layout) {
         FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
