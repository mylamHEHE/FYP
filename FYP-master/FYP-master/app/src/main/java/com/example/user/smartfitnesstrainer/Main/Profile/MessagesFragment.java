package com.example.user.smartfitnesstrainer.Main.Profile;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.smartfitnesstrainer.R;

public class MessagesFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "MessagesFragment";
    TextView bmi;
    TextView github;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_messages,container,false);
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
        return view;
    }
}
