package com.example.user.smartfitnesstrainer.Main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.user.smartfitnesstrainer.Main.DetailVideo.ExerciseActivity;
import com.example.user.smartfitnesstrainer.R;

import java.util.ArrayList;



public class Video_innerActivity extends AppCompatActivity {
    private static final String TAG = "Video_innerActivity";
ImageView image;
RecyclerView rv,videorv;
Video_inner_desp_adapter vida,vida2;
Button start;
    //varss

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mduration = new ArrayList<>();

    private ArrayList<String> videoNames = new ArrayList<>();
    private ArrayList<String> videoDuration = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_full_video_inner);
        initBasicDesp();
        initStartButton();
        image = findViewById(R.id.image);
        rv = findViewById(R.id.basic_desp);
        rv.setNestedScrollingEnabled(false);

        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                llm.getOrientation());
        rv.addItemDecoration(dividerItemDecoration);
        rv.setLayoutManager(llm);
        vida = new Video_inner_desp_adapter(getApplicationContext(),mNames,mduration,0);
        rv.setAdapter(vida);
            videorv = findViewById(R.id.videolist);
            videorv.setNestedScrollingEnabled(false);
            LinearLayoutManager llmm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(videorv.getContext(),
                    llmm.getOrientation());
            videorv.addItemDecoration(dividerItemDecoration2);
            videorv.setLayoutManager(llmm);
            image.setColorFilter(Color.rgb(100, 100, 100), PorterDuff.Mode.LIGHTEN);
            vida2 = new Video_inner_desp_adapter(getApplicationContext(), videoNames, videoDuration, 1);
            videorv.setAdapter(vida2);

        VideoView videoView = findViewById(R.id.video);
/*
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video;
        //String videoPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/video0.mp4";;

        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController((this));
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);


        mediaController.setPadding(0, 0, 0, 827);
        mediaController.isShowing();
        */
        //initImageBitmaps();
    }


        //initImageBitmaps();


    private void initStartButton(){
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), ExerciseActivity.class);
                startActivity(homeIntent);
                // Code here executes on main thread after user presses button
            }
        });
    }
    private void initBasicDesp(){

        mNames.add("Description");
        //mNames.add("Equipment");
        mduration.add("Cardio exercise is any exercise that raises your heart rate.");
       // mduration.add("None");

        videoNames.add("Plank");
        videoNames.add("Russian");
        videoNames.add("Side Plank");
        videoDuration.add("0:30");
        videoDuration.add("0:30");
        videoDuration.add("0:30");

    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        VideoRecycleAdaptor adapter = new VideoRecycleAdaptor(this, mNames, mImageUrls, mduration);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}