package com.vms.antplay.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.vms.antplay.R;

import java.util.prefs.Preferences;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.vms.antplay", Context.MODE_PRIVATE);
        boolean isNotFirstTime = sharedPreferences.getBoolean(getString(R.string.is_first_time), false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isNotFirstTime){
                    // This method will be execute once the timer is over
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    // This method will be execute once the timer is over
                    Intent i = new Intent(SplashActivity.this, OnBoardingActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        }, 4000);
    }
}