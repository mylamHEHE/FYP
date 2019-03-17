package com.example.user.smartfitnesstrainer.Main.Video_inner;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.user.smartfitnesstrainer.R;

import java.util.ArrayList;

public class Video_inner_playlist_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    Dialog myVideo;
    ArrayList<Integer> previewvideo = new ArrayList<>();
    private ArrayList<Exercise_Format> exercise_formats = new ArrayList<>();
    private Context mContext;
    private int type;
    private int stopPosition;
    private boolean bVideoIsBeingTouched = false;
    private Handler mHandler = new Handler();

    public Video_inner_playlist_adapter(Context context, ArrayList<Exercise_Format> exercise_formats,int type) {
       // previewvideo.add(R.raw.svsit);
        previewvideo.add(R.raw.tutplunk);
        previewvideo.add(R.raw.tuttstable);
        mContext = context;
        this.exercise_formats = exercise_formats;
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
             View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_video_list, parent, false);
                myVideo = new Dialog(parent.getContext());
        final Video_inner_playlist_adapter.PlaylistViewHolder holder2 = new Video_inner_playlist_adapter.PlaylistViewHolder(view2);
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder2.getAdapterPosition();
                //check if position exists
                if (position != RecyclerView.NO_POSITION) {
                    initPopWindow(v,position);
                    //TODO whatever you want
                }
            }
        });
                return holder2;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.d("on9", "onBindViewHolder: called.");


                PlaylistViewHolder viewHolder2 = (PlaylistViewHolder)holder;

                viewHolder2.name.setText(exercise_formats.get(position).getName());
                viewHolder2.rb.setRating(exercise_formats.get(position).getDifficulty());
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
    private void initPopWindow(View v,int stopPosition) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_popip, null, false);


        final VideoView videoview = (VideoView) view.findViewById(R.id.VideoView);
        Uri uri = Uri.parse("android.resource://"+v.getContext().getPackageName()+"/"+previewvideo.get(stopPosition));
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
        Log.d("vidxas",String.valueOf(exercise_formats.size()));
        return exercise_formats.size();
    }

    public class PlaylistViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        RelativeLayout parentLayout;
        TextView duration;
        ImageView imageview;
        RatingBar rb;
        public PlaylistViewHolder(final View itemView) {
            super(itemView);
            rb=itemView.findViewById(R.id.difficulty_rating);
            //image.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            name = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            duration = itemView.findViewById(R.id.duration);
            imageview = itemView.findViewById(R.id.image);
        }
    }
}