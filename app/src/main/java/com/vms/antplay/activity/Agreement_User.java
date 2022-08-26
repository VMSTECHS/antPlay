package com.vms.antplay.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.vms.antplay.R;
import com.vms.antplay.fragments.ComputerFragment;

public class Agreement_User extends AppCompatActivity {

    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_agreement_user);

        imageView = (ImageView) findViewById(R.id.img_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getSupportFragmentManager().beginTransaction().add(android.R.id.content, new ComputerFragment()).commit();
//
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.container, fragmentB)
//                        .addToBackStack(MyFragmentA.class.getSimpleName())
//                        .commit();

                finish();
            }
        });

    }
}