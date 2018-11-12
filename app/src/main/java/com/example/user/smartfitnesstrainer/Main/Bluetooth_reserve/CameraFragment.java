package com.example.user.smartfitnesstrainer.Main.Bluetooth_reserve;

import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.user.smartfitnesstrainer.Main.HomeActivity;
import com.example.user.smartfitnesstrainer.Main.ScanActivity;
import com.example.user.smartfitnesstrainer.R;
import com.gigamole.library.PulseView;

import java.util.Set;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;


public class CameraFragment extends android.support.v4.app.Fragment {
    private  static final String TAG = "CameraFragment";
    public PulsatorLayout pulsator;
    ImageButton opentooth;
    ImageButton openscan;
    private boolean runningThread = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_camera,container,false);
        pulsator = view.findViewById(R.id.pulsator);

        Log.d("yes","yes");

        opentooth=(ImageButton)view.findViewById(R.id.opentooth);
        opentooth.bringToFront();
        opentooth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName("com.android.settings",
                        "com.android.settings.bluetooth.BluetoothSettings");
                intent.setComponent(cn);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        openscan=(ImageButton)view.findViewById(R.id.openscan);
        openscan.bringToFront();
        openscan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent homeIntent = new Intent(getContext() ,ScanActivity.class);
                startActivity(homeIntent);
            }
        });

        pulsator.start();
        return view;
    }


    @Override
    public void onPause() {
        pulsator.stop();
        super.onPause();

        Log.d("pa","use");
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
