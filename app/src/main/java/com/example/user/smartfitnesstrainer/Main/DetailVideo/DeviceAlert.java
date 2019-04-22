package com.example.user.smartfitnesstrainer.Main.DetailVideo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.user.smartfitnesstrainer.Main.BLE.BluetoothLeDevice;
import com.example.user.smartfitnesstrainer.Main.BLE.ViseBle;
import com.example.user.smartfitnesstrainer.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DeviceAlert extends DialogFragment
{
    String tutVideo;
    public static DeviceAlert newInstance(String tutVideo) {
        DeviceAlert deviceAlert = new DeviceAlert();
        Bundle args = new Bundle();
        args.putString("video", tutVideo);
        deviceAlert.setArguments(args);
        return deviceAlert;
    }
    @Override
    public void onStart()
    {

        super.onStart();
        Dialog dialog = getDialog();
//        Log.d("bldlist",BluetoothDifferenter.FIRST_BLUETOOTH_DEV.getAddress());
  //      Log.d("bldlist",BluetoothDifferenter.SECOND_BLUETOOTH_DEV.getAddress());

        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.rgb(49,120,115));
        if (dialog != null)
        {
            int width = getResources().getDimensionPixelSize(R.dimen.popwidth);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commitAllowingStateLoss();
        } catch (IllegalStateException e) {
            Log.d("ABSDIALRAG", "Exception", e);
        }
    }
    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tutVideo = getArguments().getString("video");
        Log.d("cretedial",String.valueOf(tutVideo));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_check_device, null);


        VideoView video=(VideoView)view.findViewById(R.id.tutorial_vid);
        TextView left_hand = (TextView)view.findViewById(R.id.left_hand);
        TextView right_hand = (TextView)view.findViewById(R.id.right_hand);
        /*
        String first_ble = BluetoothDifferenter.FIRST_BLUETOOTH_DEV.getName();
        String second_ble = BluetoothDifferenter.SECOND_BLUETOOTH_DEV.getName();
        left_hand.setText("Please put device "+first_ble.substring(first_ble.length()-4)+"on the left part of body as video shown below.");
        right_hand.setText("Please put device "+second_ble.substring(second_ble.length()-4)+"on the right part of body as video shown below.");
        */
        Log.d("vieod",video.toString());
        String path =  tutVideo;

// Init Video
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0,0);
                mp.setLooping(true);
            }
        });
        video.setVideoURI(Uri.parse(path));
        video.start();
       // video.setVideoURI(uri);
        //video.start();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
               .setNegativeButton("Ok!", null);
        final AlertDialog dialog = builder.create();

        dialog.show();
        final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
        positiveButtonLL.gravity = Gravity.CENTER;
        positiveButton.setLayoutParams(positiveButtonLL);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set video holder

    }

}