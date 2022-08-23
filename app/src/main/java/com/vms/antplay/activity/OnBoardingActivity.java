package com.vms.antplay.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.vms.antplay.R;
import com.vms.antplay.adapter.OnBoardingPagerAdapter;
import com.vms.antplay.adapter.ZoomOutPageTransformer;

public class OnBoardingActivity extends AppCompatActivity implements View.OnClickListener {
    ViewPager viewPager;
    TabLayout tabLayout;
    TextView txtSkip, txtNext;
    int currentItem;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        txtNext = findViewById(R.id.txtNext);
        txtSkip = findViewById(R.id.txtSkip);
        context = this;

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new OnBoardingPagerAdapter(getSupportFragmentManager()));
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.getCurrentItem();

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        setOnClickListener();
        onPageChangeListener();
    }

    private void onPageChangeListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentItem = position;
                Log.d("PAGER", "Current Position: " + position);
                if (position == 5) {
                    txtNext.setText(getResources().getString(R.string.finish));
                    txtSkip.setVisibility(View.INVISIBLE);
                } else {
                    txtNext.setText(getResources().getString(R.string.next));
                    txtSkip.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setOnClickListener() {
        txtNext.setOnClickListener(OnBoardingActivity.this);
        txtSkip.setOnClickListener(OnBoardingActivity.this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtSkip:
                Intent intent = new Intent(OnBoardingActivity.this, LoginActivity.class);
                startActivity(intent);
                updateSharedPreferences(false);
                // Toast.makeText(OnBoardingActivity.this, "Close This Activity", Toast.LENGTH_SHORT).show();
                break;
            case R.id.txtNext:
                movePagerToNextPage();
                break;
            default:
        }
    }


    private void movePagerToNextPage() {
        int currentItem = viewPager.getCurrentItem();
        currentItem++;
        Log.d("PAGER", "Current Item: " + currentItem);
        if (currentItem == 6) {
            Intent intent = new Intent(OnBoardingActivity.this, LoginActivity.class);
            startActivity(intent);
            updateSharedPreferences(true);
        } else {
            viewPager.setCurrentItem(currentItem);
        }

    }

    private void updateSharedPreferences(boolean isNotFirstTime) {

        SharedPreferences preferences = context.getSharedPreferences("com.vms.antplay",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(getString(R.string.is_first_time),isNotFirstTime);
        editor.apply();
    }

}