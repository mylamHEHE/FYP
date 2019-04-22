package com.example.user.smartfitnesstrainer.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.user.smartfitnesstrainer.Main.BLE.BluetoothLeDevice;
import com.example.user.smartfitnesstrainer.Main.BLE.ViseBle;
import com.example.user.smartfitnesstrainer.Main.Profile.UserProfile;
import com.example.user.smartfitnesstrainer.Main.Splash.PrefKey;
import com.example.user.smartfitnesstrainer.Main.Splash.Refresh;
import com.example.user.smartfitnesstrainer.Main.Splash.StartLoginActivity;
import com.example.user.smartfitnesstrainer.Main.UserModel.UserClient;
import com.example.user.smartfitnesstrainer.R;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.user.smartfitnesstrainer.Main.HomeActivity.URL_Base;

//import com.example.user.smartfitnesstrainer.Utils.BottomNavigationViewHelper;
public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1000;
    private static final String TAG = "MainActivity";
    private static final int ACTIVITY_NUM = 0;
    PrefKey prefKey;
    public Retrofit.Builder builder = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(URL_Base);
    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);
    private Context mContext = MainActivity.this;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefKey = new PrefKey(this);
        setContentView(R.layout.homeactivity);

        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: starting. ");
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){

                //fix: Intent to login if not login else direct

                user_valid();

            }
        },SPLASH_TIME_OUT);
    }
    private void user_valid(){
        Log.d("prepre token",prefKey.getAccess_token());
        Call<Refresh> call = userClient.getAccess_token("application/x-www-form-urlencoded","Bearer "+prefKey.getRefresh_token());
        call.enqueue(new Callback<Refresh>() {
            @Override
            public void onResponse(Call<Refresh> call, Response<Refresh> response) {
                if (response.isSuccessful()) {

                        Log.d("new token", "vsdv");
                        prefKey.saveToken(response.body().getAccess_token(),prefKey.getRefresh_token());
                    Intent loginIntent = new Intent(mContext, HomeActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
                else{
                    Log.d("newfail token", "vsd");
                    Intent loginIntent = new Intent(mContext, StartLoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<Refresh> call, Throwable t) {
                Log.d("newfailure token","vsd");
                Intent loginIntent = new Intent(mContext, StartLoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });
    }
}