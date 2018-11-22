package com.example.user.smartfitnesstrainer.Main;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
//import com.example.user.smartfitnesstrainer.Main.DetailVideo.ExerciseActivity;
import com.example.user.smartfitnesstrainer.R;

import java.util.ArrayList;

public class Video_inner_desp_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    Dialog myVideo;
    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mduration = new ArrayList<>();
    private ArrayList<Integer> mImages = new ArrayList<>();
    private ArrayList<Integer> mVideos = new ArrayList<>();
    private Context mContext;
    private int type;
    private int stopPosition;
    private boolean bVideoIsBeingTouched = false;
    private Handler mHandler = new Handler();

    public Video_inner_desp_adapter(Context context, ArrayList<String> imageNames, ArrayList<String> duration,int type) {
        mImageNames = imageNames;

        mImages.add(R.drawable.thumb1);
        mImages.add(R.drawable.thumb2);
        mVideos.add(R.raw.video);
        mVideos.add(R.raw.video0);
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
                myVideo = new Dialog(parent.getContext());
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

                Glide.with(mContext)
                        .asBitmap()
                        .load(mImages.get(position))
                        .into(viewHolder2.imageview);

                break;
        }

    }
    public void onPause(VideoView videoView) {
        Log.d(TAG, "onPause called");
         //stopPosition is an int
        videoView.pause();
        stopPosition = videoView.getCurrentPosition();
        Log.d(TAG, String.valueOf(stopPosition));
    }
    public void onResume(VideoView videoView) {
        Log.d(TAG, String.valueOf(stopPosition));
        videoView.seekTo(stopPosition);
        videoView.start();
           //Or use resume() if it doesn't work. I'm not sure
    }
    @SuppressLint("ClickableViewAccessibility")
    private void initPopWindow(View v) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_popip, null, false);


        final VideoView videoview = (VideoView) view.findViewById(R.id.VideoView);
        Uri uri = Uri.parse("android.resource://"+v.getContext().getPackageName()+"/"+R.raw.video);
        videoview.setVideoURI(uri);
        videoview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!bVideoIsBeingTouched) {
                    bVideoIsBeingTouched = true;
                    if (videoview.isPlaying()) {
                        onPause(videoview);
                    } else {
                        Log.d("vid",String.valueOf(videoview.isPlaying()));
                        onResume(videoview);
                    }

                            bVideoIsBeingTouched = false;

                }
                return true;
            }
        });
        myVideo.setContentView(view);
        myVideo.setCanceledOnTouchOutside(true);
        myVideo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myVideo.show();
        videoview.start();
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
        ImageView imageview;
        public PlaylistViewHolder(View itemView) {
            super(itemView);

            //image.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            name = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            parentLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    initPopWindow(v);
                }
            });
            duration = itemView.findViewById(R.id.duration);
            imageview = itemView.findViewById(R.id.image);
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
