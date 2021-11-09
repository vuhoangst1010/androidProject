package com.example.covid_defender.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.covid_defender.R;

/**
 * @author PhuocNDT
 */
public class WelcomeActivity extends AppCompatActivity {

    ImageView imvLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);
        imvLogo = findViewById(R.id.imvLogo);

        imvLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_in_zoom_in));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imvLogo.startAnimation(AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.splash_out));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imvLogo.setVisibility(View.GONE);
                        startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
                    }
                }, 500);
            }
        }, 1500);
    }
}