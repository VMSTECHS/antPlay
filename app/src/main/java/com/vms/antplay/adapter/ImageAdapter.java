package com.vms.antplay.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vms.antplay.R;
import com.vms.antplay.model.ImageModel;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<ImageModel> imageModelArrayList;

    public ImageAdapter(Context context, ArrayList<ImageModel> imageModelArrayList) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.imageadapterlayout, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.MyViewHolder holder, int position) {
        ImageModel model = imageModelArrayList.get(position);
        holder.name.setText(model.getName());
        holder.image.setImageResource(model.getImage());
    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name;
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
