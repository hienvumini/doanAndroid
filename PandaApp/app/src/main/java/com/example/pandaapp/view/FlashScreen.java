package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.pandaapp.R;

public class FlashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(0000);
                    Intent intent=new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
thread.start();

    }
}
