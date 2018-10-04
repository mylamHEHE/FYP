package com.example.user.smartfitnesstrainer.Main.exercise_selection_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.user.smartfitnesstrainer.Main.VideoActivity;
import com.example.user.smartfitnesstrainer.R;

public class MainFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    CardView sport;
    CardView battle;
    CardView setting;
    CardView timetable;
    CardView video;
    CardView reserve_2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        sport = view.findViewById(R.id.sport);
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
                Toast.makeText(getActivity(),"Reserved_2 is clicked.",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

}
