package com.example.user.smartfitnesstrainer.Main;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

//import com.example.user.smartfitnesstrainer.Main.DetailVideo.ExerciseActivity;
import com.example.user.smartfitnesstrainer.Main.DetailVideo.ExerciseActivity;
import com.example.user.smartfitnesstrainer.R;

import java.util.ArrayList;



public class Video_innerActivity extends AppCompatActivity {
    private static final String TAG = "Video_innerActivity";
ImageView image;
RecyclerView rv,videorv;
Video_inner_desp_adapter vida;
Video_inner_playlist_adapter vida2;
Button start;
    //varss

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Integer> mImageUrls = new ArrayList<>();
    private ArrayList<String> mduration = new ArrayList<>();

    private ArrayList<String> videoNames = new ArrayList<>();
    private ArrayList<String> videoDuration = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_full_video_inner);
        //get request from playlist selected item
        initBasicDesp();
        initStartButton();
        image = findViewById(R.id.image);
        rv = findViewById(R.id.basic_desp);
        rv.setNestedScrollingEnabled(false);
        Log.d("create","createx");
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                llm.getOrientation());
        rv.addItemDecoration(dividerItemDecoration);

        vida = new Video_inner_desp_adapter(getApplicationContext(),mNames,mduration,0);
        rv.setAdapter(vida);
        rv.setLayoutManager(llm);
            videorv = findViewById(R.id.videolist);
            videorv.setNestedScrollingEnabled(false);
            videorv.setHasFixedSize(true);
            LinearLayoutManager llmm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(videorv.getContext(),
                    llmm.getOrientation());
            videorv.addItemDecoration(dividerItemDecoration2);

            image.setColorFilter(Color.rgb(100, 100, 100), PorterDuff.Mode.LIGHTEN);
            Log.d("vidas",String.valueOf(videoNames.size()));
            vida2 = new Video_inner_playlist_adapter(getApplicationContext(), videoNames, mImageUrls,videoDuration, 1);
            vida2.notifyDataSetChanged();

            videorv.setAdapter(vida2);

        videorv.setLayoutManager(llmm);
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


    @SuppressLint("ClickableViewAccessibility")
    private void initStartButton(){

        start = (Button) findViewById(R.id.start);
        start.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        return true;

                    }
                    case MotionEvent.ACTION_MOVE: {
                        Log.i("mandroid.cn", "button移动");
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        Intent homeIntent = new Intent(getApplicationContext(), ExerciseActivity.class);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        startActivity(homeIntent);
                        Log.d("catgirl","cat");
                        break;
                    }
                }
                return true;


            }
        });
    }
    private void initBasicDesp(){

        //Retrofit get request
        //playlist/para

        mNames.add("Description");
        //mNames.add("Equipment");
        mduration.add("Cardio exercise is any exercise that raises your heart rate.");
       // mduration.add("None");
        mImageUrls.add(R.drawable.vsitpreview);
        mImageUrls.add(R.drawable.plankpreview);
        mImageUrls.add(R.drawable.tstablepreview);
        videoNames.add("V-sit");
        videoNames.add("Plank");
        videoNames.add("T-Stabilization");
    }
/*
    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        VideoRecycleAdaptor adapter = new VideoRecycleAdaptor(this, mNames, mImageUrls, mduration);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    */
}