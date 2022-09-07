package com.vms.antplay.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.vms.antplay.R;

public class AboutUs extends AppCompatActivity {

    LinearLayout linear_back;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccentDark_light, this.getTheme()));

        callMethods();
    }

    private void callMethods() {

        linear_back = (LinearLayout) findViewById(R.id.back_linear_about);
        linear_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}