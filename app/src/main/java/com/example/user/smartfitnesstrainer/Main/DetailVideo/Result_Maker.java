package com.example.user.smartfitnesstrainer.Main.DetailVideo;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Result_Maker {
    ArrayList<Integer> score;
    ArrayList<ArrayList<Integer>> resultset;
    /*
    To make json
    1.get arraylist of saved angle
    2.get score
    3.comment????
     */
    public Result_Maker(ArrayList<Integer> score,ArrayList<ArrayList<Integer>> resultset) {
        this.score = score;
        this.resultset = resultset;
    }
    public void makeJSON(){
        JSONObject ret_obj=new JSONObject();
        JSONArray score_json = new JSONArray(score);
        JSONArray result_json = new JSONArray(resultset);
        try {
            ret_obj.put("user_id",29);
            ret_obj.put("score",score_json);
            ret_obj.put("result",result_json);
            Log.d("stringify",ret_obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
