package com.example.user.smartfitnesstrainer.Main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.user.smartfitnesstrainer.R;

import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity {
    private static final String TAG = "VideoActivity";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mduration = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_full_video);
        initImageBitmaps();
    }
    /* Called when the user taps the Send button
    public void sendMessage(View view) {
        // Do something in response to button
    }*/
    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImageUrls.add("https://cdn.liftbrands.com/snap/uploads/location_tour/2018/may/30/Urban-Fitness-Club-Battle-Ropes_ori.jpg");
        mNames.add("Core Workout");
        mduration.add("");

        mImageUrls.add("https://cdn-ami-drupal.heartyhosting.com/sites/muscleandfitness.com/files/styles/full_node_image_1090x614/public/media/rowing_2.jpg?itok=yVeSICMt&timestamp=1484950270");
        mNames.add("Cardio");
        mduration.add("");

        mImageUrls.add("https://www.dwfitnessfirst.com/media/1944/20140418_fitnessfirst_fatburn-extreme.jpg?crop=0.0653935185185186,0,0,0&cropmode=percentage&width=1500&rnd=131547870940000000");
        mNames.add("Fat-Burning");
        mduration.add("");

        mImageUrls.add("http://watchfit.com/wp-content/uploads/2016/04/male-and-female-workout_1-1024x548.jpg");
        mNames.add("Upper Body Building");
        mduration.add("");



        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        VideoRecycleAdaptor adapter = new VideoRecycleAdaptor(this, mNames, mImageUrls, mduration);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}