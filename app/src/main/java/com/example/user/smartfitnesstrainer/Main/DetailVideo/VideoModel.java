package com.example.user.smartfitnesstrainer.Main.DetailVideo;

import android.net.Uri;

import static com.example.user.smartfitnesstrainer.Main.HomeActivity.URL_Base;

public class VideoModel {
    String videoUrl;

    public VideoModel(String videoUrl) {
        this.videoUrl = URL_Base+"static/"+videoUrl+".html";
    }

}
