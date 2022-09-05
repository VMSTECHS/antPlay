package com.vms.antplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vms.antplay.R;

import java.util.ArrayList;

public class ArcadeAdapter extends RecyclerView.Adapter<ArcadeAdapter.RecyclerViewHolder> {

    private ArrayList<String> arcadeList;
    private Context context;

    public ArcadeAdapter(ArrayList<String> arcadeList, Context context) {
        this.arcadeList = arcadeList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_arcade, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        String arcadeName = arcadeList.get(position);
        holder.txtArcadeName.setText(arcadeName);

    }

    @Override
    public int getItemCount() {
        return arcadeList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView txtArcadeName;
        private ImageView imageArcade;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtArcadeName = itemView.findViewById(R.id.txtArcadeName);
            imageArcade = itemView.findViewById(R.id.imgArcade);
        }
    }
}
