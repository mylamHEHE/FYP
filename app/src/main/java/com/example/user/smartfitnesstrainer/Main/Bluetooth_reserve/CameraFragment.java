package com.example.user.smartfitnesstrainer.Main.Bluetooth_reserve;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.smartfitnesstrainer.R;
import com.gigamole.library.PulseView;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;


public class CameraFragment extends android.support.v4.app.Fragment {
    private  static final String TAG = "CameraFragment";
    public PulsatorLayout pulsator;
    private boolean runningThread = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_camera,container,false);
        pulsator = view.findViewById(R.id.pulsator);

        Log.d("yes","yes");

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
