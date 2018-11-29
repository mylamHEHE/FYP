package com.example.user.smartfitnesstrainer.Main;

import android.content.Context;
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

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class HomeActivity extends AppCompatActivity{
    SectionsPagerAdapter adapter;
    TabLayout tabLayout;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
    /*
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

*/
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

        tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();

        tabLayout.getTabAt(0).setIcon(R.drawable.bluetooth);
        tabLayout.getTabAt(1).setIcon(R.drawable.muscle);
        tabLayout.getTabAt(2).setIcon(R.drawable.user);
    }

}

