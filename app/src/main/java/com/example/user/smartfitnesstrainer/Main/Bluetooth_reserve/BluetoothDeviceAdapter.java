package com.example.user.smartfitnesstrainer.Main.Bluetooth_reserve;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.smartfitnesstrainer.Main.BLE.BluetoothLeDevice;
import com.example.user.smartfitnesstrainer.Main.DetailVideo.ExerciseListAdapter;
import com.example.user.smartfitnesstrainer.R;

import java.util.ArrayList;

public class BluetoothDeviceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    private static final int FOOTER_VIEW = 1;
    private ArrayList<BluetoothLeDevice> ble;
    public BluetoothDeviceAdapter(Context context, ArrayList<BluetoothLeDevice> ble) {
        this.ble = ble;
    }
// Define a view holder for Footer view

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

    // Now define the viewholder for Normal list item
    public class NormalViewHolder extends ViewHolder {
        public NormalViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Do whatever you want on clicking the normal items
                }
            });
        }
    }

    // And now in onCreateViewHolder you have to pass the correct view
    // while populating the list item.

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_list, parent, false);
        BasicDespViewHolder holder = new BasicDespViewHolder(view);

        return holder;
    }

    // Now bind the viewholders in onBindViewHolder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        try {
            BasicDespViewHolder viewHolder0 = (BasicDespViewHolder) holder;
            viewHolder0.name.setText(ble.get(position).getName());
            viewHolder0.duration.setText(ble.get(position).getAddress());
        }
        catch (Exception e)
        {

        }
    }

    // Now the critical part. You have return the exact item count of your list
    // I've only one footer. So I returned data.size() + 1
    // If you've multiple headers and footers, you've to return total count
    // like, headers.size() + data.size() + footers.size()

    @Override
    public int getItemCount() {
        if (ble == null) {
            return 0;
        }

        if (ble.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }

        // Add extra view to show the footer view
        return ble.size();
    }

    // Now define getItemViewType of your own.

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    // So you're done with adding a footer and its action on onClick.
    // Now set the default ViewHolder for NormalViewHolder

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Define elements of a row here
        public ViewHolder(View itemView) {
            super(itemView);
            // Find view by ID and initialize here
        }

        public void bindView(int position) {
            // bindView() method to implement actions
        }
    }
}
