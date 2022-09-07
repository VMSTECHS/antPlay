package com.vms.antplay.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vms.antplay.R;

public class ChangePassword extends AppCompatActivity {

    LinearLayout backLinear, logoutLinear;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccentDark_light, this.getTheme()));
        backLinear =(LinearLayout) findViewById(R.id.back_linear);
        logoutLinear =(LinearLayout) findViewById(R.id.logout_linear);
        backLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChangePassword.this, ProfileActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}