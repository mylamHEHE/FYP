package com.example.user.smartfitnesstrainer.Main.DetailVideo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.user.smartfitnesstrainer.R;

import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity {
    RecyclerView rv;
    ExerciseListAdapter ela;
    ArrayList <String> temp = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        rv = findViewById(R.id.rv);
        temp.add("Inchworm");
        temp.add("Power Skip");
        temp.add("Uppercut");
        temp.add("Mountain Climber Twist");
        ela = new ExerciseListAdapter(this,temp);
        rv.setAdapter(ela);
        LinearLayoutManager llmm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(llmm);
    }

}
