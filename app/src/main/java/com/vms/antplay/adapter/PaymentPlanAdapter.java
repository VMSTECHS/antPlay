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

import java.util.ArrayList;


public class PaymentPlanAdapter extends RecyclerView.Adapter<PaymentPlanAdapter.PaymentPlanViewHolder> implements View.OnClickListener {
    ArrayList<PaymentPlansModel> paymentPlansList;
    Context context;
    int hours = 0, amount = 0;

    PaymentInitiationInterface paymentInitiationInterface;


    public PaymentPlanAdapter(Context context, ArrayList<PaymentPlansModel> paymentPlansList, PaymentInitiationInterface paymentInitiationInterface) {
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
        holder.txtPlaneAmount.setText("₹ " + paymentPlansList.get(position).getAmount());
        holder.txtDurationInHours.setText(paymentPlansList.get(position).getDurationInHours() + " Hours");
        holder.txtValidity.setText(paymentPlansList.get(position).getValidityInDays() + " Days");
        holder.txtSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentInitiationInterface.onPaymentInitiated(paymentPlansList.get(holder.getAbsoluteAdapterPosition()).getAmount(),paymentPlansList.get(holder.getAbsoluteAdapterPosition()).getDurationInHours());
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
        private TextView txtSpacLine1, txtSpacLine2, txtSpacLine3, txtSpacLine4, txtSpacLine5;
        private TextView txtSubscribe;

        public PaymentPlanViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPlanName = itemView.findViewById(R.id.txtPlaneName);
            txtPlaneAmount = itemView.findViewById(R.id.txtPlaneAmount);
            txtValidity = itemView.findViewById(R.id.txtValidity);
            txtDurationInHours = itemView.findViewById(R.id.txtDurationInHours);

            txtSpacLine1 = itemView.findViewById(R.id.txtSpacLine1);
            txtSpacLine2 = itemView.findViewById(R.id.txtSpacLine2);
            txtSpacLine3 = itemView.findViewById(R.id.txtSpacLine3);
            txtSpacLine4 = itemView.findViewById(R.id.txtSpacLine4);
            txtSpacLine5 = itemView.findViewById(R.id.txtSpacLine5);

            txtSubscribe = itemView.findViewById(R.id.txtSubscribe);


        }
    }
}
