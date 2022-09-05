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
import com.vms.antplay.adapter.Faq_Adapter;
import com.vms.antplay.model.FaqModal;

import java.util.ArrayList;

public class FaqFargment extends Fragment {


    RecyclerView recyclerView;
    private ArrayList<FaqModal> faqModalArrayList;

    public FaqFargment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faq_fargment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_faq);


        faqModalArrayList = new ArrayList<>();
        faqModalArrayList.add(new FaqModal(getString(R.string.question1), getString(R.string.answer1)));
        faqModalArrayList.add(new FaqModal(getString(R.string.question2), getString(R.string.answer1)));
        faqModalArrayList.add(new FaqModal(getString(R.string.question3), getString(R.string.answer1)));
        faqModalArrayList.add(new FaqModal(getString(R.string.question4), getString(R.string.answer1)));
        Faq_Adapter adapter = new Faq_Adapter(requireContext(),faqModalArrayList);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);



        return view;
    }
}