package com.example.user.smartfitnesstrainer.Main.Profile;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.smartfitnesstrainer.Main.GraphActivity;
import com.example.user.smartfitnesstrainer.Main.MainActivity;
import com.example.user.smartfitnesstrainer.Main.ResentEmailActivity;
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

import static com.example.user.smartfitnesstrainer.Main.HomeActivity.URL_Base;
import static java.security.AccessController.getContext;

public class MessagesFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "MessagesFragment";
    public Retrofit.Builder builder = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(URL_Base);
    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);
    ImageButton setting;
    RecyclerView rv;
    List<UserRecyclerItem> uri;
    RecyclerItemAdapter ria;
    PrefKey prefKey;
    List<UserProfile.PlayerHistory> playerHistories = new ArrayList<>();
    TextView first_name;

    FloatingActionButton myFab;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
//        RelativeLayout rl = (RelativeLayout)view.findViewById(R.id.bottom_rl);
//
//        ImageView iv = new ImageView(view.getContext());
//
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(30, 40);
//        params.rightMargin = 0;
//        params.bottomMargin = 0;
//        rl.addView(iv, params);
        uri = new ArrayList<>();

        //tomilia: database get text initialize
        first_name = view.findViewById(R.id.first_name);
        myFab = (FloatingActionButton) view.findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ResentEmailActivity.class);
                startActivity(intent);
            }
        });

        prefKey = new PrefKey(getActivity().getApplicationContext());
        getProfile();
        rv = (RecyclerView)view.findViewById(R.id.rvid);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        uri.add(new UserRecyclerItem("'Cardio' Series","05/11/2018"));
        uri.add(new UserRecyclerItem("'Push-up' Series","05/11/2018"));
        uri.add(new UserRecyclerItem("'Heavyweight' Series","05/11/2018"));
        uri.add(new UserRecyclerItem("'Upperbody' Series","05/11/2018"));
        ria = new RecyclerItemAdapter(getActivity(),getContext(),playerHistories);
        rv.setAdapter(ria);
        rv.setNestedScrollingEnabled(true);
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
                        Log.d("playh",String.valueOf(playerHistories.get(0).getRef_id()));
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