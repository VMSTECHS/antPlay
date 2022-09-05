package com.vms.antplay.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vms.antplay.R;
import com.vms.antplay.adapter.ArcadeAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArcadeFragment#} factory method to
 * create an instance of this fragment.
 */
public class ArcadeFragment extends Fragment {
    private ArrayList<String> arcadeList = new ArrayList<>();
    RecyclerView arcadeRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_arcade, container, false);
        arcadeRecyclerView = view.findViewById(R.id.rvArcade);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        prepareArcadeList();
        populateAdapter();

        setLongPressListener();
    }

    private void setLongPressListener() {
        arcadeRecyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }


    private void prepareArcadeList() {
        arcadeList.add("Froza Horizon 4");
        arcadeList.add("Arkham Knight");
        arcadeList.add("Destiny 2");
        arcadeList.add("CS:GO");
        arcadeList.add("Minecraft");

        arcadeList.add("God of War");
        arcadeList.add("GTA V");
        arcadeList.add("Halo Infinite");
        arcadeList.add("Fortnite");
        arcadeList.add("assassin's creed");

        arcadeList.add("Froza Horizon 4");
        arcadeList.add("Arkham Knight");
        arcadeList.add("Destiny 2");
        arcadeList.add("CS:GO");
        arcadeList.add("Minecraft");

        arcadeList.add("God of War");
        arcadeList.add("GTA V");
        arcadeList.add("Halo Infinite");
        arcadeList.add("Fortnite");
        arcadeList.add("GTA V");
        arcadeList.add("assassin's creed");
    }

    private void populateAdapter() {
        ArcadeAdapter adapter = new ArcadeAdapter(arcadeList, getContext());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        arcadeRecyclerView.setLayoutManager(layoutManager);
       // arcadeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        arcadeRecyclerView.setAdapter(adapter);
    }
}