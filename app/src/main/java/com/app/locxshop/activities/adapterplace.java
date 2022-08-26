package com.app.locxshop.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.locxshop.R;

import java.io.ObjectInputStream;
import java.util.ArrayList;

public class adapterplace extends RecyclerView.Adapter<adapterplace.PhoneViewHold> {


    ArrayList<phonehelper> placeLocation;
    final private ListItemClickListener mOnClickListener;

    public adapterplace(ArrayList<phonehelper> placeLocation, ListItemClickListener listener){
        this.placeLocation = placeLocation;
        mOnClickListener = listener;
    }


    @NonNull
    @Override
    public PhoneViewHold  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.placerecycler,parent,false);
        return new PhoneViewHold(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PhoneViewHold holder, int position) {


        phonehelper phonehelper =  placeLocation.get(position);
        holder.image.setImageResource(phonehelper.getImage());
        //holder.title.setText(phonehelper.getTitle());
        holder.relativeLayout.setBackground(phonehelper.getgrandient());


    }

    @Override
    public int getItemCount() {
        return placeLocation.size();
    }

    public interface ListItemClickListener{
        void onphoneListClick(int clickedItemIndex);
    }

    public class PhoneViewHold extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        //TextView title;
        RelativeLayout relativeLayout;
        public PhoneViewHold(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            image = itemView.findViewById(R.id.img_plac);
            //title = itemView.findViewById(R.id.tex_plac);
            relativeLayout = itemView.findViewById(R.id.back_colo);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onphoneListClick(clickedPosition);

        }


    }
}
