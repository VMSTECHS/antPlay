package com.vms.antplay.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vms.antplay.R;
import com.vms.antplay.activity.MainActivity;
import com.vms.antplay.activity.ProfileActivity;
import com.vms.antplay.activity.SpeedTestActivity;
import com.vms.antplay.adapter.ImageAdapter;
import com.vms.antplay.dialog.BottomSheetDialog;
import com.vms.antplay.model.ImageModel;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private ArrayList<ImageModel> imageModelArrayList;
    ImageView imagePlay;
    CardView profile_card;
    LinearLayoutCompat llTimer;
    TextView txtTimer;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        imagePlay = (ImageView) view.findViewById(R.id.img_play);
        profile_card = (CardView) view.findViewById(R.id.card_profile);

        llTimer = (LinearLayoutCompat) view.findViewById(R.id.llTimer);
        txtTimer = view.findViewById(R.id.txtTimer);

        imagePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SpeedTestActivity.class);
                startActivity(i);

            }
        });
        profile_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ProfileActivity.class);
                startActivity(i);

            }
        });

        imageModelArrayList = new ArrayList<>();
        imageModelArrayList.add(new ImageModel("Alexa", R.drawable.game_one));
        imageModelArrayList.add(new ImageModel("TFS", R.drawable.game_three));
        imageModelArrayList.add(new ImageModel("Cinp", R.drawable.game_two));
        imageModelArrayList.add(new ImageModel("Cinp", R.drawable.loginwithlogo));
        imageModelArrayList.add(new ImageModel("Cinp", R.drawable.game_one));
        imageModelArrayList.add(new ImageModel("Cinp", R.drawable.game_two));
        imageModelArrayList.add(new ImageModel("Cinp", R.drawable.game_two));
        imageModelArrayList.add(new ImageModel("Cinp", R.drawable.game_two));

        ImageAdapter imageAdapter = new ImageAdapter(requireContext(), imageModelArrayList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(imageAdapter);

        llTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
                bottomSheetDialog.show(getActivity().getSupportFragmentManager(), "BackgrountSheet");
            }
        });

        return view;
    }
}