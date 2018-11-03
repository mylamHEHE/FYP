package com.example.user.smartfitnesstrainer.Main;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.smartfitnesstrainer.R;

import java.util.ArrayList;

public class Video_inner_desp_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mduration = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;
    private int type;

    public Video_inner_desp_adapter(Context context, ArrayList<String> imageNames, ArrayList<String> duration,int type) {
        mImageNames = imageNames;
        mContext = context;
        mduration = duration;
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0: View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_basic_desp_cell, parent, false);
                Video_inner_desp_adapter.BasicDespViewHolder holder = new Video_inner_desp_adapter.BasicDespViewHolder(view);
                return holder;
            default:  View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_video_list, parent, false);
                Video_inner_desp_adapter.PlaylistViewHolder holder2 = new Video_inner_desp_adapter.PlaylistViewHolder(view2);
                return holder2;
        }

    }
    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return type;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.d("on9", "onBindViewHolder: called.");

        switch (holder.getItemViewType()) {
            case 0:
                BasicDespViewHolder viewHolder0 = (BasicDespViewHolder) holder;
                viewHolder0.name.setText(mImageNames.get(position));
                viewHolder0.duration.setText(mduration.get(position));
                break;

            case 1:
                PlaylistViewHolder viewHolder2 = (PlaylistViewHolder)holder;
                viewHolder2.name.setText(mImageNames.get(position));
                viewHolder2.duration.setText(mduration.get(position));
                break;
        }

    }

    @Override
    public int getItemCount() {

        Log.d("gic",String.valueOf(mImageNames.size()));
        return mImageNames.size();
    }

    public class PlaylistViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        RelativeLayout parentLayout;
        TextView duration;

        public PlaylistViewHolder(View itemView) {
            super(itemView);

            //image.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            name = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            duration = itemView.findViewById(R.id.duration);
        }
    }
    public class BasicDespViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        LinearLayout parentLayout;
        TextView duration;

        public BasicDespViewHolder(View itemView) {
            super(itemView);

            //image.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            name = itemView.findViewById(R.id.title_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            duration = itemView.findViewById(R.id.duration);
        }
    }
}
