package com.example.user.smartfitnesstrainer.Main.DetailVideo;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.user.smartfitnesstrainer.R;

import java.util.ArrayList;

public class ExerciseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    Dialog myVideo;
    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mduration = new ArrayList<>();
    private ArrayList<Integer> mImages = new ArrayList<>();
    private Context mContext;

    public ExerciseListAdapter(Context context, ArrayList<String> imageNames) {
        mImageNames = imageNames;

        mImages.add(R.drawable.thumb1);
        mImages.add(R.drawable.thumb2);
        mImages.add(R.drawable.thumb1);
        mImages.add(R.drawable.thumb2);

        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_video_list, parent, false);
                BasicDespViewHolder holder = new BasicDespViewHolder(view);
                return holder;

    }
    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

                BasicDespViewHolder viewHolder0 = (BasicDespViewHolder) holder;
                viewHolder0.name.setText(mImageNames.get(position));
                Glide.with(mContext)
                    .asBitmap()
                    .load(mImages.get(position))
                    .into(viewHolder0.iv);



    }

    @Override
    public int getItemCount() {

        Log.d("gic",String.valueOf(mImageNames.size()));
        return mImageNames.size();
    }


    public class BasicDespViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        RelativeLayout parentLayout;
        TextView duration;
        ImageView iv;
        public BasicDespViewHolder(View itemView) {
            super(itemView);

            //image.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            iv = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            duration = itemView.findViewById(R.id.description);
        }
    }
}
