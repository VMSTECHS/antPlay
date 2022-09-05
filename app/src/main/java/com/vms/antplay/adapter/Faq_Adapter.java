package com.vms.antplay.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vms.antplay.R;
import com.vms.antplay.model.FaqModal;

import java.util.ArrayList;

public class Faq_Adapter extends RecyclerView.Adapter<Faq_Adapter.MyViewolder> {

    private Context context;
    private ArrayList<FaqModal> faqModalArrayList;
    private static int currentPosition = 0;

    public Faq_Adapter(Context context, ArrayList<FaqModal> faqModalArrayList) {
        this.context = context;
        this.faqModalArrayList = faqModalArrayList;
    }

    @NonNull
    @Override
    public MyViewolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fag_adapter, parent,false);
        return new MyViewolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewolder holder, @SuppressLint("RecyclerView") int position) {

        FaqModal modal = faqModalArrayList.get(position);
        holder.textViewName.setText(modal.getName());
        holder.textViewRealName.setText(modal.getRealName());
        holder.linearLayout.setVisibility(View.GONE);

        //if the position is equals to the item position which is to be expanded
        if (currentPosition == position) {
            //creating an animation
            Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);

            //toggling visibility
            holder.linearLayout.setVisibility(View.VISIBLE);

            //adding sliding effect
            holder.linearLayout.startAnimation(slideDown);
        }

        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting the position of the item to expand it
                currentPosition = position;

                //reloding the list
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return faqModalArrayList.size();
    }

    public class MyViewolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewRealName, textViewTeam, textViewFirstAppearance,
                textViewCreatedBy, textViewPublisher, textViewBio;
        ImageView imageView;
        LinearLayout linearLayout;
        public MyViewolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewRealName = itemView.findViewById(R.id.textViewRealName);
          //  textViewBio = itemView.findViewById(R.id.textViewBio);


            linearLayout = itemView.findViewById(R.id.linearLayout);
        }

    }
}
