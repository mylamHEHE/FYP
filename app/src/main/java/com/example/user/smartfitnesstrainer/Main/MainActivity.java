package com.example.user.smartfitnesstrainer.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.user.smartfitnesstrainer.Main.BLE.BluetoothLeDevice;
import com.example.user.smartfitnesstrainer.Main.BLE.ViseBle;
import com.example.user.smartfitnesstrainer.Main.Splash.StartLoginActivity;
import com.example.user.smartfitnesstrainer.R;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

//import com.example.user.smartfitnesstrainer.Utils.BottomNavigationViewHelper;
public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1000;
    private static final String TAG = "MainActivity";
    private static final int ACTIVITY_NUM = 0;

    private Context mContext = MainActivity.this;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.homeactivity);

        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: starting. ");
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){

                //fix: Intent to login if not login else direct
                Intent loginIntent = new Intent(mContext, StartLoginActivity.class);
                startActivity(loginIntent);
                finish();

            }
        },SPLASH_TIME_OUT);
    }
}