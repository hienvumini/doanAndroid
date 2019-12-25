package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.pandaapp.R;

public class FlashScreen extends AppCompatActivity {
    ImageView imageViewlogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);
        imageViewlogo=(ImageView) findViewById(R.id.logo_Secreen_Flash);
        Animation animationlogo= AnimationUtils.loadAnimation(FlashScreen.this,R.anim.animation_logo);
        imageViewlogo.setAnimation(animationlogo);
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(6000);
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
