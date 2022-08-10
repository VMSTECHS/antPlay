package com.vms.antplay.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vms.antplay.R;

public class SpeedTestActivity extends AppCompatActivity {

    Button buttonBack;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_test);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black, this.getTheme()));

        buttonBack = (Button) findViewById(R.id.btn_back_speed);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SpeedTestActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}