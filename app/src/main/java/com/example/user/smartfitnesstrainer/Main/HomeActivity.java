package com.example.user.smartfitnesstrainer.Main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.user.smartfitnesstrainer.Main.BLE.BluetoothLeDevice;
import com.example.user.smartfitnesstrainer.Main.BLE.ViseBle;
import com.example.user.smartfitnesstrainer.Main.Bluetooth_reserve.CameraFragment;
import com.example.user.smartfitnesstrainer.Main.Profile.MessagesFragment;
import com.example.user.smartfitnesstrainer.Main.SectionsPagerAdapter;
import com.example.user.smartfitnesstrainer.Main.exercise_selection_page.MainFragment;
import com.example.user.smartfitnesstrainer.R;

public class HomeActivity extends AppCompatActivity{
    SectionsPagerAdapter adapter;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViewPager();
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    private void setupViewPager(){
        adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CameraFragment());          //index 0
        adapter.addFragment(new MainFragment());             //index 1
        adapter.addFragment(new MessagesFragment());        //index 2
        ViewPager viewPager = (ViewPager)findViewById(R.id.container);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_camera);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_android);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_arrow);
    }

}

