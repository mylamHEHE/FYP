/*package com.example.user.smartfitnesstrainer.Main;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.user.smartfitnesstrainer.Main.BLE.DeviceScanActivity;
import com.example.user.smartfitnesstrainer.Main.DetailVideo.ExerciseActivity;
import com.example.user.smartfitnesstrainer.R;

public class CameraFragment extends android.support.v4.app.Fragment {
    private  static final String TAG = "CameraFragment";
    ImageButton opentooth;
    ImageButton openscan;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_camera,container,false);
        opentooth=(ImageButton)view.findViewById(R.id.opentooth);
        opentooth.bringToFront();
        opentooth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /*final Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName("com.android.settings",
                        "com.android.settings.bluetooth.BluetoothSettings");
                intent.setComponent(cn);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity( intent);
                Intent homeIntent = new Intent(getContext(), DeviceScanActivity.class);
                startActivity(homeIntent);
            }
        });
        openscan=(ImageButton)view.findViewById(R.id.openscan);
        openscan.bringToFront();
        openscan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity(),"BMI is clicked.",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
*/