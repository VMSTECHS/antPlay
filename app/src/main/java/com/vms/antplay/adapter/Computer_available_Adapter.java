package com.vms.antplay.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vms.antplay.R;

import com.vms.antplay.model.responseModal.GetVMResponseModal;

import java.util.ArrayList;

public class Computer_available_Adapter extends RecyclerView.Adapter<Computer_available_Adapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetVMResponseModal> computer_availableModals;

    public Computer_available_Adapter(Context context, ArrayList<GetVMResponseModal> computer_availableModals) {
        this.context = context;
        this.computer_availableModals = computer_availableModals;
    }

    @NonNull
    @Override
    public Computer_available_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.computers_available_list, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Computer_available_Adapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        GetVMResponseModal modal = computer_availableModals.get(position);
        holder.computerName.setText(modal.getVmname());
        holder.computerDesc.setText(modal.getStatus());
      //  holder.computerImage.setImageResource(modal.getImage());

        holder.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked"+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return computer_availableModals.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView computerName;
        ImageView computerImage;
        TextView computerDesc;
        Button btn_share;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            computerName = (TextView) itemView.findViewById(R.id.comp_name);
            computerDesc = (TextView) itemView.findViewById(R.id.comp_desc);
            computerImage = (ImageView) itemView.findViewById(R.id.comp_image);
            btn_share = (Button) itemView.findViewById(R.id.comp_btn);
        }
    }
}
