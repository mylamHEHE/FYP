package com.example.user.smartfitnesstrainer.Main.Profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.smartfitnesstrainer.Main.BLE.ToastUtil;
import com.example.user.smartfitnesstrainer.Main.GraphActivity;
import com.example.user.smartfitnesstrainer.Main.HomeActivity;
import com.example.user.smartfitnesstrainer.R;

import java.util.List;

public class RecyclerItemAdapter extends RecyclerView.Adapter<RecyclerItemAdapter.MessageViewHolder>{
    private Context ctx;
    private Activity activity;
    private List<UserProfile.PlayerHistory> userRecyclerItemList;

    public RecyclerItemAdapter(Activity activity,Context ctx, List<UserProfile.PlayerHistory> userRecyclerItemList) {
        this.ctx = ctx;
        this.userRecyclerItemList = userRecyclerItemList;
        this.activity = activity;
    }

    @Override

    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(ctx);
        View v = inflater.inflate(R.layout.profile_list_item,parent,false);
        ;
        final MessageViewHolder h = new MessageViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = h.getAdapterPosition();
                Log.d("post",String.valueOf(userRecyclerItemList.get(position).getRef_id()));

                Intent myIntent = new Intent(ctx, GraphActivity.class);
                myIntent.putExtra("exkey", userRecyclerItemList.get(position).getRef_id()); //Optional parameters
                ctx.startActivity(myIntent);
                //check if position exists

            }
        });

        return h;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        UserProfile.PlayerHistory uri = userRecyclerItemList.get(position);
        Log.d("nasmc",uri.getName());
        holder.title.setText(uri.getName());
        holder.create_date.setText(uri.getDate());
    }

    @Override
    public int getItemCount() {
        return userRecyclerItemList.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView create_date;
        public MessageViewHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.tvdesc);
            create_date = itemView.findViewById(R.id.tvcreatedate);

        }
    }
}
