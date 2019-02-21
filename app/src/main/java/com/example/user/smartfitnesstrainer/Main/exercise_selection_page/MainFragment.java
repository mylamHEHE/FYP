package com.example.user.smartfitnesstrainer.Main.exercise_selection_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.user.smartfitnesstrainer.Main.Splash.PrefKey;
import com.example.user.smartfitnesstrainer.Main.UserModel.UserClient;
import com.example.user.smartfitnesstrainer.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainFragment extends Fragment {
    /*private static final String TAG = "HomeFragment";
    CardView sport;
    CardView battle;
    CardView setting;
    CardView timetable;
    CardView video;
    CardView reserve_2;*/
    private static final String TAG = "MessagesFragment";
    public Retrofit.Builder builder = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://10.0.2.2:5000/");

    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);
    PrefKey prefKey;

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mduration = new ArrayList<>();
    private void initImageBitmaps(final View view){
        //Playlist preparing
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");
        Call<List<Playlist>> call = userClient.getPlaylist("application/x-www-form-urlencoded","Bearer "+prefKey.getAccess_token());
        call.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                if (response.isSuccessful()) {
                    try {
                        //tomilia: get Profile stats
                        //jsonArray translate[0]
                        List<Playlist> playlists = response.body();
                        initRecyclerView(view,playlists);
                        //
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    //  Toast.makeText(getActivity(),response.code(),Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {
                Toast.makeText(getActivity(),"fail",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initRecyclerView(View view, List<Playlist> playlists){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = view.findViewById(R.id.recyclerv_view);
        //Change videorecycler to playlist obj
        VideoRecycleAdaptor adapter = new VideoRecycleAdaptor(getContext(), playlists);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.layout_full_video,container,false);
        prefKey = new PrefKey(getActivity().getApplicationContext());
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
