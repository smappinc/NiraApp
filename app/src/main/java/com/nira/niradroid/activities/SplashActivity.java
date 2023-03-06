package com.nira.niradroid.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.nira.niradroid.R;

// Organisation logo splash screen, appears after the actual splashscreen.
// Also loads the onboarding screen on first launch
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    SharedPreferences onBoardingScreen;



    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {

            onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
            boolean isFirstTime = onBoardingScreen.getBoolean("firstTime", true);

            if (isFirstTime){

                SharedPreferences.Editor editor = onBoardingScreen.edit();
                editor.putBoolean("firstTime", false);
                editor.apply();
                startActivity(new Intent(SplashActivity.this, MyCustomOnBoarder.class));
                finish();
            }
            else {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }

        }, 1300);

    }

}