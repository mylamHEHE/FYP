package com.example.user.smartfitnesstrainer.Main.DetailVideo;

import android.content.Context;
import android.util.Log;

import com.example.user.smartfitnesstrainer.Main.Splash.PrefKey;
import com.example.user.smartfitnesstrainer.Main.UserModel.UserClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.user.smartfitnesstrainer.Main.HomeActivity.URL_Base;

public class Result_Maker {
    public Retrofit.Builder builder = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(URL_Base);
    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);
    ArrayList<Integer> score;
    int num_of_exercise;
    ArrayList<ArrayList<Integer>> resultset;
    String name_of_exercise;
    PrefKey prefKey;
    /*
    To make json
    1.get arraylist of saved angle
    2.get score
    3.comment????
     */
    public Result_Maker(Context context, String name_of_exercise, int num_of_exercise, ArrayList<Integer> score, ArrayList<ArrayList<Integer>> resultset) {
        this.score = score;
        this.num_of_exercise=num_of_exercise;
        this.resultset = resultset;
        this.name_of_exercise = name_of_exercise;
        prefKey = new PrefKey(context);
    }
    public void makeJSON(){
        JSONObject ret_obj=new JSONObject();
        JSONArray score_json = new JSONArray(score);
        JSONArray result_json = new JSONArray(resultset);
        try {
            ret_obj.put("user_id",30);

            ret_obj.put("name",name_of_exercise);
            ret_obj.put("total_exercise",num_of_exercise);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            ret_obj.put("date",formatter.format(date));
            ret_obj.put("score",score_json);
            ret_obj.put("result",result_json);

            Log.d("stringify",ret_obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            post_result(ret_obj);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void post_result(JSONObject ret_obj) throws InterruptedException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), ret_obj.toString());
        Call<ResponseBody> example = userClient.postRecord("application/json","Bearer "+prefKey.getAccess_token(),body);

        example.enqueue(
                new Callback<ResponseBody>() {
                    @Override public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                    {
                        try
                        {
                            Log.d("resbo",response.body().string());
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t)
                    {
                        Log.d("fail","fail");
                    }
                }
                );

    }
}
