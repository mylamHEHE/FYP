package com.example.user.smartfitnesstrainer.Main.Profile;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.smartfitnesstrainer.R;

import java.util.ArrayList;
import java.util.List;

public class MessagesFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "MessagesFragment";
    TextView bmi;
    TextView github;
    RecyclerView rv;
    List<UserRecyclerItem> uri;
    RecyclerItemAdapter ria;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        uri = new ArrayList<>();
        rv = (RecyclerView)view.findViewById(R.id.rvid);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        uri.add(new UserRecyclerItem("Finishing 'Cardio' Series","08/07/2018"));
        uri.add(new UserRecyclerItem("Finishing 'Push-up' Series","08/07/2018"));
        uri.add(new UserRecyclerItem("Finishing 'Heavyweight' Series","08/07/2018"));
        uri.add(new UserRecyclerItem("abcfeieh","08/07/2018"));
        ria = new RecyclerItemAdapter(getContext(),uri);
        rv.setAdapter(ria);
        rv.setNestedScrollingEnabled(false);
        /*
        bmi = view.findViewById(R.id.textEditProfile);
        bmi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity(),"BMI is clicked.",Toast.LENGTH_LONG).show();
            }
        });
        github = view.findViewById(R.id.website);
        github.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity(),"BMI is clicked.",Toast.LENGTH_LONG).show();
            }
        });
        */
        return view;
    }
}
