package com.example.user.smartfitnesstrainer.Main.exercise_selection_page;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


import com.example.user.smartfitnesstrainer.Main.BLE.ToastUtil;
import com.example.user.smartfitnesstrainer.Main.Video_innerActivity;
import com.example.user.smartfitnesstrainer.R;

public class VideoRecycleAdaptor extends RecyclerView.Adapter<VideoRecycleAdaptor.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private List<Playlist> mItem = new ArrayList<>();
    private Context mContext;

    public VideoRecycleAdaptor(Context context, List<Playlist> playlists) {
        mItem=playlists;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_video, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                //check if position exists
                if (position == RecyclerView.NO_POSITION) {

                    //TODO whatever you want
                }
                else if (position == 0)
                {
                    Intent homeIntent = new Intent(mContext,Video_innerActivity.class);
                    mContext.startActivity(homeIntent);
                }
                else
                {
                    ToastUtil.showToast(mContext, "Unlock at level 21!");
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mItem.get(position).getCover_photo())
                .into(holder.image);

        holder.imageName.setText(mItem.get(position).getName());
       /*
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Intent intent = new Intent(mContext, VideoActivity.class);
                //intent.putExtra("image_url", mImages.get(position));
                //intent.putExtra("image_name", mImageNames.get(position));
                //mContext.startActivity(intent);
            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView imageName;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);

            image.setColorFilter(Color.rgb(100,100,100), PorterDuff.Mode.LIGHTEN);
            //image.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            imageName = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}