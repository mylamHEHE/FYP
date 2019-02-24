package com.example.user.smartfitnesstrainer.Main.Profile;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.smartfitnesstrainer.Main.MainActivity;
import com.example.user.smartfitnesstrainer.Main.Splash.PrefKey;
import com.example.user.smartfitnesstrainer.Main.UserModel.User;
import com.example.user.smartfitnesstrainer.Main.UserModel.UserClient;
import com.example.user.smartfitnesstrainer.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessagesFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "MessagesFragment";
    public Retrofit.Builder builder = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.1.180:5000/");
    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);
    ImageButton setting;
    RecyclerView rv;
    List<UserRecyclerItem> uri;
    RecyclerItemAdapter ria;
    PrefKey prefKey;
    List<UserProfile.PlayerHistory> playerHistories = new ArrayList<>();
    TextView first_name;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        uri = new ArrayList<>();

        //tomilia: database get text initialize
        first_name = view.findViewById(R.id.first_name);


        prefKey = new PrefKey(getActivity().getApplicationContext());
        getProfile();
        rv = (RecyclerView)view.findViewById(R.id.rvid);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        uri.add(new UserRecyclerItem("'Cardio' Series","05/11/2018"));
        uri.add(new UserRecyclerItem("'Push-up' Series","05/11/2018"));
        uri.add(new UserRecyclerItem("'Heavyweight' Series","05/11/2018"));
        uri.add(new UserRecyclerItem("'Upperbody' Series","05/11/2018"));
        ria = new RecyclerItemAdapter(getContext(),playerHistories);
        rv.setAdapter(ria);
        rv.setNestedScrollingEnabled(true);

        setting = view.findViewById(R.id.setting);
        setting.setImageResource(R.drawable.setting);
        setting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity(),"Setting is clicked.",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.d("msgfragment","onrus");
            //相当于Fragment的onResume
        } else {
            //相当于Fragment的onPause
        }
    }
    private void getProfile(){
        Call<UserProfile> call = userClient.getProfile("application/x-www-form-urlencoded","Bearer "+prefKey.getAccess_token());
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful()) {
                    try {
                        //tomilia: get Profile stats

                           first_name.setText(response.body().getFirst_name()+" "+response.body().getLast_name());
                            playerHistories.clear();
                           playerHistories.addAll(response.body().getPlayer_history());
                            Log.d("playh",playerHistories.get(0).getName());
                            ria.notifyDataSetChanged();
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
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Toast.makeText(getActivity(),"fail",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
