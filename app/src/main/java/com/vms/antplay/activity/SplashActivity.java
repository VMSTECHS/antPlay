package com.vms.antplay.activity;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.vms.antplay.R;
import com.vms.antplay.utils.SharedPreferenceUtils;

import java.util.prefs.Preferences;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        boolean isNotFirstTime=  SharedPreferenceUtils.getBoolean(SplashActivity.this,getString(R.string.is_first_time));
        /*SharedPreferences sharedPreferences = this.getSharedPreferences("com.vms.antplay", Context.MODE_PRIVATE);
        boolean isNotFirstTime = sharedPreferences.getBoolean(getString(R.string.is_first_time), false);*/

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                       // String msg = getString(R.string.msg_token_fmt, token);
                        Log.e(TAG, token);
                       // Toast.makeText(SplashActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

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