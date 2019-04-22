package com.example.user.smartfitnesstrainer.Main.Splash;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefKey {

    private Context context;

    public PrefKey(Context context){
        this.context = context;
    }

    public void saveToken(String access_token,String renew_token) {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("access_token", access_token);
        editor.putString("refresh_token", renew_token);
        //tomilia: renewal
        editor.apply();

    }
    public String getAccess_token()
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString("access_token","");
    }
    public String getRefresh_token()
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString("refresh_token","");
    }

}