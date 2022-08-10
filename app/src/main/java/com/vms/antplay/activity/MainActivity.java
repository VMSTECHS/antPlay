package com.vms.antplay.activity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vms.antplay.R;
import com.vms.antplay.adapter.ImageAdapter;
import com.vms.antplay.model.ImageModel;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

   private ArrayList<ImageModel> imageModelArrayList;
   ImageView imagePlay;
   CardView profile_card;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccentDark_light, this.getTheme()));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        imagePlay = (ImageView) findViewById(R.id.img_play);
        profile_card = (CardView) findViewById(R.id.card_profile);
        imagePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SpeedTestActivity.class);
                startActivity(i);
                finish();
            }
        });
        profile_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
                finish();
            }
        });

        imageModelArrayList = new ArrayList<>();
        imageModelArrayList.add(new ImageModel("Alexa",  R.drawable.game_one));
        imageModelArrayList.add(new ImageModel("TFS",  R.drawable.game_three));
        imageModelArrayList.add(new ImageModel("Cinp",  R.drawable.game_two));
        imageModelArrayList.add(new ImageModel("Cinp",  R.drawable.loginwithlogo));
        imageModelArrayList.add(new ImageModel("Cinp",  R.drawable.game_one));
        imageModelArrayList.add(new ImageModel("Cinp",  R.drawable.game_two));
        imageModelArrayList.add(new ImageModel("Cinp",  R.drawable.game_two));
        imageModelArrayList.add(new ImageModel("Cinp",  R.drawable.game_two));

        ImageAdapter imageAdapter = new ImageAdapter(this, imageModelArrayList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(imageAdapter);
    }
}