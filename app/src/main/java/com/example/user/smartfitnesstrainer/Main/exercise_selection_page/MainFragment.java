package com.example.user.smartfitnesstrainer.Main.exercise_selection_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.user.smartfitnesstrainer.Main.VideoActivity;
import com.example.user.smartfitnesstrainer.Main.VideoRecycleAdaptor;
import com.example.user.smartfitnesstrainer.Main.Video_innerActivity;
import com.example.user.smartfitnesstrainer.R;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    /*private static final String TAG = "HomeFragment";
    CardView sport;
    CardView battle;
    CardView setting;
    CardView timetable;
    CardView video;
    CardView reserve_2;*/

    private static final String TAG = "VideoActivity";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mduration = new ArrayList<>();
    private void initImageBitmaps(View view){
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

        initRecyclerView(view);
    }

    private void initRecyclerView(View view){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = view.findViewById(R.id.recyclerv_view);
        VideoRecycleAdaptor adapter = new VideoRecycleAdaptor(getContext(), mNames, mImageUrls, mduration);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.layout_full_video,container,false);
        initImageBitmaps(view);
    /* Called when the user taps the Send button
    public void sendMessage(View view) {
        // Do something in response to button
    }*/

        //Intent homeIntent = new Intent(getContext(),VideoActivity.class);
        //startActivity(homeIntent);
        /*sport = view.findViewById(R.id.sport);
        sport.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity(),"Sport is clicked.",Toast.LENGTH_LONG).show();
            }
        });
        battle = view.findViewById(R.id.battle);
        battle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity(),"Battle is clicked.",Toast.LENGTH_LONG).show();
            }
        });
        setting = view.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity(),"Setting is clicked.",Toast.LENGTH_LONG).show();
            }
        });
        timetable = view.findViewById(R.id.timetable);
        timetable.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity(),"Timetable is clicked.",Toast.LENGTH_LONG).show();
            }
        });
        video = view.findViewById(R.id.video);
        video.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Toast.makeText(getActivity(),"video is clicked.",Toast.LENGTH_LONG).show();
                Intent homeIntent = new Intent(getContext(),VideoActivity.class);
                startActivity(homeIntent);
            }
        });
        reserve_2 = view.findViewById(R.id.reserve_2);
        reserve_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Toast.makeText(getActivity(),"Reserved_2 is clicked.",Toast.LENGTH_LONG).show();
                Intent homeIntent = new Intent(getContext(),Video_innerActivity.class);
                startActivity(homeIntent);
            }
        });*/

        return view;
    }

}
