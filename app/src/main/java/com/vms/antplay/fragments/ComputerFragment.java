package com.vms.antplay.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vms.antplay.R;
import com.vms.antplay.activity.Agreement_User;
import com.vms.antplay.adapter.Computer_available_Adapter;
import com.vms.antplay.api.APIClient;
import com.vms.antplay.api.RetrofitAPI;
import com.vms.antplay.model.Computer_availableModal;
import com.vms.antplay.model.requestModal.LoginRequestModal;
import com.vms.antplay.model.responseModal.GetVMResponseModal;
import com.vms.antplay.model.responseModal.LoginResponseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ComputerFragment extends Fragment {

    RecyclerView recyclerView;
    private ArrayList<Computer_availableModal> computer_availableModals;
    LinearLayout linearAgree;

    public ComputerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_computer, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_computers);

        callGetVmApi();
//        linearAgree = ( LinearLayout) view.findViewById(R.id.linear_agreement);
//
//        linearAgree.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getActivity(), Agreement_User.class);
//                getContext().startActivity(i);
//            }
//        });

        computer_availableModals = new ArrayList<>();
        computer_availableModals.add(new Computer_availableModal("Dex-70f5", "Rakesh Computer", R.drawable.computers_pc));
        computer_availableModals.add(new Computer_availableModal("Dex-70f5", "Rakesh Computer", R.drawable.computers_pc));
        computer_availableModals.add(new Computer_availableModal("Dex-70f5", "Rakesh Computer", R.drawable.computers_pc));

        Computer_available_Adapter adapter = new Computer_available_Adapter(requireContext(), computer_availableModals);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        return view;

    }

    private void callGetVmApi() {

        RetrofitAPI retrofitAPI = APIClient.getRetrofitInstance().create(RetrofitAPI.class);
        GetVMResponseModal getVMResponseModal = new GetVMResponseModal();
        Call<GetVMResponseModal> call = retrofitAPI.getVM("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjYzMzA1MTc4LCJpYXQiOjE2NjMyMTg3NzgsImp0aSI6ImE4ZmU1YTIwZTg4NzRlZjc5MjdmNzE3Nzg1ZjljYTIwIiwidXNlcl9pZCI6MjY3fQ.7Aunx1A4d33cywl8Fxlk_YhKM58uu1kBfUhHwcuuDWk");
        call.enqueue(new Callback<GetVMResponseModal>() {
            @Override
            public void onResponse(Call<GetVMResponseModal> call, Response<GetVMResponseModal> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("Hello Get VM", "" + response.body().getVmname());

                    computer_availableModals = new ArrayList<>();
                    computer_availableModals.add(
                            new Computer_availableModal(
                                    response.body().getVmname(),
                                    response.body().getStatus(),
                                    R.drawable.computers_pc));


                    Computer_available_Adapter adapter = new Computer_available_Adapter(requireContext(), computer_availableModals);

                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<GetVMResponseModal> call, Throwable t) {
                Log.e("Hello Get VM", "Failure");
            }
        });
    }
}