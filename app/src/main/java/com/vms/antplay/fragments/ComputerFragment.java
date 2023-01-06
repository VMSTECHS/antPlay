package com.vms.antplay.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vms.antplay.R;
import com.vms.antplay.activity.LoginActivity;
import com.vms.antplay.activity.ProfileActivity;
import com.vms.antplay.adapter.Computer_available_Adapter;
import com.vms.antplay.api.APIClient;
import com.vms.antplay.api.RetrofitAPI;
import com.vms.antplay.interfaces.ShutdownVMListener;
import com.vms.antplay.model.requestModal.ShutDownVMRequestModal;
import com.vms.antplay.model.responseModal.GetVMResponseModal;
import com.vms.antplay.model.responseModal.ShutDownVMResponseModal;
import com.vms.antplay.utils.AppUtils;
import com.vms.antplay.utils.Const;
import com.vms.antplay.utils.SharedPreferenceUtils;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ComputerFragment extends Fragment implements ShutdownVMListener {

    RecyclerView recyclerView;
    private GetVMResponseModal getVMResponseModals;
    Computer_available_Adapter computer_available_adapter;
    ImageView img_reload;
    TextView tv_reload, tv_computerAvailable;
    private ProgressBar loadingPB;
    AlertDialog.Builder builder;

    public ComputerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_computer, container, false);
        builder = new AlertDialog.Builder(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_computers);
        img_reload = (ImageView) view.findViewById(R.id.img_reload);
        tv_reload = (TextView) view.findViewById(R.id.tv_reload);
        loadingPB = (ProgressBar) view.findViewById(R.id.loading_getcomputer);
        tv_computerAvailable = (TextView) view.findViewById(R.id.tv_compAvailable);
        SharedPreferenceUtils.getString(getContext(), Const.ACCESS_TOKEN);
        Log.e("Hello get Token", "" + SharedPreferenceUtils.getString(getContext(), Const.ACCESS_TOKEN));
        /*getVMResponseModals = new ArrayList<>();*/
        callGetVmApi();

        tv_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Hello", "Hello reload");
                callGetVmApi();
            }
        });
        img_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callGetVmApi();
            }
        });

        return view;

    }
    private void callGetVmApi() {
        Log.e("hello get token--", "" + SharedPreferenceUtils.getString(getContext(), Const.ACCESS_TOKEN));
        loadingPB.setVisibility(View.VISIBLE);
        RetrofitAPI retrofitAPI = APIClient.getRetrofitInstance().create(RetrofitAPI.class);
        Call<GetVMResponseModal> call = retrofitAPI.getVM("Bearer " + SharedPreferenceUtils.getString(getContext(), Const.ACCESS_TOKEN));
        call.enqueue(new Callback<GetVMResponseModal>() {
            @Override
            public void onResponse(Call<GetVMResponseModal> call, Response<GetVMResponseModal> response) {

                if (response.isSuccessful()) {
                    loadingPB.setVisibility(View.GONE);
                    getVMResponseModals = response.body();
                    if (getVMResponseModals.getData().size() > 0) {
                        tv_computerAvailable.setText(getString(R.string.computer_available));
                    }
                    long remainingSec = getVMResponseModals.getData().get(0).getTimeRemaining();
                    SharedPreferenceUtils.saveLong(getContext(), Const.REMAINING_TIME, remainingSec);


                    for (int i = 0; i < getVMResponseModals.getData().size(); i++) {
                        computer_available_adapter = new Computer_available_Adapter(getContext(), getVMResponseModals.getData(), ComputerFragment.this);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(computer_available_adapter);
                    }
                } else {
                    loadingPB.setVisibility(View.GONE);
                    tv_computerAvailable.setText(getString(R.string.computer_not_available));
                    // AppUtils.showToast(Const.no_vm_found, getActivity());
                    AppUtils.showSnack(getActivity().getWindow().getDecorView().getRootView(), R.color.black, Const.no_vm_found, getContext());
                }
            }

            @Override
            public void onFailure(Call<GetVMResponseModal> call, Throwable t) {
                Log.e("Hello Get VM", "Failure");
                loadingPB.setVisibility(View.GONE);
                tv_computerAvailable.setText(getString(R.string.computer_not_available));
                //  AppUtils.showToast(Const.something_went_wrong, getActivity());
                AppUtils.showSnack(getActivity().getWindow().getDecorView().getRootView(), R.color.black, Const.something_went_wrong, getContext());

            }
        });
    }

    private void callShutdownVmApi(String vmId) {
        Log.e("hello get token--", "" + SharedPreferenceUtils.getString(getContext(), Const.ACCESS_TOKEN));
        loadingPB.setVisibility(View.VISIBLE);
        ShutDownVMRequestModal shutDownVMRequestModal = new ShutDownVMRequestModal(vmId);
        RetrofitAPI retrofitAPI = APIClient.getRetrofitInstance().create(RetrofitAPI.class);
        Call<ShutDownVMResponseModal> call = retrofitAPI.shoutDownVM("Bearer " + SharedPreferenceUtils.getString(getContext(), Const.ACCESS_TOKEN), shutDownVMRequestModal);
        call.enqueue(new Callback<ShutDownVMResponseModal>() {
            @Override
            public void onResponse(Call<ShutDownVMResponseModal> call, Response<ShutDownVMResponseModal> response) {

                if (response.isSuccessful()) {
                    loadingPB.setVisibility(View.GONE);

                } else {
                    loadingPB.setVisibility(View.GONE);
                    tv_computerAvailable.setText(getString(R.string.computer_not_available));
                    // AppUtils.showToast(Const.no_vm_found, getActivity());
                    AppUtils.showSnack(getActivity().getWindow().getDecorView().getRootView(), R.color.black, Const.no_vm_found, getContext());
                }
            }

            @Override
            public void onFailure(Call<ShutDownVMResponseModal> call, Throwable t) {
                Log.e("Hello Get VM", "Failure");
                loadingPB.setVisibility(View.GONE);
                tv_computerAvailable.setText(getString(R.string.computer_not_available));
                //  AppUtils.showToast(Const.something_went_wrong, getActivity());
                AppUtils.showSnack(getActivity().getWindow().getDecorView().getRootView(), R.color.black, Const.something_went_wrong, getContext());

            }
        });
    }


    @Override
    public void shutDownVMRequest(long vmId) {
        Log.d("VM", "Shutdown VM " + vmId + " called");
        if (vmId > 0) {
            confirmShutdown(String.valueOf(vmId));

        }
    }

    private void confirmShutdown(String vmId) {
        builder.setMessage(getResources().getString(R.string.do_you_want_to_shutdown))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.proceed), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                callShutdownVmApi(vmId);
            }
        }).setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle(getResources().getString(R.string.shutdown_vm));
        alert.show();
    }
}