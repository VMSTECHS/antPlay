package com.vms.antplay.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vms.antplay.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArcadeFragment#} factory method to
 * create an instance of this fragment.
 */
public class ArcadeFragment extends Fragment {


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

        return view;
    }
}