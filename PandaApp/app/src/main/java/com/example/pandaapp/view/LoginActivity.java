package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.pandaapp.R;
import com.example.pandaapp.fragment.FragmentLogin;
import com.example.pandaapp.fragment.FragmentSignup;

public class LoginActivity extends AppCompatActivity {
    FragmentTransaction fragmentTransaction;
    Fragment fragment;
    FragmentLogin fragmentLogin = new FragmentLogin();
    FragmentSignup fragmentSignup = new FragmentSignup();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        openFragment(fragmentLogin);

    }

    public void toSignUpFragment() {
        openFragment(fragmentSignup);
    }
    public void toSigninFragment() {
        openFragment(fragmentLogin);
    }

    private void openFragment(final Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameSigin, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


}

