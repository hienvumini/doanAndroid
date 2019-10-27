package com.example.pandaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {
    FragmentTransaction fragmentTransaction;
    Fragment fragment;
    LoginFragment loginFragment = new LoginFragment();
    SignupFragment signupFragment = new SignupFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        openFragment(loginFragment);

    }

    public void toSignUpFragment() {
        openFragment(signupFragment);
    }
    public void toSigninFragment() {
        openFragment(loginFragment);
    }

    private void openFragment(final Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameSigin, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


}

