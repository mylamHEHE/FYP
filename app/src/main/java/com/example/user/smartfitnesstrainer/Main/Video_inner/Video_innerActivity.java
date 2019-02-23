package com.example.user.smartfitnesstrainer.Main.Video_inner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

//import com.example.user.smartfitnesstrainer.Main.DetailVideo.ExerciseActivity;
import com.example.user.smartfitnesstrainer.Main.DetailVideo.ExerciseActivity;
import com.example.user.smartfitnesstrainer.Main.Splash.PrefKey;
import com.example.user.smartfitnesstrainer.Main.UserModel.UserClient;
import com.example.user.smartfitnesstrainer.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Video_innerActivity extends AppCompatActivity {
    private static final String TAG = "Video_innerActivity";
ImageView image;
RecyclerView rv,videorv;
Video_inner_desp_adapter vida;
Video_inner_playlist_adapter vida2;
TextView name;
TextView difficulty;
TextView sensor;
TextView age;
Button start;
    PrefKey prefKey;
    //varss
    public Retrofit.Builder builder = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://10.0.2.2:5000/");

    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Integer> mImageUrls = new ArrayList<>();
    private ArrayList<String> mduration = new ArrayList<>();
    private ArrayList<Exercise_Format> exercise_playlist = new ArrayList<>();
    private ArrayList<String> videoNames = new ArrayList<>();
    private ArrayList<String> videoDuration = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_full_video_inner);
        name = findViewById(R.id.name);
        difficulty = findViewById(R.id.difficulty);
        sensor = findViewById(R.id.sensor);
        age = findViewById(R.id.age);

        prefKey = new PrefKey(getApplicationContext());
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
            vida2 = new Video_inner_playlist_adapter(getApplicationContext(),exercise_playlist, 1);
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
    private void MapPlaylist(JSONArray exercise_list) throws JSONException {
        for (int ite=0; ite<exercise_list.length();ite++)
        {
            Exercise_Format exercise_format =  new Gson().fromJson(exercise_list.getString(ite), Exercise_Format.class);
            exercise_playlist.add(exercise_format);
        }
        vida2.notifyDataSetChanged();
    }
    private void initBasicDesp(){

        //Retrofit get request
        //playlist/para
        Call<ResponseBody> call = userClient.getPlaylistWithid("application/x-www-form-urlencoded","Bearer "+prefKey.getAccess_token(),1);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    try {

                        //tomilia: get Profile stats
                        //jsonArray translate[0]


                        final JSONObject obj = new JSONObject(response.body().string());

                        final JSONObject item_id = new JSONObject(obj.getString("item_id"));
                        Log.d("iteem_id",item_id.getString("name"));
                        name.setText(item_id.getString("name"));
                        difficulty.setText(item_id.getString("difficulty"));
                        sensor.setText(item_id.getString("equipment"));
                        age.setText(item_id.getString("agegroup"));
                        mduration.add(item_id.getString("description"));
                        MapPlaylist(obj.getJSONArray("exp_list"));
                        Toast.makeText(getApplicationContext(),response.body().string(),Toast.LENGTH_SHORT).show();
                        vida.notifyDataSetChanged();

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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_SHORT).show();
            }
        });
        mNames.add("Description");
        //mNames.add("Equipment");

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