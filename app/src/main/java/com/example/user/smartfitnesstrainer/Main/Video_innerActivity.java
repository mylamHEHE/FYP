package com.example.user.smartfitnesstrainer.Main;

<<<<<<< HEAD
import android.net.Uri;
import android.os.Bundle;
=======
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
>>>>>>> 4077906cc780c6d049422daab0401d96bb057a0d
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.user.smartfitnesstrainer.R;

<<<<<<< HEAD
import java.util.ArrayList;

public class Video_innerActivity extends AppCompatActivity {
    private static final String TAG = "Video_innerActivity";

    //vars
=======
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.transform.ErrorListener;

public class Video_innerActivity extends AppCompatActivity {
    private static final String TAG = "Video_innerActivity";

    //varss
>>>>>>> 4077906cc780c6d049422daab0401d96bb057a0d
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mduration = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_full_video_inner);

        VideoView videoView = findViewById(R.id.video);
<<<<<<< HEAD
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video0;
=======
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video;
        //String videoPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/video0.mp4";;
>>>>>>> 4077906cc780c6d049422daab0401d96bb057a0d
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController((this));
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
<<<<<<< HEAD

        //initImageBitmaps();
    }
=======
        mediaController.setPadding(0, 0, 0, 827);
        mediaController.isShowing();

        //initImageBitmaps();

    }

>>>>>>> 4077906cc780c6d049422daab0401d96bb057a0d
    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImageUrls.add("https://previews.123rf.com/images/martialred/martialred1507/martialred150700874/42615284-video-clip-play-line-art-icon-for-apps-and-websites.jpg");
        mNames.add("Havasu Falls");
        mduration.add("3:00");

        mImageUrls.add("https://previews.123rf.com/images/martialred/martialred1507/martialred150700874/42615284-video-clip-play-line-art-icon-for-apps-and-websites.jpg");
        mNames.add("Trondheim");
        mduration.add("1:23");

        mImageUrls.add("https://previews.123rf.com/images/martialred/martialred1507/martialred150700874/42615284-video-clip-play-line-art-icon-for-apps-and-websites.jpg");
        mNames.add("Portugal");
        mduration.add("4:56");

        mImageUrls.add("https://previews.123rf.com/images/martialred/martialred1507/martialred150700874/42615284-video-clip-play-line-art-icon-for-apps-and-websites.jpg");
        mNames.add("Rocky Mountain");
        mduration.add("7:00");


        mImageUrls.add("https://previews.123rf.com/images/martialred/martialred1507/martialred150700874/42615284-video-clip-play-line-art-icon-for-apps-and-websites.jpg");
        mNames.add("Mahahual");
        mduration.add("0:30");

        mImageUrls.add("https://previews.123rf.com/images/martialred/martialred1507/martialred150700874/42615284-video-clip-play-line-art-icon-for-apps-and-websites.jpg");
        mNames.add("Frozen Lake");
        mduration.add("4:56");

        mImageUrls.add("https://previews.123rf.com/images/martialred/martialred1507/martialred150700874/42615284-video-clip-play-line-art-icon-for-apps-and-websites.jpg");
        mNames.add("White Sands Desert");
        mduration.add("1:23");

        mImageUrls.add("https://previews.123rf.com/images/martialred/martialred1507/martialred150700874/42615284-video-clip-play-line-art-icon-for-apps-and-websites.jpg");
        mNames.add("Austrailia");
        mduration.add("3:00");

        mImageUrls.add("https://previews.123rf.com/images/martialred/martialred1507/martialred150700874/42615284-video-clip-play-line-art-icon-for-apps-and-websites.jpg");
        mNames.add("Washington");
        mduration.add("0:30");

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