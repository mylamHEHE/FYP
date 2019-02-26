package com.example.user.smartfitnesstrainer.Main.Video_inner;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.example.user.smartfitnesstrainer.Main.DetailVideo.ExerciseActivity;
import com.example.user.smartfitnesstrainer.R;

import java.util.ArrayList;

public class Video_inner_desp_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    Dialog myVideo;
    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mduration = new ArrayList<>();
    private Context mContext;
    private int type;
    private int stopPosition;
    private boolean bVideoIsBeingTouched = false;
    private Handler mHandler = new Handler();

    public Video_inner_desp_adapter(Context context, ArrayList<String> imageNames, ArrayList<String> duration,int type) {
        mImageNames = imageNames;

        mContext = context;
        mduration = duration;
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_basic_desp_cell, parent, false);
                Video_inner_desp_adapter.BasicDespViewHolder holder = new Video_inner_desp_adapter.BasicDespViewHolder(view);
                return holder;

        }



    @Override
    public int getItemCount() {
        return mImageNames.size();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.d("on9", "onBindViewHolder: called.");

        try {
            BasicDespViewHolder viewHolder0 = (BasicDespViewHolder) holder;
            viewHolder0.name.setText(mImageNames.get(position));
            viewHolder0.duration.setText(mduration.get(position));
        }catch (Exception e)
        {

        }

/*
try {
    Glide.with(mContext)
            .asBitmap()
            .load(mImages.get(position))
            .into(viewHolder2.imageview);
}
catch (Exception e)
{

}*/

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
