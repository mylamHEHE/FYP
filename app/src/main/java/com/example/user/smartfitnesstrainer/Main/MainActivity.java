package com.example.user.smartfitnesstrainer.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.user.smartfitnesstrainer.R;
//import com.example.user.smartfitnesstrainer.Utils.BottomNavigationViewHelper;
public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1000;
    private static final String TAG = "MainActivity";
    private static final int ACTIVITY_NUM = 0;

    private Context mContext = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);
        Log.d(TAG, "onCreate: starting. ");
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent homeIntent = new Intent(mContext, HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}