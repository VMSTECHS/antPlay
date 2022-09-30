package com.vms.antplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vms.antplay.R;
import com.vms.antplay.interfaces.PaymentInitiationInterface;
import com.vms.antplay.model.PaymentPlansModel;
import com.vms.antplay.model.responseModal.BillingPlan;

import java.util.ArrayList;
import java.util.List;


public class PaymentPlanAdapter extends RecyclerView.Adapter<PaymentPlanAdapter.PaymentPlanViewHolder> implements View.OnClickListener {
    List<BillingPlan> paymentPlansList;
    Context context;
    int hours = 0, amount = 0;

    PaymentInitiationInterface paymentInitiationInterface;


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
    public void onBindViewHolder(@NonNull PaymentPlanViewHolder holder, int position) {
        holder.txtPlanName.setText(paymentPlansList.get(position).getPlanName());
        holder.txtPlaneAmount.setText("₹ " + paymentPlansList.get(position).getPrice());
        holder.txtDurationInHours.setText(paymentPlansList.get(position).getHourLimit() + " Hours");
        holder.txtValidity.setText(paymentPlansList.get(position).getTerm() + " Days");

        holder.tv_gpus.setText(paymentPlansList.get(position).getGpu());
        holder.tv_cpus.setText(paymentPlansList.get(position).getCpu()+ " CPU ");
        holder.tv_rams.setText(paymentPlansList.get(position).getRam()+ " RAM ");
        holder.tv_ssds.setText(paymentPlansList.get(position).getSsd()+ " SSD ");
      //  holder.tv_mbps.setText(paymentPlansList.get(position).get());
        holder.txtSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentInitiationInterface.onPaymentInitiated(paymentPlansList.get(holder.getAbsoluteAdapterPosition()).getPrice(),paymentPlansList.get(holder.getAbsoluteAdapterPosition()).getHourLimit());
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
        private TextView txtSubscribe;

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


        }
    }
}
