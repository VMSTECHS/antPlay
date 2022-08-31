package com.vms.antplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vms.antplay.R;
import com.vms.antplay.model.GamePad_RawModal;

import java.util.ArrayList;

public class Gamepad_RawAdapter extends RecyclerView.Adapter<Gamepad_RawAdapter.MyViewHolder> {

   private Context context;
   private ArrayList<GamePad_RawModal> gamePad_rawModals;

    public Gamepad_RawAdapter(Context context, ArrayList<GamePad_RawModal> gamePad_rawModals) {
        this.context = context;
        this.gamePad_rawModals = gamePad_rawModals;
    }

    @NonNull
    @Override
    public Gamepad_RawAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gamepad_rawdata,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Gamepad_RawAdapter.MyViewHolder holder, int position) {

        GamePad_RawModal modal = gamePad_rawModals.get(position);
        holder.input.setText(modal.getInput());
        holder.state.setText(modal.getState());
        holder.min.setText(modal.getMin());
    }

    @Override
    public int getItemCount() {
        return gamePad_rawModals.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView input;
        TextView state;
        TextView min;
        TextView max;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            input =(TextView) itemView.findViewById(R.id.tv_input);
            state =(TextView) itemView.findViewById(R.id.tv_state);
            min =(TextView) itemView.findViewById(R.id.tv_min);


        }
    }
}
