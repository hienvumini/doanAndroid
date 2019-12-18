package com.example.pandaapp.Util;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pandaapp.Models.Account;
import com.example.pandaapp.R;

import java.io.Serializable;

public class FragmentUtils {
    public static void openFragment(final Fragment fragment,FragmentManager fragmentManager,int layout) {
         FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
    public static void openFragment(final Fragment fragment, FragmentManager fragmentManager, int layout, Account account, int mode) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(layout, fragment);
        transaction.addToBackStack(null);
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", account);
        bundle.putInt("mode",mode);
        fragment.setArguments(bundle);
        transaction.commit();

    }
}
