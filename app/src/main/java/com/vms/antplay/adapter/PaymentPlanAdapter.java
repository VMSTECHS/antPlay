package com.vms.antplay.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vms.antplay.R;
import com.vms.antplay.api.APIClient;
import com.vms.antplay.api.RetrofitAPI;
import com.vms.antplay.interfaces.PaymentInitiationInterface;
import com.vms.antplay.model.requestModal.StartPaymentRequestModal;
import com.vms.antplay.model.responseModal.BillingPlan;
import com.vms.antplay.model.responseModal.StartPaymentResponseModal;
import com.vms.antplay.utils.AppUtils;
import com.vms.antplay.utils.Const;
import com.vms.antplay.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaymentPlanAdapter extends RecyclerView.Adapter<PaymentPlanAdapter.PaymentPlanViewHolder> implements View.OnClickListener {
    List<BillingPlan> paymentPlansList;
    Context context;
    int hours = 0, amount = 0;
    PaymentInitiationInterface paymentInitiationInterface;
    ArrayList<String> mylist = new ArrayList<String>();


    public PaymentPlanAdapter(Context context, List<BillingPlan> paymentPlansList, PaymentInitiationInterface paymentInitiationInterface) {
        this.paymentPlansList = paymentPlansList;
        this.context = context;
        this.paymentInitiationInterface = paymentInitiationInterface;
    }

    @NonNull
    @Override
    public PaymentPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_payment_plan, parent, false);
        return new PaymentPlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentPlanViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtPlanName.setText(paymentPlansList.get(position).getPlanName());
        holder.txtPlaneAmount.setText("₹ " + paymentPlansList.get(position).getPrice());
        holder.txtDurationInHours.setText(paymentPlansList.get(position).getHourLimit() + " Hours");
        holder.txtValidity.setText(paymentPlansList.get(position).getTerm() + " Days");

        holder.tv_gpus.setText(paymentPlansList.get(position).getGpu());
        holder.tv_cpus.setText(paymentPlansList.get(position).getCpu() + " CPU ");
        holder.tv_rams.setText(paymentPlansList.get(position).getRam() + " RAM ");
        holder.tv_ssds.setText(paymentPlansList.get(position).getSsd() + " SSD ");
        //  holder.tv_mbps.setText(paymentPlansList.get(position).get());

        mylist.add(paymentPlansList.get(position).getSubscribedStatus().toString());
        Log.e("hello array - ", "" + mylist);

        if (paymentPlansList.get(position).getSubscribedStatus().equals(true)) {
            //   holder.txtSubscribe.setText("UNSUBSCRIBE");
            holder.txtActivePlan.setVisibility(View.VISIBLE);
            holder.txtSubscribe.setVisibility(View.GONE);
        }
        holder.txtSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*    paymentInitiationInterface.onPaymentInitiated(paymentPlansList.get(holder.getAbsoluteAdapterPosition()).getPrice(),paymentPlansList.get(holder.getAbsoluteAdapterPosition()).getHourLimit());
                  paymentInitiationInterface.onPaymentInitiated(2.00,899);*/

                Log.e("hi get id--", "" + paymentPlansList.get(position).getId());

                if (mylist.contains("true")) {
                    AppUtils.showSnack(v, R.color.black, Const.already_have_plan, context);
                } else {
                    // Call api for start payment
                    callStartPayment(String.valueOf(paymentPlansList.get(position).getId()));
                }

            }
        });

    }

    private void callStartPayment(String id) {
        String access_token = SharedPreferenceUtils.getString(context, Const.ACCESS_TOKEN);
        RetrofitAPI retrofitAPI = APIClient.getRetrofitInstance().create(RetrofitAPI.class);
        StartPaymentRequestModal startPaymentRequestModal = new StartPaymentRequestModal(id);
        Call<StartPaymentResponseModal> call = retrofitAPI.startPayment("Bearer " + access_token, startPaymentRequestModal);
        call.enqueue(new Callback<StartPaymentResponseModal>() {
            @Override
            public void onResponse(Call<StartPaymentResponseModal> call, Response<StartPaymentResponseModal> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.code() == 200) {
                        String url = response.body().getPaymentUrl();
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse(url));
                        context.startActivity(viewIntent);


                    }
                    // loadingPB.setVisibility(View.GONE);


                } else {
                    //  loadingPB.setVisibility(View.GONE);
                    AppUtils.showToast(Const.no_records, context);
                }
            }

            @Override
            public void onFailure(Call<StartPaymentResponseModal> call, Throwable t) {
                Log.d("Start Payment", "Failure");
                // loadingPB.setVisibility(View.GONE);
                AppUtils.showToast(Const.something_went_wrong, context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return paymentPlansList.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class PaymentPlanViewHolder extends RecyclerView.ViewHolder {
        private TextView txtPlanName, txtPlaneAmount, txtValidity, txtDurationInHours;
        private TextView tv_gpus, tv_cpus, tv_rams, tv_ssds, tv_mbps;
        private TextView txtSubscribe, txtActivePlan;

        public PaymentPlanViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPlanName = itemView.findViewById(R.id.txtPlaneName);
            txtPlaneAmount = itemView.findViewById(R.id.txtPlaneAmount);
            txtValidity = itemView.findViewById(R.id.txtValidity);
            txtDurationInHours = itemView.findViewById(R.id.txtDurationInHours);

            tv_gpus = itemView.findViewById(R.id.tv_gpu);
            tv_cpus = itemView.findViewById(R.id.tv_cpu);
            tv_rams = itemView.findViewById(R.id.tv_ram);
            tv_ssds = itemView.findViewById(R.id.tv_ssd);
            tv_mbps = itemView.findViewById(R.id.tv_mbp);

            txtSubscribe = itemView.findViewById(R.id.txtSubscribe);
            txtActivePlan = itemView.findViewById(R.id.active_plan);


        }
    }
}
